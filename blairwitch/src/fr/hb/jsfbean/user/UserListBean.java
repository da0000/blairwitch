package fr.hb.jsfbean.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import fr.hb.model.User;
import fr.hb.service.ProfileService;
import fr.hb.service.UserService;

@ManagedBean
@SessionScoped
public class UserListBean {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);

    private List<User> allUsers = new ArrayList<User>();
    private User selectedUser;
    private List<User> selectedUsers;
    private String userId;
    private String action;

    @ManagedProperty("#{userService}")
    private UserService service;

    // Getters & setters
    public User getSelectedUser() {
	return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
	this.selectedUser = selectedUser;
    }

    public List<User> getSelectedUsers() {
	return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
	this.selectedUsers = selectedUsers;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    public void setAllUsers(List<User> allUsers) {
	this.allUsers = allUsers;
    }

    public List<User> getAllUsers() {
	return allUsers;
    }

    public void setAllUsers(Collection<User> allUsers) {
	this.allUsers = (List<User>) allUsers;
    }

    public UserService getService() {
	return service;
    }

    public void setService(UserService service) {
	this.service = service;
    }

    // Constructor
    public UserListBean() {
	super();
    }

    @PostConstruct
    public void init() {
	UserService userService = new UserService();
	allUsers = (List<User>) userService.findAllUser();
    }

    // Sélection d'une checkbox
    public void onRowSelect(SelectEvent event) {
	FacesMessage msg = new FacesMessage("User Selected",
		((User) event.getObject()).getId().toString());
	FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // Désélection d'une checkbox
    public void onRowUnselect(UnselectEvent event) {
	FacesMessage msg = new FacesMessage("User Unselected",
		((User) event.getObject()).getId().toString());
	FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // Destruction d'une ligne cochée
    public void destroyOneUser() {
	FacesContext fc = FacesContext.getCurrentInstance();
	Map<String, String> params = fc.getExternalContext()
		.getRequestParameterMap();

	userId = params.get("userId");
	LOG.info("param : " + userId);
	addMessage("Suppression", "L'utilisateur " + userId
		+ " a été supprimé.");
    }

    // methode ok
    public void deleteOneUser() {
	FacesContext context = FacesContext.getCurrentInstance();

	UserService userService = new UserService();
	LOG.info("userId = " + userId);
	Integer uId = Integer.parseInt(userId);

	userService.removeUser(uId);
	setAllUsers(userService.findAllUser());

	context.addMessage(null, new FacesMessage("Successful",
		"L'utilisateur " + userId + " est supprimé."));
    }

    public void deleteProfile(Integer uId) {
	LOG.info("delete profile = " + uId);
    }

    // ajout message pop-up
    public void addMessage(String summary, String detail) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
		summary, detail);
	FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
