package udistrital.avanzada.mascotasexoticas.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación para la gestión de mascotas exóticas.
 * <p>
 * Esta clase forma parte de la capa de Vista en la arquitectura MVC.
 * Su responsabilidad es únicamente presentar la interfaz gráfica de usuario (GUI),
 * sin manejar lógica de negocio ni eventos. Los controladores son quienes interactúan
 * con esta clase para obtener datos y actualizar la interfaz.
 * </p>
 *
 * <p><strong>Características principales:</strong></p>
 * <ul>
 *     <li>Formulario para registrar o modificar mascotas exóticas.</li>
 *     <li>Panel de consultas por distintos criterios.</li>
 *     <li>Área de resultados para mostrar información.</li>
 *     <li>Botonera para operaciones CRUD, serialización y salida.</li>
 * </ul>
 *
 * @author
 *     Juan R, Sarit
 * @version
 *     1.0
 * @since
 *     12-10-2025
 */
public class VistaMascota extends JFrame {

    // ==================== Paneles principales ====================
    private JPanel panelPrincipal;
    private JPanel panelFormulario;
    private JPanel panelBotones;
    private JPanel panelConsulta;
    private JPanel panelResultados;

    // ==================== Campos de formulario ====================
    private JTextField txtNombre;
    private JTextField txtApodo;
    private JComboBox<String> cmbClasificacion;
    private JTextField txtFamilia;
    private JTextField txtGenero;
    private JTextField txtEspecie;
    private JComboBox<String> cmbTipoAlimento;

    // ==================== Campos de consulta ====================
    private JTextField txtBuscarApodo;
    private JComboBox<String> cmbBuscarClasificacion;
    private JTextField txtBuscarFamilia;
    private JComboBox<String> cmbBuscarAlimento;

    // ==================== Área de resultados ====================
    private JTextArea txtAreaResultados;
    private JScrollPane scrollResultados;

    // ==================== Botones de operación ====================
    private JButton btnAdicionar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnConsultarApodo;
    private JButton btnConsultarClasificacion;
    private JButton btnConsultarFamilia;
    private JButton btnConsultarAlimento;
    private JButton btnListarTodas;
    private JButton btnLimpiar;
    private JButton btnSerializar;
    private JButton btnSalir;

    // ==================== Lista desplegable de mascotas ====================
    private JComboBox<String> cmbMascotasRegistradas;
    private JButton btnCargarMascota;

    /**
     * Constructor principal de la vista.
     * Inicializa la ventana y todos los componentes gráficos.
     */
    public VistaMascota() {
        inicializarVentana();
        inicializarComponentes();
        configurarLayout();
    }

    /**
     * Configura las propiedades básicas de la ventana principal.
     * Incluye título, tamaño, cierre y comportamiento visual.
     */
    private void inicializarVentana() {
        setTitle("Gestión de Mascotas Exóticas - Universidad Distrital");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    /**
     * Inicializa los paneles y componentes gráficos que conforman la interfaz.
     */
    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(new Color(240, 248, 255));

        inicializarPanelFormulario();
        inicializarPanelConsulta();
        inicializarPanelResultados();
        inicializarPanelBotones();
    }

    /**
     * Crea e inicializa el panel de formulario para ingresar datos de las mascotas exóticas.
     */
    private void inicializarPanelFormulario() {
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                "Datos de la Mascota Exótica",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(70, 130, 180)
        ));
        panelFormulario.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre Común:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Apodo:"), gbc);
        gbc.gridx = 3;
        txtApodo = new JTextField(20);
        panelFormulario.add(txtApodo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Clasificación:"), gbc);
        gbc.gridx = 1;
        String[] clasificaciones = {"Reptil", "Mamífero", "Ave", "Anfibio", "Pez", "Invertebrado"};
        cmbClasificacion = new JComboBox<>(clasificaciones);
        panelFormulario.add(cmbClasificacion, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Familia:"), gbc);
        gbc.gridx = 3;
        txtFamilia = new JTextField(20);
        panelFormulario.add(txtFamilia, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        txtGenero = new JTextField(20);
        panelFormulario.add(txtGenero, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Especie:"), gbc);
        gbc.gridx = 3;
        txtEspecie = new JTextField(20);
        panelFormulario.add(txtEspecie, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Tipo de Alimento:"), gbc);
        gbc.gridx = 1;
        String[] alimentos = {"Lácteos", "Carnes", "Verduras", "Frutas", "Forrajes", "Cereales", "Leguminosas", "Omnívoros"};
        cmbTipoAlimento = new JComboBox<>(alimentos);
        panelFormulario.add(cmbTipoAlimento, gbc);

        gbc.gridx = 2; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Mascotas Registradas:"), gbc);
        gbc.gridx = 3;
        cmbMascotasRegistradas = new JComboBox<>();
        panelFormulario.add(cmbMascotasRegistradas, gbc);

        gbc.gridx = 4; gbc.gridy = 3;
        btnCargarMascota = new JButton("Cargar");
        btnCargarMascota.setBackground(new Color(100, 149, 237));
        btnCargarMascota.setForeground(Color.WHITE);
        panelFormulario.add(btnCargarMascota, gbc);
    }

    /**
     * Inicializa el panel para realizar consultas filtradas.
     */
    private void inicializarPanelConsulta() {
        panelConsulta = new JPanel(new GridBagLayout());
        panelConsulta.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 2),
                "Consultas",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(34, 139, 34)
        ));
        panelConsulta.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Consulta por apodo
        gbc.gridx = 0; gbc.gridy = 0;
        panelConsulta.add(new JLabel("Buscar por Apodo:"), gbc);
        gbc.gridx = 1;
        txtBuscarApodo = new JTextField(15);
        panelConsulta.add(txtBuscarApodo, gbc);
        gbc.gridx = 2;
        btnConsultarApodo = new JButton("Buscar");
        estilizarBoton(btnConsultarApodo, new Color(34, 139, 34));
        panelConsulta.add(btnConsultarApodo, gbc);

        // Consulta por clasificación
        gbc.gridx = 0; gbc.gridy = 1;
        panelConsulta.add(new JLabel("Buscar por Clasificación:"), gbc);
        gbc.gridx = 1;
        String[] clasificaciones = {"Reptil", "Mamífero", "Ave", "Anfibio", "Pez", "Invertebrado"};
        cmbBuscarClasificacion = new JComboBox<>(clasificaciones);
        panelConsulta.add(cmbBuscarClasificacion, gbc);
        gbc.gridx = 2;
        btnConsultarClasificacion = new JButton("Buscar");
        estilizarBoton(btnConsultarClasificacion, new Color(34, 139, 34));
        panelConsulta.add(btnConsultarClasificacion, gbc);

        // Consulta por familia
        gbc.gridx = 0; gbc.gridy = 2;
        panelConsulta.add(new JLabel("Buscar por Familia:"), gbc);
        gbc.gridx = 1;
        txtBuscarFamilia = new JTextField(15);
        panelConsulta.add(txtBuscarFamilia, gbc);
        gbc.gridx = 2;
        btnConsultarFamilia = new JButton("Buscar");
        estilizarBoton(btnConsultarFamilia, new Color(34, 139, 34));
        panelConsulta.add(btnConsultarFamilia, gbc);

        // Consulta por alimento
        gbc.gridx = 0; gbc.gridy = 3;
        panelConsulta.add(new JLabel("Buscar por Alimento:"), gbc);
        gbc.gridx = 1;
        String[] alimentos = {"Lácteos", "Carnes", "Verduras", "Frutas", "Forrajes", "Cereales", "Leguminosas", "Omnívoros"};
        cmbBuscarAlimento = new JComboBox<>(alimentos);
        panelConsulta.add(cmbBuscarAlimento, gbc);
        gbc.gridx = 2;
        btnConsultarAlimento = new JButton("Buscar");
        estilizarBoton(btnConsultarAlimento, new Color(34, 139, 34));
        panelConsulta.add(btnConsultarAlimento, gbc);
    }

    /**
     * Inicializa el panel donde se muestran los resultados de las operaciones.
     */
    private void inicializarPanelResultados() {
        panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
                "Resultados",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(255, 140, 0)
        ));
        panelResultados.setBackground(Color.WHITE);

        txtAreaResultados = new JTextArea(15, 50);
        txtAreaResultados.setEditable(false);
        txtAreaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaResultados.setBackground(new Color(255, 255, 240));
        scrollResultados = new JScrollPane(txtAreaResultados);

        panelResultados.add(scrollResultados, BorderLayout.CENTER);
    }

    /**
     * Inicializa el panel con los botones principales de operación.
     */
    private void inicializarPanelBotones() {
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(240, 248, 255));

        btnAdicionar = new JButton("Adicionar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListarTodas = new JButton("Listar Todas");
        btnLimpiar = new JButton("Limpiar");
        btnSerializar = new JButton("Serializar");
        btnSalir = new JButton("Salir");

        estilizarBoton(btnAdicionar, new Color(70, 130, 180));
        estilizarBoton(btnModificar, new Color(255, 165, 0));
        estilizarBoton(btnEliminar, new Color(220, 20, 60));
        estilizarBoton(btnListarTodas, new Color(147, 112, 219));
        estilizarBoton(btnLimpiar, new Color(128, 128, 128));
        estilizarBoton(btnSerializar, new Color(46, 139, 87));
        estilizarBoton(btnSalir, new Color(178, 34, 34));

        panelBotones.add(btnAdicionar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListarTodas);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSerializar);
        panelBotones.add(btnSalir);
    }

    /**
     * Aplica estilo visual a un botón.
     * @param boton Botón a estilizar.
     * @param color Color de fondo.
     */
    private void estilizarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Configura la disposición general de la ventana principal.
     * Combina los paneles en una estructura vertical y horizontal.
     */
    private void configurarLayout() {
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.setBackground(new Color(240, 248, 255));
        panelSuperior.add(panelFormulario);
        panelSuperior.add(panelConsulta);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelResultados, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // ==================== Métodos utilitarios ====================

    /**
     * Limpia todos los campos de texto y restablece los selectores a su valor por defecto.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtApodo.setText("");
        txtFamilia.setText("");
        txtGenero.setText("");
        txtEspecie.setText("");
        txtBuscarApodo.setText("");
        txtBuscarFamilia.setText("");
        cmbClasificacion.setSelectedIndex(0);
        cmbTipoAlimento.setSelectedIndex(0);
        cmbBuscarClasificacion.setSelectedIndex(0);
        cmbBuscarAlimento.setSelectedIndex(0);
    }

    /**
     * Muestra un texto en el área de resultados, reemplazando su contenido actual.
     * @param texto Texto a mostrar.
     */
    public void mostrarResultados(String texto) {
        txtAreaResultados.setText(texto);
    }

    /**
     * Agrega una línea de texto al área de resultados.
     * @param texto Texto a agregar.
     */
    public void agregarResultado(String texto) {
        txtAreaResultados.append(texto + "\n");
    }

    /**
     * Muestra un mensaje informativo al usuario mediante un JOptionPane.
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error al usuario mediante un JOptionPane.
     * @param mensaje Mensaje de error.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de confirmación al usuario.
     * @param mensaje Texto a mostrar en el cuadro.
     * @return true si el usuario confirma, false si cancela.
     */
    public boolean confirmar(String mensaje) {
        int resultado = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return resultado == JOptionPane.YES_OPTION;
    }

    // ==================== GETTERS ====================

    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtApodo() { return txtApodo; }
    public JComboBox<String> getCmbClasificacion() { return cmbClasificacion; }
    public JTextField getTxtFamilia() { return txtFamilia; }
    public JTextField getTxtGenero() { return txtGenero; }
    public JTextField getTxtEspecie() { return txtEspecie; }
    public JComboBox<String> getCmbTipoAlimento() { return cmbTipoAlimento; }
    public JTextField getTxtBuscarApodo() { return txtBuscarApodo; }
    public JComboBox<String> getCmbBuscarClasificacion() { return cmbBuscarClasificacion; }
    public JTextField getTxtBuscarFamilia() { return txtBuscarFamilia; }
    public JComboBox<String> getCmbBuscarAlimento() { return cmbBuscarAlimento; }
    public JTextArea getTxtAreaResultados() { return txtAreaResultados; }
    public JComboBox<String> getCmbMascotasRegistradas() { return cmbMascotasRegistradas; }

    public JButton getBtnAdicionar() { return btnAdicionar; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnConsultarApodo() { return btnConsultarApodo; }
    public JButton getBtnConsultarClasificacion() { return btnConsultarClasificacion; }
    public JButton getBtnConsultarFamilia() { return btnConsultarFamilia; }
    public JButton getBtnConsultarAlimento() { return btnConsultarAlimento; }
    public JButton getBtnListarTodas() { return btnListarTodas; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnSerializar() { return btnSerializar; }
    public JButton getBtnSalir() { return btnSalir; }
    public JButton getBtnCargarMascota() { return btnCargarMascota; }
}
