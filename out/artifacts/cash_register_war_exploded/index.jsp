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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/superhero/bootstrap.min.css" integrity="sha384-HnTY+mLT0stQlOwD3wcAzSVAZbrBp141qwfR4WfTqVQKSgmcgzk+oP0ieIyrxiFO" crossorigin="anonymous">
    <title>Cash register</title>
<%--    <jsp:include page="/WEB-INF/view/header.jsp"></jsp:include>--%>
  </head>
  <body>
  <nav class="navbar navbar-expand-lg py-3 navbar-dark bg-dark shadow-sm">
    <div class="container">
      <a href="#" class="navbar-brand">
        <!-- Logo Image -->
        <img src="https://res.cloudinary.com/mhmd/image/upload/v1557368579/logo_iqjuay.png" width="45" alt="" class="d-inline-block align-middle mr-2">
        <!-- Logo Text -->
        <span class="text-uppercase font-weight-bold">Company</span>
      </a>

      <button type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler"><span class="navbar-toggler-icon"></span></button>
      <div id="navbarSupportedContent" class="collapse navbar-collapse">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <form>
              <select class="form-control" id="language" name="language" onchange="submit()">
                <option selected value="Language">${sessionScope.language}</option>
                <option value="EN" ${sessionScope.language == 'en' ? 'selected' : ''}>English</option>
                <option value="UA" ${sessionScope.language == 'ua' ? 'selected' : ''}>Українська</option>
              </select>
            </form>

          </li>
          <li class="nav-item">
            <form name="logout" action="logout" method="POST">
              <button class="btn btn-link" type="submit" name="command" value="Logout" method="POST" action="logout" onsubmit="${sessionStorage.clear()}">
                <fmt:message key="logout"/></button>
            </form>
          </li>
        </ul>
      </div>
    </div>
  </nav>
   <div class="container h-100">
     <div class="d-flex h-100">
       <div class="col-md-6 col-md-offset-2">
         <hr>
         <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>

         <h2 class="page-header"><fmt:message key="login/register"/></h2>

         <form name="loginForm" method="POST" action="welcome-page">
           <div class="form-group">
             <label for="login"><fmt:message key="login"/></label>
             <input type="login"
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

            <button type="submit" class="btn btn-secondary mr-2" name="command" value="Login"><fmt:message key="login"/></button>
           <button formaction="register" type="submit" class="btn btn-secondary" name="command" value="Register"><fmt:message key="register"/></button>
         </form>
         <fmt:message key="description"/>
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
