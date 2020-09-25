<%--
  Created by IntelliJ IDEA.
  User: SEVEN-WORK
  Date: 16.09.2020
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
   <div class="container h-100">
     <div class="d-flex h-100">
       <div class="col-md-6 col-md-offset-2">
         <hr>
         <h2 class="page-header">Please login or register</h2>
         <form name="registration-form" method="GET" >
           <input hidden name="registration"/>
           <div class="form-group">
             <label for="login">Login</label>
             <input type="login"
                    name="login"
                    class="form-control"
                    id="login"
                    placeholder="Login"
                    required
             >
           </div>
           <div class="form-group">
             <label for="exampleInputPassword">Password</label>
             <input type="password"
                    name="password"
                    class="form-control"
                    id="exampleInputPassword"
                    placeholder="Password"
                    required
             >
           </div>
           <div class="form-group">
             <label for="exampleInputPassword">Role</label>
             <jsp:useBean id="roles" scope="request" type="java.util.List"/>
             <select class="form-control" name="role" required>
               <option value="" selected disabled hidden>Choose role</option>
               <c:forEach items="${roles}"  var="role">
                 <option value="${role.id}">${role.name}
                 </option>
               </c:forEach>
             </select>
            </div>
           <hr>
              <button type="submit" class="btn btn-secondary mr-2" name="command" value="Register" >Register</button>
         </form>

       </div>
     </div>
   </div>
   <p>
     <a href="${pageContext.request.contextPath}/index.jsp">Back to main</a>
   </p>

  <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>



  </body>
</html>
