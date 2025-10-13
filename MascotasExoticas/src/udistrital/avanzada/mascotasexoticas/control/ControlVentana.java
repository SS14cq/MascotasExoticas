package udistrital.avanzada.mascotasexoticas.control;

import udistrital.avanzada.mascotasexoticas.vista.VistaMascota;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.AnimalVO;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * Controlador encargado de gestionar los eventos de la interfaz gráfica
 * {@link VistaMascota}, coordinando la interacción entre la vista y la lógica
 * de negocio representada por {@link IControlMascota}.
 * <p>
 * Esta clase implementa las acciones de adicionar, modificar, eliminar,
 * listar, consultar y serializar mascotas exóticas, así como la carga inicial
 * de datos en los componentes visuales.
 * </p>
 *
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public class ControlVentana {

    /** Referencia a la vista principal de la interfaz gráfica. */
    private final VistaMascota vista;

    /** Controlador de la lógica de negocio de mascotas. */
    private final IControlMascota controlMascota;

    /**
     * Constructor principal con inyección de dependencias.
     *
     * @param vista Vista de la interfaz gráfica
     * @param controlMascota Controlador de la lógica de negocio
     */
    public ControlVentana(VistaMascota vista, IControlMascota controlMascota) {
        this.vista = vista;
        this.controlMascota = controlMascota;
        inicializar();
    }

    /**
     * Constructor que usa la fábrica de dependencias para instanciar
     * {@link IControlMascota}.
     *
     * @param vista Vista de la interfaz gráfica
     */
    public ControlVentana(VistaMascota vista) {
        this(vista, FabricaDependencias.getControlMascota());
    }

    /**
     * Inicializa el controlador configurando la lista inicial de mascotas
     * y agregando los listeners a los componentes de la interfaz.
     */
    private void inicializar() {
        cargarListaInicial();
        agregarListeners();
    }

    /**
     * Agrega todos los listeners para manejar los eventos de los botones
     * de la interfaz gráfica.
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
    }

    /**
     * Carga la lista inicial de mascotas en el combo box de la interfaz.
     */
    private void cargarListaInicial() {
        vista.getCmbMascotasRegistradas().removeAllItems();
        List<MascotaVO> lista = controlMascota.listarTodasMascotas();
        for (MascotaVO m : lista) {
            vista.getCmbMascotasRegistradas().addItem(m.getApodo() + " - " + m.getNombre());
        }
    }

    /**
     * Adiciona una nueva mascota al sistema a partir de los datos ingresados en la vista.
     * Valida campos obligatorios antes de enviarlos al controlador de negocio.
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

            if (nombre.isEmpty() || apodo.isEmpty()) {
                vista.mostrarError("Nombre y apodo son obligatorios.");
                return;
            }
            if (familia.isEmpty() || genero.isEmpty() || especie.isEmpty()) {
                vista.mostrarError("Familia, género y especie son obligatorios.");
                return;
            }

            AnimalVO animal = new AnimalVO(nombre, clasificacion, familia, genero, especie, alimento);
            MascotaVO mascota = new MascotaVO(animal, apodo);

            boolean exito = controlMascota.adicionarMascota(mascota);

            if (exito) {
                vista.mostrarMensaje("Mascota adicionada exitosamente!");
                actualizarComboMascotas();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("No se pudo adicionar la mascota.");
            }
        } catch (Exception e) {
            vista.mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Modifica los datos de una mascota existente en el sistema.
     * Solo permite modificar nombre, clasificación y tipo de alimento.
     */
    private void modificarMascota() {
        try {
            String apodo = vista.getTxtApodo().getText().trim();
            if (apodo.isEmpty()) {
                vista.mostrarError("Debe indicar el apodo de la mascota a modificar.");
                return;
            }

            List<MascotaVO> existentes = controlMascota.consultarPorApodo(apodo);
            if (existentes.isEmpty()) {
                vista.mostrarError("No se encontró una mascota con ese apodo.");
                return;
            }

            MascotaVO mascotaActual = existentes.get(0);
            String nuevoNombre = vista.getTxtNombre().getText().trim();
            String nuevaClasificacion = (String) vista.getCmbClasificacion().getSelectedItem();
            String nuevoAlimento = (String) vista.getCmbTipoAlimento().getSelectedItem();

            AnimalVO animalModificado = new AnimalVO(
                    nuevoNombre.isEmpty() ? mascotaActual.getNombre() : nuevoNombre,
                    nuevaClasificacion == null ? mascotaActual.getClasificacion() : nuevaClasificacion,
                    mascotaActual.getFamilia(),
                    mascotaActual.getGenero(),
                    mascotaActual.getEspecie(),
                    nuevoAlimento == null ? mascotaActual.getAlimento() : nuevoAlimento
            );

            MascotaVO mascotaModificada = new MascotaVO(animalModificado, apodo);
            boolean exito = controlMascota.modificarMascota(mascotaModificada);

            if (exito) {
                vista.mostrarMensaje("Mascota modificada exitosamente!");
                actualizarComboMascotas();
            } else {
                vista.mostrarError("No se pudo modificar la mascota.");
            }
        } catch (Exception e) {
            vista.mostrarError("Error modificando mascota: " + e.getMessage());
        }
    }

    /**
     * Elimina una mascota del sistema previa confirmación del usuario.
     */
    private void eliminarMascota() {
        try {
            String apodo = vista.getTxtApodo().getText().trim();
            if (apodo.isEmpty()) {
                vista.mostrarError("Ingrese el apodo de la mascota a eliminar.");
                return;
            }

            List<MascotaVO> existentes = controlMascota.consultarPorApodo(apodo);
            if (existentes.isEmpty()) {
                vista.mostrarError("No existe una mascota con el apodo: " + apodo);
                return;
            }

            MascotaVO mascotaAEliminar = existentes.get(0);
            String mensajeConfirmacion = String.format(
                    "¿Está seguro de eliminar la siguiente mascota?\n\n" +
                            "Nombre: %s\nApodo: %s\nClasificación: %s\nFamilia: %s\nGénero: %s\nEspecie: %s",
                    mascotaAEliminar.getNombre(),
                    mascotaAEliminar.getApodo(),
                    mascotaAEliminar.getClasificacion(),
                    mascotaAEliminar.getFamilia(),
                    mascotaAEliminar.getGenero(),
                    mascotaAEliminar.getEspecie()
            );

            if (!vista.confirmar(mensajeConfirmacion)) {
                vista.mostrarMensaje("Eliminación cancelada.");
                return;
            }

            boolean exito = controlMascota.eliminarMascota(apodo);
            if (exito) {
                vista.mostrarMensaje("Mascota eliminada exitosamente!");
                actualizarComboMascotas();
                vista.limpiarCampos();
            } else {
                vista.mostrarError("No se pudo eliminar la mascota.");
            }
        } catch (Exception e) {
            vista.mostrarError("Error eliminando mascota: " + e.getMessage());
        }
    }

    /**
     * Lista todas las mascotas registradas y las muestra en el área de resultados.
     */
    private void listarTodas() {
        vista.getTxtAreaResultados().setText("");
        List<MascotaVO> lista = controlMascota.listarTodasMascotas();
        if (lista.isEmpty()) {
            vista.mostrarMensaje("No hay mascotas registradas.");
            return;
        }
        for (MascotaVO m : lista) {
            vista.agregarResultado(m.toString());
            vista.agregarResultado("-----------");
        }
    }

    /**
     * Realiza una consulta de mascotas filtrando por apodo.
     */
    private void consultarPorApodo() {
        String apodo = vista.getTxtBuscarApodo().getText().trim();
        if (apodo.isEmpty()) {
            vista.mostrarError("Ingrese un apodo para buscar.");
            return;
        }
        vista.getTxtAreaResultados().setText("");
        List<MascotaVO> lista = controlMascota.consultarPorApodo(apodo);
        mostrarResultadosConsulta(lista, "Apodo: " + apodo);
    }

    /**
     * Realiza una consulta de mascotas filtrando por clasificación.
     */
    private void consultarPorClasificacion() {
        String clasificacion = (String) vista.getCmbBuscarClasificacion().getSelectedItem();
        if (clasificacion == null || clasificacion.isEmpty()) {
            vista.mostrarError("Seleccione una clasificación para buscar.");
            return;
        }
        vista.getTxtAreaResultados().setText("");
        List<MascotaVO> lista = controlMascota.consultarPorClasificacion(clasificacion);
        mostrarResultadosConsulta(lista, "Clasificación: " + clasificacion);
    }

    /**
     * Realiza una consulta de mascotas filtrando por familia biológica.
     */
    private void consultarPorFamilia() {
        String familia = vista.getTxtBuscarFamilia().getText().trim();
        if (familia.isEmpty()) {
            vista.mostrarError("Ingrese una familia para buscar.");
            return;
        }
        vista.getTxtAreaResultados().setText("");
        List<MascotaVO> lista = controlMascota.consultarPorFamilia(familia);
        mostrarResultadosConsulta(lista, "Familia: " + familia);
    }

    /**
     * Realiza una consulta de mascotas filtrando por tipo de alimento.
     */
    private void consultarPorAlimento() {
        String alimento = (String) vista.getCmbBuscarAlimento().getSelectedItem();
        if (alimento == null || alimento.isEmpty()) {
            vista.mostrarError("Seleccione un tipo de alimento para buscar.");
            return;
        }
        vista.getTxtAreaResultados().setText("");
        List<MascotaVO> lista = controlMascota.consultarPorAlimento(alimento);
        mostrarResultadosConsulta(lista, "Alimento: " + alimento);
    }

    /**
     * Carga en el formulario los datos de la mascota seleccionada en el combo box.
     */
    private void cargarMascotaSeleccionada() {
        String sel = (String) vista.getCmbMascotasRegistradas().getSelectedItem();
        if (sel == null) {
            vista.mostrarError("No hay mascota seleccionada.");
            return;
        }

        String apodo = sel.split(" - ")[0];
        List<MascotaVO> encontrados = controlMascota.consultarPorApodo(apodo);
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
    }

    /**
     * Serializa todas las mascotas omitiendo el campo de alimento,
     * cumpliendo con el requerimiento de IDPYBA.
     */
    private void serializarSinAlimento() {
        try {
            List<MascotaVO> mascotas = controlMascota.listarTodasMascotas();
            if (mascotas.isEmpty()) {
                vista.mostrarError("No hay mascotas registradas para serializar.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Serializar Mascotas (Sin Alimento) - IDPYBA");
            fileChooser.setSelectedFile(new File("mascotas_idpyba.ser"));
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Archivos Serializados (*.ser)", "ser"));

            int resultado = fileChooser.showSaveDialog(vista);
            if (resultado != JFileChooser.APPROVE_OPTION) {
                vista.mostrarMensaje("Serialización cancelada.");
                return;
            }

            File archivo = fileChooser.getSelectedFile();
            if (archivo.exists() && !vista.confirmar("El archivo ya existe. ¿Desea sobrescribirlo?")) {
                return;
            }

            boolean exito = controlMascota.serializarMascotasSinAlimento(archivo.getAbsolutePath());
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
     * Guarda el estado actual de las mascotas en un archivo de acceso aleatorio
     * antes de cerrar la aplicación.
     */
    private void salirYGuardarEstado() {
        try {
            List<MascotaVO> mascotas = controlMascota.listarTodasMascotas();
            File archivo = new File("estado_mascotas_random.dat");
            boolean exito = controlMascota.guardarEstadoMascotas(archivo.getAbsolutePath());
            if (exito) {
                vista.mostrarMensaje("Estado guardado exitosamente.");
            } else {
                vista.mostrarError("No se pudo guardar el estado.");
            }
            System.exit(0);
        } catch (Exception ex) {
            vista.mostrarError("Error guardando estado: " + ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Actualiza el combo box de mascotas registradas con los datos actuales.
     */
    private void actualizarComboMascotas() {
        vista.getCmbMascotasRegistradas().removeAllItems();
        List<MascotaVO> mascotas = controlMascota.listarTodasMascotas();
        for (MascotaVO mascota : mascotas) {
            vista.getCmbMascotasRegistradas().addItem(mascota.getApodo() + " - " + mascota.getNombre());
        }
    }

    /**
     * Muestra los resultados de una consulta en el área de texto de la vista
     * con un formato legible para el usuario.
     *
     * @param resultados Lista de mascotas encontradas
     * @param criterio   Criterio utilizado en la búsqueda
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
}
