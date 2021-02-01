package at.htl.onlinebanking.client;

import javax.persistence.PersistenceException;
import at.htl.onlinebanking.model.Kunde;

public class ClientSampleLoadEntity extends ClientCommon {

	public static void main(String[] args) {
		(new ClientSampleLoadEntity()).execute();	
	}

	void execute() {
		// remove before execution:
		// javax.persistence.schema-generation.database.action drop-and-create
		
		// remove entities before execution:
		// delete from ONLINEBANKING.OBKunde where KUNDNR LIKE '10010%';

		try {			
			openDbConnection();
			tx.begin();
			// jpa state transient
			Kunde kunde100200 = Kunde.builder()
					.setKundennummer("100200")
					.setVorname("Martin")
					.setNachname("Huber")
					.build();
			em.persist(kunde100200); // jpa state managed
			
            em.flush(); // force db write uncommitted, readable with uncommitted_read
			
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
			Kunde kundeProxyNotFound = em.getReference(Kunde.class, "xxx"); 
			
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
			closeDbConnection();
		}	
	}
}
