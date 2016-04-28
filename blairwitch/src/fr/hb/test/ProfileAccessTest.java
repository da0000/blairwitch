package fr.hb.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import fr.hb.model.Access;
import fr.hb.model.Profile;
import fr.hb.service.ProfileService;

public class ProfileAccessTest extends TestCase {

    private static ProfileService profileService = new ProfileService();

    @Test
    public void testSaveProfileAccess() {
	Profile profile = new Profile();
	profile.setLibel("Testeur");
	Set<Access> profAccesses = new HashSet<Access>();

	Access accNone = new Access("test.none");
	Access acc1 = new Access("tool.format.none");
	Access acc2 = new Access("tool.check");

	profAccesses.add(accNone);

	profAccesses.add(accNone);
	profAccesses.add(acc1);
	profAccesses.add(acc2);

	try {

	    profile.setAccesses(profAccesses);

	    profile = profileService.save(profile);
	    Assert.assertNotNull(profile);
	    Assert.assertNotNull(profile.getAccesses());

	} catch (Exception e) {
	    assertNull(e);
	}

    }

}
