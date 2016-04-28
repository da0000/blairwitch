/**
 * 
 */
package fr.hb.dao;

import java.util.List;

import fr.hb.model.Access;

/**
 * @author hb
 *
 */
public interface IDaoAccess {

    /**
     * @param args
     */

    public void cleanAccess();

    public void insertAccess();

    public void deleteAccess(Integer id);

    public void updateAccess(Integer id);

    public Access findAccess(Integer id);

    public List<Access> findAllAccess();

}
