package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import sun.tools.jconsole.ConnectDialog;

import javax.management.relation.RelationSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Connection connection = Util.connect("myDS");
            try (PreparedStatement statement = connection.prepareStatement("create table if not exists user(" +
                    "    id       int(20) unsigned auto_increment\n" +
                    "        primary key,\n" +
                    "    fname    varchar(10) null,\n" +
                    "    lastName varchar(15) null,\n" +
                    "    age      tinyint(10) null\n" +
                    ");")) {
                statement.executeUpdate();
            }
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
        Connection connection = Util.connect("myDS");
        try (PreparedStatement statement = connection.prepareStatement("DROP TABLE user")) {
            statement.executeUpdate();
        }
        connection.commit();
    }catch (Exception es){
            Util.RollBack();
        es.printStackTrace();
    }

    }

    public void saveUser(String name, String lastName, byte age) {
            try {
                Connection connection = Util.connect("myDS");
                try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                        "INSERT INTO  user (fname, lastName, age) VALUES (?,?,?)")) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setByte(3, age);
                    preparedStatement.executeUpdate();

                }
                connection.commit();
            } catch (Exception exception) {
                Util.RollBack();
                exception.printStackTrace();
            }
    }

    public void removeUserById(long id) {

        try {
            Connection connection = Util.connect("myDS");
            try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER WHERE id = ?")) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        }catch (Exception ec ) {
            Util.RollBack();
            ec.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
            Connection connection = Util.connect("myDS");
            try( Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT fname, lastName, age FROM user")) {
                while (resultSet.next()) {
                    User users = new User();
                    users.setName(resultSet.getString(1));
                    users.setLastName(resultSet.getString(2));
                    users.setAge(resultSet.getByte(3));
                    userList.add(users);
                }

            }
        }catch (Exception ec ){
            ec.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            Connection connection = Util.connect("myDS");
            try (PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE user")) {
                preparedStatement.executeUpdate();
            }
            connection.commit();
        }catch (Exception exception ){
            Util.RollBack();
            exception.printStackTrace();
        }
    }
}
