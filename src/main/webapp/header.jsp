<%--
  Created by IntelliJ IDEA.
  User: kmode
  Date: 11.11.2019
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link href='<c:url value="/css/style.css"/>' rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>
<body>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-around">
        <a href="/" class="navbar-brand main-logo">
            Zaplanuj <span>Jedzonko</span>
        </a>
        <ul class="nav nounderline text-uppercase">
            <li class="nav-item ml-4">
                <a class="nav-link color-header" href="/app/dashboard">Zaplanuj posiłki</a>
            </li>
                <li class="nav-item ml-4">
                    <a class="nav-link color-header" href="/login">logowanie</a>
                </li>
                <c:choose>
                    <c:when test="${sessionScope.id == null}">
                        <li class="nav-item ml-4">
                            <a class="nav-link color-header" href="/register">rejestracja</a>
                        </li>
                    </c:when>
                </c:choose>
                <li class="nav-item ml-4">
                    <a class="nav-link" href="/about.jsp">o aplikacji</a>
                </li>
                <li class="nav-item ml-4">
                    <a class="nav-link disabled" href="/recipes">Przepisy</a>
                </li>
                <li class="nav-item ml-4">
                    <a class="nav-link disabled" href="/contact.jsp">Kontakt</a>
                </li>
            </ul>
    </nav>
</header>

</body>
</html>
