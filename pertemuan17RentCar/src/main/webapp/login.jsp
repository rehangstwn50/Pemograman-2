<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login — RentCar System</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI',Arial,sans-serif; display:flex; height:100vh; background:#f5f6fa; }
        .left-panel {
            width: 42%;
            background: #1a2e4a;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            padding: 48px 40px;
            position: relative;
            overflow: hidden;
        }
        .left-panel::before {
            content:'';
            position:absolute;
            bottom:-80px; right:-80px;
            width:280px; height:280px;
            border-radius:50%;
            background: rgba(79,195,247,0.08);
        }
        .left-panel::after {
            content:'';
            position:absolute;
            top:-60px; left:-60px;
            width:200px; height:200px;
            border-radius:50%;
            background: rgba(255,255,255,0.04);
        }
        .car-icon {
            font-size:40px;
            background: rgba(255,255,255,0.12);
            width:64px; height:64px;
            border-radius:14px;
            display:flex; align-items:center; justify-content:center;
            margin-bottom:20px;
        }
        .left-panel h1 { color:#fff; font-size:26px; font-weight:700; margin-bottom:6px; }
        .left-panel p { color:rgba(255,255,255,0.55); font-size:13px; margin-bottom:32px; }
        .car-img-placeholder {
            width:100%;
            max-width:320px;
            height:170px;
            background: rgba(255,255,255,0.06);
            border-radius:12px;
            display:flex; align-items:center; justify-content:center;
            color:rgba(255,255,255,0.2);
            font-size:48px;
            margin-top:12px;
        }
        .right-panel {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 40px;
            background: #fff;
        }
        .login-box { width: 100%; max-width: 360px; }
        .login-box h2 { font-size:24px; font-weight:700; color:#1a2e4a; margin-bottom:4px; }
        .login-box .subtitle { font-size:13px; color:#718096; margin-bottom:28px; }
        .form-group { margin-bottom:16px; }
        .form-group label { display:block; font-size:12px; color:#4a5568; font-weight:600; margin-bottom:6px; letter-spacing:0.3px; }
        .input-wrap { position:relative; }
        .input-wrap .ic {
            position:absolute; left:12px; top:50%; transform:translateY(-50%);
            color:#a0aec0; font-size:15px;
        }
        .input-wrap input {
            width:100%; padding:10px 12px 10px 36px;
            border:1.5px solid #e8eaf0; border-radius:8px;
            font-size:14px; outline:none; color:#2d3748;
            transition:border-color 0.2s;
        }
        .input-wrap input:focus { border-color:#1a2e4a; }
        .input-wrap .toggle-pw {
            position:absolute; right:12px; top:50%; transform:translateY(-50%);
            background:none; border:none; cursor:pointer; color:#a0aec0; font-size:15px;
        }
        .row-check {
            display:flex; align-items:center; justify-content:space-between;
            margin-bottom:20px;
        }
        .row-check label { display:flex; align-items:center; gap:7px; font-size:13px; color:#4a5568; cursor:pointer; }
        .row-check a { font-size:12px; color:#1a2e4a; text-decoration:none; }
        .btn-login {
            width:100%; padding:12px;
            background:#1a2e4a; color:#fff;
            border:none; border-radius:8px;
            font-size:15px; font-weight:600;
            cursor:pointer; transition:background 0.2s;
            letter-spacing:0.3px;
        }
        .btn-login:hover { background:#243d61; }
        .divider { text-align:center; font-size:12px; color:#a0aec0; margin:20px 0 8px; }
        .footer-text { text-align:center; font-size:11px; color:#a0aec0; margin-top:24px; }
        .alert-err {
            background:#ffebee; border:1px solid #ef9a9a;
            color:#c62828; border-radius:8px;
            padding:10px 14px; font-size:13px;
            margin-bottom:16px; display:flex; gap:8px; align-items:center;
        }
    </style>
</head>
<body>
<div class="left-panel">
    <div class="car-icon">🚗</div>
    <h1>RentCar System</h1>
    <p>Kelola Penyewaan Mobil dengan Mudah</p>
    <div class="car-img-placeholder">🚘</div>
</div>
<div class="right-panel">
    <div class="login-box">
        <h2>Masuk ke Sistem</h2>
        <p class="subtitle">Silakan masuk menggunakan akun Anda</p>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert-err">⚠ <%= request.getAttribute("error") %></div>
        <% } %>

        <form method="post" action="<%= request.getContextPath() %>/login">
            <div class="form-group">
                <label>Username</label>
                <div class="input-wrap">
                    <span class="ic">👤</span>
                    <input type="text" name="username" placeholder="Masukkan username" required autocomplete="username">
                </div>
            </div>
            <div class="form-group">
                <label>Password</label>
                <div class="input-wrap">
                    <span class="ic">🔒</span>
                    <input type="password" name="password" id="pw" placeholder="Masukkan password" required autocomplete="current-password">
                    <button type="button" class="toggle-pw" onclick="togglePw()">👁</button>
                </div>
            </div>
            <div class="row-check">
                <label><input type="checkbox"> Ingat saya</label>
                <a href="#">Lupa Password?</a>
            </div>
            <button type="submit" class="btn-login">Masuk</button>
        </form>

        <div class="divider">— Sistem Logistik Internal —</div>
        <div class="footer-text">© 2024 RentCar System. All rights reserved.</div>
    </div>
</div>
<script>
function togglePw() {
    var pw = document.getElementById('pw');
    pw.type = pw.type === 'password' ? 'text' : 'password';
}
</script>
</body>
</html>
