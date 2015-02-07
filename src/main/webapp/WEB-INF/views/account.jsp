<%--suppress XmlDuplicatedId --%>
<%@ include file="include.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Account Information</title>
</head>
<body>
<%@ include file="header.jsp" %>

<c:choose>
<c:when test="${empty addresses}">
	<p><font color="red"><c:out value="No shipping address on record, please add address prior to checkout."></c:out></font></p>
</c:when>
<c:otherwise>
<table border="1">
<thead>Current addresses:</thead>
<c:forEach items="${addresses}" var="mailingAddress">
	<tr>
		<td><c:out value="${mailingAddress.streetAddress}" /></td>
	</tr>
	<tr>
		<td><c:out value="${mailingAddress.city}" />, </td><td>
		<c:out value="${mailingAddress.state}" /> </td><td><c:out value="${mailingAddress.zip}" /></td>
	</tr>
	<br>
</c:forEach>
</table>
</c:otherwise>
</c:choose>
<br>
	<form:form action="/addAddress" method="post" modelAttribute="address">
		<div style="width:500px;text-align:left">
		    <form:label path="streetAddress">Street address:</form:label><form:input path="streetAddress"/><br>
		    <form:label path="city">City:</form:label><form:input path="city"/>,
		    <form:label path="state">State:</form:label><form:input size="2" path="state"/>&nbsp;
		    <form:label path="zip">Zip:</form:label>&nbsp;<form:input size="5" path="zip"/><br>
		    <input type="submit" value="Add Address"/>
		</div>
	</form:form>
	<br>
	<form:form action="/updateAccount" method="post" modelAttribute="userinfo">
		<div style="width:500px;text-align:left">
		    <form:label path="name">Name:</form:label><form:input path="name"/><br>
		    <form:label path="emailAddress">Email address:</form:label><form:input path="emailAddress"/><br>
		    <form:label path="password">password:</form:label><form:password path="password"/><br>
		    <form:label path="phoneNumber">Phone number:</form:label><form:input path="phoneNumber"/><br>
		    <form:label path="creditCard">Credit Card:</form:label><form:input path="creditCard"/><br>
		    <input type="submit" value="Update Account"/>
		</div>
	</form:form>
	<form action="/">
		<input type="submit" value="Continue Shopping" >
	</form>

<%@ include file="footer.jsp" %>
</body>
</html>