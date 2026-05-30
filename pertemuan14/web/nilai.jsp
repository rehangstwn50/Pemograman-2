<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*,model.Nilai,model.Mahasiswa,model.MataKuliah"%>

<%
    if(session.getAttribute("user")==null){
        response.sendRedirect(request.getContextPath()+"/login");
        return;
    }

    List<Nilai> listNilai = (List<Nilai>) request.getAttribute("listNilai");
    List<Mahasiswa> listMhs = (List<Mahasiswa>) request.getAttribute("listMahasiswa");
    List<MataKuliah> listMk = (List<MataKuliah>) request.getAttribute("listMataKuliah");

    String pesan = (String) session.getAttribute("pesan");
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Transaksi Nilai - UNPAM</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">

<style>

.preview-box{
    display:inline-block;
    background:#e8f0ff;
    border:1px solid #aabbdd;
    border-radius:4px;
    padding:6px 14px;
    font-size:13px;
    color:#003399;
    font-weight:bold;
    min-width:120px;
    text-align:center;
}

/* POPUP PESAN */
.pesan{
    position:fixed;
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    background:#28a745;
    color:white;
    padding:18px 35px;
    border-radius:10px;
    font-size:16px;
    font-weight:bold;
    box-shadow:0 5px 15px rgba(0,0,0,0.3);
    z-index:9999;
    animation:fadeIn 0.4s;
}
}

@keyframes fadeIn{
    from{
        opacity:0;
        transform:translateY(-10px);
    }
    to{
        opacity:1;
        transform:translateY(0);
    }
}

</style>

</head>

<body>

<% if(pesan != null){ %>

<div class="pesan" id="pesanBox">
    <%= pesan %>
</div>

<script>
setTimeout(function(){
    var box = document.getElementById("pesanBox");
    if(box){
        box.style.display = "none";
    }
}, 3000);
</script>

<%
session.removeAttribute("pesan");
}
%>

<%@ include file="header.jsp" %>

<div class="content">

  <div class="sidebar">

    <div class="sidebar-section">Master Data</div>

    <a href="${pageContext.request.contextPath}/mahasiswa"
       class="sidebar-link">Mahasiswa</a>

    <a href="${pageContext.request.contextPath}/matakuliah"
       class="sidebar-link">Mata Kuliah</a>

    <div class="sidebar-section">Transaksi</div>

    <a href="${pageContext.request.contextPath}/nilai"
       class="sidebar-link">Nilai</a>

    <div class="sidebar-section">Laporan</div>

    <a href="${pageContext.request.contextPath}/laporan"
       class="sidebar-link">Nilai</a>

    <div class="sidebar-section">Akun</div>

    <a href="${pageContext.request.contextPath}/logout"
       class="sidebar-link">Logout</a>

  </div>

  <div class="main">

    <h2>Transaksi Nilai</h2>

    <div class="form-box">

      <b>Input Nilai Mahasiswa</b>

      <form method="post"
            action="${pageContext.request.contextPath}/nilai">

        <input type="hidden" name="action" value="tambah"/>

        <div class="form-row" style="margin-bottom:8px;">

          <select name="nim" required style="width:220px;">

            <option value="">-- Pilih Mahasiswa --</option>

            <% for(Mahasiswa m : listMhs) { %>

            <option value="<%= m.getNim() %>">
                <%= m.getNim() %> - <%= m.getNama() %>
            </option>

            <% } %>

          </select>

          <select name="kode_mk" required style="width:230px;">

            <option value="">-- Pilih Mata Kuliah --</option>

            <% for(MataKuliah mk : listMk) { %>

            <option value="<%= mk.getKodeMk() %>">
                <%= mk.getKodeMk() %> - <%= mk.getNamaMk() %>
            </option>

            <% } %>

          </select>

        </div>

        <div class="form-row">

          <input type="number"
                 name="tugas"
                 id="tugas"
                 placeholder="Nilai Tugas (0-100)"
                 min="0"
                 max="100"
                 step="0.1"
                 required
                 style="width:175px;"
                 oninput="hitungPreview()"/>

          <input type="number"
                 name="uts"
                 id="uts"
                 placeholder="Nilai UTS (0-100)"
                 min="0"
                 max="100"
                 step="0.1"
                 required
                 style="width:165px;"
                 oninput="hitungPreview()"/>

          <input type="number"
                 name="uas"
                 id="uas"
                 placeholder="Nilai UAS (0-100)"
                 min="0"
                 max="100"
                 step="0.1"
                 required
                 style="width:165px;"
                 oninput="hitungPreview()"/>

          <span class="preview-box" id="preview">
              Nilai Akhir: -
          </span>

          <button type="submit" class="btn-tambah">
              Simpan
          </button>

        </div>

      </form>

    </div>

    <a href="${pageContext.request.contextPath}/laporan"
       class="btn-laporan">

       &#128438; Cetak Laporan PDF

    </a>

    <div class="tbl-wrap">

    <table class="data-table">

      <tr>
        <th>No</th>
        <th>NIM</th>
        <th>Nama</th>
        <th>Sem</th>
        <th>Kelas</th>
        <th>Kode MK</th>
        <th>Mata Kuliah</th>
        <th>SKS</th>
        <th>Tugas</th>
        <th>UTS</th>
        <th>UAS</th>
        <th>Nilai Akhir</th>
        <th>Huruf</th>
        <th>Status</th>
        <th>Aksi</th>
      </tr>

      <% int no=1; for(Nilai n : listNilai) { %>

      <tr>

        <td><%= no++ %></td>
        <td><%= n.getNim() %></td>

        <td class="td-left">
            <%= n.getNamaMahasiswa() %>
        </td>

        <td><%= n.getSemester() %></td>
        <td><%= n.getKelas() %></td>
        <td><%= n.getKodeMk() %></td>

        <td class="td-left">
            <%= n.getNamaMk() %>
        </td>

        <td><%= n.getSks() %></td>
        <td><%= n.getTugas() %></td>
        <td><%= n.getUts() %></td>
        <td><%= n.getUas() %></td>

        <td>
            <b><%= n.getNilaiAkhir() %></b>
        </td>

        <td>
            <b><%= n.getHuruf() %></b>
        </td>

        <td class="<%= "Lulus".equals(n.getStatus()) ? "badge-lulus" : "badge-tidak" %>">
            <%= n.getStatus() %>
        </td>

        <td>

          <form method="post"
                action="${pageContext.request.contextPath}/nilai"
                style="display:inline;">

            <input type="hidden" name="action" value="hapus"/>

            <input type="hidden"
                   name="id"
                   value="<%= n.getId() %>"/>

            <button type="submit"
                    class="btn-hapus"
                    onclick="return confirm('Hapus data ini?')">

                Hapus

            </button>

          </form>

        </td>

      </tr>

      <% } %>

      <% if(listNilai.isEmpty()) { %>

      <tr>
        <td colspan="15"
            style="padding:20px;color:#999;">

            Belum ada data nilai.

        </td>
      </tr>

      <% } %>

    </table>

    </div>

  </div>

</div>

<div class="footer">
    <p>Copyright &copy; 2016 Universitas Pamulang</p>
</div>

<script>

function hitungPreview() {

    var t = parseFloat(document.getElementById('tugas').value) || 0;
    var u = parseFloat(document.getElementById('uts').value) || 0;
    var a = parseFloat(document.getElementById('uas').value) || 0;

    var na =
        Math.round(((t * 0.30) + (u * 0.35) + (a * 0.35)) * 10) / 10;

    var huruf =
        na >= 80 ? 'A' :
        na >= 70 ? 'B' :
        na >= 60 ? 'C' :
        na >= 50 ? 'D' : 'E';

    document.getElementById('preview').innerText =
        'Nilai Akhir: ' + na + ' (' + huruf + ')';
}

</script>

</body>
</html>