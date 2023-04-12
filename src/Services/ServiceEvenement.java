package Services;

import Entities.Evenement;
import utils.Myconnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement implements IServiceEvenement {

    public static Evenement getEventById(Integer eventId) throws SQLException {
        String requete = "SELECT * FROM evenement WHERE id = ?";
        PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(requete);
        pstmt.setInt(1, eventId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt(1));
            e.setNom(rs.getString(2));
            e.setDate_evenement(rs.getDate("date_evenement"));
            e.setLieu(rs.getString(4));
            e.setDescription(rs.getString(5));
            return e;
        }
        return null;
    }

    @Override
    public boolean ajouterEvenement(Evenement evenement) {
        try {
            String query = "INSERT INTO evenement(nom, description, lieu,image, date_evenement) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
            pstmt.setString(1, evenement.getNom());
            pstmt.setString(2, evenement.getDescription());
            pstmt.setString(3, evenement.getLieu());
            pstmt.setString(4, evenement.getImageUrl());
            pstmt.setDate(5, new Date(evenement.getDate_evenement().getTime()));
            pstmt.executeUpdate();

            System.out.println("Evenement ajouté!");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean modifierEvenement(Evenement evenement) {
        try {
            String query = "UPDATE evenement SET nom=?, description=?, lieu=?, image=?, date_evenement=? WHERE id=?";
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
            pstmt.setInt(6, evenement.getId());
            pstmt.setString(1, evenement.getNom());
            pstmt.setString(2, evenement.getDescription());
            pstmt.setString(3, evenement.getLieu());
            pstmt.setString(4, evenement.getImageUrl());
            pstmt.setDate(5, new Date(evenement.getDate_evenement().getTime()));

            pstmt.executeUpdate();

            System.out.println("Evenement modifié!");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean supprimerEvenement(int idEvenement) {
        try {
            String requete = "DELETE FROM evenement where id=" + String.valueOf(idEvenement) + "";
            PreparedStatement pst = Myconnexion.getCnx().prepareStatement(requete);
            pst.execute();
            System.out.println("Evenement supprimé");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public List<Evenement> afficherEvenements() throws SQLException {

        List<Evenement> listEvenements = new ArrayList<>();
        String requete = "SELECT * FROM evenement";
        Statement stmt = Myconnexion.getCnx().createStatement();
        ResultSet rs = stmt.executeQuery(requete);

        while (rs.next()) {
            Evenement evenement = new Evenement(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getString("lieu"),
                    rs.getString("image"),
                    rs.getDate("date_evenement")
            );
            listEvenements.add(evenement);
        }

        return listEvenements;
    }

    public void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + ex.getMessage());
        }
    }


   /* public Evenement chercherEvenementParNom(String evenementNom) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Evenement evenement = null;

        try {
            conn = Myconnexion.getInstance().getCnx();
            String query = "SELECT * FROM evenement WHERE nom=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, evenementNom);
            rs = pst.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                String lieu = rs.getString("lieu");
                String image = rs.getString("image");
                Date date_evenement = rs.getDate("date_evenement");

                evenement = new Evenement(id, nom, description, lieu, image, date_evenement);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la recherche de l'événement par nom : " + ex.getMessage());
        } finally {
            closeConnection(conn, pst, rs);
        }

        return evenement;
    }*/
/*
    private Evenement getEvenementById(int eventid) throws SQLException {
        String requete = "SELECT * FROM evenement WHERE id = ?";
        PreparedStatement pstmt = Myconnexion.getInstance().getCnx().prepareStatement(requete);
        pstmt.setInt(1, eventid);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt(1));
            e.setNom(rs.getString(2));
            e.setDate_evenement(rs.getDate("date_evenement"));
            e.setLieu(rs.getString(4));
            e.setDescription(rs.getString(5));
            return e;
        }

        return null;



    }*/
}
