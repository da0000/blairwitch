package fr.hb.test;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import fr.hb.model.Profile;
import fr.hb.model.User;
import fr.hb.service.ProfileService;
import fr.hb.service.UserService;

public class UserProfileTest extends TestCase {

    private static ProfileService profileService = new ProfileService();
    private static UserService userService = new UserService();

    @Test
    public void testSaveUser() {
	User user = new User("DUTEST", "Hector", "HDT", "cestinterdit");
	try {
	    userService.saveUser(user);
	} catch (Exception e) {
	    assertNull(e);
	}
    }

    @Test
    public void testSaveUserProfile() {
	User user = new User("DUTEST", "Hector", "HDT5", "cestinterdit");
	Profile profTst = new Profile(user.getLogin());
	// Assert.assertNotNull(profTst);

	try {
	    user.getProfiles().add(profTst);
	    userService.saveUser(user);
	    // Assert.assertNotNull(user);
	    Assert.assertNotNull(user.getProfiles());

	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    assertNull(e);
	}

    }

}
