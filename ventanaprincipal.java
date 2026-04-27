
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
 
/**
 * Interfaz gráfica del Sistema Veterinario
 * Nivel: 3er semestre - Ingeniería de Sistemas
 * Componentes: JFrame, JPanel, JTextField, JComboBox,
 *              JButton, JLabel, JTable, JTabbedPane
 */
public class VentanaPrincipal extends JFrame {
 
    // ── Listas que guardan los datos en memoria ──────────────────────────
    private ArrayList<Dueno>   listaDuenos   = new ArrayList<>();
    private ArrayList<Mascota> listaMascotas = new ArrayList<>();
    private ArrayList<Cita>    listaCitas    = new ArrayList<>();
 
    // ── Contadores de ID ─────────────────────────────────────────────────
    private int idDueno   = 1;
    private int idMascota = 1;
    private int idCita    = 1;
 
    public VentanaPrincipal() {
        // Configuración básica de la ventana
        setTitle("🐾 Sistema Veterinario");
        setSize(850, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
 
        // ── Cabecera ─────────────────────────────────────────────────────
        JPanel cabecera = new JPanel();
        cabecera.setBackground(new Color(33, 97, 140));
        cabecera.setPreferredSize(new Dimension(850, 50));
        JLabel lblTitulo = new JLabel("🐾  Sistema Veterinario  -  POO 2");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        cabecera.add(lblTitulo);
 
        // ── Pestañas principales ─────────────────────────────────────────
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pestanas.addTab("👤 Dueños",   panelDuenos());
        pestanas.addTab("🐶 Mascotas", panelMascotas());
        pestanas.addTab("📅 Citas",    panelCitas());
 
        // ── Ensamblar ────────────────────────────────────────────────────
        setLayout(new BorderLayout());
        add(cabecera,  BorderLayout.NORTH);
        add(pestanas,  BorderLayout.CENTER);
    }
 
    // ════════════════════════════════════════════════════════════════════
    //  PANEL DUEÑOS
    // ════════════════════════════════════════════════════════════════════
 
    private JPanel panelDuenos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
        // ── Formulario ───────────────────────────────────────────────────
        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar Dueño"));
 
        JLabel  lblNombre    = new JLabel("Nombre:*");
        JTextField txtNombre = new JTextField();
 
        JLabel     lblTel    = new JLabel("Teléfono:*");
        JTextField txtTel    = new JTextField();
 
        JLabel     lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();
 
        JLabel     lblDir    = new JLabel("Dirección:");
        JTextField txtDir    = new JTextField();
 
        formulario.add(lblNombre);  formulario.add(txtNombre);
        formulario.add(lblTel);     formulario.add(txtTel);
        formulario.add(lblCorreo);  formulario.add(txtCorreo);
        formulario.add(lblDir);     formulario.add(txtDir);
 
        // ── Botones ──────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnGuardar  = new JButton("💾 Guardar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnLimpiar  = new JButton("🧹 Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
 
        // ── Tabla ────────────────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Teléfono", "Correo", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getColumnModel().getColumn(0).setMaxWidth(35);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Dueños Registrados"));
 
        // ── Panel norte (formulario + botones) ───────────────────────────
        JPanel norte = new JPanel(new BorderLayout());
        norte.add(formulario,   BorderLayout.CENTER);
        norte.add(panelBotones, BorderLayout.SOUTH);
 
        panel.add(norte,  BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
 
        // ── Acción: Guardar ──────────────────────────────────────────────
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String tel    = txtTel.getText().trim();
 
            if (nombre.isEmpty() || tel.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                        "⚠️ Nombre y teléfono son obligatorios.",
                        "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
 
            Dueno nuevo = new Dueno(nombre, tel, txtCorreo.getText().trim(), txtDir.getText().trim());
            listaDuenos.add(nuevo);
            modelo.addRow(new Object[]{idDueno++, nombre, tel,
                    txtCorreo.getText().trim(), txtDir.getText().trim()});
 
            JOptionPane.showMessageDialog(panel, "✅ Dueño registrado correctamente.");
            txtNombre.setText(""); txtTel.setText("");
            txtCorreo.setText(""); txtDir.setText("");
        });
 
        // ── Acción: Eliminar fila seleccionada ───────────────────────────
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(panel, "⚠️ Selecciona un dueño de la tabla.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "¿Eliminar este dueño?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                modelo.removeRow(fila);
                listaDuenos.remove(fila);
                JOptionPane.showMessageDialog(panel, "✅ Dueño eliminado.");
            }
        });
 
        // ── Acción: Limpiar campos ───────────────────────────────────────
        btnLimpiar.addActionListener(e -> {
            txtNombre.setText(""); txtTel.setText("");
            txtCorreo.setText(""); txtDir.setText("");
        });
 
        return panel;
    }
 
    // ════════════════════════════════════════════════════════════════════
    //  PANEL MASCOTAS
    // ════════════════════════════════════════════════════════════════════
 
    private JPanel panelMascotas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
        // ── Formulario ───────────────────────────────────────────────────
        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar Mascota"));
 
        JLabel     lblNombre  = new JLabel("Nombre:*");
        JTextField txtNombre  = new JTextField();
 
        JLabel    lblEspecie  = new JLabel("Especie:*");
        // JComboBox — permite seleccionar la especie fácilmente
        JComboBox<String> cmbEspecie = new JComboBox<>(
                new String[]{"Perro", "Gato", "Ave", "Conejo", "Reptil", "Otro"});
 
        JLabel     lblEdad    = new JLabel("Edad (años):*");
        JTextField txtEdad    = new JTextField();
 
        JLabel     lblDueno   = new JLabel("Nombre del Dueño:*");
        JTextField txtDueno   = new JTextField();
 
        formulario.add(lblNombre);  formulario.add(txtNombre);
        formulario.add(lblEspecie); formulario.add(cmbEspecie);
        formulario.add(lblEdad);    formulario.add(txtEdad);
        formulario.add(lblDueno);   formulario.add(txtDueno);
 
        // ── Botones ──────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnGuardar  = new JButton("💾 Guardar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnLimpiar  = new JButton("🧹 Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
 
        // ── Tabla ────────────────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre", "Especie", "Edad", "Dueño"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getColumnModel().getColumn(0).setMaxWidth(35);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Mascotas Registradas"));
 
        JPanel norte = new JPanel(new BorderLayout());
        norte.add(formulario,   BorderLayout.CENTER);
        norte.add(panelBotones, BorderLayout.SOUTH);
        panel.add(norte,  BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
 
        // ── Acción: Guardar ──────────────────────────────────────────────
        btnGuardar.addActionListener(e -> {
            String nombre  = txtNombre.getText().trim();
            String especie = (String) cmbEspecie.getSelectedItem();
            String edadStr = txtEdad.getText().trim();
            String dueno   = txtDueno.getText().trim();
 
            if (nombre.isEmpty() || edadStr.isEmpty() || dueno.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "⚠️ Nombre, edad y dueño son obligatorios.");
                return;
            }
 
            int edad;
            try {
                edad = Integer.parseInt(edadStr);
                if (edad < 0 || edad > 50) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "⚠️ La edad debe ser un número entre 0 y 50.");
                return;
            }
 
            Mascota nueva = new Mascota(nombre, especie, edad);
            listaMascotas.add(nueva);
            modelo.addRow(new Object[]{idMascota++, nombre, especie, edad, dueno});
 
            JOptionPane.showMessageDialog(panel, "✅ Mascota registrada correctamente.");
            txtNombre.setText(""); txtEdad.setText(""); txtDueno.setText("");
            cmbEspecie.setSelectedIndex(0);
        });
 
        // ── Acción: Eliminar ─────────────────────────────────────────────
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(panel, "⚠️ Selecciona una mascota."); return; }
            if (JOptionPane.showConfirmDialog(panel, "¿Eliminar esta mascota?", "Confirmar",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                modelo.removeRow(fila);
                listaMascotas.remove(fila);
                JOptionPane.showMessageDialog(panel, "✅ Mascota eliminada.");
            }
        });
 
        btnLimpiar.addActionListener(e -> {
            txtNombre.setText(""); txtEdad.setText(""); txtDueno.setText("");
            cmbEspecie.setSelectedIndex(0);
        });
 
        return panel;
    }
 
    // ════════════════════════════════════════════════════════════════════
    //  PANEL CITAS
    // ════════════════════════════════════════════════════════════════════
 
    private JPanel panelCitas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
        // ── Formulario ───────────────────────────────────────────────────
        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Agendar Cita"));
 
        JLabel     lblFecha   = new JLabel("Fecha (dd/mm/aaaa):*");
        JTextField txtFecha   = new JTextField();
 
        JLabel     lblHora    = new JLabel("Hora (ej: 10:00 AM):*");
        JTextField txtHora    = new JTextField();
 
        JLabel     lblDueno   = new JLabel("Nombre del Dueño:*");
        JTextField txtDueno   = new JTextField();
 
        JLabel     lblMascota = new JLabel("Nombre de la Mascota:*");
        JTextField txtMascota = new JTextField();
 
        formulario.add(lblFecha);   formulario.add(txtFecha);
        formulario.add(lblHora);    formulario.add(txtHora);
        formulario.add(lblDueno);   formulario.add(txtDueno);
        formulario.add(lblMascota); formulario.add(txtMascota);
 
        // ── Botones ──────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        JButton btnAgendar  = new JButton("📅 Agendar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnLimpiar  = new JButton("🧹 Limpiar");
 
        // JComboBox para filtrar por estado
        JLabel lblFiltro        = new JLabel("  Filtrar:");
        JComboBox<String> cmbFiltro = new JComboBox<>(
                new String[]{"Todas", "Pendiente", "Completada", "Cancelada"});
 
        panelBotones.add(btnAgendar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(lblFiltro);
        panelBotones.add(cmbFiltro);
 
        // ── Tabla ────────────────────────────────────────────────────────
        String[] columnas = {"ID", "Fecha", "Hora", "Dueño", "Mascota", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getColumnModel().getColumn(0).setMaxWidth(35);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Citas Registradas"));
 
        JPanel norte = new JPanel(new BorderLayout());
        norte.add(formulario,   BorderLayout.CENTER);
        norte.add(panelBotones, BorderLayout.SOUTH);
        panel.add(norte,  BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
 
        // ── Acción: Agendar ──────────────────────────────────────────────
        btnAgendar.addActionListener(e -> {
            String fecha   = txtFecha.getText().trim();
            String hora    = txtHora.getText().trim();
            String dueno   = txtDueno.getText().trim();
            String mascota = txtMascota.getText().trim();
 
            if (fecha.isEmpty() || hora.isEmpty() || dueno.isEmpty() || mascota.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "⚠️ Todos los campos son obligatorios.");
                return;
            }
 
            // Verificar si el horario ya está ocupado
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (modelo.getValueAt(i, 1).equals(fecha) && modelo.getValueAt(i, 2).equals(hora)) {
                    JOptionPane.showMessageDialog(panel,
                            "🔴 El horario " + hora + " del " + fecha + " ya está ocupado.",
                            "Horario no disponible", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
 
            modelo.addRow(new Object[]{idCita++, fecha, hora, dueno, mascota, "Pendiente"});
            JOptionPane.showMessageDialog(panel, "✅ Cita agendada correctamente.");
            txtFecha.setText(""); txtHora.setText("");
            txtDueno.setText(""); txtMascota.setText("");
        });
 
        // ── Acción: Eliminar ─────────────────────────────────────────────
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(panel, "⚠️ Selecciona una cita."); return; }
            if (JOptionPane.showConfirmDialog(panel, "¿Eliminar esta cita?", "Confirmar",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                modelo.removeRow(fila);
                JOptionPane.showMessageDialog(panel, "✅ Cita eliminada.");
            }
        });
 
        // ── Acción: Limpiar ──────────────────────────────────────────────
        btnLimpiar.addActionListener(e -> {
            txtFecha.setText(""); txtHora.setText("");
            txtDueno.setText(""); txtMascota.setText("");
        });
 
        // ── Doble clic en tabla para cambiar estado ──────────────────────
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    if (fila == -1) return;
                    String[] opciones = {"Pendiente", "Completada", "Cancelada"};
                    String nuevo = (String) JOptionPane.showInputDialog(panel,
                            "Cambiar estado de la cita:",
                            "Estado", JOptionPane.PLAIN_MESSAGE,
                            null, opciones, modelo.getValueAt(fila, 5));
                    if (nuevo != null) {
                        modelo.setValueAt(nuevo, fila, 5);
                    }
                }
            }
        });
 
        return panel;
    }
 
    // ════════════════════════════════════════════════════════════════════
    //  MAIN — punto de entrada
    // ════════════════════════════════════════════════════════════════════
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}