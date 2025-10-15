
package udistrital.avanzada.mascotasexoticas.vista;

import java.util.List;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ConexionPropiedades;

/**
 *
 * @author Steban
 * @version 1.0
 */
public class Importacion implements IImportacion{
    
    @Override
    public List<String[]> leerRegistros(String rutaArchivo) {
        return ConexionPropiedades.leerRegistros(rutaArchivo);
    }
}

