package com.esgi.greenrepack.obj;

import java.util.Date;

public class GreenCoin {
    Double montant;
    Date dateExpiration;
    int userId;

    public Double getMontant(){
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
