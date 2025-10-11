/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.control;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import udistrital.avanzada.mascotasexoticas.modelo.AnimalVO;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ConexionBD;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ConexionPropiedades;


/**
 *
 * @author juanr
 */


/**
 * Controlador que gestiona la construcción de mascotas y operaciones sobre la base de datos.
 */
public class ControlMascota {
    private Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public ControlMascota() {
        conexion = ConexionBD.getInstancia().getConexion();
    }

    /**
     * Construye una lista de MascotaVO a partir de los datos del archivo .properties.
     * @param rutaArchivo Ruta del archivo .properties.
     * @return Lista de mascotas construidas.
     */
    public List<MascotaVO> construirMascotas(String rutaArchivo) {
        List<String[]> registros = ConexionPropiedades.leerRegistros(rutaArchivo);
        List<MascotaVO> mascotas = new ArrayList<>();

        for (String[] partes : registros) {
            if (partes.length >= 7) {
                AnimalVO animal = new AnimalVO(partes[0], partes[2], partes[3], partes[4], partes[5], partes[6]);
                MascotaVO mascota = new MascotaVO(animal, partes[1]);
                mascotas.add(mascota);
            }
        }

        return mascotas;
    }

    /**
     * Inserta una mascota en la base de datos.
     * @param mascota Mascota a insertar.
     * @return true si se insertó correctamente.
     */
    public boolean insertarMascota(MascotaVO mascota) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Consulta mascotas por clasificación.
     * @param clasificacion Clasificación a buscar.
     * @return Lista de mascotas que coinciden.
     */
    public List<MascotaVO> consultarPorClasificacion(String clasificacion) {
        List<MascotaVO> resultado = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE clasificacion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, clasificacion);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AnimalVO animal = new AnimalVO(
                    rs.getString("nombre_comun"),
                    rs.getString("clasificacion"),
                    rs.getString("familia"),
                    rs.getString("genero"),
                    rs.getString("especie"),
                    rs.getString("tipo_alimento")
                );
                MascotaVO mascota = new MascotaVO(animal, rs.getString("apodo"));
                resultado.add(mascota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Métodos similares para eliminar, modificar, consultar por familia, tipo de alimento, etc.
}

