<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Validasi</title>
</head>
<body>
<%
    String userId   = request.getParameter("userId");
    String password = request.getParameter("password");
    Cookie cookie;

    if ((userId != null) && (userId.equalsIgnoreCase("ADMIN"))
            && (password != null) && (password.equalsIgnoreCase("ADMIN"))) {

        java.text.SimpleDateFormat waktu =
                new java.text.SimpleDateFormat("HH:mm:ss_dd-MM-yyyy");
        java.util.Date waktuLogin = new java.util.Date();

        // Simpan ke Session
        session.setAttribute("userLogin", "Administrator");
        session.setAttribute("waktuLogin", waktu.format(waktuLogin));
        session.setMaxInactiveInterval(20);

        // Simpan ke Cookie
        cookie = new Cookie("nama", "Administrator");
        cookie.setMaxAge(15);
        response.addCookie(cookie);

        cookie = new Cookie("waktuLogin", waktu.format(waktuLogin));
        cookie.setMaxAge(20);
        response.addCookie(cookie);

    } else {
        cookie = new Cookie("keterangan", "User+ID+atau+password+salah");
        cookie.setMaxAge(15);
        response.addCookie(cookie);
    }

    response.sendRedirect("index.jsp");
%>
</body>
</html>