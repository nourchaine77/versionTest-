package Services;

import Entities.Ticket;
import utils.Myconnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceTicket implements IServiceTicket {

    @Override
    public boolean ajouterTicket(Ticket t) {
        try {
            String query = "INSERT INTO ticket( evenement_id, prix, type, etat,nbr_ticket) VALUES ( ?,?, ?, ?, ?)";
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
            pstmt.setFloat(1, t.getEvenement_id());
            pstmt.setFloat(2, t.getPrix());
            pstmt.setString(3, t.getType());
            pstmt.setBoolean(4, t.isEtat());
            pstmt.setInt(5, t.getNbrTicket());
            pstmt.executeUpdate();

            System.out.println("Ticket ajouté!");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean modifierTicket(Ticket t) {
        try {
            String query = "UPDATE ticket SET evenement_id=?, prix=?, type=?, etat=?, nbr_ticket=? WHERE id=?";
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
            pstmt.setInt(1, t.getEvenement_id());
            pstmt.setFloat(2, t.getPrix());

            pstmt.setString(3, t.getType());
            pstmt.setBoolean(4, t.isEtat());
            pstmt.setInt(5, t.getNbrTicket());

            pstmt.executeUpdate();

            System.out.println("Ticket modifié!");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean supprimerTicket(int idTicket) {
        try {
            String requete = "DELETE FROM ticket where id=" + String.valueOf(idTicket) + "";
            PreparedStatement pst = Myconnexion.getCnx().prepareStatement(requete);
            pst.execute();
            System.out.println("Ticket supprimé");

            return true;

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return false;
    }

    @Override
    public List<Ticket> afficherTickets() throws SQLException {
        List<Ticket> listTickets = new ArrayList<>();
        String requete = "select * from ticket";
        Statement stmt = Myconnexion.getCnx().createStatement();
        ResultSet rs = stmt.executeQuery(requete);

        while (rs.next()) {
            Ticket t = new Ticket();
            t.setId(rs.getInt(1));
            t.setEvenement_id(rs.getInt(2));

            t.setPrix(rs.getFloat(3));
            t.setType(rs.getString(4));

            t.setEtat(rs.getBoolean(5));
            t.setNbrTicket(rs.getInt(6));

            listTickets.add(t);
        }

        return listTickets;

    }

    public Ticket getTicketById(int id) {
        try {
            String query = "SELECT * FROM ticket WHERE id=?";
            PreparedStatement pstmt = Myconnexion.getCnx().prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setEvenement_id(rs.getInt("evenement_id"));
                ticket.setPrix(rs.getFloat("prix"));
                ticket.setType(rs.getString("type"));
                ticket.setEtat(rs.getBoolean("etat"));
                ticket.setNbrTicket(rs.getInt("nbr_ticket"));
                return ticket;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLSTATE: " + ex.getSQLState());
            System.out.println("VnedorError: " + ex.getErrorCode());
        }
        return new Ticket();
    }
}
