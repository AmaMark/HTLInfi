package at.htl.onlinebanking.client;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUtil;

import at.htl.onlinebanking.model.Buchung;
import at.htl.onlinebanking.model.Buchungsart;
import at.htl.onlinebanking.model.GiroKonto;
import at.htl.onlinebanking.model.Konto;
import at.htl.onlinebanking.model.Kunde;
import at.htl.onlinebanking.model.SparKonto;

public class Client extends ClientCommon {
	
	public static void main(String[] args) {
		(new Client()).execute();
	}

	void execute() {		
		// execute with: javax.persistence.schema-generation.database.action drop-and-create
		try {			
			openDbConnection();
			tx.begin();
			
			Kunde NewKunde = Kunde.builder()
					.setKundennummer("1023030")
					.setVorname("Markus")
					.setNachname("Amann")
					.build();
			
			GiroKonto newGiroKonto = GiroKonto.builder()
					.setKontonummer(new BigInteger("50089299610"))
					.setKontobezeichnung("Gehaltskonto")
					.setRahmen(new BigDecimal(5000))
					.setZinssatzHaben(new BigDecimal(0.005))
					.setZinssatzSoll(new BigDecimal(13))
					.build();
			
			NewKunde.addKonto(newGiroKonto);
			
			SparKonto newSparKonto = SparKonto.builder()
					.setKontonummer(new BigInteger("90089299610"))
					.setKontobezeichnung("Mein Sparbuch")
					.setZinssatzHaben(new BigDecimal(0.005))
					.build();
			
			NewKunde.addKonto(newSparKonto);
			
			createEntity(NewKunde);
			System.out.println(NewKunde.toString());
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
		
	public <T> void createEntity(T entity) {
		try {			
			em.persist(entity);
		} catch( RuntimeException e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}
}
