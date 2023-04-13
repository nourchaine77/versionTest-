package Services;

import Entities.Evenement;
import Entities.Publicite;
import utils.Myconnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicePublicite implements IServicPublicite{



    @Override
    public boolean ajouterPublicite(Publicite p) {
        try {
            String query = "INSERT INTO publicite(nom, image, date_Debut, date_Fin, evenement_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
            pstmt.setString(1, p.getNom());
            pstmt.setString(2, p.getImage());

            if (p.getDateDebut() != null) {
                pstmt.setDate(3, new java.sql.Date(p.getDateDebut().getTime()));
            } else {
                pstmt.setDate(3, null);
            }

            if (p.getDateFin() != null) {
                pstmt.setDate(4, new java.sql.Date(p.getDateFin().getTime()));
            } else {
                pstmt.setDate(4, null);
            }

            pstmt.setInt(5, p.getIdevenement());
            pstmt.executeUpdate();

            System.out.println("Publicite ajoutée!");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean modifierPublicite(Publicite p) {
        try {
            String query = "UPDATE publicite SET nom=?, image=?, date_Debut=?, date_Fin=? ,evenement_id=? WHERE id=?";
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
            pstmt.setInt(6, p.getId());
            pstmt.setString(1, p.getNom());
            pstmt.setString(2, p.getImage());
            pstmt.setDate(3, p.getDateDebut() != null ? new java.sql.Date(p.getDateDebut().getTime()) : null);
            pstmt.setDate(4, p.getDateFin() != null ? new java.sql.Date(p.getDateFin().getTime()) : null);
            pstmt.setInt(5, p.getIdevenement());
            pstmt.executeUpdate();

            System.out.println("Publicite modifiée!");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }


    @Override
    public boolean supprimerPublicite(int idPublicite) {
        try {
            String requete = "DELETE FROM publicite where id=" + String.valueOf(idPublicite) + "";
            PreparedStatement pst = Myconnexion.getCnx().prepareStatement(requete);
            pst.execute();
            System.out.println("Publicite supprimée");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public List<Publicite> afficherPublicites() throws SQLException {
        List<Publicite> listPublicites = new ArrayList<>();
        String requete = "SELECT * FROM publicite";
        Statement stmt = Myconnexion.getCnx().createStatement();
        ResultSet rs = stmt.executeQuery(requete);
        while (rs.next()) {
            Publicite publicite=new Publicite(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("image"),
                    rs.getDate("date_Debut"),
                    rs.getDate("date_Fin"),
            rs.getInt("evenement_id")
            );
            listPublicites.add(publicite);
        }

        return listPublicites;
    }


    public List<Evenement> getEvenements() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String requete = "SELECT * FROM evenement ";
        try {
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(requete);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Evenement e = new Evenement();
                e.setId(rs.getInt(1));
                e.setNom(rs.getString(2));
                e.setDescription(rs.getString(3));
                e.setLieu(rs.getString(4));
                e.setImage(rs.getString(5));
                e.setDate_evenement(rs.getDate("date_evenement"));
                evenements.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception here, such as showing an error message to the user
        }
        return evenements;


    }

    public boolean hasPublicite(int idEvenement) throws SQLException {
        String query = "SELECT * FROM publicite WHERE evenement_id = ?";
        PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
        pstmt.setInt(1, idEvenement);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    }


