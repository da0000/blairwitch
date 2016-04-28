package fr.hb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.hb.dao.IDaoUser;
import fr.hb.model.User;
import fr.hb.tools.ConnectionDB;

public class UserService implements IDaoUser {

    private ConnectionDB connection = ConnectionDB.getInstance();
    private static final Logger LOG = Logger.getLogger(UserService.class);

    // Insérer USER factice
    @Override
    public void saveUser() {
	EntityManager entityManager = connection.emf.createEntityManager();

	// USERS factices
	User user1 = new User();
	user1.setName("Martin");
	user1.setSurname("DUPONT");
	user1.setLogin("login1");
	user1.setPassword("pass1");

	User user2 = new User();
	user2.setName("Victoria");
	user2.setSurname("PLUM");
	user2.setLogin("login2");
	user2.setPassword("pass2");

	User user3 = new User();
	user3.setName("Sam");
	user3.setSurname("BRUN");
	user3.setLogin("login3");
	user3.setPassword("pass3");

	entityManager.getTransaction().begin();
	entityManager.persist(user1);
	entityManager.persist(user2);
	entityManager.persist(user3);

	Collection<User> allUsers = new ArrayList<User>();
	allUsers.add(user1);
	allUsers.add(user2);
	allUsers.add(user3);

	for (User user : allUsers) {
	    LOG.info("\rNEW USER");
	    LOG.info(user.toString());
	}
	entityManager.getTransaction().commit();
	entityManager.close();
    }

    // Insérer USER en paramètre
    public void saveUser(User user) {
	EntityManager entityManager = connection.emf.createEntityManager();

	entityManager.getTransaction().begin();
	entityManager.persist(user);

	LOG.info("\rNEW USER");
	LOG.info(user.toString());

	entityManager.getTransaction().commit();
	entityManager.close();
    }

    // Afficher 1 USER
    @Override
    public User findUser(int id) {
	EntityManager entityManager = connection.emf.createEntityManager();

	User user = entityManager.find(User.class, id);

	LOG.info("\rFIND USER");
	if (null != user) {
	    LOG.info(user.toString());
	} else {
	    LOG.info("L'ID utilisateur est null.");
	}

	entityManager.close();
	return user;
    }

    // Afficher tous les USERS
    @Override
    public Collection<User> findAllUser() {
	EntityManager entityManager = connection.emf.createEntityManager();

	Query query = entityManager.createQuery("SELECT u FROM User u");
	Collection<User> allUsers = query.getResultList();

	LOG.info("\rFIND ALL USERS");
	for (User user : allUsers) {
	    LOG.info(user.toString());
	}
	entityManager.close();
	return allUsers;
    }

    // Mise à jour 1 USER
    @Override
    public User updateUser(User newUser) {
	EntityManager entityManager = connection.emf.createEntityManager();

	User oldUser = entityManager.find(User.class, newUser.getId());

	LOG.info("\rUPDATE USER");

	if (null != newUser) {
	    entityManager.getTransaction().begin();

	    // attribution des nouvelles valeurs
	    oldUser.setName(newUser.getName());
	    oldUser.setSurname(newUser.getSurname());
	    oldUser.setLogin(newUser.getLogin());
	    oldUser.setPassword(newUser.getPassword());

	    entityManager.getTransaction().commit();
	} else {
	    LOG.info("L'ID utilisateur est null.");
	}
	entityManager.close();
	return newUser;
    }

    // Suppression 1 USER
    @Override
    public void removeUser(int id) {
	EntityManager entityManager = connection.emf.createEntityManager();

	entityManager.getTransaction().begin();
	User user = entityManager.find(User.class, id);

	LOG.info("\rREMOVE USER");
	if (null != user) {
	    entityManager.remove(user);
	} else {
	    LOG.info("L'ID utilisateur est null.");
	}
	entityManager.getTransaction().commit();
	entityManager.close();
    }

    // Liste des ids des USERS à supprimer
    public void removeListById(List<Integer> ids) {
	for (Integer i : ids) {
	    this.removeUser(i);
	}
    }

}
