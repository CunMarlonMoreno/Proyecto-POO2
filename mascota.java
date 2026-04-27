// Mascota ahora implementa la interfaz Mostrable
public class mascota implements Mostrable {

    private String nombre;
    private String especie;
    private int    edad;

    public mascota(String nombre, String especie, int edad) {
        this.nombre  = nombre;
        this.especie = especie;
        this.edad    = edad;
    }

    public String getNombre()  { return nombre; }
    public String getEspecie() { return especie; }
    public int    getEdad()    { return edad; }

    public void setNombre(String nombre)   { this.nombre  = nombre; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setEdad(int edad)          { this.edad    = edad; }

    // ── Implementación OBLIGATORIA de la interfaz Mostrable ──────────────
    @Override
    public void mostrarInfo() {
        System.out.println("Mascota : " + nombre +
                           " | Especie: " + especie +
                           " | Edad: " + edad + " años.");
    }
}