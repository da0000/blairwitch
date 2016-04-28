package fr.hb.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Profile implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String libel;

    // relation profil (many) -> access (many) via une table de jointure
    // profil_access
    // profil_access(profil_id) est cle etrangere sur profil(id)
    // profil_access(access_id) est cle etrangere sur Aaccess(id)
    // cascade=CascadeType.PERSIST : persistance d'1 profil entraine celle de
    // ses access
    @ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinTable(name = "profile_access", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "access_id"))
    private Set<Access> accesses = new HashSet<Access>();
    // 'Set' because Set does not allow duplicated values.

    // relation inverse Profile -> User
    @ManyToMany(mappedBy = "profiles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<User>();

    public Profile() {
	super();
    }

    public Profile(Integer id, String libel) {
	super();
	this.id = id;
	this.libel = libel;
    }

    public Profile(String libel) {
	super();
	this.libel = libel;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getLibel() {
	return libel;
    }

    public void setLibel(String libel) {
	this.libel = libel;
    }

    public Set<Access> getAccesses() {
	return accesses;
    }

    public void setAccesses(Set<Access> accesses) {
	this.accesses = accesses;
    }

    @Override
    public String toString() {
	return "Profile [id=" + id + ", libel=" + libel + "]";
    }

}
