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


</head>
<body>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<div class="container h-100">
    <div class="d-flex h-100">
        <div class="align-self-start mr-auto">
            <hr>
            <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>
            <hr>

            <form name="generateReport" method="POST" action="generate-top-10-report">
                <input hidden name="report" value="top10"/>
                <button type="submit" class="btn btn-info btn-sm" name="command"
                        value="chief.GenerateReport" method="POST" action="generate-top-10-report">
                    Generate top 10 products report</button>
            </form>
            <form name="generateReport" method="POST" action="generate-best-op-report">
                <input hidden name="report" value="best-operators"/>
                <button type="submit" class="btn btn-info btn-sm" name="command"
                        value="chief.GenerateReport" method="POST" action="generate-best-op-report">
                    Generate best operators report</button>
            </form>

            <hr>
            <form name="welcome" method="POST" action="back-welcome">
                <button type="submit" class="btn btn-secondary btn-sm" name="command"
                        value="chief.Welcome" method="POST" action="cancel-receipt">
                    Back</button>
            </form>

        </div>
    </div>


</div>

</body>
</html>

