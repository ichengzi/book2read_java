<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>List</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>

<div class="container">
    <h1>${name}</h1>
    <ul>
        <c:forEach items="${articles}" var="item">
            <li><a href="${item.href}">${item.content}</a></li>
        </c:forEach>
    </ul>
</div>
<script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/app.js"></script>
</body>
</html>
