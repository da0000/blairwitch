/**
 * 
 */
package fr.hb.jsfbean.access;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import fr.hb.model.Access;
import fr.hb.service.AccessService;

/**
 * @author hb
 *
 */

@ManagedBean
@ViewScoped
public class AccessListBean {

    private List<Access> accessListe = new ArrayList<Access>();
    private Access selectedAccess;
    private Integer accessId;
    private String action;

    @ManagedProperty("#{accessService}")
    private AccessService service;

    /**
     * @return the service
     */
    public AccessService getService() {
	return service;
    }

    /**
     * @param service
     *            the service to set
     */
    public void setService(AccessService service) {
	this.service = service;
    }

    private static final Logger LOG = Logger.getLogger(AccessService.class);

    /**
     * @return the selectedAccess
     */
    public Access getSelectedAccess() {
	return selectedAccess;
    }

    /**
     * @param selectedAccess
     *            the selectedAccess to set
     */
    public void setSelectedAccess(Access selectedAccess) {
	this.selectedAccess = selectedAccess;
    }

    /**
     * @return the accessId
     */
    public Integer getAccessId() {
	return accessId;
    }

    /**
     * @param accessId
     *            the accessId to set
     */
    public void setAccessId(Integer accessId) {
	this.accessId = accessId;
    }

    /**
     * @return the action
     */
    public String getAction() {
	return action;
    }

    /**
     * @param action
     *            the action to set
     */
    public void setAction(String action) {
	this.action = action;
    }

    /**
     * @return the accessListe
     */
    public List<Access> getAccessListe() {
	if (accessListe == null || accessListe.isEmpty()) {

	    accessListe = new ArrayList<Access>();
	    accessListe.add(new Access(null, "Facturation"));
	    accessListe.add(new Access(null, "Comptabilite"));
	    for (Access element : accessListe) {
		LOG.info("Authorisation : " + element.getAuthorization());
	    }
	}
	return accessListe;
    }

    /**
     * @param accessListe
     *            the accessListe to set
     */
    public void setAccessListe(List<Access> accessListe) {
	this.accessListe = accessListe;
    }

    @PostConstruct
    public void populateAccessList() {
	AccessService accessService = new AccessService();
	accessListe = accessService.findAllAccess();
    }

    public void onRowSelect(SelectEvent event) {
	FacesMessage msg = new FacesMessage("Access Selected",
		((Access) event.getObject()).getId().toString());
	FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselected(UnselectEvent event) {
	FacesMessage msg = new FacesMessage("Access Unselected",
		((Access) event.getObject()).getId().toString());
	FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void buttonAction(ActionEvent actionEvent) {
	LOG.info("Bouton action");
    }

    public void deleteOneAccess() {
	FacesContext fc = FacesContext.getCurrentInstance();
	AccessService accessService = new AccessService();
	LOG.info("Methode accessService.deleteAccess(accessId)");
	accessService.deleteAccess(accessId);
	fc.addMessage(null, new FacesMessage("Suppression", "Le droit "
		+ accessId + "a ete supprime"));
    }

    public void attrListener(ActionEvent event) {
	accessId = (Integer) event.getComponent().getAttributes().get(accessId);
	LOG.info("attrListen : " + accessId);
    }

    public void attrOneAccess() {
	LOG.info("attribut : " + accessId);
	addMessage("Attr Suppression", "Le droit " + accessId
		+ " a ete supprime");
    }

    public void editAction() {
	FacesContext context = FacesContext.getCurrentInstance();
	LOG.info("action = " + action);
	LOG.info("accessId : " + accessId);
	context.addMessage(null, new FacesMessage("Successful", "Le droit "
		+ accessId + " est supprime"));
    }

    public void addMessage(String summary, String detail) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		summary, detail);
	FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
