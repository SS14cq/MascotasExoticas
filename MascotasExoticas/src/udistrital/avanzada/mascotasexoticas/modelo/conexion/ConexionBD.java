/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Singleton que gestiona una única conexión a la base de datos.
 */
public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    private static final String URLBD = "jdbc:mysql://localhost:3307/Animal";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    /**
     * Constructor privado que establece la conexión.
     */
    private ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URLBD, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve la instancia única de ConexionBD.
     * @return Instancia Singleton.
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Devuelve la conexión activa.
     * @return Objeto Connection.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión y elimina la instancia.
     */
    public static void desconectar() {
        instancia = null;
    }
}