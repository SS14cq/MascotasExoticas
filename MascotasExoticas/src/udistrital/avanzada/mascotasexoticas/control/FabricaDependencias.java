package udistrital.avanzada.mascotasexoticas.control;

import udistrital.avanzada.mascotasexoticas.modelo.DAO.MascotaDAO;
import udistrital.avanzada.mascotasexoticas.modelo.DAO.MascotaDAOImpl;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ConexionSerializacion;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ISerializacionService;

/**
 * Fábrica centralizada para la creación e inyección de dependencias.
 * Implementa el patrón Factory para desacoplar la creación de objetos.
 * 
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public class FabricaDependencias {
    
    private static MascotaDAO mascotaDAO;
    private static ISerializacionService serializacionService;
    private static IControlMascota controlMascota;
    
    /**
     * Obtiene la instancia del DAO de mascotas.
     * Implementa inicialización perezosa (lazy initialization).
     *
     * @return Instancia de MascotaDAO
     */
    public static MascotaDAO getMascotaDAO() {
        if (mascotaDAO == null) {
            mascotaDAO = new MascotaDAOImpl();
        }
        return mascotaDAO;
    }
    
    /**
     * Obtiene la instancia del servicio de serialización.
     * Implementa inicialización perezosa (lazy initialization).
     *
     * @return Instancia de ISerializacionService
     */
    public static ISerializacionService getSerializacionService() {
        if (serializacionService == null) {
            serializacionService = new ConexionSerializacion();
        }
        return serializacionService;
    }
    
    /**
     * Obtiene la instancia del controlador de mascotas.
     * Implementa inicialización perezosa e inyección de dependencias.
     *
     * @return Instancia de IControlMascota
     */
    public static IControlMascota getControlMascota() {
        if (controlMascota == null) {
            controlMascota = new ControlMascota(
                getMascotaDAO(),
                getSerializacionService()
            );
        }
        return controlMascota;
    }
}