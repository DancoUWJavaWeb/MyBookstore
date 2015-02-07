<%--suppress XmlDuplicatedId --%>
<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
    <style>
        .formfield {
        }
        .errors {
            color: red;
            font-size: small;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp"%>

<p>Welcome to <fmt:message key="title"/>, please enter your username and password</p>
<form:form method="post" modelAttribute="userinfo">
<div style="width:300px;text-align:right">
    <div class="formfield"><form:label path="name">Name:</form:label><form:input path="name"/><br></div>
    <div class="errors"><form:errors path="name" /></div>

    <div class="formfield"><form:label path="password">Password:</form:label><form:password path="password"/><br></div>
    <div class="errors"><form:errors path="password" /></div>

    <div class="formfield"><form:label path="emailAddress">EmailAddress:</form:label><form:input path="emailAddress"/><br></div>
    <div class="errors"><form:errors path="emailAddress" /></div>

    <div class="formfield"><form:label path="phoneNumber">PhoneNumber:</form:label><form:input path="phoneNumber"/><br></div>
    <div class="errors"><form:errors path="phoneNumber" /></div>
    <br>
    <div class="formfield"><form:label path="creditCard">CreditCard:</form:label><form:input path="creditCard"/><br></div>
    <div class="errors"><form:errors path="creditCard" /></div>
    <%--<form:label path="ccExpDate">Expiration date:</form:label><form:input path="ccExpDate"/><br>--%>

    <input type="submit"/>
</div>
</form:form>

<%@ include file="footer.jsp"%>
</body>
</html>