// ============================================================
//  INTERFAZ: Mostrable
//  Proyecto: Sistema Veterinario - POO 2
//
//  ¿Por qué esta interfaz?
//  Las tres clases del proyecto (Dueno, Mascota, Cita) tienen
//  un método para mostrar su información. Con esta interfaz
//  formalizamos ese "contrato": cualquier clase que implemente
//  Mostrable ESTÁ OBLIGADA a definir mostrarInfo().
// ============================================================

public interface Mostrable {

    /**
     * Muestra la información del objeto por consola.
     * Toda clase que implemente esta interfaz debe definir
     * cómo se presenta su propia información.
     */
    void mostrarInfo();
}
