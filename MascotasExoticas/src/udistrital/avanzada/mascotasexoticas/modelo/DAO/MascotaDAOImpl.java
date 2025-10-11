/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo.DAO;

/**
 *
 * @author juanr
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.mascotasexoticas.modelo.AnimalVO;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ConexionBD;

/**
 * Implementación del patrón DAO para la entidad MascotaVO.
 * Esta clase se encarga de realizar operaciones CRUD sobre la base de datos.
 */
public class MascotaDAOImpl implements MascotaDAO {

    private Connection conexion;

    /**
     * Constructor que obtiene la conexión desde la clase Singleton ConexionBD.
     */
    public MascotaDAOImpl() {
        conexion = ConexionBD.getInstancia().getConexion();
    }

    /**
     * Inserta una nueva mascota en la base de datos.
     * @param mascota MascotaVO a insertar.
     * @return true si la inserción fue exitosa.
     */
    @Override
    public boolean insertar(MascotaVO mascota) {
        String sql = "INSERT INTO mascotas (nombre_comun, apodo, clasificacion, familia, genero, especie, tipo_alimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Consulta mascotas por apodo.
     * @param apodo Apodo a buscar.
     * @return Lista de mascotas que coinciden.
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
     * Consulta mascotas por clasificación.
     */
    @Override
    public List<MascotaVO> consultarPorClasificacion(String clasificacion) {
        return consultarPorCampo("clasificacion", clasificacion);
    }

    /**
     * Consulta mascotas por familia.
     */
    @Override
    public List<MascotaVO> consultarPorFamilia(String familia) {
        return consultarPorCampo("familia", familia);
    }

    /**
     * Consulta mascotas por tipo de alimento.
     */
    @Override
    public List<MascotaVO> consultarPorTipoAlimento(String tipoAlimento) {
        return consultarPorCampo("tipo_alimento", tipoAlimento);
    }

    /**
     * Elimina una mascota por apodo.
     */
    @Override
    public boolean eliminar(String apodo) {
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
     * Modifica los datos de una mascota, excepto familia, género y especie.
     */
    @Override
    public boolean modificar(MascotaVO mascota) {
        String sql = "UPDATE mascotas SET nombre_comun = ?, clasificacion = ?, tipo_alimento = ? WHERE apodo = ?";
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
     * Lista todas las mascotas registradas en la base de datos.
     */
    @Override
    public List<MascotaVO> listarTodas() {
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
     * Método auxiliar para construir un objeto MascotaVO desde un ResultSet.
     */
    private MascotaVO construirMascotaDesdeResultSet(ResultSet rs) throws SQLException {
        AnimalVO animal = new AnimalVO(
            rs.getString("nombre_comun"),
            rs.getString("clasificacion"),
            rs.getString("familia"),
            rs.getString("genero"),
            rs.getString("especie"),
            rs.getString("tipo_alimento")
        );
        return new MascotaVO(animal, rs.getString("apodo"));
    }

    /**
     * Método auxiliar para consultas por campo específico.
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