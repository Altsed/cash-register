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
     <div class="container h-100">
     <div class="d-flex h-100">
       <div class="col-md-6 col-md-offset-2">
         <hr>
<%--         <h4 class="page-header"><%= request.getAttribute("message") %></h4>--%>
         <h4 class="page-header">${requestScope.message}</h4>
         <form name="loginForm" method="POST">
           <input hidden name="registration"/>
           <div class="form-group">
             <label for="login"><fmt:message key="login"/></label>
             <input type="text"
                    name="login"
                    class="form-control"
                    id="login"
                    placeholder=<fmt:message key="login"/>
                    value=${requestScope.login}
             >
           </div>
           <div class="form-group">
             <label for="exampleInputPassword"><fmt:message key="password"/></label>
             <input type="password"
                    name="password"
                    class="form-control"
                    id="exampleInputPassword"
                    placeholder=<fmt:message key="password"/>
             >
           </div>
           <div class="form-group">
             <label for="exampleInputPassword"><fmt:message key="role"/></label>
             <jsp:useBean id="roles" scope="session" type="java.util.List"/>
             <select class="form-control" name="role" required>
               <option value="" selected disabled hidden><fmt:message key="chooserole"/></option>
               <c:forEach items="${roles}"  var="role">
                 <option value="${role.id}">${role.name}
                 </option>
               </c:forEach>
             </select>
            </div>
           <hr>
             <button onclick="window.location.href='index.jsp'" class="btn btn-secondary mr-2" ></i><fmt:message key="back"/></button>
              <button type="submit" class="btn btn-secondary mr-2" name="command" value="Register"><fmt:message key="register"/></button>
         </form>
       </div>
     </div>
   </div>
   <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body>
</html>
