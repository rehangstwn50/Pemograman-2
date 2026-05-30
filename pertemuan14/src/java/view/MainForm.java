
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainForm extends JFrame {

    JTable table;

    public MainForm(){
        setTitle("Dashboard UNPAM");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel header = new JPanel();
        header.setBackground(new Color(0,51,153));

        JLabel title = new JLabel("DASHBOARD AKADEMIK UNPAM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        header.add(title);

        String[] kolom = {"NIM","Nama","Jurusan"};
        Object[][] data = {
            {"231011", "Andi", "Teknik Informatika"},
            {"231012", "Budi", "Sistem Informasi"}
        };

        table = new JTable(new DefaultTableModel(data, kolom));

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new MainForm().setVisible(true);
    }
}
