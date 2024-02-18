<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.addHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Customer Page</h1>
<form id="logout_form" action="CustomerLogout" method="POST">
    <input type="hidden" name="action-type" value="Logout">
    <button type="submit">Logout</button>
</form>
</body>
</html>
