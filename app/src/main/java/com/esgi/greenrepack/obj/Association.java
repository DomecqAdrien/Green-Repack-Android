package com.esgi.greenrepack.obj;

import java.util.ArrayList;

public class Association {
    int id;
    String Denomination_Social;
    String Adresse;
    String Numero_RNA;

    public int getId() {
        return id;
    }

    public String getDenomination_Social() {
        return Denomination_Social;
    }

    public void setDenomination_Social(String denomination_Social) {
        Denomination_Social = denomination_Social;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getNumero_RNA() {
        return Numero_RNA;
    }

}
