<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="label.welcome"/></title>
    <link href="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" rel="stylesheet"/>
</head>
<body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div class="login-panel">
        <div>
            <label>
                <fmt:message key="label.login"/>
                <input name="login" type="text">
            </label>
        </div>
        <div>
            <label>
                <fmt:message key="label.password"/>
                <input name="password" type="password">
            </label>
        </div>
        <input type="submit" name="submit" value="<fmt:message key="button.login" />">
    </div>
</form>
</body>
</html>

<script>
    window.onload = function () {
        const queryString = window.location.search;
        const param = new URLSearchParams(queryString);
        const login = param.get('login');
        if (login === 'bad') {
            toastr.error('<fmt:message key="label.place"/>', '<fmt:message key="label.error"/>');
        }
    }
</script>
