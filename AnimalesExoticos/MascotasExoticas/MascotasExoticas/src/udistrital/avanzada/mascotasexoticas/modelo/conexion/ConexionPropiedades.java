/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo.conexion;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



/**
 *
 * @author juanr
 */


/**
 * Clase utilitaria que lee el archivo .properties y devuelve los registros como arreglos de texto.
 */
public class ConexionPropiedades {

    /**
     * Lee el archivo .properties y devuelve los registros como listas de String[].
     * @param rutaArchivo Ruta del archivo .properties.
     * @return Lista de registros crudos.
     */
    public static List<String[]> leerRegistros(String rutaArchivo) {
        List<String[]> registros = new ArrayList<>();
        try (InputStream input = new FileInputStream(rutaArchivo)) {
            Properties props = new Properties();
            props.load(input);

            for (String key : props.stringPropertyNames()) {
                registros.add(props.getProperty(key).split(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registros;
    }
}

