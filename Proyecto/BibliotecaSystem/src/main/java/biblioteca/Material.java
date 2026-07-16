package biblioteca;

public class Material {
    private String id, titulo, autor, disponibilidad;
    private short anio;

    public Material(String id, String titulo, String autor, short anio, String disponibilidad) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.disponibilidad = disponibilidad;
    }
    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public short getAnio() { return anio; }
    public String getDisponibilidad() { return disponibilidad; }
}