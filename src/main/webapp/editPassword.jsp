<%--
  Created by IntelliJ IDEA.
  User: artur
  Date: 16.11.2019
  Time: 17:30
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
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>

<body>
<header class="page-header">
   <%@include file="dashboardHeader.jsp"%>>
</header>

<section class="dashboard-section">
    <%@include file="sideMenu.jsp"%>
        <div class="m-4 p-3 width-medium text-color-darker">
            <div class="m-4 border-dashed view-height">
                <div class="mt-4 ml-4 mr-4">
                    <!-- fix action, method -->
                    <!-- add name attribute for all inputs -->
                    <form action="/app/edit/password" method="post">
                        <div class="row border-bottom border-3">
                            <div class="col"><h3 class="color-header text-uppercase">Zmień hasło</h3></div>
                            <div class="col d-flex justify-content-end mb-2">
                                <button type="submit" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz
                                </button>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${wrong != null}">
                                <p>Hasła nie zgadzają się</p>
                            </c:when>
                        </c:choose>
                        <table class="table borderless">
                            <tbody>
                            <tr class="d-flex">
                                <th scope="row" class="col-2"><h4>Nowe hasło</h4></th>
                                <td class="col-7">
                                    <input class="w-100 p-1" type="text" name="newPassword">
                                </td>
                            </tr>
                            <tr class="d-flex">
                                <th scope="row" class="col-2"><h4>Powtórz hasło</h4></th>
                                <td class="col-7">
                                    <input class="w-100 p-1" type="text" name="newPasswordRepeat">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>

        </div>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>

</body>
</html>
