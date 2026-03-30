package model;

/**
 * Representa al dueño de una o más mascotas.
 * Patrón POO: Encapsulamiento con getters/setters.
 */
public class Dueno {

    private int id;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;

    // ── Constructor completo (usado al cargar desde BD) ──────────────────
    public Dueno(int id, String nombre, String telefono, String correo, String direccion) {
        this.id        = id;
        this.nombre    = nombre;
        this.telefono  = telefono;
        this.correo    = correo;
        this.direccion = direccion;
    }

    // ── Constructor sin ID (usado al crear un nuevo dueño) ───────────────
    public Dueno(String nombre, String telefono, String correo, String direccion) {
        this(0, nombre, telefono, correo, direccion);
    }

    // ── Getters ──────────────────────────────────────────────────────────
    public int    getId()        { return id; }
    public String getNombre()    { return nombre; }
    public String getTelefono()  { return telefono; }
    public String getCorreo()    { return correo; }
    public String getDireccion() { return direccion; }

    // ── Setters ──────────────────────────────────────────────────────────
    public void setId(int id)               { this.id        = id; }
    public void setNombre(String nombre)    { this.nombre    = nombre; }
    public void setTelefono(String tel)     { this.telefono  = tel; }
    public void setCorreo(String correo)    { this.correo    = correo; }
    public void setDireccion(String dir)    { this.direccion = dir; }

    // ── Representación legible (útil para logs y debug) ──────────────────
    @Override
    public String toString() {
        return String.format("Dueno{id=%d, nombre='%s', telefono='%s', correo='%s'}",
                id, nombre, telefono, correo);
    }
}