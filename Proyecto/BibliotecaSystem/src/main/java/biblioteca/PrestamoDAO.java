package biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public List<ReporteDTO> obtenerReporte() {
        List<ReporteDTO> lista = new ArrayList<>();
        String sql = "{call SP_ObtenerReportePrestamos}";
        try (Connection con = ConnectionFactory.getConnection();
             CallableStatement stmt = con.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new ReporteDTO(rs.getString("Cliente"), rs.getInt("CantidadPrestamos"), rs.getDouble("PromedioAnioLibros")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public String registrarPrestamo(String usuarioId, String materialId) {
        String sql = "{call SP_RegistrarPrestamoTransaccional(?, ?, ?)}";
        try (Connection con = ConnectionFactory.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, usuarioId);
            stmt.setString(2, materialId);
            stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            stmt.execute();
            return stmt.getString(3);
        } catch (SQLException e) { return "Error: " + e.getMessage(); }
    }

    public int getDiasPrestamo(int prestamoId) {
        String sql = "SELECT dbo.fn_DiasPrestamo(?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, prestamoId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { return -1; }
    }
}