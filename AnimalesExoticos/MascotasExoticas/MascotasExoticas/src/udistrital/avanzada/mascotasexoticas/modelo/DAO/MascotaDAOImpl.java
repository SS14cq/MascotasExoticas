
package udistrital.avanzada.mascotasexoticas.modelo.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.mascotasexoticas.modelo.AnimalVO;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ConexionBD;

/**
 * Implementación concreta de la interfaz {@link ICRUDMascota} utilizando JDBC.
 * <p>
 * Esta clase implementa el patrón DAO (Data Access Object), separando la lógica de
 * acceso a datos de la lógica de negocio. Permite realizar operaciones CRUD sobre la tabla
 * <code>mascotas</code> de la base de datos relacional configurada en {@link ConexionBD}.
 * </p>
 * 
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Establecer la comunicación con la base de datos mediante {@link java.sql.Connection}.</li>
 *   <li>Ejecutar sentencias SQL de inserción, eliminación, modificación y consulta.</li>
 *   <li>Transformar registros de la base de datos en objetos {@link MascotaVO}.</li>
 *   <li>Manejar excepciones SQL sin propagar detalles de implementación a otras capas.</li>
 * </ul>
 * 
 * @author Sofia
 * @autor Steban
 * @version 1.0
 * @since 12-10-2025
 */
public class MascotaDAOImpl implements ICRUDMascota {

    /** Conexión activa a la base de datos. */
    private final Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     * <p>
     * Utiliza el patrón Singleton definido en {@link ConexionBD} para garantizar
     * una única instancia de conexión compartida.
     * </p>
     */
    public MascotaDAOImpl() {
        conexion = ConexionBD.getInstancia().getConexion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean adicionarMascota(MascotaVO mascota) {
        String sql = "INSERT INTO mascotas (nombre, apodo, clasificacion, familia, genero, especie, alimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getApodo());
            stmt.setString(3, mascota.getClasificacion());
            stmt.setString(4, mascota.getFamilia());
            stmt.setString(5, mascota.getGenero());
            stmt.setString(6, mascota.getEspecie());
            stmt.setString(7, mascota.getAlimento());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // En un proyecto real se debe usar un Logger
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MascotaVO> consultarPorApodo(String apodo) {
        List<MascotaVO> resultado = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE apodo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, apodo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultado.add(construirMascotaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MascotaVO> consultarPorClasificacion(String clasificacion) {
        return consultarPorCampo("clasificacion", clasificacion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MascotaVO> consultarPorFamilia(String familia) {
        return consultarPorCampo("familia", familia);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MascotaVO> consultarPorAlimento(String tipoAlimento) {
        return consultarPorCampo("alimento", tipoAlimento);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eliminarMascota(String apodo) {
        String sql = "DELETE FROM mascotas WHERE apodo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, apodo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean modificarMascota(MascotaVO mascota) {
        String sql = "UPDATE mascotas SET nombre = ?, clasificacion = ?, alimento = ? WHERE apodo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, mascota.getNombre());
            stmt.setString(2, mascota.getClasificacion());
            stmt.setString(3, mascota.getAlimento());
            stmt.setString(4, mascota.getApodo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MascotaVO> listarTodasMascotas() {
        List<MascotaVO> resultado = new ArrayList<>();
        String sql = "SELECT * FROM mascotas";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultado.add(construirMascotaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Construye un objeto {@link MascotaVO} a partir de los datos obtenidos de un {@link ResultSet}.
     * <p>
     * Este método centraliza la lógica de mapeo entre los registros de la tabla y la representación
     * de objetos en memoria, evitando duplicación de código.
     * </p>
     *
     * @param rs ResultSet con la fila actual de datos.
     * @return Objeto {@link MascotaVO} con los datos del registro.
     * @throws SQLException si ocurre un error al leer los datos.
     */
    private MascotaVO construirMascotaDesdeResultSet(ResultSet rs) throws SQLException {
        AnimalVO animal = new AnimalVO(
            rs.getString("nombre"),
            rs.getString("clasificacion"),
            rs.getString("familia"),
            rs.getString("genero"),
            rs.getString("especie"),
            rs.getString("alimento")
        );
        return new MascotaVO(animal, rs.getString("apodo"));
    }

    /**
     * Ejecuta una consulta filtrando por un campo específico.
     * <p>
     * Método auxiliar utilizado para evitar duplicar código en consultas por
     * clasificación, familia o tipo de alimento.
     * </p>
     *
     * @param campo Nombre de la columna a filtrar.
     * @param valor Valor que debe tener el campo.
     * @return Lista de mascotas que coinciden con el filtro.
     */
    private List<MascotaVO> consultarPorCampo(String campo, String valor) {
        List<MascotaVO> resultado = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE " + campo + " = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, valor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                resultado.add(construirMascotaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
