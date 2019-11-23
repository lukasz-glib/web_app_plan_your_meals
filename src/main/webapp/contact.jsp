<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kmode
  Date: 11.11.2019
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
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
<header>
    <%@include file="header.jsp" %>
</header>
<section class="last-info-section padding-small" id="contact">
    <div class="container">
        <div class="row">
            <div class="col">
                <h3 class="mb-4">Lorem ipsum dolor</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at porttitor sem. Aliquam erat
                    volutpat. Donec placerat nisl magna.</p>
            </div>
            <div class="col pl-4 ml-4">
                <h3 class="mb-4">Lorem ipsum dolor</h3>
                <ul class="container">
                    <li>consectetur adipiscing elit</li>
                    <li>sed do eiusmod tempor</li>
                    <li>incididunt ut labore</li>
                    <li>et dolore magna aliqua</li>
                </ul>
            </div>
            <div class="col">
                <h3 class="mb-4">Lorem ipsum dolor</h3>
                <div class="input-group mb-3">
                    <input type="text" class="form-control border-0 rounded-0" placeholder=""
                           aria-label="Recipient's username" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                        <button class="input-group-text btn-color border-0 rounded-0" type="submit" id="basic-addon2"><a
                                href="index.html">Lorem</a></button>
                    </div>
                </div>
                <div class="container d-flex-row">
                    <a href="#">
                        <i class="fab fa-facebook-square mr-4 icon-social"></i>
                    </a>
                    <a href="#">
                        <i class="fab fa-twitter-square mr-4 icon-social"></i>

                    </a>
                    <a href="#">
                        <i class="fab fa-instagram icon-social"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</section>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
