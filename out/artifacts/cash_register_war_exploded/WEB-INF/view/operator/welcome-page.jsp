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
<jsp:useBean id="receipt" scope="request" type="java.util.Map"/>
<script>
    function validateform(){
        var isWeight=document.updateQuantityForm.is_weight.value;
        var quantity=document.updateQuantityForm.quantity_to_update.value;

        if(isWeight == "false" && quantity % 1 != 0){
            alert("It not weight product, quantity must be whole number");
            return false;
        }
     }
</script>

<div class="container h-100">
    <div class="d-flex h-100">
        <div class="align-self-start mr-auto">
            <hr>
            <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>
            <hr>
            <form name="addProductsToReceiptForm" method="POST" action="add-product-to-receipt">
                <div class="container-fluid">
                    <table class="table table-bordered table-hover">
                        <tr>
                            <th scope="col"><fmt:message key="reference"/></th>
                            <th scope="col"><fmt:message key="name"/></th>
                            <th scope="col"><fmt:message key="quantity"/></th>

                        </tr>
                        <tr>
                            <td><input name="reference" value="${requestScope.reference}"></input></td>
                            <td><input name="name" value="${requestScope.name}"></input></td>
                            <td><input type="number" step="0.01" min="0" name="quantity" value="${requestScope.quantity}" required></input></td>
                            <td><button type="submit" class="btn btn-secondary btn-sm" name="command" value="operator.AddProductToReceipt"><fmt:message key="addproduct"/></button>
                                <input hidden type="number" name="receipt_id" value="${receipt_id}"/>
                                <input hidden type="text" name="message"/>
                        </tr>
                    </table>
                </div>
            </form>

            <div class="container-fluid">
                <table class="table table-bordered table-hover" id="table">
                    <tr>
                        <th scope="col"><fmt:message key="reference"/></th>
                        <th scope="col"><fmt:message key="name"/></th>
                        <th scope="col"><fmt:message key="quantity"/></th>
                         <th scope="col"><fmt:message key="action"/></th>
                    </tr>
                   <c:forEach var="receipt" items="${receipt}">
                         <tr>
                            <td>${receipt.key.reference}</td>
                            <td>${receipt.key.name}</td>
                            <td>${receipt.value}</td>
                            <td>
                                <form name="updateQuantityForm" method="POST" action="update-quantity" onsubmit="return validateform()">
                                    <input type="number" step="0.01" min="0" name="quantity_to_update" value="${requestScope.quantity_to_update}" required/>
                                     <input hidden type="text" name="is_weight" value="${receipt.key.isWeight}"/>
                                    <input hidden name="product_id" value="${receipt.key.id}"/>
                                    <input hidden name="reference" value="${receipt.key.reference}"/>
                                    <input hidden type="number" name="receipt_id" value="${receipt_id}"/>
                                    <button type="submit" class="btn btn-secondary btn-sm" name="command"
                                            value="operator.UpdateQuantityInReceipt" method="POST" action="update-quantity">
                                        <fmt:message key="updatequantity"/></button>
                                </form>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
                <div class="text-lest">
                    <form name="closeReceipt" method="POST" action="close-receipt">
                        <input hidden name="receipt_id" value="${receipt_id}"/>
                        <button type="submit" class="btn btn-secondary btn-sm" name="command"
                             value="operator.CloseReceipt" method="POST" action="close-receipt">
                            <fmt:message key="closereceipt"/></button>
                    </form>
                </div>

            </div>

        </div>
    </div>


</div>


</body>
</html>
