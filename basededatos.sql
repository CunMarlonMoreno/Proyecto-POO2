-- ============================================================
--  VetClinica · Esquema de base de datos SQLite
--  Este archivo es solo referencia visual.
--  Las tablas se crean automáticamente desde DatabaseManager.java
-- ============================================================

PRAGMA foreign_keys = ON;

-- ── Dueños de mascotas ───────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS duenos (
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre    TEXT    NOT NULL,
    telefono  TEXT    NOT NULL,
    correo    TEXT,
    direccion TEXT
);

-- ── Mascotas (vinculadas a un dueño) ────────────────────────────────────
CREATE TABLE IF NOT EXISTS mascotas (
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre        TEXT    NOT NULL,
    especie       TEXT    NOT NULL,          -- PERRO, GATO, AVE, CONEJO, REPTIL, OTRO
    raza          TEXT,
    edad_anios    INTEGER DEFAULT 0,
    observaciones TEXT,
    dueno_id      INTEGER NOT NULL,
    FOREIGN KEY (dueno_id) REFERENCES duenos(id) ON DELETE CASCADE
);

-- ── Citas médicas ────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS citas (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    fecha       TEXT    NOT NULL,            -- formato: YYYY-MM-DD
    hora        TEXT    NOT NULL,            -- formato: HH:mm
    motivo      TEXT    NOT NULL,
    estado      TEXT    NOT NULL DEFAULT 'PENDIENTE',  -- PENDIENTE | COMPLETADA | CANCELADA
    mascota_id  INTEGER NOT NULL,
    dueno_id    INTEGER NOT NULL,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,
    FOREIGN KEY (dueno_id)   REFERENCES duenos(id)   ON DELETE CASCADE,
    UNIQUE (fecha, hora)     -- Evita choques de horario a nivel de BD
);

-- ── Consultas de ejemplo ─────────────────────────────────────────────────

-- Citas de hoy con nombre de mascota y dueño
SELECT
    c.id,
    c.hora,
    c.motivo,
    c.estado,
    m.nombre  AS mascota,
    m.especie,
    d.nombre  AS dueno,
    d.telefono
FROM citas c
JOIN mascotas m ON c.mascota_id = m.id
JOIN duenos   d ON c.dueno_id   = d.id
WHERE c.fecha = DATE('now')
ORDER BY c.hora;

-- Mascotas de un dueño específico
SELECT * FROM mascotas WHERE dueno_id = 1;