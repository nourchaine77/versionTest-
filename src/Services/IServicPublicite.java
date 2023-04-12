package Services;

import Entities.Publicite;

import java.sql.SQLException;
import java.util.List;

public interface IServicPublicite {
    boolean ajouterPublicite(Publicite p);

    boolean modifierPublicite(Publicite p);

    boolean supprimerPublicite(int idPublicite);

    List<Publicite> afficherPublicites() throws SQLException;
}
