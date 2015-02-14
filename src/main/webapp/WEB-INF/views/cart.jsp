<%@ include file="include.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
</head>
<body>
<%@ include file="header.jsp" %>

<table border="1">
<c:forEach items="${books}" var="book">
	<tr>
		<c:url value="/details?title=${book.title}" var="url"/>
		<td><a href="<c:out value='${url}'/>"><c:out value="${book.title}"/></a></td>
		<td><fmt:formatNumber value="${book.price}" type="currency"/></td>
		<td><c:url value="/cart?isbn=${book.ISBN}" var="remove" /> <a href="<c:out value='${ remove }'/>">Remove</a></td>
	</tr>
</c:forEach>
</table>
<table border="1">
	<tr><td>Subtotal:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${subtotal}" /></td>
	<tr><td>tax:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${tax}" /></td>
	<tr><td>shipping:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${shipping}" /></td>
	<tr><td>Total:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${orderTotal}" /></td>
	
</table>

	<form action="/">
		<input type="submit" value="Continue Shopping" >
	</form>
	<br>
	
<c:choose>
	<c:when test="${not empty books}">
		<form:form action="/checkout" method="post" modelAttribute="userInfo">
			<div style="width:300px;text-align:right">
			    <input type="submit" value="checkout"/>
			</div>
		</form:form>
	</c:when>
</c:choose>	

<%@ include file="footer.jsp" %>

</body>
</html>