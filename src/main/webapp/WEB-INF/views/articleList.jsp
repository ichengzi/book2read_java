<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018-04-08
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Spring Boot</title>
</head>
<body>
<h1>${name}</h1>
<hr>
<ui>
    <c:forEach items="${articles}" var="item">
        <li><a href="${item.href}">${item.content}</a></li>
    </c:forEach>
</ui>


</body>
</html>
