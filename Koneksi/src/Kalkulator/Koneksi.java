import java.sql.*;

public class Koneksi {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url  = "jdbc:mysql://localhost:3306/akademik";
                String user = "root";
                String pass = "";  // sesuaikan password MySQL Anda
                koneksi = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi berhasil!");
            } catch (SQLException e) {
                System.out.println("Koneksi gagal: " + e.toString());
            }
        }
        return koneksi;
    }
}