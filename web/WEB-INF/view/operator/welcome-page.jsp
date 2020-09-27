<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: SEVEN-WORK
  Date: 24.09.2020
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

</head>

<body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<jsp:useBean id="products" scope="request" type="java.util.List"/>

<div class="container h-100">
    <div class="d-flex h-100">
        <div class="align-self-start mr-auto">
            <hr>
            <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>
            <hr>
            <form name="addProductForm" method="POST" action="add-product" onsubmit="return validateform()">
                <div class="container-fluid">
                    <table class="table table-bordered table-hover">
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">is weight</th>
                            <th scope="col">on stock</th>
                        </tr>
                        <tr>
                            <td><input name="name" value="${requestScope.nameOfProduct}" required></input></td>
                            <td><input id="weight" class="weight_checkbox" type="checkbox" name="isWeight"></input></td>
                            <td><input type="number" step="0.01" min="0" name="stock"></input></td>
                            <td><button type="submit" class="btn btn-secondary btn-sm" name="command" value="AddProduct">Add product</button>
                        </tr>
                    </table>
                </div>
            </form>
            <div class="container-fluid">
                <table class="table table-bordered table-hover" id="table">
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">is weight</th>
                        <th scope="col">on stock</th>
                        <th scope="col">action</th>

                    </tr>

                    <c:forEach var="products" items="${products}" >
                        <!-- construct update link with customer id -->
                        <c:url var="updateLink" value="welcome-page.jsp">
                            <c:param name="productId" value="${products.id}" />
                        </c:url>
                        <c:url var="updateLinkName" value="">
                            <c:param name="productName" value="${products.name}" />

                        </c:url>
                        <tr>
                            <td>${products.name}</td>
                            <td>${products.isWeight}</td>
                            <td>${products.stock}</td>
                            <td>

                                    <form name="updateQuantityForm" method="POST" action="update-quantity">
                                        <input type="number" step="0.01" min="0" name="stock"/>
                                        <input hidden type="text" name="nameOfProduct" value="${products.name}"/>
                                        <input hidden type="text" name="isWeight" value="${products.isWeight}"/>
                                        <input hidden name="productId" value="${products.id}">
                                        <button type="submit" class="btn btn-secondary btn-sm" name="command" value="UpdateStock" method="POST" action="update-quantity">Update quantity</button>
                                    </form>



                             </td>

                        </tr>
                    </c:forEach>
                </table>

            </div>

        </div>
    </div>


</div>


</body>
</html>
