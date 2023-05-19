/**
* User Dao Implementation.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.dao;

import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.sample.assignment.config.PropertyFileConfig;
import com.sample.assignment.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao{
	
	//Loading Properties from File
	Properties prop = PropertyFileConfig.readPropertyFileFromClasspath();
	
	Logger logger = Logger.getLogger(UserDaoImpl.class);

	//Add User
	@Override
	public void addUser(User user) {
		 logger.debug("addUser:Start");
		 Session session = HibernateSessionFactory.getSessionFactory().openSession();
		 Transaction tx = null;
		 try {
	         tx = session.beginTransaction();
	         session.save(user);
	         tx.commit();
		 } catch (HibernateException e) {
			 logger.trace(e);
	         if (tx!=null) tx.rollback();
	      } finally {
	         session.close(); 
	      }
	}
	
	//Get Users
	@Override
	public List<User> findAllUsers() {
		 logger.debug("findAllUsers:Start");
		 Session session = HibernateSessionFactory.getSessionFactory().openSession();
	     Transaction tx = null;
	     List<User> users = null;
	     try {
	         tx = session.beginTransaction();
	         CriteriaBuilder builder = session.getCriteriaBuilder();
	         CriteriaQuery<User> criteria = builder.createQuery(User.class);
	         criteria.from(User.class);
	         users = session.createQuery(criteria).getResultList();
	         tx.commit();
	      } catch (HibernateException e) {
	    	 logger.trace(e);
	         if (tx!=null) tx.rollback();
	      } finally {
	         session.close(); 
	      }
	     return users;
	}
	
	//Get User By ID.
	@Override
	public User findById(long id) {
		logger.debug("findById:Start");
		Session session = HibernateSessionFactory.getSessionFactory().openSession();
	    Transaction tx = null;
	    User user = null;
	    try {
	         tx = session.beginTransaction();
	         user = session.get(User.class,id);
	         tx.commit();
	    } catch (HibernateException e) {
	    	 logger.trace(e);
	         if (tx!=null) tx.rollback();
	    } finally {
	         session.close(); 
	    }
	    return user;
	}
	
	//Update User
	@Override
	public void updateUser(User user) {
		 logger.debug("updateUser:Start");
		 Session session = HibernateSessionFactory.getSessionFactory().openSession();
	     Transaction tx = null;
	     try {
	        tx = session.beginTransaction();
	        session.update(user);
	        tx.commit();
	     } catch (HibernateException e) {
	    	logger.trace(e);
	        if (tx!=null) tx.rollback();
	     } finally {
	        session.close(); 
	     }
	}
	
	//Delete User
	@Override
	public void deleteUser(User user) {
		 logger.debug("deleteUser:Start");
		 Session session = HibernateSessionFactory.getSessionFactory().openSession();
	     Transaction tx = null;
	     try {
	        tx = session.beginTransaction();
	        session.delete(user); 
	        tx.commit();
	     } catch (HibernateException e) {
	    	 logger.trace(e);
	         if (tx!=null) tx.rollback(); 
	     } finally {
	        session.close(); 
	     }
	}
	
	//Get User By Name
	@Override
	public User findByUserName(String userName) {
		logger.debug("findByUserName:Start:UserName: "+userName);
		Session session = HibernateSessionFactory.getSessionFactory().openSession();
	    Transaction tx = null;
	    User existingUser = null;
	    try {
	         tx = session.beginTransaction();
	         CriteriaBuilder builder = session.getCriteriaBuilder();
	         CriteriaQuery<User> criteria = builder.createQuery(User.class);
	         Root<User> userRoot = criteria.from(User.class);
	         criteria.select(userRoot);
	         criteria
	         	.where(builder
	         			.equal(userRoot
	         					.get(prop.getProperty("criteria.field")),userName));
	         List<User> users = session.createQuery(criteria).getResultList();
	 		 for (User user : users) {
	 		    existingUser = user;
	 		 }
	 		 logger.debug("Existing User: "+existingUser);
	         tx.commit();
	    } catch (HibernateException e) {
	    	 logger.trace(e);
	         if (tx!=null) tx.rollback();
	    } finally {
	         session.close(); 
	    }
	    return existingUser;
	}

}
