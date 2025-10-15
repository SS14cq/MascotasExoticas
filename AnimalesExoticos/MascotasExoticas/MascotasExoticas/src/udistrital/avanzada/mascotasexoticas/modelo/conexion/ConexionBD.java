
package udistrital.avanzada.mascotasexoticas.modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Singleton responsable de gestionar una única conexión activa a la base de datos.
 * <p>
 * Esta clase centraliza la creación y obtención de la conexión a la base de datos MySQL,
 * evitando que múltiples instancias de conexión sean abiertas innecesariamente en la aplicación.
 * </p>
 * 
 * <p><b>Características principales:</b></p>
 * <ul>
 *   <li>Utiliza el patrón Singleton para garantizar una sola instancia de conexión.</li>
 *   <li>Proporciona métodos para obtener y cerrar la conexión.</li>
 *   <li>Evita la duplicación de código de conexión en otras clases.</li>
 * </ul>
 * 
 * @author Juan
 * @version 1.0
 */
public class ConexionBD {

    /** Instancia única de la clase (Singleton). */
    private static ConexionBD instancia;

    /** Objeto que representa la conexión activa con la base de datos. */
    private Connection conexion;

    /** URL de conexión a la base de datos. */
    private static final String URLBD = "jdbc:mysql://localhost/animales";

    /** Usuario para autenticación en la base de datos. */
    private static final String USUARIO = "root";

    /** Contraseña para autenticación en la base de datos. */
    private static final String CONTRASENA = "";

    /**
     * Constructor privado que inicializa la conexión a la base de datos.
     * <p>
     * Carga el driver JDBC de MySQL y establece la conexión con los parámetros configurados.
     * </p>
     */
    private ConexionBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URLBD, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la instancia única de {@link ConexionBD}.
     * <p>
     * Si no existe una instancia, la crea e inicializa la conexión.
     * </p>
     *
     * @return Instancia única de la clase ConexionBD.
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Devuelve la conexión activa con la base de datos.
     *
     * @return Objeto {@link Connection} con la conexión activa.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión activa con la base de datos y elimina la instancia Singleton.
     * <p>
     * Este método debe llamarse al finalizar la ejecución de la aplicación
     * para liberar correctamente los recursos de la base de datos.
     * </p>
     */
    public static void desconectar() {
        if (instancia != null && instancia.conexion != null) {
            try {
                instancia.conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instancia = null;
            }
        }
    }
}
