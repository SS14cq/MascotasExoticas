package udistrital.avanzada.mascotasexoticas.vista;

import javax.swing.*;
import java.awt.*;

/**
 * <b>RegistroDialog</b><br>
 * Ventana de diálogo modal que permite al usuario completar o modificar
 * los datos de registro de una mascota exótica.
 * <p>
 * Este formulario contiene campos para nombre, apodo, clasificación taxonómica,
 * familia, género, especie y tipo de alimento.
 * Incluye validación básica para campos obligatorios (nombre y apodo).
 * </p>
 *
 * <p>Forma parte de la capa de vista (MVC), encargada únicamente
 * de la interacción con el usuario, sin lógica de negocio.</p>
 *
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public class RegistroDialog extends JDialog {

    /** Campo de texto para el nombre de la mascota. */
    private final JTextField txtNombre = new JTextField(20);

    /** Campo de texto para el apodo de la mascota. */
    private final JTextField txtApodo = new JTextField(20);

    /** ComboBox para seleccionar la clasificación taxonómica. */
    private final JComboBox<String> cmbClasificacion;

    /** Campo de texto para la familia biológica. */
    private final JTextField txtFamilia = new JTextField(20);

    /** Campo de texto para el género biológico. */
    private final JTextField txtGenero = new JTextField(20);

    /** Campo de texto para la especie. */
    private final JTextField txtEspecie = new JTextField(20);

    /** ComboBox para seleccionar el tipo de alimento. */
    private final JComboBox<String> cmbAlimento;

    /** Bandera que indica si el usuario canceló la operación. */
    private boolean cancelado = false;

    /**
     * Crea e inicializa el cuadro de diálogo de registro de mascota.
     *
     * @param owner       Ventana principal que posee este diálogo.
     * @param nombre      Nombre de la mascota (puede ser nulo o vacío).
     * @param apodo       Apodo de la mascota (puede ser nulo o vacío).
     * @param clas        Clasificación taxonómica (puede ser nula).
     * @param familia     Familia biológica (puede ser nula).
     * @param genero      Género biológico (puede ser nulo).
     * @param especie     Especie (puede ser nula).
     * @param alimento    Tipo de alimento (puede ser nulo).
     */
    public RegistroDialog(Frame owner, String nombre, String apodo, String clas, String familia, String genero, String especie, String alimento) {
        super(owner, "Completar registro", true);
        String[] clasificaciones = {"Reptil", "Mamífero", "Ave", "Anfibio", "Pez", "Invertebrado"};
        String[] alimentos = {"Lácteos", "Carnes", "Verduras", "Frutas", "Forrajes", "Cereales", "Leguminosas", "Omnívoros"};
        cmbClasificacion = new JComboBox<>(clasificaciones);
        cmbAlimento = new JComboBox<>(alimentos);

        txtNombre.setText(nombre);
        txtApodo.setText(apodo);
        if (clas != null && !clas.isEmpty()) cmbClasificacion.setSelectedItem(clas);
        txtFamilia.setText(familia);
        txtGenero.setText(genero);
        txtEspecie.setText(especie);
        if (alimento != null && !alimento.isEmpty()) cmbAlimento.setSelectedItem(alimento);

        configurarLayout();
        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Configura el diseño de la interfaz gráfica utilizando GridBagLayout.
     * Incluye etiquetas, campos de texto, combo boxes y botones de acción.
     * También define las validaciones básicas y el comportamiento de los botones.
     */
    private void configurarLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Apodo:"), gbc);
        gbc.gridx = 1; panel.add(txtApodo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Clasificación:"), gbc);
        gbc.gridx = 1; panel.add(cmbClasificacion, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Familia:"), gbc);
        gbc.gridx = 1; panel.add(txtFamilia, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1; panel.add(txtGenero, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Especie:"), gbc);
        gbc.gridx = 1; panel.add(txtEspecie, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Alimento:"), gbc);
        gbc.gridx = 1; panel.add(cmbAlimento, gbc);

        JPanel botones = new JPanel();
        JButton btnOk = new JButton("Guardar");
        JButton btnCancel = new JButton("Cancelar");
        botones.add(btnOk);
        botones.add(btnCancel);

        // Acción de guardado con validación
        btnOk.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtApodo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y apodo son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            setVisible(false);
        });

        // Acción de cancelación
        btnCancel.addActionListener(e -> {
            cancelado = true;
            setVisible(false);
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(botones, BorderLayout.SOUTH);
    }

    /**
     * Indica si el usuario canceló el registro.
     *
     * @return true si el usuario presionó "Cancelar", false en caso contrario.
     */
    public boolean isCancelado() { return cancelado; }

    /**
     * Obtiene el nombre ingresado.
     * @return Nombre de la mascota.
     */
    public String getNombre() { return txtNombre.getText().trim(); }

    /**
     * Obtiene el apodo ingresado.
     * @return Apodo de la mascota.
     */
    public String getApodo() { return txtApodo.getText().trim(); }

    /**
     * Obtiene la clasificación seleccionada.
     * @return Clasificación taxonómica.
     */
    public String getClasificacion() { return (String) cmbClasificacion.getSelectedItem(); }

    /**
     * Obtiene la familia ingresada.
     * @return Familia biológica.
     */
    public String getFamilia() { return txtFamilia.getText().trim(); }

    /**
     * Obtiene el género ingresado.
     * @return Género biológico.
     */
    public String getGenero() { return txtGenero.getText().trim(); }

    /**
     * Obtiene la especie ingresada.
     * @return Especie.
     */
    public String getEspecie() { return txtEspecie.getText().trim(); }

    /**
     * Obtiene el alimento seleccionado.
     * @return Tipo de alimento.
     */
    public String getAlimento() { return (String) cmbAlimento.getSelectedItem(); }
}
