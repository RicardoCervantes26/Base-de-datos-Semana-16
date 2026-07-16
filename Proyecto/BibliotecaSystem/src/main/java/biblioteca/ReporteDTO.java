package biblioteca;

public class ReporteDTO {
    private String cliente;
    private int cantidadPrestamos;
    private double promedioAnio;

    public ReporteDTO(String cliente, int cantidad, double promedio) {
        this.cliente = cliente;
        this.cantidadPrestamos = cantidad;
        this.promedioAnio = promedio;
    }
    // Getters
    public String getCliente() { return cliente; }
    public int getCantidadPrestamos() { return cantidadPrestamos; }
    public double getPromedioAnio() { return promedioAnio; }
}