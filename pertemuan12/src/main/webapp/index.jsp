<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pertemuan 12 - Menghitung Nilai</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="card">
    <h1>Program Menghitung Nilai Mahasiswa</h1>
    <p class="subtitle">Pertemuan 12 - Servlet dan JSP</p>

    <form action="MenghitungNilai" method="post">
        <label>Nama Mahasiswa</label>
        <input type="text" name="nama" placeholder="Masukkan nama" required>

        <label>NIM</label>
        <input type="text" name="nim" placeholder="Masukkan NIM" required>

        <label>Nilai Tugas</label>
        <input type="number" name="tugas" min="0" max="100" required>

        <label>Nilai UTS</label>
        <input type="number" name="uts" min="0" max="100" required>

        <label>Nilai UAS</label>
        <input type="number" name="uas" min="0" max="100" required>

        <button type="submit">Hitung Nilai</button>
    </form>

    <p class="center"><a href="TestJSP.jsp">Buka TestJSP</a></p>
</div>
</body>
</html>
