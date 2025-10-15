package udistrital.avanzada.mascotasexoticas.control;
import udistrital.avanzada.mascotasexoticas.modelo.DAO.ICRUDMascota;

/**
 * Interfaz que define las operaciones de negocio para la gestión de mascotas exóticas.
 * Proporciona métodos para CRUD, consultas y operaciones de serialización.
 * 
 * @author Sofia 
 * @author Steban
 * @since 12-10-2025
 * @version 1.0 
 */
public interface IControlMascota extends ICRUDMascota{
    
    /**
     * Serializa todas las mascotas omitiendo el campo de alimento.
     * Cumple con el requerimiento del IDPYBA.
     *
     * @param rutaArchivo Ruta donde se guardará el archivo serializado
     * @return true si la serialización fue exitosa, false en caso contrario
     */
    boolean serializarMascotasSinAlimento(String rutaArchivo);
    
    /**
     * Guarda el estado actual de las mascotas en un archivo de acceso aleatorio.
     * Persiste el estado después de todas las operaciones realizadas.
     *
     * @param rutaArchivo Ruta donde se guardará el archivo de estado
     * @return true si el guardado fue exitoso, false en caso contrario
     */
    boolean guardarEstadoMascotas(String rutaArchivo);
    
    /**
     * Verifica si existe una mascota con el apodo especificado.
     *
     * @param apodo Apodo a verificar
     * @return true si existe una mascota con ese apodo, false en caso contrario
     */
    boolean existeMascotaPorApodo(String apodo);
}