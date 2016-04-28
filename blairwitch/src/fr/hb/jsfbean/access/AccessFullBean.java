/**
 * 
 */
package fr.hb.jsfbean.access;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import fr.hb.model.Access;
import fr.hb.service.AccessService;
import fr.hb.service.ProfileService;

/**
 * @author hb
 *
 */
@ViewScoped
@ManagedBean
public class AccessFullBean {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);

    private Integer accessId;
    private String action;
    private String authorization;

    /**
     * @return the authorization
     */
    public String getAuthorization() {
	return authorization;
    }

    /**
     * @param authorization
     *            the authorization to set
     */
    public void setAuthorization(String authorization) {
	this.authorization = authorization;
    }

    public Integer getAccessId() {
	return accessId;
    }

    public void setAccessId(Integer accessId) {
	this.accessId = accessId;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    @PostConstruct
    public void init() {
	// récupération du user passé en parametre si id>0
	FacesContext fc = FacesContext.getCurrentInstance();
	Map<String, String> params = fc.getExternalContext()
		.getRequestParameterMap();

	if (null != params.get("accessId")
		&& !"null".equalsIgnoreCase(params.get("accessId"))) {
	    accessId = Integer.parseInt(params.get("accessId"));
	    AccessService accessService = new AccessService();
	    Access access;
	    LOG.info("param : " + accessId);
	    if (accessId != 0) {
		access = accessService.findAccess(accessId);
		if (null == access) {
		    LOG.info("pas trouvé");
		} else {
		    this.authorization = access.getAuthorization();
		}
	    }
	}
    }

    public void saveOneAccess() {
	FacesContext context = FacesContext.getCurrentInstance();

	AccessService accessService = new AccessService();
	Access access;

	if (null == accessId || 0 == accessId) {
	    LOG.info("nouveau");
	    access = new Access(accessId, authorization);
	    accessService.saveAccess(access);
	} else {
	    LOG.info("modif");
	    access = new Access(accessId, authorization);
	    accessService.updateAccess(access);
	}

	if (null == accessId || 0 == accessId) {
	    context.addMessage(null, new FacesMessage("Successful",
		    "Nouveau droit : " + authorization));
	} else {
	    context.addMessage(null, new FacesMessage("Successful",
		    "Modification droit : " + authorization));
	}
    }

}
