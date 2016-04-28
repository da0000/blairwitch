package fr.hb.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
// @Table (name = "bw_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length = 30, nullable = false)
	private String name;

	@Column(length = 30, nullable = false)
	private String surname;

	@Column(length = 30, nullable = false, unique = true)
	private String login;

	@Column(length = 30, nullable = false)
	private String password;

	// Table intermédiaire user/profile
	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "user_profile", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"))
	private Set<Profile> profiles = new HashSet<Profile>();

	// Constructors
	public User() {
		super();
	}

	public User(Integer id, String name, String surname, String login,
			String password) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
	}

	public User(String name, String surname, String login, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
	}

	// Getters & setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", login=" + login + ", password=" + password + "]";
	}

}
