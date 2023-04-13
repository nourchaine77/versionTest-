package Services;

import Entities.Evenement;

import java.sql.SQLException;
import java.util.List;

public interface IServiceEvenement {
    boolean ajouterEvenement(Evenement evenement);

    boolean modifierEvenement(Evenement evenement);

    boolean supprimerEvenement(int idEvenement);

    List<Evenement> afficherEvenements() throws SQLException;


}
