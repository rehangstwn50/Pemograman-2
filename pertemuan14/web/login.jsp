<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - UNPAM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="header">
    <img src="${pageContext.request.contextPath}/logo.png" alt="Logo" class="logo"/>
    <div class="header-text">
        <h2>Informasi Nilai Mahasiswa</h2>
        <h1>UNIVERSITAS PAMULANG</h1>
        <p>Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</p>
    </div>
</div>
<div style="display:flex;justify-content:center;align-items:center;min-height:400px;background:#f0f5ff;">
<div style="background:white;padding:40px;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.15);width:320px;">
    <h2 style="color:#003399;text-align:center;margin-bottom:20px;">Login Admin</h2>
    <% if(request.getAttribute("error") != null) { %>
    <div style="background:#ffe0e0;color:#cc0000;padding:8px;border-radius:4px;margin-bottom:12px;text-align:center;">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <div style="margin-bottom:15px;">
            <label style="display:block;font-weight:bold;margin-bottom:5px;color:#333;">Username</label>
            <input type="text" name="username" style="width:100%;padding:8px;border:1px solid #ccc;border-radius:4px;box-sizing:border-box;" required/>
        </div>
        <div style="margin-bottom:20px;">
            <label style="display:block;font-weight:bold;margin-bottom:5px;color:#333;">Password</label>
            <input type="password" name="password" style="width:100%;padding:8px;border:1px solid #ccc;border-radius:4px;box-sizing:border-box;" required/>
        </div>
        <button type="submit" style="width:100%;padding:10px;background:#003399;color:white;border:none;border-radius:4px;font-size:15px;cursor:pointer;">Login</button>
    </form>
</div>
</div>
<div class="footer">
    <p>Copyright &copy; 2016 Universitas Pamulang</p>
</div>
</body>
</html>
