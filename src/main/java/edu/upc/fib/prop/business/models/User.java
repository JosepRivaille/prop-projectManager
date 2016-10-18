package edu.upc.fib.prop.business.models;

public class User {

    private String email;
    private String name;
    private String password;
    private Boolean admin;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.admin = Boolean.FALSE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
