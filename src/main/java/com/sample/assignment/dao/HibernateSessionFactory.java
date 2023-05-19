/**
* Hibernate Session Factory Configuration.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    static Logger logger = Logger.getLogger(HibernateSessionFactory.class);
 
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().
            		configure(HibernateSessionFactory.class.getResource("/hibernate.cfg.xml")).
            			buildSessionFactory();
        } catch (Exception ex) {
            logger.error("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
 
}