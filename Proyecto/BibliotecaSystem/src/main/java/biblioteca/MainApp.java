package biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        // 1. Verificación de conexión
        try (Connection con = ConnectionFactory.getConnection()) {
            System.out.println("✅ Conectado a BibliotecaSistema.");
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo conectar a la base de datos.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        PrestamoDAO pDAO = new PrestamoDAO();
        UsuarioDAO uDAO = new UsuarioDAO();
        MaterialDAO mDAO = new MaterialDAO();

        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n--- MENÚ BIBLIOTECA SISTEMA ---");
            System.out.println("1. Listar Usuarios");
            System.out.println("2. Ver Materiales Disponibles");
            System.out.println("3. Ver Reporte de Préstamos (SP)");
            System.out.println("4. Registrar Nuevo Préstamo (Transaccional)");
            System.out.println("5. Ver días de un préstamo (Función)");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            if (!sc.hasNextInt()) { sc.next(); continue; }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    uDAO.listarTodos().forEach(u -> System.out.println(u.getNombre() + " " + u.getApellido()));
                    break;
                case 2:
                    mDAO.listarDisponibles().forEach(m -> System.out.println(m.getTitulo() + " (" + m.getId() + ")"));
                    break;
                case 3:
                    System.out.println("\n--- REPORTE ---");
                    pDAO.obtenerReporte().forEach(r ->
                            System.out.printf("Cliente: %-15s | Prestamos: %d | Promedio Año: %.1f%n",
                                    r.getCliente(), r.getCantidadPrestamos(), r.getPromedioAnio()));
                    break;
                case 4:
                    System.out.print("Ingresa ID Usuario (ej. USR001): "); String u = sc.next();
                    System.out.print("Ingresa ID Material (ej. LIB001): "); String m = sc.next();
                    String resultado = pDAO.registrarPrestamo(u, m);
                    System.out.println("Resultado: " + resultado);
                    break;
                case 5:
                    System.out.print("Ingresa ID del préstamo: "); int id = sc.nextInt();
                    int dias = pDAO.getDiasPrestamo(id);
                    System.out.println("El libro lleva " + dias + " días prestado.");
                    break;
                case 6:
                    System.out.println("¡Hasta luego!");
                    return;
            }
        }
        sc.close();
    }
}