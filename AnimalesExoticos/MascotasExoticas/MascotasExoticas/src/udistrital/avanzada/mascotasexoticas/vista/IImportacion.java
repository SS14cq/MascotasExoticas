package udistrital.avanzada.mascotasexoticas.vista;

import java.util.List;

/**
 *
 * @author Steban
 * @version 1.0
 */
public interface IImportacion {

    List<String[]> leerRegistros(String rutaArchivo);
}
