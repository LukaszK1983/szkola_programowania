package pl.coderslab.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String passwordSalt;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.hashPassword(password);
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hashPassword(password);
    }

    private void hashPassword(String password) {
        this.passwordSalt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, passwordSalt);
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordSalt='" + passwordSalt + '\'' +
                '}';
    }
}
