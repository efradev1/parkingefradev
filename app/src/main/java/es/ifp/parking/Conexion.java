package es.ifp.parking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:jtds:sqlserver://sqlparking.cp9msqcpetdy.eu-north-1.rds.amazonaws.com:1433";
    private static final String Usuario = "admin";
    private static final String Password = "admin123";

    public static Connection obtenerConexion() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            return DriverManager.getConnection(URL,Usuario, Password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
