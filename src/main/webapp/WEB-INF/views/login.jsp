<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title"/></title>
</head>
<body>
<%@ include file="header.jsp"%>

<p>Welcome to <fmt:message key="title"/>, please enter your username and password</p>
<form:form method="post" modelAttribute="userinfo">
<div style="width:300px;text-align:right">
    <form:label path="name">Name:</form:label><form:input path="name"/><br>
    <form:label path="password">Password:</form:label><form:password path="password"/><br>
    <form:label path="emailAddress">EmailAddress:</form:label><form:input path="emailAddress"/><br>
    <form:label path="phoneNumber">PhoneNumber:</form:label><form:input path="phoneNumber"/><br>
    <br>
    <form:label path="creditCard">CreditCard:</form:label><form:input path="creditCard"/><br>
    
    <input type="submit"/>
</div>
</form:form>

<%@ include file="footer.jsp"%>
</body>
</html>