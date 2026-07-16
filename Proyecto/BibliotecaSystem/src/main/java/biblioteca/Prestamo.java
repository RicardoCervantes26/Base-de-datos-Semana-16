package biblioteca;

public class Prestamo {
    private int id;
    private String usuarioId, materialId, fechaPrestamo;

    public Prestamo(int id, String usuarioId, String materialId, String fechaPrestamo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.materialId = materialId;
        this.fechaPrestamo = fechaPrestamo;
    }
    // Getters
    public int getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getMaterialId() { return materialId; }
    public String getFechaPrestamo() { return fechaPrestamo; }
}