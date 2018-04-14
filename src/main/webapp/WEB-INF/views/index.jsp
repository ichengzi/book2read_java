<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>

<div class="container">

    <%--<div class="form">--%>
    <%--<form action="hello" method="post" onsubmit="return validate()">--%>
    <%--<table>--%>
    <%--<tr>--%>
    <%--<td>Enter Your name</td>--%>
    <%--<td><input id="name" name="name"></td>--%>
    <%--<td><input type="submit" value="Submit"></td>--%>
    <%--</tr>--%>
    <%--</table>--%>
    <%--</form>--%>
    <%--</div>--%>

    <ul>
        <li><a href="/articleList?name=shengxu">圣墟</a></li>
        <li><a href="/articleList?name=fanren">凡人仙界篇</a></li>
    </ul>
</div>
<script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/app.js"></script>
</body>
</html>
