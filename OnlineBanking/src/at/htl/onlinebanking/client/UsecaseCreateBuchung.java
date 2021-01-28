package at.htl.onlinebanking.client;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import at.htl.onlinebanking.model.Buchung;
import at.htl.onlinebanking.model.Buchungsart;
import at.htl.onlinebanking.model.Konto;

public class UsecaseCreateBuchung extends ClientCommon {
	
	static private class BuchungData {
		public BigInteger fromKonto;
		public BigInteger toKonto;
		public BigDecimal value = new BigDecimal(0);
		public String comment = "";
	}
	
	public static void main(String[] args) {
		BuchungData b = new UsecaseCreateBuchung.BuchungData();
		b.fromKonto = new BigInteger("50089299610");
		b.toKonto = new BigInteger("90089299610");
		b.value = new BigDecimal(500);
		b.comment = "Sparen";
	
		(new UsecaseCreateBuchung()).execute(b);	
	}
	
	void execute(BuchungData buchung) {
		try {
			openDbConnection();
			tx.begin();
			
			TypedQuery<Konto> tqFrom = em.createQuery("select k from Konto k where k.kontonummer = :kto", Konto.class);
			tqFrom.setParameter("kto", buchung.fromKonto);
			Konto fromKonto = tqFrom.getSingleResult();
			
			TypedQuery<Konto> tqTo = em.createQuery("select k from Konto k where k.kontonummer = :kto", Konto.class);
			tqTo.setParameter("kto", buchung.toKonto);
			Konto toKonto = tqTo.getSingleResult();
			
			String sql = "select coalesce(max(b.nummer),0) from OBBuchung b where b.konto = " + buchung.fromKonto;			
			Query queryMaxCounterFrom = em.createNativeQuery(sql);
			Long longMaxCounterFrom = (Long)queryMaxCounterFrom.getSingleResult();
			Integer maxCounterFrom = Integer.valueOf(longMaxCounterFrom.toString());
			maxCounterFrom++;
			
			String sqlTo = "select coalesce(max(b.nummer),0) from OBBuchung b where b.konto = " + buchung.toKonto;			
			Query queryMaxCounterTo = em.createNativeQuery(sqlTo);
			Long longMaxCounterTo = (Long)queryMaxCounterTo.getSingleResult();
			Integer maxCounterTo = Integer.valueOf(longMaxCounterTo.toString());
			maxCounterTo++;
			
			Buchung buchungAuftraggeber = new Buchung(buchung.value, Buchungsart.SOLL, buchung.comment);
			buchungAuftraggeber.setNummer(maxCounterFrom);
			fromKonto.addBuchug(buchungAuftraggeber);
		
			Buchung buchungEmpfaenger = new Buchung(buchung.value, Buchungsart.HABEN, buchung.comment);
			buchungEmpfaenger.setNummer(maxCounterTo);
			toKonto.addBuchug(buchungEmpfaenger);
			tx.commit();	
		
		} catch (PersistenceException e) {
			e.printStackTrace();
			if( tx != null && tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		} finally {
			closeDbConnection();
		}
	}
}
