package pl.coderslab;

import pl.coderslab.UserDAO.UserDAO;
import pl.coderslab.models.User;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        User user = new User("Lukasz", "l@wp.pl", "Haslo");
        User user2 = new User("Piotr", "p@wp.pl", "Haslo");
        UserDAO dao = new UserDAO();
        dao.create(user);
        dao.create(user2);

        User user3 = dao.read(20);
        User user100 = dao.read(100);

        // modyfikacja istniejącego użytkownika
        user3.setName("Witold");
        dao.update(user3);
        user3 = dao.read(2);

        // modyfikacja nieistniejącego użytkownika
        User notExisting = new User();
        notExisting.setId(1000);
        notExisting.setPassword("haslo");
        notExisting.setPasswordSalt("salt");
        notExisting.setEmail("n@wp.pl");
        notExisting.setName("Nie ma");

        dao.update(notExisting);

        // pobranie wszystkich użytkowników
        List<User> allUsers = dao.findAll();
        System.out.println("All users: ");
        System.out.println(allUsers);

        // usunięcie użytkownika
        dao.delete(user.getId());
        dao.delete(user2.getId());
    }
}
