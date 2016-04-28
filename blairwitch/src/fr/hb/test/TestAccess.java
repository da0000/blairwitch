package fr.hb.test;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.hb.model.Access;
import fr.hb.service.ProfileService;
import fr.hb.tools.ConnectionDB;

public class TestAccess {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);
    private static final ConnectionDB connection = ConnectionDB.getInstance();

    public void main(String[] args) {
	LOG.setLevel(Level.ALL);
	dropAccess();
	populateAccess();

	LOG.info("Tests début");

	LOG.info("Tests fin");

    }

    // pour etre appelée à partir du Main
    public static void populateAccess() {
	LOG.info("populate access");

	mySaveAcces(new Access("tool.ged.all"));
	mySaveAcces(new Access("tool.ged.none"));
	mySaveAcces(new Access("tool.ged.share"));
	mySaveAcces(new Access("tool.invoice.none"));
	mySaveAcces(new Access("tool.invoice.update"));
	mySaveAcces(new Access("tool.paid.never"));
	mySaveAcces(new Access("tool.viral.all"));
	mySaveAcces(new Access("tool.office.none"));
	mySaveAcces(new Access("tool.office.word"));
	mySaveAcces(new Access("tool.office.excel"));
	mySaveAcces(new Access("tool.facebook.none.none.none"));

    }

    // pour etre appelée à partir du Main
    public static void dropAccess() {
	LOG.info("drop access");
	EntityManagerFactory emf = Persistence
		.createEntityManagerFactory("blairwitch");
	EntityManager em = emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Query sql1;
	sql1 = em.createNativeQuery("delete from access");
	sql1.executeUpdate();
	tx.commit();
	em.close();
	emf.close();
    }

    private static Access mySaveAcces(Access access) {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	try {
	    em.persist(access);
	} catch (EntityExistsException e) {
	    LOG.info("Access already exist");
	} catch (Exception e) {
	    // IllegalArgumentException
	    // TransactionRequiredException
	    LOG.debug(e.toString());
	}
	tx.commit();
	em.close();
	return access;
    }
}
