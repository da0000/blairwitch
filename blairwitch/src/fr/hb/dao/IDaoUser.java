package fr.hb.dao;

import java.util.Collection;

import fr.hb.model.User;

public interface IDaoUser {

    void saveUser();

    User findUser(int id);

    Collection<User> findAllUser();

    User updateUser(User user);

    void removeUser(int id);
}
