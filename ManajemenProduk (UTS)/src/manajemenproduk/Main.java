package manajemenproduk;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ManajemenProdukGUI gui = new ManajemenProdukGUI();
            gui.setVisible(true);
        });
    }
}