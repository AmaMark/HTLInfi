package at.htl.onlinebanking.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class ClientCommon {
	final protected String persistenceUnitName = "OnlineBanking";
	protected EntityManagerFactory emf;
	protected EntityManager em; 
	protected EntityTransaction tx;
	
	protected void openDbConnection() {
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}
	protected void closeDbConnection() {
		em.close();
		emf.close();
	}
}
