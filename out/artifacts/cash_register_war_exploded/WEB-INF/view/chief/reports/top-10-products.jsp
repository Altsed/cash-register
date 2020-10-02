<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: SEVEN-WORK
  Date: 24.09.2020
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.1.0/css/flag-icon.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/superhero/bootstrap.min.css" integrity="sha384-HnTY+mLT0stQlOwD3wcAzSVAZbrBp141qwfR4WfTqVQKSgmcgzk+oP0ieIyrxiFO" crossorigin="anonymous">
    <title>Cash register</title>
    <jsp:include page="/WEB-INF/view/header.jsp"></jsp:include>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</head>
<body>
<jsp:useBean id="top10" scope="request" type="java.util.List"/>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.1.1/jspdf.umd.min.js"></script>


<div class="container h-100">
    <div class="d-flex h-100">
        <div class="align-self-start mr-auto">
            <hr>
            <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>
            <hr>
            <div class="container-fluid">
                <table class="table table-bordered table-hover" id="table">
                    <tr>
                        <th scope="col">Reference</th>
                        <th scope="col">Name of product</th>
                        <th scope="col">Sum</th>

                    </tr>
                    <c:forEach var="product" items="${top10}">
                        <tr>
                            <td>${product.reference}</td>
                            <td>${product.name}</td>
                            <td>${product.stock}</td>
                         </tr>
                    </c:forEach>
                </table>
            </div>
           <hr>
            <form name="welcome" method="POST" action="generate-report">
                <button type="submit" class="btn btn-secondary btn-sm" name="command"
                        value="chief.GenerateReport" method="POST" action="generate-report">
                    Back</button>
            </form>

        </div>
    </div>


</div>
</body>
</html>

