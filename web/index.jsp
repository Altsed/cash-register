<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/superhero/bootstrap.min.css" integrity="sha384-HnTY+mLT0stQlOwD3wcAzSVAZbrBp141qwfR4WfTqVQKSgmcgzk+oP0ieIyrxiFO" crossorigin="anonymous">
    <title>Cash register</title>
    <jsp:include page="/WEB-INF/view/header.jsp"></jsp:include>
  </head>
  <body>
   <div class="container h-100">
     <div class="d-flex h-100">
       <div class="col-md-6 col-md-offset-2">
         <hr>
         <h4 style="color: yellow" class="page-header">${requestScope.message}</h4>
         <h2 class="page-header">Please login or register</h2>

         <form name="loginForm" method="POST" action="welcome-page">
           <div class="form-group">
             <label for="login">Login</label>
             <input type="login"
                    name="login"
                    class="form-control"
                    id="login"
                   placeholder="Login"
                    value=${requestScope.login}

             >
           </div>
           <div class="form-group">
             <label for="exampleInputPassword">Password</label>
             <input type="password"
                    name="password"
                    class="form-control"
                    id="exampleInputPassword"
                    placeholder="Password"
             >
           </div>

            <button type="submit" class="btn btn-secondary mr-2" name="command" value="Login">Login</button>
           <button formaction="register" type="submit" class="btn btn-secondary" name="command" value="Register">Register</button>
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
