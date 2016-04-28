package fr.hb.jsfbean.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fr.hb.model.Profile;
import fr.hb.service.ProfileService;

@ManagedBean
@ViewScoped
public class ProfileListBean {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);

    private List<Profile> profiles = new ArrayList<Profile>();
    private String profileId;
    private String action;

    // pour le check
    private List<Profile> selectedProfiles;

    // ---

    public String getProfileId() {
	return profileId;
    }

    public void setProfileId(String profileId) {
	this.profileId = profileId;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    @ManagedProperty("#{profileService}")
    private ProfileService service;

    public List<Profile> getProfiles() {
	return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
	this.profiles = profiles;
    }

    public List<Profile> getSelectedProfiles() {
	return selectedProfiles;
    }

    public void setSelectedProfiles(List<Profile> selectedProfiles) {
	this.selectedProfiles = selectedProfiles;
    }

    public ProfileService getService() {
	return service;
    }

    public void setService(ProfileService service) {
	this.service = service;
    }

    @PostConstruct
    public void populateEmployesList() {
	ProfileService profileService = new ProfileService();
	profiles = profileService.findAll();
    }

    public void destroyOneProfile() {

	FacesContext fc = FacesContext.getCurrentInstance();
	Map<String, String> params = fc.getExternalContext()
		.getRequestParameterMap();

	profileId = params.get("profileId");
	// inutilise__service.removeOne(profileId);
	LOG.debug("param : " + profileId);
	addMessage("Suppression", "Le profil " + profileId + " a été supprimé.");
    }

    // methode ok
    public void deleteOneProfile() {
	FacesContext context = FacesContext.getCurrentInstance();

	ProfileService profileService = new ProfileService();
	LOG.debug("profId = " + profileId);
	Integer pId = Integer.parseInt(profileId);

	profileService.removeOne(pId);
	setProfiles(profileService.findAll());

	context.addMessage(null, new FacesMessage("Successful", "Le profil "
		+ profileId + " est supprimé."));
    }

    public void addMessage(String summary, String detail) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		summary, detail);
	FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // Supression par la liste
    public void deleteListProfile() {
	ProfileService profileService = new ProfileService();
	LOG.setLevel(Level.ALL);

	LOG.debug("*debut delete list profiles*");
	for (Profile p : selectedProfiles) {
	    LOG.debug(p.toString());
	    profileService.removeOne(p.getId());
	}
	LOG.debug("*fin delete list profiles*");
    }

    // pour le check
    public void submit() {
	// Now do your thing with checkedItems.
	LOG.debug("*debut selected profiles*");
	for (Profile p : selectedProfiles) {
	    LOG.debug(p.toString());
	}
	LOG.debug("*fin selected profiles*");
    }

}
