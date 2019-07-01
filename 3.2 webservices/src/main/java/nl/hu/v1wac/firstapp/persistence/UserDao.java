package nl.hu.v1wac.firstapp.persistence;

public interface UserDao {
    String findRoleForUser(String name, String pass);
}

