package at.htl.onlinebanking.client;

import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import at.htl.onlinebanking.model.Kunde;

public class ClientSamplePersist extends ClientCommon {

	public static void main(String[] args) {
		(new ClientSamplePersist()).execute();	
	}
	
	void execute() {
		// remove before execution:
		// javax.persistence.schema-generation.database.action drop-and-create
		
		// remove entities before execution:
		// delete from ONLINEBANKING.OBKunde where KUNDNR LIKE '10010%';
		
		try {			
			openDbConnection();
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
			closeDbConnection();
		}	
	}
}
