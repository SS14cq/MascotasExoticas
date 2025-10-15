
package udistrital.avanzada.mascotasexoticas.modelo.DAO;

import java.util.List;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;

/**
 * Interfaz que define las operaciones CRUD y de consulta específicas.
 * Proporciona métodos para CRUD y consultas.
 *
 * @author Steban
 * @version 1.0
 * @since 13-10-2025
 */
public interface ICRUDMascota {

    /**
     * Adiciona una nueva mascota exótica al sistema. Realiza validaciones para
     * evitar duplicados exactos.
     *
     * @param mascota Objeto MascotaVO con los datos de la mascota a adicionar
     * @return true si la mascota fue adicionada exitosamente, false en caso
     * contrario
     * @throws IllegalArgumentException si ya existe una mascota con las mismas
     * características
     */
    boolean adicionarMascota(MascotaVO mascota);

    /**
     * Modifica los datos de una mascota existente. Solo permite modificar
     * nombre, clasificación y tipo de alimento.
     *
     * @param mascota Objeto MascotaVO con los datos actualizados
     * @return true si la modificación fue exitosa, false en caso contrario
     * @throws IllegalArgumentException si no existe una mascota con el apodo
     * proporcionado
     */
    boolean modificarMascota(MascotaVO mascota);

    /**
     * Elimina una mascota del sistema por su apodo.
     *
     * @param apodo Apodo único de la mascota a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    boolean eliminarMascota(String apodo);

    /**
     * Obtiene todas las mascotas registradas en el sistema.
     *
     * @return Lista de todas las mascotas existentes
     */
    List<MascotaVO> listarTodasMascotas();

    /**
     * Consulta mascotas por su apodo.
     *
     * @param apodo Apodo de la mascota a buscar
     * @return Lista de mascotas que coinciden con el apodo
     */
    List<MascotaVO> consultarPorApodo(String apodo);

    /**
     * Consulta mascotas por clasificación taxonómica.
     *
     * @param clasificacion Clasificación a buscar (Mamífero, Reptil, etc.)
     * @return Lista de mascotas que pertenecen a la clasificación especificada
     */
    List<MascotaVO> consultarPorClasificacion(String clasificacion);

    /**
     * Consulta mascotas por familia biológica.
     *
     * @param familia Familia taxonómica a buscar
     * @return Lista de mascotas que pertenecen a la familia especificada
     */
    List<MascotaVO> consultarPorFamilia(String familia);

    /**
     * Consulta mascotas por tipo de alimento.
     *
     * @param alimento Tipo de alimento a buscar
     * @return Lista de mascotas que consumen el tipo de alimento especificado
     */
    List<MascotaVO> consultarPorAlimento(String alimento);

    /**
     * Serializa todas las mascotas omitiendo el campo de alimento. Cumple con
     * el requerimiento del IDPYBA.
     *
     * @param rutaArchivo Ruta donde se guardará el archivo serializado
     * @return true si la serialización fue exitosa, false en caso contrario
     */
}
