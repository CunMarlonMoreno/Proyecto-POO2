package controller;

import dao.DuenoDAO;
import model.Dueno;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de la entidad Dueno.
 * Aplica validaciones de negocio antes de delegar al DAO.
 * La vista NUNCA habla directo con el DAO — siempre pasa por aquí.
 */
public class DuenoController {

    private final DuenoDAO duenoDAO;

    public DuenoController() {
        this.duenoDAO = new DuenoDAO();
    }

    // ════════════════════════════════════════════════════════════════════
    //  REGISTRAR
    // ════════════════════════════════════════════════════════════════════

    /**
     * Registra un nuevo dueño tras validar los campos obligatorios.
     *
     * @throws IllegalArgumentException si faltan datos requeridos.
     * @return el Dueno creado con su ID asignado.
     */
    public Dueno registrar(String nombre, String telefono, String correo, String direccion) {
        validarNombre(nombre);
        validarTelefono(telefono);

        Dueno nuevo = new Dueno(nombre.trim(), telefono.trim(),
                correo   != null ? correo.trim()    : "",
                direccion != null ? direccion.trim() : "");

        return duenoDAO.insertar(nuevo)
                .orElseThrow(() -> new RuntimeException("No se pudo registrar el dueño en la base de datos."));
    }

    // ════════════════════════════════════════════════════════════════════
    //  CONSULTAR
    // ════════════════════════════════════════════════════════════════════

    /** Devuelve todos los dueños ordenados por nombre. */
    public List<Dueno> obtenerTodos() {
        return duenoDAO.obtenerTodos();
    }

    /**
     * Busca dueños por nombre o teléfono (búsqueda parcial).
     * Si el término está vacío, devuelve todos.
     */
    public List<Dueno> buscar(String termino) {
        if (termino == null || termino.isBlank()) return obtenerTodos();
        return duenoDAO.buscarPorNombreOTelefono(termino.trim());
    }

    /** Obtiene un dueño por ID o lanza excepción si no existe. */
    public Dueno obtenerPorId(int id) {
        return duenoDAO.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Dueño con ID " + id + " no encontrado."));
    }

    // ════════════════════════════════════════════════════════════════════
    //  ACTUALIZAR
    // ════════════════════════════════════════════════════════════════════

    /**
     * Actualiza los datos de un dueño existente.
     *
     * @throws IllegalArgumentException si los datos no son válidos.
     * @throws RuntimeException         si el dueño no existe.
     */
    public void actualizar(int id, String nombre, String telefono, String correo, String direccion) {
        validarNombre(nombre);
        validarTelefono(telefono);

        Dueno existente = obtenerPorId(id);
        existente.setNombre   (nombre.trim());
        existente.setTelefono (telefono.trim());
        existente.setCorreo   (correo    != null ? correo.trim()    : "");
        existente.setDireccion(direccion != null ? direccion.trim() : "");

        if (!duenoDAO.actualizar(existente)) {
            throw new RuntimeException("No se pudo actualizar el dueño.");
        }
    }

    // ════════════════════════════════════════════════════════════════════
    //  ELIMINAR
    // ════════════════════════════════════════════════════════════════════

    /**
     * Elimina un dueño y en cascada sus mascotas y citas.
     *
     * @throws RuntimeException si no se pudo eliminar.
     */
    public void eliminar(int id) {
        if (!duenoDAO.eliminar(id)) {
            throw new RuntimeException("No se pudo eliminar el dueño con ID " + id + ".");
        }
    }

    // ════════════════════════════════════════════════════════════════════
    //  VALIDACIONES PRIVADAS
    // ════════════════════════════════════════════════════════════════════

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del dueño es obligatorio.");
        }
        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres.");
        }
    }

    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono del dueño es obligatorio.");
        }
        // Acepta formatos: 3001234567, +573001234567, 601-234-5678
        if (!telefono.trim().matches("[+]?[0-9\\-\\s]{7,15}")) {
            throw new IllegalArgumentException("El teléfono ingresado no tiene un formato válido.");
        }
    }
}