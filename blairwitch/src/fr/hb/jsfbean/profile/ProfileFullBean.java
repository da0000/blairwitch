package fr.hb.jsfbean.profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import fr.hb.model.Access;
import fr.hb.model.Profile;
import fr.hb.service.MyAccessService;
import fr.hb.service.ProfileService;

@ViewScoped
@ManagedBean
public class ProfileFullBean {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);

    private Integer profileId;
    private String action;
    private String libel;

    // Acces
    private List<Access> accesses = new ArrayList<Access>();
    private Access selectedCar;
    private List<Access> selectedCars;

    public Integer getProfileId() {
	return profileId;
    }

    public void setProfileId(Integer profileId) {
	this.profileId = profileId;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    public String getLibel() {
	return libel;
    }

    public void setLibel(String libel) {
	this.libel = libel;
    }

    // Pour la table des access
    public List<Access> getAccesses() {
	return accesses;
    }

    public void setAccesses(List<Access> accesses) {
	this.accesses = accesses;
    }

    public Access getSelectedCar() {
	return selectedCar;
    }

    public void setSelectedCar(Access selectedCar) {
	this.selectedCar = selectedCar;
    }

    public List<Access> getSelectedCars() {
	return selectedCars;
    }

    public void setSelectedCars(List<Access> selectedCars) {
	this.selectedCars = selectedCars;
    }

    @PostConstruct
    public void init() {
	// récupération du profile passé en parametre si id>0
	FacesContext fc = FacesContext.getCurrentInstance();
	Map<String, String> params = fc.getExternalContext()
		.getRequestParameterMap();

	if (null != params.get("profileId")) {
	    profileId = Integer.parseInt(params.get("profileId"));
	} else {
	    profileId = 0;
	}
	ProfileService profileService = new ProfileService();
	Profile profile;
	LOG.debug("param : " + profileId);
	if (profileId != 0) {
	    profile = profileService.find(profileId);
	    if (null == profile) {
		LOG.debug("pas trouvé");
	    } else {
		this.libel = profile.getLibel();
	    }
	}
	// liste des access
	MyAccessService accessService = new MyAccessService();
	accesses = accessService.findAll();
	LOG.debug("nb acces (my): " + accesses.size());

    }

    public void saveOneProfile() {
	FacesContext context = FacesContext.getCurrentInstance();

	ProfileService profilService = new ProfileService();
	Profile profile;
	Set<Access> sets = new HashSet<Access>(selectedCars);

	// > Liste des acces cochés
	for (Access acc : selectedCars) {
	    LOG.debug(acc.toString());
	}
	// >
	if (profileId == 0) {
	    LOG.debug("nouveau");
	    profile = new Profile(libel);
	    profile.setAccesses(sets);
	    profilService.save(profile);
	} else {
	    LOG.debug("modif");
	    profile = new Profile(profileId, libel);
	    profile.setAccesses(sets);
	    profilService.update(profile);
	}

	if (profileId == 0) {
	    context.addMessage(null, new FacesMessage("Successful",
		    "Nouveau profil : " + libel));
	} else {
	    context.addMessage(null, new FacesMessage("Successful",
		    "Modification profil : " + libel));
	}
    }
}