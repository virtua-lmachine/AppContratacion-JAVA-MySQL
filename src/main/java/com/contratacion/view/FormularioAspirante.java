package com.contratacion.view;

import com.contratacion.dao.AspiranteDAO;
import com.contratacion.dao.AspiranteDaoImpl;
import com.contratacion.model.Aspirante;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


/*
FORMULARIO JFrame
Usamos JCombobox de la libreria Swing para ENUM con lista desplegable
Usamos JCheckBox para la variable SET con casilla multiple
*/
public class FormularioAspirante extends JFrame {
    // para el texto
    private JTextField txtNombre;
    private JTextField txtCedula;
    // ENUM combobox
    private JComboBox<String> cmbNivelEstudio;
    private JComboBox<String> cmbTurno;
    // SET con checkbox
    private JCheckBox chkEspañol, chkIngles, chkFrances, chkAleman, chkPortugues;
    private JCheckBox chkJava, chkSQL, chkGit, chkHTML, chkPython, chkExcel;
    // los botones
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnVerRegistros;
    // crear tabla
    private JTable tabla;
    private javax.swing.table.DefaultTableModel modeloTabla;
    // el objeto DAO
    private final AspiranteDAO dao = new AspiranteDaoImpl();

    // llamada de los metodos
    public FormularioAspirante() {
        inicializarVentana(); // ventana, formas
        construirFormulario(); // ventana, componentes
        construirTabla();
        pack(); // para ajustar el tamaño de la ventana
        setLocationRelativeTo(null); // para centrar la ventana
        cargarTabla();
    }

    // ventana
    private void inicializarVentana() {
        setTitle("SISTEMA DE CONTRATACION");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // cerrar con la X
        setLayout(new BorderLayout(10, 10)); // espacio entre borde px
        getContentPane().setBackground(new Color(245, 247, 250)); // color de fondo
    }
    // construiFormulario
    private void construirFormulario() {
        JPanel panelFrom = new JPanel(new GridBagLayout());
        panelFrom.setBackground(Color.white);
        panelFrom.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(12, 15, 10, 15), // borde externo del padding
                BorderFactory.createTitledBorder( // borde interno con titulo
                        BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                                "Formulario aspirante",
                                TitledBorder.LEFT,
                                TitledBorder.TOP, // posicion del titulo
                                new Font("Segoe UI", Font.BOLD, 13), // fuente y color
                                new Color(60, 80, 180))
                )
        );
        // posicionamineto de paddin interno y componentes ( las letras) dentro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6,8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0; // navegar por las filas

        // nombre
        agregarEtiqueta(panelFrom, gbc, fila, "Nombre Completo");
        txtNombre = new JTextField(25);
        estilizarCampo(txtNombre);
        agregarControl(panelFrom, gbc, fila++, txtNombre);

        // cedula
        agregarEtiqueta(panelFrom, gbc, fila, "Numero de Cedula");
        txtCedula = new JTextField(25);
        estilizarCampo(txtCedula);
        agregarControl(panelFrom, gbc, fila++, txtCedula);

        // nivel de estudio ENUM JComboBox
        agregarEtiqueta(panelFrom, gbc, fila, "Nivel de Estudio");
        String[] niveles = {"bachiller","tecnico","tecnologo","profesional"};
        cmbNivelEstudio = new JComboBox<>(niveles);
        estilizarCombo(cmbNivelEstudio);
        agregarControl(panelFrom, gbc, fila++, cmbNivelEstudio);

        // turno preferido
        agregarEtiqueta(panelFrom, gbc, fila, "Turno Preferido");
        String[] turnos = {"manana", "tarde", "noche"};
        cmbTurno = new JComboBox<>(turnos);
        estilizarCombo(cmbTurno);
        agregarControl(panelFrom, gbc, fila++, cmbTurno);

        // idiomas con JCheckbox seleccion
        agregarEtiqueta(panelFrom, gbc, fila, "Idiomas que Domina");
        JPanel panelIdiomas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelIdiomas.setBackground(Color.white);

        chkEspañol = new JCheckBox("Español");
        chkIngles = new JCheckBox("Ingles");
        chkFrances = new JCheckBox("Frances");
        chkAleman = new JCheckBox("Aleman");
        chkPortugues = new JCheckBox("Portugues");
        panelIdiomas.add(chkEspañol);
        panelIdiomas.add(chkIngles);
        panelIdiomas.add(chkFrances);
        panelIdiomas.add(chkAleman);
        panelIdiomas.add(chkPortugues);
        panelIdiomas.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));
        agregarControl(panelFrom,gbc, fila++, panelIdiomas);

        // habilidades tecnicas JCheckbox
        agregarEtiqueta(panelFrom, gbc, fila, "Habilidades Tecnicas");
        JPanel panelHabilidades = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelHabilidades.setBackground(Color.WHITE);
        chkJava = crearCheckbox("JAVA");
        chkSQL = crearCheckbox("SQL");
        chkGit = crearCheckbox("Git");
        chkHTML = crearCheckbox("HTML");
        chkPython = crearCheckbox("Python");
        chkExcel = crearCheckbox("Excel");
        // agregarlos al panel del frame
        panelHabilidades.add(chkJava);
        panelHabilidades.add(chkSQL);
        panelHabilidades.add(chkGit);
        panelHabilidades.add(chkHTML);
        panelHabilidades.add(chkPython);
        panelHabilidades.add(chkExcel);
        agregarControl(panelFrom, gbc, fila++, panelHabilidades);
        panelHabilidades.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));

        // botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panelBotones.setBackground(Color.white);
        // crear botones con colores personalizados
        btnGuardar = crearBoton("Guardar", new Color(46, 125, 50));
        btnLimpiar = crearBoton("Limpiar", new Color(100, 116, 139));
        btnVerRegistros = crearBoton("Ver Registros", new Color(30, 100, 180));
        // asignar acciones a los botones
        btnGuardar.addActionListener(this::accionGuardar);
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVerRegistros.addActionListener(e -> cargarTabla());
        // agregar los botones al panel osea la ventana JFrame de Swing
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVerRegistros);
        // posiciones el panel
        gbc.gridx = 0; gbc.gridy = fila;
        gbc.gridwidth = 2;
        panelFrom.add(panelBotones, gbc); // agrega el panel al formulario

        add(panelFrom, BorderLayout.NORTH); // agregar a la ventana (frame)

    }

    // contruccion de la tabla de  resultados
    private void construirTabla() {
        String[] columnas = { "ID", "Nombre", "Cedula", "Nivel", "Turno", "Idiomas", "Habilidades"};
        modeloTabla = new javax.swing.table.DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) {return false;} // la tabla no es deitable(false) solo lectura
        };
        // apariencia de la tabla
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(100, 149, 237));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 225, 235));
        tabla.setSelectionBackground(new Color(173, 216, 230));
        // tabla dentro del panel
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(0, 15, 15, 15),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
                        "Aspirantes Registrados ",
                        TitledBorder.LEFT, TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 13),
                        new Color(60, 80, 180))
        ));
        scroll.setPreferredSize(new Dimension(900, 220));
        add(scroll, BorderLayout.CENTER);

    }

    // guardar
    private void accionGuardar(ActionEvent e) {
        // lee los campos y los valida

        String nombreCompleto = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();

        if (nombreCompleto.isEmpty() || cedula.isEmpty()) {
            mostrarError("Nombre y Cedula son obligatorios");
            return;
        }

        //  leer ENUM desde jComboBox valor unico
        String nivelEstudios    = ((String) cmbNivelEstudio.getSelectedItem()).toLowerCase();
        String turnoPreferencia = ((String) cmbTurno.getSelectedItem()).toLowerCase();

        // leer string de  SET concatenado
        String idiomas = construirStringSet( chkEspañol, chkIngles, chkFrances, chkAleman, chkPortugues);
        // validacion de idiomas
        if (idiomas.isEmpty()) {
            mostrarError("Seleccionar almenos un idioma");
            return;
        }
        // construir string set con JCheckBox habilidades
        String habilidadesTecnicas = construirStringSet(chkJava, chkSQL, chkGit, chkHTML, chkPython, chkExcel);
        // validar habilidades
        if (habilidadesTecnicas.isEmpty()) {
            mostrarError("Selecciones almenos una habilidad");
            return;
        }

        // crear objeto aspirate para enviar al DAO que ejecutara en el model  INSERT
        Aspirante aspirante = new Aspirante(0,nombreCompleto,cedula,nivelEstudios,turnoPreferencia,idiomas,habilidadesTecnicas);
        boolean exito = dao.insertar(aspirante);
        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "Registrado Exitosamente.\n" +
                    "Idiomas: " + idiomas + "\n" +
                    "Habilidades: " + habilidadesTecnicas,
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarError("No se pudo guardar con exito");
        }

    }
    // concatenar lo JCheckBox
    private String construirStringSet(JCheckBox... checkboxes) {
        List<String> seleccionados = new ArrayList<>();
        for (JCheckBox cb : checkboxes) {
            if (cb.isSelected()) {
                seleccionados.add(cb.getText().toLowerCase());
            }
        }
        return String.join(",", seleccionados); // requerido por MYSQL SET
    }

    // recarga datos de la BD a la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0); // limpia las filas
        List<Aspirante> lista = dao.listarTodos();
        for (Aspirante a : lista) {
            modeloTabla.addRow(new Object[] {
                    a.getid(),
                    a.getnombreCompleto(),
                    a.getcedula(),
                    a.getnivelEstudios(),
                    a.getturnoPreferencia(),
                    a.getidiomas(),
                    a.gethabilidadesTecnicas(),
            });
        }
    }
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCedula.setText("");
        cmbNivelEstudio.setSelectedIndex(0);
        cmbTurno.setSelectedIndex(0);
        for (JCheckBox cb : new JCheckBox[]{chkEspañol, chkIngles, chkFrances, chkAleman, chkPortugues, chkJava, chkSQL, chkGit, chkHTML, chkPython, chkExcel}) {
            cb.setSelected(false);
        }
        txtNombre.requestFocus();
    }
    // ayudas para estilos
    private void agregarEtiqueta(JPanel p, GridBagConstraints gbc, int fila, String texto) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1; gbc.weightx = 0;
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(55, 65, 95));
        p.add(lbl, gbc);
    }
    private void agregarControl(JPanel p, GridBagConstraints gbc, int fila, JComponent ctrl) {
        gbc.gridx = 1; gbc.gridy = fila; gbc.gridwidth = 1; gbc.weightx = 1;
        p.add(ctrl, gbc);
    }
    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 195, 220)),
                new EmptyBorder(4, 8, 4, 8)));
    }
    private void estilizarCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
    }
    private JCheckBox crearCheckbox(String texto) {
        JCheckBox cb = new JCheckBox(texto);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cb.setBackground(Color.WHITE);
        return cb;
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(fondo);
        btn.setForeground(Color.DARK_GRAY);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error al validación",
                JOptionPane.WARNING_MESSAGE);
    }

    // main
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new FormularioAspirante().setVisible(true));
    }

}
