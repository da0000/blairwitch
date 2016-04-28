package fr.hb.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import fr.hb.model.Access;
import fr.hb.tools.ConnectionDB;

public class MyAccessService {

    private static final Logger LOG = Logger.getLogger(ProfileService.class);
    private static final ConnectionDB connection = ConnectionDB.getInstance();

    public Access find(Integer id) {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	Access access = null;
	try {
	    access = em.getReference(Access.class, id);
	} catch (EntityNotFoundException e) {
	    LOG.info("personne non trouvée");
	}
	// tx.commit();
	em.close();
	return access;
    }

    public List<Access> findAll() {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	List accesses = em.createQuery(
		"select p from Access p order by p.authorization asc")
		.getResultList();
	tx.commit();
	em.close();
	return accesses;
    }

    public Access save(Access access) {
	EntityManager em = connection.emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	try {
	    em.persist(access);
	} catch (EntityExistsException e) {
	    LOG.info("Access already exist");
	} catch (Exception e) {
	    // IllegalArgumentException
	    // TransactionRequiredException
	    LOG.info(e);
	}
	tx.commit();
	em.close();
	return access;
    }
}
