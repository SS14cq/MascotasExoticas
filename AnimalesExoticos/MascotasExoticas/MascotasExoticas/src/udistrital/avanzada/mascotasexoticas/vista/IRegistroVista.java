
package udistrital.avanzada.mascotasexoticas.vista;

/**
 * Interfaz que define las operaciones de la vista relacionadas con la captura o
 * confirmación de datos de registro de mascotas.
 *
 * @author Steban
 * @version 1.0
 */
public interface IRegistroVista {

    /**
     * Solicita al usuario completar los datos de una mascota con información
     * incompleta.
     *
     * @param nombre
     * @param apodo
     * @param clasificacion
     * @param familia
     * @param genero
     * @param especie
     * @param alimento
     * @return Un arreglo con los campos completados o {@code null} si el
     * usuario cancela.
     */
    String[] completarRegistroIncompleto(String nombre, String apodo, String clasificacion,
            String familia, String genero, String especie, String alimento);

    /**
     * Muestra un mensaje informativo.
     * @param mensaje
     */
    void mostrarMensaje(String mensaje);
}
