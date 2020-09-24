<%--
  Created by IntelliJ IDEA.
  User: SEVEN-WORK
  Date: 16.09.2020
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <h2>Result:</h2>

  <form name="loginForm" method="GET" action="AddProduct">
    <input type="hidden" name="command" value="AddProduct" />
    <input type="submit" value="AddProduct"  />

  </form>

  Link <a href="/stock">stockman</a>
  <br>
  Link <a href="/operator">operator</a>
  <br>
  Link <a href="/cheif_operator">cheif_operator</a>

  </body>
</html>
