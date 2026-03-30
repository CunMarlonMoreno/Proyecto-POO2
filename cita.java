package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa una cita médica agendada para una mascota.
 * Contiene la fecha, hora, motivo y estado de la cita.
 */
public class Cita {

    // ── Estados posibles de la cita ──────────────────────────────────────
    public enum Estado {
        PENDIENTE, COMPLETADA, CANCELADA;

        public static Estado fromString(String texto) {
            try {
                return Estado.valueOf(texto.toUpperCase());
            } catch (IllegalArgumentException e) {
                return PENDIENTE;
            }
        }
    }

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FMT_HORA  = DateTimeFormatter.ofPattern("HH:mm");

    private int        id;
    private LocalDate  fecha;
    private LocalTime  hora;
    private String     motivo;
    private Estado     estado;
    private int        mascotaId;  // FK hacia Mascota
    private int        duenoId;    // FK hacia Dueno (desnormalizado para consultas rápidas)

    // ── Constructor completo (carga desde BD) ────────────────────────────
    public Cita(int id, LocalDate fecha, LocalTime hora, String motivo,
                Estado estado, int mascotaId, int duenoId) {
        this.id        = id;
        this.fecha     = fecha;
        this.hora      = hora;
        this.motivo    = motivo;
        this.estado    = estado;
        this.mascotaId = mascotaId;
        this.duenoId   = duenoId;
    }

    // ── Constructor sin ID, estado por defecto PENDIENTE ─────────────────
    public Cita(LocalDate fecha, LocalTime hora, String motivo,
                int mascotaId, int duenoId) {
        this(0, fecha, hora, motivo, Estado.PENDIENTE, mascotaId, duenoId);
    }

    // ── Helpers de fecha/hora como String (para BD y UI) ─────────────────
    public String getFechaStr() { return fecha.format(FMT_FECHA); }
    public String getHoraStr()  { return hora.format(FMT_HORA); }

    /** Verifica si esta cita corresponde al día de hoy. */
    public boolean esHoy() {
        return fecha.equals(LocalDate.now());
    }

    /** Verifica si hay conflicto de horario con otra cita (misma fecha y hora). */
    public boolean colisionaCon(Cita otra) {
        return this.fecha.equals(otra.fecha) && this.hora.equals(otra.hora);
    }

    // ── Getters ──────────────────────────────────────────────────────────
    public int       getId()        { return id; }
    public LocalDate getFecha()     { return fecha; }
    public LocalTime getHora()      { return hora; }
    public String    getMotivo()    { return motivo; }
    public Estado    getEstado()    { return estado; }
    public int       getMascotaId() { return mascotaId; }
    public int       getDuenoId()   { return duenoId; }

    // ── Setters ──────────────────────────────────────────────────────────
    public void setId(int id)              { this.id        = id; }
    public void setFecha(LocalDate fecha)  { this.fecha     = fecha; }
    public void setHora(LocalTime hora)    { this.hora      = hora; }
    public void setMotivo(String motivo)   { this.motivo    = motivo; }
    public void setEstado(Estado estado)   { this.estado    = estado; }
    public void setMascotaId(int mid)      { this.mascotaId = mid; }
    public void setDuenoId(int did)        { this.duenoId   = did; }

    @Override
    public String toString() {
        return String.format("Cita{id=%d, fecha=%s, hora=%s, motivo='%s', estado=%s, mascotaId=%d}",
                id, getFechaStr(), getHoraStr(), motivo, estado, mascotaId);
    }
}