package udistrital.avanzada.mascotasexoticas.control;

import udistrital.avanzada.mascotasexoticas.vista.VistaMascota;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.AnimalVO;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ConexionPropiedades;
import udistrital.avanzada.mascotasexoticas.vista.RegistroDialog;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Controlador principal de la aplicación de gestión de mascotas exóticas.
 * Se encarga de inicializar el sistema, leer y validar los registros desde
 * un archivo de propiedades, completar información faltante y lanzar
 * la interfaz gráfica de usuario.
 * </p>
 *
 * <p>
 * Implementa un flujo de carga inicial que:
 * <ul>
 *     <li>Lee los registros desde un archivo {@code mascotas.properties}.</li>
 *     <li>Solicita al usuario completar información incompleta.</li>
 *     <li>Verifica si las mascotas ya existen en la base de datos.</li>
 *     <li>Inserta nuevas mascotas en el sistema.</li>
 *     <li>Inicia la ventana principal de la aplicación.</li>
 * </ul>
 * </p>
 *
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public class ControlPrincipal {

    /** Controlador encargado de la lógica de negocio relacionada con las mascotas. */
    private final IControlMascota controlMascota;

    /**
     * Crea una nueva instancia de {@code ControlPrincipal} usando
     * inyección de dependencias.
     *
     * @param controlMascota instancia de la interfaz {@link IControlMascota}
     *                       para manejar operaciones de negocio.
     */
    public ControlPrincipal(IControlMascota controlMascota) {
        this.controlMascota = controlMascota;
    }

    /**
     * Crea una nueva instancia de {@code ControlPrincipal} usando
     * la fábrica de dependencias para obtener automáticamente
     * la implementación de {@link IControlMascota}.
     */
    public ControlPrincipal() {
        this(FabricaDependencias.getControlMascota());
    }

    /**
     * Inicia la aplicación:
     * <ol>
     *     <li>Ubica y lee el archivo de propiedades.</li>
     *     <li>Valida y completa registros incompletos con una ventana modal.</li>
     *     <li>Inserta las mascotas en la base de datos si no existen.</li>
     *     <li>Lanza la interfaz gráfica principal.</li>
     * </ol>
     * Si el archivo de propiedades no se encuentra, muestra un mensaje de error.
     */
    public void iniciar() {
        URL resource = getClass().getClassLoader().getResource("resources/Specs/data/mascotas.properties");
        if (resource == null) {
            JOptionPane.showMessageDialog(null,
                    "No se encontró el archivo de propiedades",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rutaProperties = resource.getFile();
        List<String[]> registros = ConexionPropiedades.leerRegistros(rutaProperties);
        List<MascotaVO> mascotasParaInsertar = new ArrayList<>();

        for (String[] campos : registros) {
            String nombre = obtenerCampo(campos, 0);
            String apodo = obtenerCampo(campos, 1);
            String clasificacion = obtenerCampo(campos, 2);
            String familia = obtenerCampo(campos, 3);
            String genero = obtenerCampo(campos, 4);
            String especie = obtenerCampo(campos, 5);
            String alimento = obtenerCampo(campos, 6);

            boolean incompleto = nombre.isEmpty() || apodo.isEmpty() || clasificacion.isEmpty()
                    || familia.isEmpty() || genero.isEmpty() || especie.isEmpty();

            if (incompleto) {
                RegistroDialog dialog = new RegistroDialog(
                        null, nombre, apodo, clasificacion, familia, genero, especie, alimento
                );
                dialog.setVisible(true);

                if (dialog.isCancelado()) {
                    continue;
                }

                nombre = dialog.getNombre();
                apodo = dialog.getApodo();
                clasificacion = dialog.getClasificacion();
                familia = dialog.getFamilia();
                genero = dialog.getGenero();
                especie = dialog.getEspecie();
                alimento = dialog.getAlimento();
            }

            AnimalVO animal = new AnimalVO(nombre, clasificacion, familia, genero, especie, alimento);
            MascotaVO mascota = new MascotaVO(animal, apodo);
            mascotasParaInsertar.add(mascota);
        }

        int insertadas = 0;
        for (MascotaVO nueva : mascotasParaInsertar) {
            try {
                if (!controlMascota.existeMascotaPorApodo(nueva.getApodo())) {
                    boolean resultado = controlMascota.adicionarMascota(nueva);
                    if (resultado) {
                        insertadas++;
                    }
                }
            } catch (Exception e) {
                // Se ignora el error para continuar con la siguiente mascota
            }
        }

        SwingUtilities.invokeLater(() -> {
            VistaMascota vista = new VistaMascota();
            new ControlVentana(vista, controlMascota);
            vista.setVisible(true);
        });
    }

    /**
     * Obtiene un campo específico de un arreglo, verificando que no sea nulo
     * ni esté fuera de rango.
     *
     * @param array arreglo de cadenas leído desde el archivo de propiedades.
     * @param index posición del campo a obtener.
     * @return valor del campo o cadena vacía si no existe.
     */
    private String obtenerCampo(String[] array, int index) {
        if (array != null && array.length > index && array[index] != null) {
            return array[index].trim();
        }
        return "";
    }
}
