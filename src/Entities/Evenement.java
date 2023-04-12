package Entities;

import utils.Myconnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Evenement {

    private int id;
    private String nom;
    private String description;
    private String lieu;
    private String image;
    private Date date_evenement;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Evenement(int id, String nom, String description, String lieu, String image, Date date_evenement) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.image = image;
        this.date_evenement = date_evenement;
    }

    public Evenement() {

    }

    @Override
    public String toString() {
        return nom;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }

    public Date getDate_evenement() {
        return date_evenement;
    }

    public void setDate_evenement(Date date_evenement) {
        this.date_evenement = date_evenement;
    }

    public List<Evenement> getAll() {
        List<Evenement> evenements = new ArrayList<>();
int id=1;
        try (Connection connection = Myconnexion.getCnx();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM evenement WHERE id = ?")){
             statement.setInt(1,id);
             ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");
                String lieu = resultSet.getString("lieu");
                String image = resultSet.getString("image");
                Date date_evenement = resultSet.getDate("date_evenement");

                Evenement evenement = new Evenement(id, nom, description, lieu, image, date_evenement);
                evenements.add(evenement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evenements;
    }

}


