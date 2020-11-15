package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.transform.Transformers;
import sun.nio.cs.UTF_32LE;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
         session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (" +
                "id int(10) unsigned not null auto_increment," +
                "fname varchar(15) default null," +
                "lastName varchar(15) default null," +
                "age tinyint(5) default null," +
                "primary key(id)" +
                ")")
                .executeUpdate();
        t.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        try {

            Session session = Util.getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.createSQLQuery("Drop table if exists user").executeUpdate();
            t.commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = Util.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save( new User(name, lastName, age));
        t.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("delete from user u where u.id = :OldId ")
               .setLong("OldId", id)
               .executeUpdate();

        tr.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
//        return (List<User>) Util.getSessionFactory().openSession().createSQLQuery("FROM user").list();
        Session session = Util.getSessionFactory().openSession();
        List<User> list = session.createSQLQuery("SELECT  * FROM user").list();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
         session.createSQLQuery("TRUNCATE TABLE user")
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}
