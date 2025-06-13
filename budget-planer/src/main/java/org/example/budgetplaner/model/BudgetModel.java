package org.example.budgetplaner.model;

public class BudgetModel {
    private String id;
    private Float betrag;
    private int monat;
    private int jahr;
    private int kategorieId;

    public BudgetModel(String id, Float betrag, int monat, int jahr, int kategorieId) {
        setId(id);
        setBetrag(betrag);
        setMonat(monat);
        setJahr(jahr);
        setKategorieId(kategorieId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKategorieId() {
        return kategorieId;
    }

    public void setKategorieId(int kategorieId) {
        this.kategorieId = kategorieId;
    }

    public int getMonat() {
        return monat;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public Float getBetrag() {
        return betrag;
    }

    public void setBetrag(Float betrag) {
        this.betrag = betrag;
    }
}
