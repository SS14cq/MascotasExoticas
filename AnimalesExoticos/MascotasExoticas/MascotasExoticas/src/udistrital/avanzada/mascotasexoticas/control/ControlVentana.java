package udistrital.avanzada.mascotasexoticas.control;

import udistrital.avanzada.mascotasexoticas.vista.VistaMascota;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;

import java.io.File;
import java.util.List;
import udistrital.avanzada.mascotasexoticas.vista.RegistroDialog;

/**
 * Controlador encargado de gestionar los eventos de la interfaz gráfica
 * {@link VistaMascota}, coordinando la interacción entre la vista y la lógica
 * de negocio representada por {@link IControlMascota}.
 * <p>
 * Esta clase implementa las acciones de adicionar, modificar, eliminar, listar,
 * consultar y serializar mascotas exóticas, así como la carga inicial de datos
 * en los componentes visuales.
 * </p>
 *
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public class ControlVentana {

    /**
     * Referencia a la vista principal de la interfaz gráfica.
     */
    private final VistaMascota vista;

    /**
     * Controlador del flujo de informacion y puente entre vista y logica.
     */
    private final ControlPrincipal controlPrincipal;

    /**
     * Constructor principal con inyección de dependencias.
     *
     * @param controlPrincipal Controlador del flujo de informacion
     */
    public ControlVentana(ControlPrincipal controlPrincipal) {
        this.vista = new VistaMascota();
        this.controlPrincipal = controlPrincipal;
        inicializar();
    }

    /**
     * Constructor que usa la fábrica de dependencias para instanciar
     * {@link IControlMascota}.
     *
     * @param vista Vista de la interfaz gráfica
     *
     * public ControlVentana(VistaMascota vista) { this(vista,
     * FabricaDependencias.getControlMascota()); }
     *
     */
    /**
     * Inicializa el controlador configurando la lista inicial de mascotas y
     * agregando los listeners a los componentes de la interfaz.
     */
    private void inicializar() {
        cargarListaInicial();
        agregarListeners();
        vista.seleccionarArchivoProperties();
        vista.setVisible(true);
    }

    /**
     * Agrega todos los listeners para manejar los eventos de los botones de la
     * interfaz gráfica.
     */
    private void agregarListeners() {
        vista.getBtnAdicionar().addActionListener(e -> adicionarMascota());
        vista.getBtnModificar().addActionListener(e -> modificarMascota());
        vista.getBtnEliminar().addActionListener(e -> eliminarMascota());
        vista.getBtnListarTodas().addActionListener(e -> listarTodas());
        vista.getBtnLimpiar().addActionListener(e -> vista.limpiarCampos());
        vista.getBtnSerializar().addActionListener(e -> serializarSinAlimento());
        vista.getBtnSalir().addActionListener(e -> salirYGuardarEstado());
        vista.getBtnConsultarApodo().addActionListener(e -> consultarPorApodo());
        vista.getBtnConsultarClasificacion().addActionListener(e -> consultarPorClasificacion());
        vista.getBtnConsultarFamilia().addActionListener(e -> consultarPorFamilia());
        vista.getBtnConsultarAlimento().addActionListener(e -> consultarPorAlimento());
        vista.getBtnCargarMascota().addActionListener(e -> cargarMascotaSeleccionada());
        //vista.getBtnSeleccionarArchivo().addActionListener(e -> controlPrincipal.iniciar());
    }

    /**
     * Carga la lista inicial de mascotas en el combo box de la interfaz.
     */
    private void cargarListaInicial() {
        vista.getCmbMascotasRegistradas().removeAllItems();
        List<MascotaVO> lista = controlPrincipal.listaMascotas();
        for (MascotaVO m : lista) {
            vista.getCmbMascotasRegistradas().addItem(m.getApodo() + " - " + m.getNombre());
        }
    }

    /**
     * Adiciona una nueva mascota al sistema a partir de los datos ingresados en
     * la vista.
     * <p>
     * Este método realiza las validaciones de los campos obligatorios y delega
     * la creación y registro de la mascota al {@link ControlPrincipal},
     * cumpliendo con el patrón MVC y el principio de responsabilidad única
     * (SRP).
     * </p>
     *
     * <p>
     * <b>Responsabilidades:</b></p>
     * <ul>
     * <li>Extraer los valores de los componentes de la vista.</li>
     * <li>Validar los campos requeridos antes de enviar los datos.</li>
     * <li>Mostrar mensajes de éxito o error en la interfaz.</li>
     * </ul>
     *
     * <p>
     * La lógica de negocio y persistencia es responsabilidad del controlador
     * principal.</p>
     */
    private void adicionarMascota() {
        try {
            String nombre = vista.getTxtNombre().getText().trim();
            String apodo = vista.getTxtApodo().getText().trim();
            String clasificacion = (String) vista.getCmbClasificacion().getSelectedItem();
            String familia = vista.getTxtFamilia().getText().trim();
            String genero = vista.getTxtGenero().getText().trim();
            String especie = vista.getTxtEspecie().getText().trim();
            String alimento = (String) vista.getCmbTipoAlimento().getSelectedItem();

            // Validaciones
            if (nombre.isEmpty() || apodo.isEmpty()) {
                vista.mostrarError("Nombre y apodo son obligatorios.");
                return;
            }
            if (familia.isEmpty() || genero.isEmpty() || especie.isEmpty()) {
                vista.mostrarError("Familia, género y especie son obligatorios.");
                return;
            }
            if (clasificacion == null || clasificacion.isEmpty()) {
                vista.mostrarError("Seleccione una clasificación válida.");
                return;
            }
            if (alimento == null || alimento.isEmpty()) {
                vista.mostrarError("Seleccione un tipo de alimento válido.");
                return;
            }

            // Delegar la lógica al controlador principal
            boolean exito = controlPrincipal.adicionarMascota(
                    nombre, apodo, clasificacion, familia, genero, especie, alimento
            );

            // Respuesta a la vista
            if (exito) {
                vista.mostrarMensaje("Mascota adicionada exitosamente!");
                actualizarComboMascotas();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("No se pudo adicionar la mascota (ya existe o error en datos).");
            }

        } catch (Exception e) {
            vista.mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Envía los datos capturados en la vista al controlador principal para que
     * gestione la modificación de la mascota.
     */
    private void modificarMascota() {
        try {
            String apodo = vista.getTxtApodo().getText().trim();
            String nuevoNombre = vista.getTxtNombre().getText().trim();
            String nuevaClasificacion = (String) vista.getCmbClasificacion().getSelectedItem();
            String nuevoAlimento = (String) vista.getCmbTipoAlimento().getSelectedItem();

            // Validación mínima a nivel de vista
            if (apodo.isEmpty()) {
                vista.mostrarError("Debe indicar el apodo de la mascota a modificar.");
                return;
            }

            // Delegar al ControlPrincipal la gestión completa
            boolean exito = controlPrincipal.modificarMascota(apodo, nuevoNombre, nuevaClasificacion, nuevoAlimento);

            if (exito) {
                vista.mostrarMensaje("Mascota modificada exitosamente!");
                actualizarComboMascotas();
            } else {
                vista.mostrarError("No se pudo modificar la mascota (no existe o error en datos).");
            }

        } catch (Exception e) {
            vista.mostrarError("Error modificando mascota: " + e.getMessage());
        }
    }

    /**
     * Envía el apodo de la mascota a eliminar al controlador principal. La
     * confirmación y eliminación real son gestionadas fuera de esta clase.
     */
    private void eliminarMascota() {
        try {
            String apodo = vista.getTxtApodo().getText().trim();

            if (apodo.isEmpty()) {
                vista.mostrarError("Ingrese el apodo de la mascota a eliminar.");
                return;
            }

            // Confirmación del usuario antes de proceder
            if (!vista.confirmar("¿Está seguro de eliminar la mascota con apodo: " + apodo + "?")) {
                vista.mostrarMensaje("Eliminación cancelada.");
                return;
            }

            // Delegar la lógica al ControlPrincipal
            boolean exito = controlPrincipal.eliminarMascota(apodo);

            if (exito) {
                vista.mostrarMensaje("Mascota eliminada exitosamente!");
                actualizarComboMascotas();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("No se pudo eliminar la mascota (no existe o error en datos).");
            }

        } catch (Exception e) {
            vista.mostrarError("Error eliminando mascota: " + e.getMessage());
        }
    }

    /**
     * Solicita al controlador principal la lista completa de mascotas y
     * actualiza el área de resultados en la vista.
     */
    private void listarTodas() {
        try {
            vista.getTxtAreaResultados().setText("");

            List<MascotaVO> lista = controlPrincipal.listaMascotas(); // Delegación al ControlPrincipal

            if (lista == null || lista.isEmpty()) {
                vista.mostrarMensaje("No hay mascotas registradas.");
                return;
            }

            for (MascotaVO m : lista) {
                vista.agregarResultado(m.toString());
                vista.agregarResultado("-----------");
            }

        } catch (Exception e) {
            vista.mostrarError("Error al listar mascotas: " + e.getMessage());
        }
    }

    /**
     * Realiza una consulta de mascotas filtrando por apodo.
     */
    private void consultarPorApodo() {
        try {
            String apodo = vista.getTxtBuscarApodo().getText().trim();
            if (apodo.isEmpty()) {
                vista.mostrarError("Ingrese un apodo para buscar.");
                return;
            }

            vista.getTxtAreaResultados().setText("");
            List<MascotaVO> lista = controlPrincipal.consultarPorApodo(apodo);
            mostrarResultadosConsulta(lista, "Apodo: " + apodo);

        } catch (Exception e) {
            vista.mostrarError("Error consultando por apodo: " + e.getMessage());
        }
    }

    /**
     * Realiza una consulta de mascotas filtrando por clasificación.
     */
    private void consultarPorClasificacion() {
        try {
            String clasificacion = (String) vista.getCmbBuscarClasificacion().getSelectedItem();
            if (clasificacion == null || clasificacion.isEmpty()) {
                vista.mostrarError("Seleccione una clasificación para buscar.");
                return;
            }

            vista.getTxtAreaResultados().setText("");
            List<MascotaVO> lista = controlPrincipal.consultarPorClasificacion(clasificacion);
            mostrarResultadosConsulta(lista, "Clasificación: " + clasificacion);

        } catch (Exception e) {
            vista.mostrarError("Error consultando por clasificación: " + e.getMessage());
        }
    }

    /**
     * Realiza una consulta de mascotas filtrando por familia biológica.
     */
    private void consultarPorFamilia() {
        try {
            String familia = vista.getTxtBuscarFamilia().getText().trim();
            if (familia.isEmpty()) {
                vista.mostrarError("Ingrese una familia para buscar.");
                return;
            }

            vista.getTxtAreaResultados().setText("");
            List<MascotaVO> lista = controlPrincipal.consultarPorFamilia(familia);
            mostrarResultadosConsulta(lista, "Familia: " + familia);

        } catch (Exception e) {
            vista.mostrarError("Error consultando por familia: " + e.getMessage());
        }
    }

    /**
     * Realiza una consulta de mascotas filtrando por tipo de alimento.
     */
    private void consultarPorAlimento() {
        try {
            String alimento = (String) vista.getCmbBuscarAlimento().getSelectedItem();
            if (alimento == null || alimento.isEmpty()) {
                vista.mostrarError("Seleccione un tipo de alimento para buscar.");
                return;
            }

            vista.getTxtAreaResultados().setText("");
            List<MascotaVO> lista = controlPrincipal.consultarPorAlimento(alimento);
            mostrarResultadosConsulta(lista, "Alimento: " + alimento);

        } catch (Exception e) {
            vista.mostrarError("Error consultando por alimento: " + e.getMessage());
        }
    }

    /**
     * Carga en el formulario los datos de la mascota seleccionada.
     */
    private void cargarMascotaSeleccionada() {
        try {
            String sel = (String) vista.getCmbMascotasRegistradas().getSelectedItem();
            if (sel == null) {
                vista.mostrarError("No hay mascota seleccionada.");
                return;
            }

            String apodo = sel.split(" - ")[0];
            List<MascotaVO> encontrados = controlPrincipal.consultarPorApodo(apodo);

            if (encontrados.isEmpty()) {
                vista.mostrarError("No se encontró la mascota seleccionada.");
                return;
            }

            MascotaVO m = encontrados.get(0);
            vista.getTxtApodo().setText(m.getApodo());
            vista.getTxtNombre().setText(m.getNombre());
            vista.getTxtFamilia().setText(m.getFamilia());
            vista.getTxtGenero().setText(m.getGenero());
            vista.getTxtEspecie().setText(m.getEspecie());
            vista.getCmbClasificacion().setSelectedItem(m.getClasificacion());
            vista.getCmbTipoAlimento().setSelectedItem(m.getAlimento());

        } catch (Exception e) {
            vista.mostrarError("Error cargando mascota: " + e.getMessage());
        }
    }

    /**
     * Solicita al ControlPrincipal la serialización de mascotas sin el campo de
     * alimento.
     */
    private void serializarSinAlimento() {
        try {
            // Pedir ruta de guardado desde la vista
            File archivo = vista.seleccionarArchivoParaSerializar("mascotas_idpyba.ser");
            if (archivo == null) {
                vista.mostrarMensaje("Serialización cancelada.");
                return;
            }

            boolean exito = controlPrincipal.serializarMascotasSinAlimento(archivo);
            if (exito) {
                vista.mostrarMensaje("Serialización exitosa!");
            } else {
                vista.mostrarError("Error en la serialización.");
            }

        } catch (Exception ex) {
            vista.mostrarError("Error en serialización: " + ex.getMessage());
        }
    }

    /**
     * Guarda el estado actual de las mascotas antes de salir del sistema.
     */
    private void salirYGuardarEstado() {
        try {
            boolean exito = controlPrincipal.guardarEstadoMascotas();
            if (exito) {
                vista.mostrarMensaje("Estado guardado exitosamente.");
            } else {
                vista.mostrarError("No se pudo guardar el estado.");
            }
        } catch (Exception ex) {
            vista.mostrarError("Error guardando estado: " + ex.getMessage());
        } finally {
            System.exit(0);
        }
    }

    /**
     * Actualiza el combo box de mascotas registradas con los datos actuales.
     */
    private void actualizarComboMascotas() {
        vista.getCmbMascotasRegistradas().removeAllItems();
        List<MascotaVO> mascotas = controlPrincipal.listaMascotas();
        for (MascotaVO mascota : mascotas) {
            vista.getCmbMascotasRegistradas().addItem(
                    mascota.getApodo() + " - " + mascota.getNombre()
            );
        }
    }

    /**
     * Muestra los resultados de una consulta en el área de texto de la vista
     * con un formato legible para el usuario.
     *
     * @param resultados Lista de mascotas encontradas
     * @param criterio Criterio utilizado en la búsqueda
     */
    private void mostrarResultadosConsulta(List<MascotaVO> resultados, String criterio) {
        if (resultados.isEmpty()) {
            vista.agregarResultado("No se encontraron mascotas para el criterio: " + criterio);
            vista.mostrarMensaje("No se encontraron resultados.");
            return;
        }

        vista.agregarResultado("RESULTADOS DE CONSULTA");
        vista.agregarResultado("Criterio: " + criterio);
        vista.agregarResultado("Total encontrado: " + resultados.size() + " mascota(s)");
        vista.agregarResultado("==========================================");

        for (int i = 0; i < resultados.size(); i++) {
            MascotaVO mascota = resultados.get(i);
            vista.agregarResultado("Mascota #" + (i + 1));
            vista.agregarResultado("Nombre: " + mascota.getNombre());
            vista.agregarResultado("Apodo: " + mascota.getApodo());
            vista.agregarResultado("Clasificación: " + mascota.getClasificacion());
            vista.agregarResultado("Familia: " + mascota.getFamilia());
            vista.agregarResultado("Género: " + mascota.getGenero());
            vista.agregarResultado("Especie: " + mascota.getEspecie());
            vista.agregarResultado("Alimento: " + mascota.getAlimento());
            vista.agregarResultado("------------------------------------------");
        }

        vista.mostrarMensaje("Consulta completada.");
    }

    public File seleccionarArchivoProperties(){
        return vista.seleccionarArchivoProperties();
    }
    
    public void mostrarMensaje(String mensaje){
        vista.mostrarMensaje(mensaje);
    }
}
