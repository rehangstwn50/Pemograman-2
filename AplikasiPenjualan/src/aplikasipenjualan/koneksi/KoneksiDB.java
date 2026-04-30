package aplikasipenjualan.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class KoneksiDB {

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB   = "db_penjualan";
    private static final String USER = "root";
    private static final String PASS = "";   // sesuaikan password MySQL kamu

    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://" + HOST + ":" + PORT
                           + "/" + DB
                           + "?useSSL=false&serverTimezone=Asia/Jakarta"
                           + "&allowPublicKeyRetrieval=true";
                conn = DriverManager.getConnection(url, USER, PASS);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Koneksi Database Gagal!\n" + e.getMessage(),
                "Error Koneksi", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}