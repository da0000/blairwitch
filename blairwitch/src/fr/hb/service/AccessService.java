/**
 * 
 */
package fr.hb.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.hb.model.Access;

/**
 * @author hb
 *
 */

public class AccessService implements AccessRuntime {

    private static final Logger LOG = Logger.getLogger(AccessService.class);

    private final static String TABLE_DROIT = "access";

    private static Access u1;

    static// TODO Auto-generated method stub
	  // Contexte de persistance
    EntityManagerFactory emf = Persistence
	    .createEntityManagerFactory("blairwitch");

    private static EntityManager emGlobal = null;

    /**
     * @param args
     */

    public static void main(String[] args) throws ParseException {
	// on récupère un EntityManager à partir de l'EntityManagerFatory
	emGlobal = emf.createEntityManager();

	// fin contexte de persistance
	if (emGlobal != null && emGlobal.isOpen()) {
	    emGlobal.close();
	}

    }

    // raz BD
    @Override
    public void cleanAccess() {
	// contexte de persistance
	EntityManager em = getEntityManager();
	// debut transaction
	LOG.info("Clean debut Transaction");
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	// supprimer les elements de la table PERSONNES
	em.createNativeQuery("delete from " + TABLE_DROIT).executeUpdate();
	// fin transaction
	tx.commit();
	LOG.info("Fin de clean");
    }

    // Inserer des donnees
    @Override
    public void insertAccess() {
	// contexte de persistance
	EntityManager em = getEntityManager();
	// creation de 2 droits
	Access a1 = new Access(null, "Comptabilite");
	Access a2 = new Access(null, "Facturation");
	Access a3 = new Access(null, "Paye");

	// debut transaction
	LOG.info("Insertion debut Transaction");
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	// persistance des droits
	em.persist(a1);
	em.persist(a2);
	em.persist(a3);

	// fin transaction
	tx.commit();
	LOG.info("Fin d'Insertion");

    }

    // Inserer USER en paramÃ¨tre
    public void saveAccess(Access access) {
	// contexte de persistance
	EntityManager em = getEntityManager();

	EntityTransaction tx = em.getTransaction();
	tx.begin();
	em.persist(access);

	Collection<Access> allAccess = new ArrayList<Access>();
	allAccess.add(access);

	LOG.info("\rNEW ACCESS");
	LOG.info(access.toString());

	tx.commit();
	em.close();
    }

    // Inserer des donnees
    @Override
    public Access updateAccess(Access access) {
	// contexte de persistance
	EntityManager em = getEntityManager();

	// debut transaction
	LOG.info("Mise a jour debut Transaction");
	EntityTransaction tx = em.getTransaction();
	tx.begin();

	u1 = em.find(Access.class, access.getId());
	// Mise à jour des droits
	u1.setAuthorization(access.getAuthorization());

	// fin transaction
	tx.commit();
	LOG.info("Fin Mise a jour");
	return u1;
    }

    // Supprimer des donnees
    @Override
    public void deleteAccess(Integer id) {

	// contexte de persistance
	EntityManager em = getEntityManager();

	// début transaction
	EntityTransaction tx = em.getTransaction();
	try {

	    // suppression droit d1
	    LOG.info("Suppression debut Transaction");
	    tx.begin();
	    Access d1 = em.find(Access.class, id);
	    LOG.info("d1 : " + d1);
	    em.remove(d1);
	    // fin transaction
	    tx.commit();
	} catch (PersistenceException e) {
	    Throwable th = e.getCause();
	    LOG.info("Message : " + th.getCause().toString());
	}
    }

    // recuperer l'EntityManager courant

    public EntityManager getEntityManager() {
	if (emGlobal == null || !emGlobal.isOpen()) {
	    emGlobal = emf.createEntityManager();
	}
	return emGlobal;
    }

    // recuperer un EntityManager neuf

    public EntityManager getNewEntityManager() {
	if (emGlobal != null && emGlobal.isOpen()) {
	    emGlobal.close();
	}
	emGlobal = emf.createEntityManager();
	return emGlobal;
    }

    // affichage contenu table access
    @Override
    public List<Access> findAllAccess() {
	// contexte de persistance
	EntityManager em = getEntityManager();
	// debut transaction
	EntityTransaction tx = em.getTransaction();

	tx.begin();
	// affichage personnes
	LOG.info("[droit]");
	for (Object a : em.createQuery("select a from Access a")
		.getResultList()) {
	    LOG.info(a.toString());
	}
	List accesses = em.createQuery("select a from Access a")
		.getResultList();

	// fin transaction
	tx.commit();
	return accesses;
    }

    // affichage contenu table access
    @Override
    public Access findAccess(Integer id) {
	// contexte de persistance
	EntityManager em = getEntityManager();
	// debut transaction
	EntityTransaction tx = em.getTransaction();
	tx.begin();
	// affichage personnes
	LOG.info("[droit]");
	for (Object a : em.createQuery(
		"select a from Access a where a.id=" + id).getResultList()) {
	    LOG.info(a.toString());
	}
	Query querySingleResult = em
		.createQuery("select a from Access a where a.id=" + id);
	Access accessSingle = (Access) querySingleResult.getSingleResult();
	// fin transaction
	tx.commit();
	return accessSingle;
    }

}
