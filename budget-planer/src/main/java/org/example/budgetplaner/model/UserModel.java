package org.example.budgetplaner.model;

public class UserModel {
    private int id;
    private String name;
    private String geburtstag;
    private String email;
    private String passwort;
    private String profilBildPfad;

    public UserModel(int id, String name, String geburtstag, String email, String passwort, String profilBildPfad) {
        this.id = id;
        this.name = name;
        this.geburtstag = geburtstag;
        this.email = email;
        this.passwort = passwort;
        this.profilBildPfad = profilBildPfad;
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
    public String getGeburtstag() {
        return geburtstag;
    }
    public void setGeburtstag(String geburtstag) {
        this.geburtstag = geburtstag;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswort() {
        return passwort;
    }
    public String getProfilBildPfad() {
        return profilBildPfad;
    }

    public void setProfilBildPfad(String profilBildPfad) {
        this.profilBildPfad = profilBildPfad;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}

