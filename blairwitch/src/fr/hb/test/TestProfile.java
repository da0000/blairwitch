package fr.hb.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.hb.model.Profile;
import fr.hb.service.ProfileService;

public class TestProfile {

    private static final int FIND_ID = 59;

    private static final Logger LOG = Logger.getLogger(ProfileService.class);

    private static ProfileService profileService = new ProfileService();

    public static void main(String[] args) {
	LOG.setLevel(Level.ALL);
	dropProfile();
	populateProfile();

	LOG.info("Tests début");
	//
	// testFind();
	//
	// testFindNotFound();
	//
	// testFindByLibel();
	//
	// testFindAll();
	//
	// testRemove();
	//
	// testRemoveNotFound();

	testUpdate();

	LOG.info("Tests fin");

    }

    private static void testRemoveNotFound() {
	LOG.info("");
	profileService.removeOne(999);
    }

    private static void testRemove() {
	LOG.info("");
	Profile profTest = profileService.find(4);
	profileService.removeOne(profTest.getId());
    }

    private static void testFindAll() {
	LOG.info("");
	List<Profile> profiles = profileService.findAll();
	for (Profile p : profiles) {
	    LOG.info(p.toString());
	}
    }

    private static void testFindByLibel() {
	LOG.info("");
	Profile profTest = profileService.findByLibel("Terminator");
	LOG.info(profTest.toString());
    }

    private static void testFindNotFound() {
	LOG.info("");
	Profile profTest = profileService.find(999);
	LOG.info(profTest.toString());
    }

    private static void testFind() {
	LOG.info("");
	Profile profTest = profileService.find(24);
	LOG.info(profTest.toString());
    }

    private static void testUpdate() {
	LOG.info("update");
	Profile profTest = profileService.find(FIND_ID);
	profTest.setLibel("updated");
	profTest = profileService.update(profTest);
	LOG.info(profTest.toString());
    }

    // pour etre appelée à partir du Main
    public static void populateProfile() {
	LOG.info("populate profil");
	Profile prof1 = new Profile("Administrator");
	Profile prof2 = new Profile("Probationor");
	Profile prof3 = new Profile("Moderator");
	Profile proft = new Profile("Terminator");

	profileService.save(prof1);
	profileService.save(prof2);
	profileService.save(prof3);
	profileService.save(proft);
	profileService.save(new Profile("Visitor"));
	profileService.save(new Profile("Syntaxerror"));

    }

    // pour etre appelée à partir du Main
    public static void dropProfile() {
	LOG.info("drop profil");
	EntityManagerFactory emf = Persistence
		.createEntityManagerFactory("blairwitch");
	EntityManager em = emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Query sql1;
	sql1 = em.createNativeQuery("delete from profile");
	sql1.executeUpdate();
	tx.commit();
	em.close();
	emf.close();
    }
}
