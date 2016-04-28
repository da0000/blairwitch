package fr.hb.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.hb.model.User;
import fr.hb.service.ProfileService;
import fr.hb.tools.ConnectionDB;

public class TestUser {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);
    private static final ConnectionDB connection = ConnectionDB.getInstance();

    public static void main(String[] args) {
	LOG.setLevel(Level.ALL);
	dropUser();
	populateUser();

	LOG.info("Tests début");

	LOG.info("Tests fin");

    }

    // pour etre appelée à partir du Main
    public static void populateUser() {
	LOG.info("populate user");
	EntityManager entityManager = connection.emf.createEntityManager();

	User user1 = new User();
	// /////////////////////// VERSION A GERER !!!
	user1.setName("Martin");
	user1.setSurname("DUPONT");
	user1.setLogin("login1");
	user1.setPassword("pass1");

	User user2 = new User();
	user2.setName("Victoria");
	user2.setSurname("PLUM");
	user2.setLogin("login2");
	user2.setPassword("pass2");

	User user3 = new User();
	user3.setName("Sam");
	user3.setSurname("BRUN");
	user3.setLogin("login3");
	user3.setPassword("pass3");

	entityManager.getTransaction().begin();
	entityManager.persist(user1);
	entityManager.persist(user2);
	entityManager.persist(user3);

	entityManager.getTransaction().commit();
	entityManager.close();
    }

    // pour etre appelée à partir du Main
    public static void dropUser() {
	LOG.info("drop user");
	EntityManagerFactory emf = Persistence
		.createEntityManagerFactory("blairwitch");
	EntityManager em = emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Query sql1;
	sql1 = em.createNativeQuery("delete from user");
	sql1.executeUpdate();
	tx.commit();
	em.close();
	emf.close();
    }

}
