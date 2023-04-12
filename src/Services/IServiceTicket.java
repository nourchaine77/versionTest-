package Services;

import Entities.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface IServiceTicket {
    boolean ajouterTicket(Ticket t);

    boolean modifierTicket(Ticket t);

    boolean supprimerTicket(int idTicket);

    List<Ticket> afficherTickets() throws SQLException;
}
