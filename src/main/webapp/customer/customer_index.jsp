<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<a href="customer/cus2.jsp">Goto..</a>
</body>
</html>
