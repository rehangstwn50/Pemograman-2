package manajemenproduk;

import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class StackProduk {

    private static final Logger logger = Logger.getLogger(StackProduk.class.getName());
    private LinkedList<Produk> stack = new LinkedList<>();

    // Tambah produk ke stack (push)
    public void push(Produk p) {
        stack.push(p);
        logger.log(Level.INFO, "Produk ditambahkan: {0}, Stok: {1}",
                new Object[]{p.getNama(), p.getStok()});
    }

    // Hapus produk teratas dari stack (pop)
    public Produk pop() throws Exception {
        if (stack.isEmpty()) {
            throw new Exception("Stack kosong! Tidak ada produk untuk dihapus.");
        }
        Produk p = stack.peek();
        if (p.getStok() == 0) {
            throw new Exception("Stok produk '" + p.getNama() + "' kosong! Tidak bisa dihapus dari gudang.");
        }
        Produk removed = stack.pop();
        logger.log(Level.INFO, "Produk dihapus: {0}", removed.getNama());
        return removed;
    }

    // Lihat produk teratas tanpa menghapus
    public Produk peek() throws Exception {
        if (stack.isEmpty()) {
            throw new Exception("Stack kosong!");
        }
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    // Ambil semua produk sebagai list
    public LinkedList<Produk> getAll() {
        return new LinkedList<>(stack);
    }

    // Searching berdasarkan nama
    public Produk cariByNama(String nama) {
        for (Produk p : stack) {
            if (p.getNama().equalsIgnoreCase(nama)) {
                return p;
            }
        }
        return null;
    }

    // Sorting berdasarkan harga (bubble sort)
    public LinkedList<Produk> sortByHarga() {
        LinkedList<Produk> list = new LinkedList<>(stack);
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getHarga() > list.get(j + 1).getHarga()) {
                    Produk temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }

    // Sorting berdasarkan kategori (bubble sort)
    public LinkedList<Produk> sortByKategori() {
        LinkedList<Produk> list = new LinkedList<>(stack);
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getKategori().compareToIgnoreCase(list.get(j + 1).getKategori()) > 0) {
                    Produk temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }
}