<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Materi Session & Cookies</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1a1a2e, #16213e, #0f3460);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', sans-serif;
        }
        .card {
            border: none;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.4);
            backdrop-filter: blur(10px);
            background: rgba(255,255,255,0.95);
            width: 420px;
        }
        .card-header {
            background: linear-gradient(135deg, #667eea, #764ba2);
            border-radius: 20px 20px 0 0 !important;
            padding: 30px;
            text-align: center;
        }
        .form-control {
            border-radius: 10px;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            transition: all 0.3s;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102,126,234,0.25);
        }
        .btn-login {
            background: linear-gradient(135deg, #667eea, #764ba2);
            border: none;
            border-radius: 10px;
            padding: 12px;
            font-weight: 600;
            letter-spacing: 1px;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(102,126,234,0.4);
        }
        .input-group-text {
            background: #f8f9fa;
            border: 2px solid #e0e0e0;
            border-right: none;
            border-radius: 10px 0 0 10px;
        }
        .info-card {
            background: linear-gradient(135deg, #11998e, #38ef7d);
            border-radius: 15px;
            color: white;
            padding: 25px;
        }
        .cookie-item {
            background: rgba(255,255,255,0.15);
            border-radius: 8px;
            padding: 8px 12px;
            margin: 5px 0;
            font-size: 0.85rem;
        }
        .badge-session {
            background: rgba(255,255,255,0.2);
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.75rem;
        }
    </style>
</head>
<body>
<%
    String userLogin = "";
    Cookie[] cookies = request.getCookies();
    String waktuLogin = "";
    java.util.Date saatIni = new java.util.Date();
    java.text.SimpleDateFormat waktu = new java.text.SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    if (!session.isNew()) {
        try {
            userLogin = session.getAttribute("userLogin").toString();
            waktuLogin = session.getAttribute("waktuLogin").toString();
        } catch (Exception ex) {}
    }
%>

<div class="card">
    <div class="card-header">
        <i class="bi bi-shield-lock-fill text-white" style="font-size: 2.5rem;"></i>
        <h4 class="text-white mt-2 mb-0">Session & Cookies</h4>
        <small class="text-white opacity-75">Pemrograman 2 - Pertemuan 10</small>
    </div>

    <div class="card-body p-4">

        <%-- Tampilkan info cookies jika ada --%>
        <% if (cookies != null && cookies.length > 0) { %>
        <div class="mb-3">
            <small class="text-muted fw-bold"><i class="bi bi-cookie me-1"></i>DATA COOKIES</small>
            <% for (int i = 0; i < cookies.length; i++) { %>
            <div class="cookie-item bg-light border mt-1">
                <span class="text-muted">Cookie ke-<%=i%>:</span>
                <strong><%=cookies[i].getName()%></strong> = 
                <span class="text-primary"><%=cookies[i].getValue()%></span>
            </div>
            <% } %>
        </div>
        <hr>
        <% } %>

        <%-- Jika sudah login --%>
        <% if (!userLogin.equals("")) { %>
        <div class="info-card text-center">
            <i class="bi bi-person-check-fill" style="font-size: 3rem;"></i>
            <h5 class="mt-2 mb-1">Selamat Datang!</h5>
            <h4 class="fw-bold"><%=userLogin%></h4>
            <hr style="border-color: rgba(255,255,255,0.3)">
            <div class="row text-center">
                <div class="col-6">
                    <small class="opacity-75">Waktu Login</small>
                    <div class="badge-session mt-1">
                        <i class="bi bi-clock me-1"></i><%=waktuLogin%>
                    </div>
                </div>
                <div class="col-6">
                    <small class="opacity-75">Waktu Sekarang</small>
                    <div class="badge-session mt-1">
                        <i class="bi bi-clock-history me-1"></i><%=waktu.format(saatIni)%>
                    </div>
                </div>
            </div>
            <div class="mt-3">
                <small class="opacity-75">
                    <i class="bi bi-info-circle me-1"></i>
                    Session akan berakhir dalam 20 detik tidak aktif
                </small>
            </div>
        </div>

        <%-- Jika belum login - tampilkan form --%>
        <% } else { %>
        <h5 class="text-center mb-4 text-secondary">Silakan Login</h5>
        <form action="Validasi.jsp" method="post">
            <div class="mb-3">
                <label class="form-label fw-semibold text-secondary">
                    <i class="bi bi-person me-1"></i>User ID
                </label>
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-person-fill text-secondary"></i></span>
                    <input type="text" class="form-control" name="userId" 
                           placeholder="Masukkan User ID" autocomplete="off">
                </div>
            </div>
            <div class="mb-4">
                <label class="form-label fw-semibold text-secondary">
                    <i class="bi bi-lock me-1"></i>Password
                </label>
                <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-lock-fill text-secondary"></i></span>
                    <input type="password" class="form-control" name="password" 
                           placeholder="Masukkan Password">
                </div>
            </div>
            <div class="d-grid">
                <button type="submit" class="btn btn-login btn-primary text-white">
                    <i class="bi bi-box-arrow-in-right me-2"></i>LOGIN
                </button>
            </div>
            <p class="text-center text-muted mt-3 mb-0" style="font-size: 0.8rem;">
                <i class="bi bi-key me-1"></i>Hint: gunakan <strong>ADMIN</strong> / <strong>ADMIN</strong>
            </p>
        </form>
        <% } %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>