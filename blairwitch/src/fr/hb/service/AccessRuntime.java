/**
 * 
 */
package fr.hb.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.hb.model.Access;


/**
 * @author hb
 *
 */
public interface AccessRuntime {

	/**
	 * @param args
	 */
	
	
		// TODO Auto-generated method stub
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("blairwitch");
		EntityManager entityManager = managerFactory.createEntityManager();
				
								
		public void cleanAccess();
		
		public void insertAccess();
		
		public void deleteAccess(Integer id);
		
		public Access updateAccess(Access access);
				
		public Access findAccess(Integer id);
		
		public List<Access> findAllAccess() ;

	
}
