package com.esgi.greenrepack.obj;

public class Utilisateur {

    int id;
    String Nom;
    String Prenom;

    public int getId() {
        return id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }
}
