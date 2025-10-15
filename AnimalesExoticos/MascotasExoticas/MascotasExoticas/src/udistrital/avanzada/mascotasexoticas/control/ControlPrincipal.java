package udistrital.avanzada.mascotasexoticas.control;

import java.io.File;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.AnimalVO;

import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.mascotasexoticas.vista.IRegistroVista;
import udistrital.avanzada.mascotasexoticas.vista.Importacion;

/**
 * <p>
 * Controlador principal de la aplicación de gestión de mascotas exóticas. Se
 * encarga de inicializar el sistema, leer y validar los registros desde un
 * archivo de propiedades, completar información faltante y lanzar la interfaz
 * gráfica de usuario.
 * </p>
 *
 * <p>
 * Implementa un flujo de carga inicial que:
 * <ul>
 * <li>Lee los registros desde un archivo {@code mascotas.properties}.</li>
 * <li>Solicita al usuario completar información incompleta.</li>
 * <li>Verifica si las mascotas ya existen en la base de datos.</li>
 * <li>Inserta nuevas mascotas en el sistema.</li>
 * <li>Inicia la ventana principal de la aplicación.</li>
 * </ul>
 * </p>
 *
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public class ControlPrincipal {

    /**
     * Controlador encargado de la lógica de negocio relacionada con las
     * mascotas.
     */
    private IRegistroVista vista;
    private IControlMascota controlMascota;
    private ControlVentana controlventana;
    private Importacion importacion;

    /**
     * Crea una nueva instancia de {@code ControlPrincipal} usando inyección de
     * dependencias.
     *
     * @param controlMascota instancia de la interfaz {@link IControlMascota}
     * para manejar operaciones de negocio.
     */
    public ControlPrincipal(IControlMascota controlMascota) {
        this.controlMascota = controlMascota;
    }

    /**
     * Crea una nueva instancia de {@code ControlPrincipal} usando la fábrica de
     * dependencias para obtener automáticamente la implementación de
     * {@link IControlMascota}.
     */
    public ControlPrincipal() {
        this(FabricaDependencias.getControlMascota());
        this.controlventana = new ControlVentana(this);
        this.importacion = new Importacion();
    }

    /**
     * Inicia la aplicación:
     * <ol>
     * <li>Ubica y lee el archivo de propiedades.</li>
     * <li>Valida y completa registros incompletos con una ventana modal.</li>
     * <li>Inserta las mascotas en la base de datos si no existen.</li>
     * <li>Lanza la interfaz gráfica principal.</li>
     * </ol>
     * Si el archivo de propiedades no se encuentra, muestra un mensaje de
     * error.
     */
    /**
     * Inicializa el sistema cargando los datos de mascotas desde un archivo de
     * propiedades.
     * <p>
     * Este método delega la lectura del archivo al servicio de importación y
     * solicita a la vista los datos faltantes cuando haya campos incompletos.
     * </p>
     *
     */
    public void iniciar() {
        File archivo = controlventana.seleccionarArchivoProperties();
        if (archivo == null) {
            controlventana.mostrarMensaje("No se seleccionó ningún archivo.");
            return;
        }
        List<String[]> registros = importacion.leerRegistros(archivo.getAbsolutePath());
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

            if (incompleto && vista != null) {
                String[] datosCompletos = vista.completarRegistroIncompleto(
                        nombre, apodo, clasificacion, familia, genero, especie, alimento
                );
                if (datosCompletos == null) {
                    continue; // usuario canceló
                }
                nombre = datosCompletos[0];
                apodo = datosCompletos[1];
                clasificacion = datosCompletos[2];
                familia = datosCompletos[3];
                genero = datosCompletos[4];
                especie = datosCompletos[5];
                alimento = datosCompletos[6];
            }

            MascotaVO mascota = new MascotaVO(
                    new AnimalVO(nombre, clasificacion, familia, genero, especie, alimento),
                    apodo
            );
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
                // Loguear si es necesario, pero continuar
            }
        }
        controlventana.mostrarMensaje(insertadas + " mascotas cargadas correctamente.");
        if (vista != null) {
            vista.mostrarMensaje(insertadas + " mascotas fueron registradas exitosamente.");
        }
    }

    /**
     * Obtiene un campo específico de un arreglo, verificando que no sea nulo ni
     * esté fuera de rango.
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

    /**
     * Obtiene la lista completa de mascotas registradas en el sistema.
     *
     * @return Lista de mascotas, o una lista vacía si no hay registros.
     */
    public List<MascotaVO> listaMascotas() {
        return controlMascota.listarTodasMascotas();
    }

    /**
     * Registra una nueva mascota en el sistema.
     *
     * @param nombre Nombre común.
     * @param apodo Apodo de la mascota.
     * @param clasificacion Clasificación biológica.
     * @param familia Familia biológica.
     * @param genero Género biológico.
     * @param especie Especie biológica.
     * @param alimento Tipo de alimento.
     * @return true si fue registrada correctamente; false si ya existía o hubo
     * error.
     */
    public boolean adicionarMascota(String nombre, String apodo, String clasificacion,
            String familia, String genero, String especie, String alimento) {
        MascotaVO nueva = new MascotaVO(
                new AnimalVO(nombre, clasificacion, familia, genero, especie, alimento),
                apodo
        );
        return controlMascota.adicionarMascota(nueva);
    }

    /**
     * Consulta las mascotas registradas filtrando por apodo.
     *
     * @param apodo Apodo de la mascota a buscar.
     * @return Lista de mascotas con el apodo indicado; lista vacía si no hay
     * coincidencias.
     */
    public List<MascotaVO> consultarPorApodo(String apodo) {
        return controlMascota.consultarPorApodo(apodo);
    }

    /**
     * Consulta las mascotas registradas filtrando por clasificación.
     *
     * @param clasificacion Clasificación (ej. Mamífero, Ave, Reptil, etc.)
     * @return Lista de mascotas con la clasificación indicada.
     */
    public List<MascotaVO> consultarPorClasificacion(String clasificacion) {
        return controlMascota.consultarPorClasificacion(clasificacion);
    }

    /**
     * Consulta las mascotas registradas filtrando por familia biológica.
     *
     * @param familia Familia (ej. Felidae, Canidae, etc.)
     * @return Lista de mascotas pertenecientes a la familia especificada.
     */
    public List<MascotaVO> consultarPorFamilia(String familia) {
        return controlMascota.consultarPorFamilia(familia);
    }

    /**
     * Consulta las mascotas registradas filtrando por tipo de alimento.
     *
     * @param alimento Tipo de alimento (Herbívoro, Carnívoro, Omnívoro, etc.)
     * @return Lista de mascotas con ese tipo de alimentación.
     */
    public List<MascotaVO> consultarPorAlimento(String alimento) {
        return controlMascota.consultarPorAlimento(alimento);
    }

    /**
     * Gestiona la modificación de una mascota existente. Solo se pueden
     * modificar nombre, clasificación y tipo de alimento.
     *
     * @param apodo Apodo de la mascota a modificar.
     * @param nuevoNombre Nuevo nombre común (opcional).
     * @param nuevaClasificacion Nueva clasificación (opcional).
     * @param nuevoAlimento Nuevo tipo de alimento (opcional).
     * @return true si la modificación fue exitosa, false si no se encontró la
     * mascota.
     */
    public boolean modificarMascota(String apodo, String nuevoNombre, String nuevaClasificacion, String nuevoAlimento) {
        List<MascotaVO> existentes = consultarPorApodo(apodo);
        if (existentes.isEmpty()) {
            return false;
        }

        MascotaVO mascotaActual = existentes.get(0);

        // Crear el nuevo objeto de dominio (responsabilidad de este controlador)
        AnimalVO animalModificado = new AnimalVO(
                nuevoNombre.isEmpty() ? mascotaActual.getNombre() : nuevoNombre,
                (nuevaClasificacion == null || nuevaClasificacion.isBlank())
                ? mascotaActual.getClasificacion() : nuevaClasificacion,
                mascotaActual.getFamilia(),
                mascotaActual.getGenero(),
                mascotaActual.getEspecie(),
                (nuevoAlimento == null || nuevoAlimento.isBlank())
                ? mascotaActual.getAlimento() : nuevoAlimento
        );

        MascotaVO mascotaModificada = new MascotaVO(animalModificado, apodo);
        return controlMascota.modificarMascota(mascotaModificada);
    }

    /**
     * Gestiona la eliminación de una mascota existente en el sistema.
     *
     * @param apodo Apodo de la mascota a eliminar.
     * @return true si se eliminó exitosamente, false si no se encontró o hubo
     * error.
     */
    public boolean eliminarMascota(String apodo) {
        List<MascotaVO> existentes = controlMascota.consultarPorApodo(apodo);

        if (existentes.isEmpty()) {
            return false;
        }

        MascotaVO mascotaAEliminar = existentes.get(0);

        // Aquí podrías registrar logs, validar permisos, etc.
        return controlMascota.eliminarMascota(mascotaAEliminar.getApodo());
    }

    public boolean serializarMascotasSinAlimento(File archivo) {
        return controlMascota.serializarMascotasSinAlimento(archivo.getAbsolutePath());
    }

    public boolean guardarEstadoMascotas() {
        File archivo = new File("estado_mascotas_random.dat");
        return controlMascota.guardarEstadoMascotas(archivo.getAbsolutePath());
    }

}
