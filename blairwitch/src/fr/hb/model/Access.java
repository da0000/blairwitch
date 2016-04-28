package fr.hb.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Access implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String authorization;

    // relation inverse Access -> Profile
    @ManyToMany(mappedBy = "accesses", fetch = FetchType.LAZY)
    private Set<Profile> profiles = new HashSet<Profile>();

    public Access() {
	super();
    }

    // rajouté par Damien
    public Access(String authorization) {
	super();
	this.authorization = authorization;
    }

    public Access(Integer id, String authorization) {
	super();
	this.id = id;
	this.authorization = authorization;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getAuthorization() {
	return authorization;
    }

    public void setAuthorization(String authorization) {
	this.authorization = authorization;
    }

    public Set<Profile> getProfiles() {
	return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
	this.profiles = profiles;
    }

    @Override
    public String toString() {
	return "Access [id=" + getId() + ", authorization="
		+ getAuthorization() + "]";
    }

}
