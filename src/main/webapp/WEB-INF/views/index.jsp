<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <meta name="theme-color" content="#101010" />
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">book2read</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/">Home</a></li>
                <li><a href="/articleList2?name=圣墟">圣墟</a></li>
                <li><a href="/articleList2?name=凡人仙界篇">凡人</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container body-content">

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
    <h2>列表</h2>
    <ul>
        <li><a href="/articleList?name=圣墟" class="bigFont">圣墟</a></li>
        <li><a href="/articleList?name=凡人仙界篇" class="bigFont">凡人仙界篇</a></li>
        <li></li>
        <li><a href="/CrawlBook?name=圣墟" class="bigFont">爬取圣墟</a></li>
        <li><a href="/CrawlBook?name=凡人仙界篇" class="bigFont">爬取凡人仙界篇</a></li>
        <li></li>
        <li><a href="/articleList2?name=圣墟" class="bigFont">圣墟2</a></li>
        <li><a href="/articleList2?name=凡人仙界篇" class="bigFont">凡人仙界篇2</a></li>
    </ul>

    <footer>
        <p style="font-size: 12px">©2018-book2read</p>
    </footer>
</div>
<script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/app.js"></script>
</body>
</html>
