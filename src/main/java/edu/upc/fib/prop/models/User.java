package edu.upc.fib.prop.models;

import javafx.util.Pair;

import java.util.ArrayList;

public class User {

    private String email;
    private String name;
    private String password;
    private Boolean admin;
    private ArrayList <Pair<String, String>> favorites = new ArrayList<>();
    private ArrayList <Pair<String, String>> property = new ArrayList<>();

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

    public void setAdmin(Boolean admin) {this.admin = admin; }

    public void addFavorites(String author, String titol) {favorites.add(new Pair<>(author,titol));}

    public void addProperty(String author, String titol) {property.add(new Pair<>(author,titol));}

    public void deleteFavorites(String author, String titol) {favorites.add(new Pair<>(author,titol);}

    public void deleteProperty(String author, String titol) {property.add(new Pair<>(author,titol));}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email != null ? email.equals(user.email) : user.email == null
                && (name != null ? name.equals(user.name) : user.name == null
                && (password != null ? password.equals(user.password) : user.password == null
                && (admin != null ? admin.equals(user.admin) : user.admin == null)));
    }
}
