# Proyecto-POO2 - Sistema de Gestión Veterinaria

Este es un avance de proyecto desarrollado en Java que simula un sistema básico de gestión para una clínica veterinaria. El sistema permite el registro de dueños, sus mascotas y la programación de citas médicas, aplicando conceptos fundamentales de Programación Orientada a Objetos (POO).

## Características Principales

* **Registro de Mascotas:** Permite crear pacientes (mascotas) almacenando su nombre, especie y edad.
* **Gestión de Dueños:** Permite registrar clientes con su cédula, nombre y teléfono. Además, cada dueño puede tener múltiples mascotas asociadas.
* **Agendamiento de Citas:** Facilita la programación de citas vinculando una fecha, hora, un dueño y una mascota específica.

## Estructura de Clases

El proyecto está compuesto por las siguientes clases:

* `Mascota`: Define los atributos del paciente (nombre, especie, edad) y sus métodos de acceso (getters/setters).
* `Dueno`: Define al cliente y mantiene una lista (`ArrayList`) de los objetos `Mascota` que le pertenecen.
* `Cita`: Relaciona a un `Dueno` y una `Mascota` con una fecha y hora determinadas.
* `Main`: Clase principal de prueba donde se instancian los objetos, se relacionan (asignando mascotas a dueños) y se programan citas de ejemplo.

## Tecnologías Utilizadas
* Java (JDK 8 o superior)

## Cómo ejecutar el proyecto

1.  Asegúrate de tener instalado Java en tu sistema.
2.  Clona este repositorio o descarga el código fuente.
3.  Compila los archivos `.java`:
    ```bash
    javac *.java
    ```
4.  Ejecuta la clase principal:
    ```bash
    java Main
    ```
