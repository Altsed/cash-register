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
<jsp:useBean id="receipts" scope="request" type="java.util.List"/>
<div class="container h-100">
    <div class="d-flex h-100">
        <div class="align-self-start mr-auto">
            <hr>
            <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>
            <hr>

            <form name="generateReport" method="POST" action="generate-report">
                <button type="submit" class="btn btn-info btn-sm" name="command"
                        value="chief.GenerateReport" method="POST" action="generate-report">
                    Generate report</button>
            </form>

            <hr>
            <div class="container-fluid">
                <table class="table table-bordered table-hover" id="table">
                    <tr>
                        <th scope="col"><fmt:message key="receipt"/></th>
                        <th scope="col"><fmt:message key="operator"/></th>
                        <th scope="col"><fmt:message key="status"/></th>
                         <th scope="col"><fmt:message key="action"/></th>
                    </tr>
                   <c:forEach var="receipt" items="${receipts}">
                         <tr>
                            <td>${receipt.id}</td>
                            <td>${receipt.user.login}</td>
                            <td>${receipt.isClosed}</td>
                            <td>
                                <form name="updateReceipt" method="POST" action="update-receipt">
                                     <input hidden type=number name="receipt_id" value="${receipt.id}"/>
                                     <button type="submit" class="btn btn-secondary btn-sm" name="command"
                                            value="chief.UpdateReceipt" method="POST" action="update-quantity">
                                         <fmt:message key="updatereceipt"/></button>
                                </form>
                             <td>
                                 <form name="cancelReceipt" method="POST" action="cancel-receipt">
                                     <input hidden type=number name="receipt_id" value="${receipt.id}"/>
                                     <button type="submit" class="btn btn-secondary btn-sm" name="command"
                                             value="chief.CancelReceipt" method="POST" action="cancel-receipt">
                                         <fmt:message key="cancelreceipt"/></button>
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
