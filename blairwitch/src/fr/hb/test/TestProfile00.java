package fr.hb.test;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.hb.model.Access;
import fr.hb.model.Profile;
import fr.hb.model.User;

public class TestProfile00 {

    private static final Logger LOG = Logger.getLogger(TestProfile00.class);

    // private final static String TABLE_PROFILE = "profile";

    // Contexte de persistance
    private static EntityManagerFactory emf = Persistence
	    .createEntityManagerFactory("blairwitch");
    private static EntityManager em;

    public static void main(String[] args) {

	em = emf.createEntityManager();

	// clean();

	// EntityManager em = getEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();

	User user1 = new User("Daniel", "Myrick", "DM_" + getLogin(), "mdp");

	Profile profil1 = new Profile("Administrateur");
	Profile profil2 = new Profile();
	profil2.setLibel("Stagiaire");

	Access acc1 = new Access(null, "compta.bilan.all");
	Access acc2 = new Access(null, "compta.saisie.all");
	Access acc3 = new Access(null, "compta.none");
	Access acc4 = new Access(null, "docs.all");

	profil1.getAccesses().add(acc1);
	profil1.getAccesses().add(acc2);
	profil2.getAccesses().add(acc3);
	profil2.getAccesses().add(acc4);

	// persister
	em.persist(profil1);
	em.persist(profil2);

	user1.getProfiles().add(profil2);

	em.persist(user1);

	// fin transaction
	tx.commit();
	dumpProfiles();
	dumpUsers();

	em.close();

    }

    private static void clean() {
	// contexte de persistance
	// EntityManager em = getEntityManager();
	// debut transaction
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Query sql1;
	// supprimer les elements des tables
	sql1 = em.createNativeQuery("delete from access");
	sql1.executeUpdate();
	sql1 = em.createNativeQuery("delete from profile");
	sql1.executeUpdate();
	sql1 = em.createNativeQuery("delete from user");
	sql1.executeUpdate();
	// fin transaction
	tx.commit();
    }

    private static EntityManager getEntityManager() {
	if (em == null || !em.isOpen()) {
	    em = emf.createEntityManager();
	}
	return em;
    }

    private static void dumpProfiles() {
	// contexte de persistance
	EntityManager em = getEntityManager();
	// debut transaction
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	// affichage tous les profiles
	System.out.println("[profiles]");
	LOG.info("[profiles]");
	for (Object p : em.createQuery(
		"select p from Profile p order by p.libel asc").getResultList()) {
	    System.out.println(p.toString());
	    LOG.info(p.toString());
	}
	// fin transaction
	tx.commit();
    }

    private static void dumpUsers() {
	// contexte de persistance
	EntityManager em = getEntityManager();
	// debut transaction
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	// affichage tous les profiles
	System.out.println("[users]");
	LOG.info("[users]");
	for (Object p : em.createQuery(
		"select p from User p order by p.name asc")
	// "select c from Categorie c order by c.nom asc"
		.getResultList()) {
	    System.out.println(p.toString());
	    LOG.info(p.toString());
	}
	// fin transaction
	tx.commit();
    }

    private static String getLogin() {
	Calendar cal = Calendar.getInstance();
	int hour = cal.get(Calendar.HOUR_OF_DAY);
	int minute = cal.get(Calendar.MINUTE);
	return hour + "" + minute;
    }
}
