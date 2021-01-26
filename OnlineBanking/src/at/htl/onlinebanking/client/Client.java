package at.htl.onlinebanking.client;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import at.htl.onlinebanking.model.Buchung;
import at.htl.onlinebanking.model.Buchungsart;
import at.htl.onlinebanking.model.GiroKonto;
import at.htl.onlinebanking.model.Konto;
import at.htl.onlinebanking.model.Kunde;
import at.htl.onlinebanking.model.SparKonto;

public class Client {
	EntityManagerFactory emf;
	EntityManager em;
	EntityTransaction tx;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// (new Client()).intitDB();
		//(new Client()).examplesPersistEntityT001();
		(new Client()).examplesLoadEntityT001();	
	}

	void intitDB() {		
		// execute with: javax.persistence.schema-generation.database.action drop-and-create
		String persistenceUnitName = "OnlineBanking";
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);

		try {			
			em = emf.createEntityManager();
			tx = em.getTransaction();
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
			em.close();
			emf.close();
		}
	}
	
	void examplesPersistEntityT001() {
		// remove before execution:
		// javax.persistence.schema-generation.database.action drop-and-create
		
		// remove before execution:
		// delete from ONLINEBANKING.OBKunde where KUNDNR LIKE '10010%';
		
		String persistenceUnitName = "OnlineBanking";
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);

		try {			
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			
			// state transient
			Kunde kunde100100 = Kunde.builder().setKundennummer("100100").setVorname("Eva0").setNachname("Maria0").build();
			Kunde kunde100101 = Kunde.builder().setKundennummer("100101").setVorname("Eva1").setNachname("Maria1").build();
			Kunde kunde100102 = Kunde.builder().setKundennummer("100102").setVorname("Eva2").setNachname("Maria2").build();
			Kunde kunde100103 = Kunde.builder().setKundennummer("100103").setVorname("Eva3").setNachname("Maria3").build();
			Kunde kunde100104 = Kunde.builder().setKundennummer("100104").setVorname("Eva4").setNachname("Maria4").build();
			Kunde kunde100105 = Kunde.builder().setKundennummer("100105").setVorname("Eva5").setNachname("Maria5").build();
			
			//  state managed
			em.persist(kunde100100);
			em.persist(kunde100101);
			em.persist(kunde100102);
			
			em.flush();	// synchronize database
						// key generated value:  IDENTY -> primary key auto generated
			
			// attention: to read uncommitted rows change isolation level to READ UNCOMMITTED
			// SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
			// select * from OBKunde;
			// change to none dirty read
			// - COMMIT;
			// - SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ;
			// - auto commit
			
			em.persist(kunde100103);
			em.persist(kunde100104);
			em.persist(kunde100105);

			// tx.begin(); same result, commit point is relevant 
			tx.commit();	
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			if( tx != null && tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
			emf.close();
		}	
	}

	void examplesLoadEntityT001() {
		// remove before execution:
		// delete from ONLINEBANKING.OBKunde where KUNDNR LIKE  '10020%';

		String persistenceUnitName = "OnlineBanking";
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);

		try {			
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			// sample code
			Kunde kunde100200 = Kunde.builder()
					.setKundennummer("100200")
					.setVorname("Martin")
					.setNachname("Huber")
					.build();
			em.persist(kunde100200);
			tx.commit();
			
			tx.begin();
			// null if not found
            Kunde kunde = em.find(Kunde.class, kunde100200.getId()); 
            
            System.out.println(kunde.toString());
            kunde.setNachname("Maier");	// force update
            System.out.println(kunde.toString());
			tx.commit();
			
			tx.begin();
			// javax.persistence.EntityNotFoundException
			Kunde kundeProxy = em.getReference(Kunde.class, kunde100200.getId());
			// Kunde kundeProxyNotFound = em.getReference(Kunde.class, "xxx"); 
			
            System.out.println(kundeProxy.toString());
            kundeProxy.setNachname("Huber");	// force update
            System.out.println(kundeProxy.toString());
			tx.commit();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			if( tx != null && tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
			emf.close();
		}	
	}
	
	void examplesGetreferenceT001() {
		String persistenceUnitName = "OnlineBanking";
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);

		try {			
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			// sample code
			
			tx.commit();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			if( tx != null && tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
			emf.close();
		}	
	}
	
	void examplesPersistsTxxx() {
		String persistenceUnitName = "OnlineBanking";
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);

		try {			
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			// sample code
			
			tx.commit();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			if( tx != null && tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
			emf.close();
		}	
	}
	
	void buchen(Konto fromKonto, Konto toKonto) {
		Buchung buchungAuftraggeber = new Buchung(new BigDecimal(500), Buchungsart.SOLL, "Sparen");
		buchungAuftraggeber.setNummer(1);
		fromKonto.addBuchug(buchungAuftraggeber);
		
		Buchung buchungEmpfaenger = new Buchung(new BigDecimal(500), Buchungsart.HABEN, "Sparen");
		buchungEmpfaenger.setNummer(1);
		toKonto.addBuchug(buchungEmpfaenger);
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
