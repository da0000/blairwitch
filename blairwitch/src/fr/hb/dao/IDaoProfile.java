package fr.hb.dao;

import java.util.List;

import fr.hb.model.Profile;

public interface IDaoProfile {

    Profile find(Integer id);

    Profile findByLibel(String libel);

    List<Profile> findAll();

    Profile save(Profile profile);

    Profile update(Profile profile);

    void removeOne(Integer id);

}
