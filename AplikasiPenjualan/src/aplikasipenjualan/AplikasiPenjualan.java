package aplikasipenjualan;

import aplikasipenjualan.form.FormMenu;

public class AplikasiPenjualan {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormMenu().setVisible(true);
        });
    }
}