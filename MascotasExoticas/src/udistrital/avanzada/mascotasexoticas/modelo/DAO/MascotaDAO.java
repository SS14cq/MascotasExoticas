/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.List;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;

/**
 *
 * @author sarit
 */

/**
 * Objeto de Acceso a Datos (DAO) para la entidad MascotaVO.
 * Se encarga de la comunicación con la base de datos.
 */

/**
 * Interfaz que define las operaciones CRUD para la entidad MascotaVO.
 * Forma parte del patrón DAO, permitiendo desacoplar la lógica de negocio del acceso a datos.
 */
public interface MascotaDAO {

    /**
     * Inserta una nueva mascota en la base de datos.
     * @param mascota MascotaVO a insertar.
     * @return true si la inserción fue exitosa.
     */
    boolean insertar(MascotaVO mascota);

    /**
     * Consulta mascotas por apodo.
     * @param apodo Apodo de la mascota.
     * @return Lista de mascotas que coinciden con el apodo.
     */
    List<MascotaVO> consultarPorApodo(String apodo);

    /**
     * Consulta mascotas por clasificación taxonómica.
     * @param clasificacion Clasificación a buscar.
     * @return Lista de mascotas que coinciden con la clasificación.
     */
    List<MascotaVO> consultarPorClasificacion(String clasificacion);

    /**
     * Consulta mascotas por familia biológica.
     * @param familia Familia a buscar.
     * @return Lista de mascotas que coinciden con la familia.
     */
    List<MascotaVO> consultarPorFamilia(String familia);

    /**
     * Consulta mascotas por tipo de alimento principal.
     * @param tipoAlimento Tipo de alimento a buscar.
     * @return Lista de mascotas que coinciden con el tipo de alimento.
     */
    List<MascotaVO> consultarPorTipoAlimento(String tipoAlimento);

    /**
     * Elimina una mascota de la base de datos por su apodo.
     * @param apodo Apodo de la mascota a eliminar.
     * @return true si la eliminación fue exitosa.
     */
    boolean eliminar(String apodo);

    /**
     * Modifica los datos de una mascota en la base de datos.
     * No se permite modificar familia, género ni especie.
     * @param mascota MascotaVO con los nuevos datos.
     * @return true si la modificación fue exitosa.
     */
    boolean modificar(MascotaVO mascota);

    /**
     * Lista todas las mascotas registradas en la base de datos.
     * @return Lista completa de mascotas.
     */
    List<MascotaVO> listarTodas();
}





