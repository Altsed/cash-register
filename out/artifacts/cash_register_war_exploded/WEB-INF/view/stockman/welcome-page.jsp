<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language" />
<html lang="${language}">
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
<script>
    function validateform(){
        var stock=document.addProductForm.stock.value;
        if(!$('#weight').is(":checked") && stock % 1 != 0){
            alert("It not weight product, quantity must be whole number");
            return false;
        }
     }
</script>

<div class="container h-100">
    <div class="d-flex h-100">
        <div class="align-self-start mr-auto">
            <hr>
            <h4 style="color: yellow" class="page-header"><fmt:message key="warehousewelcome"/></h4>
            <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>
            <h4 style="color: yellow" class="page-header"></h4>
            <hr>
            <form name="addProductForm" method="POST" action="add-product" onsubmit="return validateform()">
                <div class="container-fluid">
                    <table class="table table-bordered table-hover">
                        <tr>
                            <th scope="col"><fmt:message key="reference"/></th>
                            <th scope="col"><fmt:message key="name"/></th>
                            <th scope="col"><fmt:message key="weight"/></th>
                            <th scope="col"><fmt:message key="stock"/></th>
                        </tr>
                        <tr>
                            <td><input name="reference" value="${requestScope.referenceOfProduct}" required></input></td>
                            <td><input name="name" value="${requestScope.nameOfProduct}" required></input></td>
                            <td><input id="weight" class="weight_checkbox" type="checkbox" name="isWeight"></input></td>
                            <td><input type="number" step="0.01" min="0" name="stock" required></input></td>
                            <td><button type="submit" class="btn btn-secondary btn-sm" name="command" value="stockman.AddProduct"><fmt:message key="addproduct"/></button>
                        </tr>
                    </table>
                </div>
            </form>
            <div class="container-fluid">
                <table class="table table-bordered table-hover" id="table">
                    <tr>
                        <th scope="col"><fmt:message key="reference"/></th>
                        <th scope="col"><fmt:message key="name"/></th>
                        <th scope="col"><fmt:message key="weight"/></th>
                        <th scope="col"><fmt:message key="stock"/></th>
                        <th scope="col"><fmt:message key="action"/></th>

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
                            <td>${products.reference}</td>
                            <td>${products.name}</td>
                            <td>${products.isWeight}</td>
                            <td>${products.stock}</td>
                            <td>
                                    <form name="updateQuantityForm" method="POST" action="update-quantity">
                                        <input type="number" step="0.01" min="0" name="stock" required/>
                                        <input hidden type="text" name="nameOfProduct" value="${products.name}"/>
                                        <input hidden type="text" name="isWeight" value="${products.isWeight}"/>
                                        <input hidden name="productId" value="${products.id}">
                                        <button type="submit" class="btn btn-secondary btn-sm" name="command" value="stockman.UpdateStock" method="POST" action="update-quantity"><fmt:message key="updatequantity"/></button>
                                    </form>
                             </td>

                        </tr>
                    </c:forEach>
                </table>
            </div>

            <form class="form-inline" action="ReadProducts">
                <input type="hidden" name="currentPage" value="1">
                <div class="form-group mb-2">
                    <label for="records">Select records per page:</label>
                </div>
                <div class="form-group mx-sm-3 mb-2">
                    <select class="form-control form-control-sm" id="records" name="recordsPerPage">
                        <option value="5">5</option>
                        <option value="10" selected>10</option>
                        <option value="15">15</option>
                    </select>

                </div>
                <button type="submit" class="btn btn-secondary btn-sm mb-2" name="command" value="stockman.Welcome">Submit</button>
            </form>
            <nav aria-label="Navigation for countries">
                <ul class="pagination pagination-sm">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item">
                            <form method="post" action="page-${currentPage-1}" class="inline">
                                <input type="hidden" name="recordsPerPage" value=${recordsPerPage}>
                                <input type="hidden" name="currentPage" value=${currentPage-1}>
                                <button type="submit" class="btn btn-secondary btn-sm" name="command" value="stockman.Welcome" class="link-button">
                                    Previous
                                </button>
                            </form>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="page-item active"><a class="page-link">
                                        ${i} <span class="sr-only">(current)</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <form method="post" action="page-${i}" class="inline">
                                        <input type="hidden" name="recordsPerPage" value=${recordsPerPage}>
                                        <input type="hidden" name="currentPage" value=${i}>
                                        <button type="submit" class="btn btn-secondary btn-sm" name="command" value="stockman.Welcome"
                                                class="link-button">
                                                ${i}
                                        </button>
                                    </form>
                               </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item">
                            <form method="post" action="page-${currentPage+1}" class="inline">
                                <input type="hidden" name="recordsPerPage" value=${recordsPerPage}>
                                <input type="hidden" name="currentPage" value=${currentPage+1}>
                                <button type="submit" class="btn btn-secondary btn-sm" name="command" value="stockman.Welcome"
                                        class="link-button">
                                    Next
                                </button>
                            </form>
                        </li>
                   </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>


</body>
</html>
