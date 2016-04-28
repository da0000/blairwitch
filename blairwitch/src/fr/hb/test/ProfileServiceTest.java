package fr.hb.test;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import fr.hb.model.Profile;
import fr.hb.service.ProfileService;

public class ProfileServiceTest extends TestCase {

    private static final int REMOVE_ID = 93;
    private static ProfileService profileService = new ProfileService();

    @Test
    public void testSaveProfile() {
	Profile profile = new Profile();
	profile.setLibel("Testeur");

	try {
	    profile = profileService.save(profile);
	    Assert.assertNotNull(profile);
	    Assert.assertNotNull(profile.getId());
	    Assert.assertNotNull(profile.getLibel());
	    assertEquals(profile.getLibel(), "Testeur");

	} catch (Exception e) {
	    assertNull(e);
	}

    }

    @Test
    public void testSaveProfileWithNull() {
	Profile profile = null;

	// ProfileService profileService = new ProfileService();

	try {
	    profile = profileService.save(profile);
	    Assert.assertNull(profile);
	} catch (NullPointerException e) {
	    assertNotNull(e);
	}

    }

    @Test
    public void testProfileRemove() {
	try {
	    profileService.removeOne(REMOVE_ID);
	} catch (Exception e) {
	    assertNull(e);
	}
	// ca remove bien en cascade
    }
}
