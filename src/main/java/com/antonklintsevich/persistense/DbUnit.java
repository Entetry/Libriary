package com.antonklintsevich.persistense;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DbUnit {
    
    private static SessionFactory sessionFactory;
    
    
    
   public static SessionFactory getSessionFactory() {
       if (sessionFactory == null) {
           Configuration configuration = new Configuration().configure();
           ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
           registry.applySettings(configuration.getProperties());
           ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
           sessionFactory = configuration.buildSessionFactory(serviceRegistry);}
           return sessionFactory;
   }
}
