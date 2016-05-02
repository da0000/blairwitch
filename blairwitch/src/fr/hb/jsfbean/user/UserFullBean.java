package fr.hb.jsfbean.user;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import fr.hb.model.User;
import fr.hb.service.UserService;

//ViewScoped permet de recharger à chaque nouvelle vue
@ViewScoped
@ManagedBean
public class UserFullBean {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	private Integer userId;
	private String surname;
	private String name;
	private String login;
	private String password;

	// Getters & setters
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Constructor
	public UserFullBean() {
		super();
	}

	@PostConstruct
	public void init() {
		// récupération du user passé en parametre si id>0
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext()
				.getRequestParameterMap();

		// verification que le param userId est renseigné
		if (null != params.get("userId")
				&& !"null".equalsIgnoreCase(params.get("userId"))) {
			userId = Integer.parseInt(params.get("userId"));
			UserService userService = new UserService();
			User user;
			LOG.info("param : " + userId);
			if (userId != 0) {
				user = userService.findUser(userId);
				if (null == user) {
					LOG.info("pas trouvé");
				} else {
					// récupération des valeurs de la bdd
					this.surname = user.getSurname();
					this.name = user.getName();
					this.login = user.getLogin();
					this.password = user.getPassword();
				}
			}
		}
	}

	// Ajout d'un nouvel utilisateur
	public void saveOneUser() {
		FacesContext context = FacesContext.getCurrentInstance();

		UserService userService = new UserService();
		User user;

		if (null == userId || 0 == userId) {
			LOG.info("nouveau");
			user = new User(name, surname, login, password);
			userService.saveUser(user);
			// pop-up
			context.addMessage(null, new FacesMessage("Successful",
					"Nouvel utilisateur : " + surname + " " + name + " "
							+ login));
		} else {
			LOG.info("modif");
			user = new User(userId, name, surname, login, password);
			userService.updateUser(user);
			// pop-up
			context.addMessage(null, new FacesMessage("Successful",
					"Modification utilisateur : " + surname + " " + name + " "
							+ login));
		}

	}
}
