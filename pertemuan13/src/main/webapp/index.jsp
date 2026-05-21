<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aplikasi Administrasi Nilai Web</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="app-shell">
    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon">A</div>
            <div>
                <h2>AdminNilai</h2>
                <p>Pertemuan 13</p>
            </div>
        </div>

        <nav class="menu">
            <a class="active" href="index.jsp">Dashboard</a>
            <div class="menu-group">
                <button>Data Master <span>▾</span></button>
                <div class="submenu">
                    <a href="mahasiswa.jsp">Data Mahasiswa</a>
                    <a href="matakuliah.jsp">Data Mata Kuliah</a>
                    <a href="dosen.jsp">Data Dosen</a>
                </div>
            </div>
            <div class="menu-group">
                <button>Transaksi <span>▾</span></button>
                <div class="submenu">
                    <a href="input-nilai.jsp">Input Nilai</a>
                    <a href="rekap-nilai.jsp">Rekap Nilai</a>
                </div>
            </div>
            <div class="menu-group">
                <button>Laporan <span>▾</span></button>
                <div class="submenu">
                    <a href="laporan-mahasiswa.jsp">Laporan Mahasiswa</a>
                    <a href="laporan-nilai.jsp">Laporan Nilai</a>
                </div>
            </div>
            <a href="keluar.jsp">Keluar</a>
        </nav>
    </aside>

    <main class="content">
        <header class="topbar">
            <div>
                <p class="eyebrow">Aplikasi Web</p>
                <h1>Informasi Nilai Mahasiswa Universitas Pamulang</h1>
            </div>
            <div class="user-card">
                <span class="avatar">AR</span>
                <div>
                    <strong>Admin</strong>
                    <small>Universitas Pamulang</small>
                </div>
            </div>
        </header>

        <section class="hero-card">
            <div>
                <p class="eyebrow">Selamat Datang</p>
                <h2>Kelola data akademik Mahasiswa.</h2>
                <p>Project ini dibuat sesuai materi Pertemuan 13: pembuatan form utama aplikasi web menggunakan JSP dan CSS.</p>
                <a class="primary-button" href="input-nilai.jsp">Mulai Input Nilai</a>
            </div>
            <div class="hero-graphic">
                <span>JSP</span>
            </div>
        </section>

        <section class="stats-grid">
            <article class="stat-card">
                <span>👨‍🎓</span>
                <h3>128</h3>
                <p>Mahasiswa</p>
            </article>
            <article class="stat-card">
                <span>📚</span>
                <h3>12</h3>
                <p>Mata Kuliah</p>
            </article>
            <article class="stat-card">
                <span>📝</span>
                <h3>86%</h3>
                <p>Nilai Terinput</p>
            </article>
            <article class="stat-card">
                <span>📄</span>
                <h3>5</h3>
                <p>Laporan</p>
            </article>
        </section>

        <section class="panel-grid">
            <article class="panel">
                <h3>Menu Utama</h3>
                <p>Menu sudah dibuat dropdown seperti materi, tetapi tampilannya dibuat lebih modern.</p>
                <ul>
                    <li>Data Master</li>
                    <li>Transaksi Nilai</li>
                    <li>Laporan Akademik</li>
                </ul>
            </article>
            <article class="panel">
                <h3>Aktivitas Terbaru</h3>
                <div class="activity"><span></span> Admin membuka dashboard</div>
                <div class="activity"><span></span> Menu input nilai tersedia</div>
                <div class="activity"><span></span> Laporan siap dikembangkan</div>
            </article>
        </section>
    </main>
</div>
</body>
</html>
