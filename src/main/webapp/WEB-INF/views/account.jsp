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
		<td><c:out value="${mailingAddress.city}" /></td>,
        <td><c:out value="${mailingAddress.state}" /> </td><td><c:out value="${mailingAddress.zip}" /></td>
	</tr>
	<br>
</c:forEach>
</table>
</c:otherwise>
</c:choose>
<br>
	<form:form action="/addAddress" method="post" modelAttribute="mailingAddress">
		<div style="width:500px;text-align:left">
		    <form:label path="streetAddress">Street address:</form:label><form:input path="streetAddress"/><br>
            <div class="errors"><form:errors path="streetAddress" /></div>
		    <form:label path="city">City:</form:label><form:input path="city"/>
            <div class="errors"><form:errors path="city" /></div>
            <form:label path="state">State:</form:label><form:input size="2" path="state"/>&nbsp;
            <div class="errors"><form:errors path="state" /></div>
		    <form:label path="zip">Zip:</form:label><form:input size="5" path="zip"/><br>
            <div class="errors"><form:errors path="zip" /></div>

		    <input type="submit" value="Add Address"/>
		</div>
	</form:form>
	<br>
	<form:form action="/updateAccount" method="post" modelAttribute="userInfo">
		<div style="width:500px;text-align:left">
            <div class="formfield"><form:label path="name">Name:</form:label><form:input path="name"/><br></div>
            <div class="errors"><form:errors path="name" /></div>

            <div class="formfield"><form:label path="password">Password:</form:label><form:password path="password"/><br></div>
            <div class="errors"><form:errors path="password" /></div>

            <div class="formfield"><form:label path="emailAddress">EmailAddress:</form:label><form:input path="emailAddress"/><br></div>
            <div class="errors"><form:errors path="emailAddress" /></div>

            <div class="formfield"><form:label path="phoneNumber">PhoneNumber:</form:label><form:input path="phoneNumber"/><br></div>
            <div class="errors"><form:errors path="phoneNumber" /></div>
            <br>
            <div class="formfield"><form:label path="creditCard">CreditCard:</form:label><form:input path="creditCard"/></div>
            <div class="errors"><form:errors path="creditCard" /></div>
            <div class="formfield"><form:label path="ccExpDate">Expiration date:</form:label><form:input path="ccExpDate"/><br></div>
            <div class="errors"><form:errors path="ccExpDate" /></div>

            <div><input type="submit" value="Update Account"/></div>
		</div>
	</form:form>
	<form action="/">
		<input type="submit" value="Continue Shopping" >
	</form>

<%@ include file="footer.jsp" %>
</body>
</html>