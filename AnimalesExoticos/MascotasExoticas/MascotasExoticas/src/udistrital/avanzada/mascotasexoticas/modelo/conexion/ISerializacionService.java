package udistrital.avanzada.mascotasexoticas.modelo.conexion;

import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import java.util.List;

/**
 * Interfaz para servicios de serialización y manejo de archivos.
 * Define operaciones para serialización de datos y persistencia en archivos.
 * 
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public interface ISerializacionService {
    
    /**
     * Serializa una lista de mascotas omitiendo el campo de alimento.
     * Cumple con el requerimiento específico del IDPYBA.
     *
     * @param mascotas Lista de mascotas a serializar
     * @param rutaArchivo Ruta del archivo donde se guardarán los datos serializados
     * @throws Exception Si ocurre algún error durante la serialización
     */
    void serializarSinAlimento(List<MascotaVO> mascotas, String rutaArchivo) throws Exception;
    
    /**
     * Guarda el estado de las mascotas en un archivo de acceso aleatorio.
     * Utiliza formato de texto con separadores para persistencia.
     *
     * @param mascotas Lista de mascotas a guardar
     * @param rutaArchivo Ruta del archivo de acceso aleatorio
     * @throws Exception Si ocurre algún error durante el guardado
     */
    void guardarEstadoRandomAccess(List<MascotaVO> mascotas, String rutaArchivo) throws Exception;
}