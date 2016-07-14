<<<<<<< HEAD
<%--
  Created by IntelliJ IDEA.
  User: Ben_Big
  Date: 7/1/16
  Time: 9:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="<c:url value="/resources/Test.js" />"> </script>
</head>
<body>
    <h1>Hello World</h1>
</body>
</html>
=======
<%--
  Created by IntelliJ IDEA.
  User: Ben_Big
  Date: 7/1/16
  Time: 9:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="app">
<head>
    <title>Title</title>
    <%--<script src="<c:url value="/resources/Test.js" />"> </script>--%>

    <!-- Angular JS-->
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.min.js"/>"></script>

    <!-- ng Route-->
    <script src="<c:url value="//ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-route.js" />"> </script>

    <script src="<c:url value="/resources/testAngular.js"/>"></script>
</head>
<body>
    <h1>Hello World</h1>

    <div class="container body-content app-container">

        <!-- Page goes here-->
        <div ng-view></div>
    </div>



</body>
</html>
>>>>>>> origin/tv_sprintbranch
