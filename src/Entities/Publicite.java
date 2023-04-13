package Entities;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.Evenement;
import utils.Myconnexion;

public class Publicite {
    private int id;
    private String nom;
    private String image;
    private Date dateDebut;
    private Date dateFin;
    private int idevenement;

Evenement evenement =new Evenement();
    public Publicite(int id, String nom, String image, Date dateDebut, Date dateFin, int idevenement) {
        this.id = id;
        this.nom = nom;
        this.image = image;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idevenement = idevenement;
    }

    public Publicite() {
    }

    public Publicite(String nom, String image, Date dateDebut, Date dateFin, int idevenement) {
        this.nom = nom;
        this.image = image;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idevenement = idevenement;
    }

    @Override
    public String toString() {
        return "Publicite{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", idevenement=" + idevenement +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getIdevenement() {
        return idevenement;
    }

    public void setIdevenement(int idevenement) {
        this.idevenement = idevenement;
    }

    public Evenement getEvenement() {
        return evenement;
    }
}

