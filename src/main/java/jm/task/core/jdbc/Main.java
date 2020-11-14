package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService userDao = new UserServiceImpl();
        userDao.dropUsersTable();
        userDao.createUsersTable();
        User user1 = new User("Pasha", "Akaev", (byte)21);
        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User с именем – " + user1.getName() + " добавлен в базу данных");
        User user2 = new User("Amir", "Belbekov", (byte)22);
        userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        System.out.println("User с именем – " + user2.getName() + " добавлен в базу данных");
        User user3 = new User("Jamil", "Malik", (byte)32);
        userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        System.out.println("User с именем – " + user3.getName() + " добавлен в базу данных");
        User user4 = new User("Samon", "Samonov", (byte)28);
        userDao.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println("User с именем – " + user4.getName() + " добавлен в базу данных");
        System.out.println("####################################");
        System.out.println(userDao.getAllUsers().toString());
        userDao.cleanUsersTable();


    }
}
