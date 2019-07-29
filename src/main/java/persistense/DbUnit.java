package persistense;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class DbUnit {
public static Session getSession() {
//addClass(entity.Book.class)
	//  SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(new ServiceRegistryBuilder().buildServiceRegistry());
	SessionFactory sessionFactory= new AnnotationConfiguration().configure().buildSessionFactory();;
	
	return sessionFactory.openSession();
}
}
