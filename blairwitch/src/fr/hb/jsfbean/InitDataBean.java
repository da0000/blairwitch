package fr.hb.jsfbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import fr.hb.service.ProfileService;
import fr.hb.test.TestAccess;
import fr.hb.test.TestProfile;
import fr.hb.test.TestUser;

@ManagedBean
public class InitDataBean {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);

    public void resetWorld() {
	LOG.debug("-- init database --");

	TestAccess.dropAccess();
	TestAccess.populateAccess();

	TestProfile.dropProfile();
	TestProfile.populateProfile();

	TestUser.dropUser();
	TestUser.populateUser();

	addMessage("Success", "La base est initialisée");
    }

    public void addMessage(String summary, String detail) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		summary, detail);
	FacesContext.getCurrentInstance().addMessage(null, message);
    }
}