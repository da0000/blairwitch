package fr.hb.main;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.hb.service.ProfileService;
import fr.hb.test.TestAccess;
import fr.hb.test.TestProfile;
import fr.hb.test.TestUser;

public class Main {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);

    public static void main(String[] args) {

	LOG.setLevel(Level.ALL);
	LOG.info("-----");
	LOG.info("Initialisation de la base.");

	LOG.info("Initialisation User");
	TestUser.dropUser();
	TestUser.populateUser();

	LOG.info("Initialisation Profile.");
	TestProfile.dropProfile();
	TestProfile.populateProfile();

	LOG.info("Initialisation Acces.");
	TestAccess.dropAccess();
	TestAccess.populateAccess();

	LOG.info("Initialisation Terminée.");
	LOG.info("-----");

    }

}
