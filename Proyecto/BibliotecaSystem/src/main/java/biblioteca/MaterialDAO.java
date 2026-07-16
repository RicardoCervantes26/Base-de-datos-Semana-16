package biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    public List<Material> listarDisponibles() {
        List<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM Material WHERE Disponibilidad = 'disponible'";
        try (Connection con = ConnectionFactory.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Material(rs.getString("ID"), rs.getString("Titulo"),
                        rs.getString("Autor"), rs.getShort("Anio"), rs.getString("Disponibilidad")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}