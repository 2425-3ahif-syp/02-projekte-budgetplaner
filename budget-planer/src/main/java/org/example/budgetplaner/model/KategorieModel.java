package org.example.budgetplaner.model;

public class KategorieModel {
    private int id;
    private String name;
    private float prozentAnteil;

    public KategorieModel(int id, String name) {
        setId(id);
        setName(name);
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

    public float getProzentAnteil() {
        return prozentAnteil;
    }

    public void setProzentAnteil(float prozentAnteil) {
        this.prozentAnteil = prozentAnteil;
    }
}
