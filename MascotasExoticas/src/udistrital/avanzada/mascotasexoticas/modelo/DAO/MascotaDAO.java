/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo.DAO;

import java.util.List;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;

/**
 * Interfaz que define las operaciones CRUD y de consulta específicas para la entidad {@link MascotaVO}.
 * <p>
 * Forma parte de la capa de acceso a datos (DAO) del patrón MVC y tiene como propósito
 * desacoplar la lógica de negocio de la lógica de persistencia, facilitando la mantenibilidad y extensibilidad del sistema.
 * </p>
 * 
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Declarar operaciones de inserción, consulta, modificación y eliminación de registros de mascotas.</li>
 *   <li>Definir consultas específicas basadas en atributos biológicos de las mascotas.</li>
 *   <li>Proveer una interfaz genérica que pueda ser implementada con diferentes mecanismos de persistencia (JDBC, JPA, etc.).</li>
 * </ul>
 * 
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public interface MascotaDAO {

    /**
     * Inserta una nueva mascota en la base de datos.
     * <p>
     * Este método debe almacenar todos los atributos del objeto {@link MascotaVO} en la tabla correspondiente.
     * </p>
     *
     * @param mascota Objeto {@link MascotaVO} que contiene la información de la mascota a registrar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    boolean insertar(MascotaVO mascota);

    /**
     * Consulta mascotas registradas en la base de datos filtrando por apodo.
     * 
     * @param apodo Apodo de la mascota a buscar.
     * @return Lista de objetos {@link MascotaVO} que coinciden con el apodo proporcionado.
     * Si no se encuentran coincidencias, la lista debe estar vacía.
     */
    List<MascotaVO> consultarPorApodo(String apodo);

    /**
     * Consulta mascotas registradas filtrando por clasificación taxonómica.
     *
     * @param clasificacion Clasificación taxonómica de las mascotas a buscar (por ejemplo: Mamífero, Ave, Reptil).
     * @return Lista de mascotas que coinciden con la clasificación proporcionada.
     */
    List<MascotaVO> consultarPorClasificacion(String clasificacion);

    /**
     * Consulta mascotas registradas filtrando por familia biológica.
     *
     * @param familia Nombre de la familia biológica de las mascotas a buscar (por ejemplo: Felidae, Psittacidae).
     * @return Lista de mascotas que pertenecen a la familia indicada.
     */
    List<MascotaVO> consultarPorFamilia(String familia);

    /**
     * Consulta mascotas registradas filtrando por tipo de alimento principal.
     *
     * @param tipoAlimento Tipo de alimento que consume la mascota (por ejemplo: Herbívoro, Carnívoro, Omnívoro).
     * @return Lista de mascotas que coinciden con el tipo de alimento especificado.
     */
    List<MascotaVO> consultarPorTipoAlimento(String tipoAlimento);

    /**
     * Elimina una mascota de la base de datos según su apodo.
     * <p>
     * La eliminación debe basarse en un apodo único por mascota. Si no se encuentra la mascota,
     * no se realiza ningún cambio.
     * </p>
     *
     * @param apodo Apodo de la mascota que se desea eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    boolean eliminar(String apodo);

    /**
     * Modifica la información de una mascota existente en la base de datos.
     * <p>
     * Solo se permite modificar los campos: nombre, clasificación taxonómica y tipo de alimento.
     * No se deben modificar los campos relacionados con familia, género ni especie.
     * </p>
     *
     * @param mascota Objeto {@link MascotaVO} con los nuevos datos a actualizar.
     * @return {@code true} si la modificación fue exitosa, {@code false} en caso contrario.
     */
    boolean modificar(MascotaVO mascota);

    /**
     * Lista todas las mascotas registradas en la base de datos.
     *
     * @return Lista completa de objetos {@link MascotaVO} que representan todas las mascotas almacenadas.
     * Si no existen registros, debe retornar una lista vacía (no {@code null}).
     */
    List<MascotaVO> listarTodas();
}
