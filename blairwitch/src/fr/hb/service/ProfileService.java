package fr.hb.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.hb.dao.IDaoProfile;
import fr.hb.model.Profile;
import fr.hb.tools.ConnectionDB;

public class ProfileService implements IDaoProfile {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);
    // private static EntityManagerFactory emf = Persistence
    // .createEntityManagerFactory("blairwitch");
    // private static EntityManager em;
    private ConnectionDB connection = ConnectionDB.getInstance();

    @Override
    public Profile find(Integer id) {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Profile profile = null;
	try {
	    profile = em.getReference(Profile.class, id);
	} catch (EntityNotFoundException e) {
	    LOG.debug("personne non trouvée");
	}
	// tx.commit();
	em.close();
	return profile;
    }

    @Override
    public List<Profile> findAll() {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	List profiles = em.createQuery(
		"select p from Profile p order by p.libel asc").getResultList();
	tx.commit();
	em.close();
	return profiles;
    }

    @Override
    public Profile save(Profile profile) {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	try {
	    em.persist(profile);
	} catch (EntityExistsException e) {
	    LOG.info("Profile already exist");
	} catch (Exception e) {
	    // IllegalArgumentException
	    // TransactionRequiredException
	    LOG.debug(e.toString());
	}
	tx.commit();
	em.close();
	return profile;
    }

    @Override
    public Profile update(Profile profile) {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Profile updprofile = em.find(Profile.class, profile.getId());
	updprofile.setLibel(profile.getLibel());
	tx.commit();
	em.close();
	return updprofile;
    }

    @Override
    public void removeOne(Integer id) {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Profile profile;
	profile = em.getReference(Profile.class, id);
	if (null != profile) {
	    em.remove(profile);
	}
	tx.commit();
	em.close();
    }

    @Override
    public Profile findByLibel(String libel) {
	EntityManager em = connection.emf.createEntityManager();
	Query query = em.createQuery("select p from Profile p where p.libel='"
		+ libel + "'");
	Profile profile = (Profile) query.getSingleResult();
	// if (profile == null) {
	em.close();
	return profile;

    }

    // Liste des Profiles à supprimer
    public void removeList(List<Profile> profiles) {
	for (Profile p : profiles) {
	    this.removeOne(p.getId());
	}
    }

    // Liste des ids des Profiles à supprimer
    public void removeListById(List<Integer> ids) {
	for (Integer i : ids) {
	    this.removeOne(i);
	}
    }

}