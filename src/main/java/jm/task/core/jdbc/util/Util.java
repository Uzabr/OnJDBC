package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.MetadataSource;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;


import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection con;
    private static StandardServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    public static Connection getCon () {
        return con;
    }


    public static Connection connect (String db) {
        Connection connection = null;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
            +db+"?characterEncoding = utf8&serverTimezone=UTC", "root", "toor");
            connection.setAutoCommit(false);
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        return connection;
    }
    public static void RollBack (){
        try {
            con = Util.getCon();
            con.rollback();
        }
        catch (Exception ex ) {
            ex.printStackTrace();
        }

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                Properties settings =  new Properties();
//                settings.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//                settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/myDS");
//                settings.put("hibernate.connection.username", "root");
//                settings.put("hibernate.connection.password", "toor");
//                settings.put("hibernate.show_sql", "true");

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/myDS?characterEncoding = utf8&serverTimezone=UTC");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "toor");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
//                Configuration configuration = new Configuration();
//                configuration.setProperties(settings);
//                configuration.addAnnotatedClass(User.class);
//                serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
//                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                settings.put(Environment.HBM2DDL_AUTO, "update");
                sessionFactory = new Configuration()
                        .addProperties(settings)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory().openSession().getSessionFactory();
            }
            catch (Exception exception ) {
                exception.printStackTrace();
                if (serviceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(serviceRegistry);
                }
            }
        }
        return sessionFactory;
    }

}
