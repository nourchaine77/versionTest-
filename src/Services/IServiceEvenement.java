package Services;

import Entities.Evenement;
import utils.Myconnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IServiceEvenement {
    boolean ajouterEvenement(Evenement evenement);

    boolean modifierEvenement(Evenement evenement);

    boolean supprimerEvenement(int idEvenement);

    List<Evenement> afficherEvenements() throws SQLException;


}
