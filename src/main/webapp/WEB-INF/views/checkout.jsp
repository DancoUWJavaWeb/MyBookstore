<%@ include file="include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bookstore Checkout</title>
</head>
<body>
<%@ include file="header.jsp" %>

<table border="1">
<c:forEach items="${cart.books}" var="book">
	<tr>
		<c:url value="/details?title=${book.title}" var="url"/>
		<td><a href="<c:out value='${url}'/>"><c:out value="${book.title}"/></a></td>
		<td><fmt:formatNumber value="${book.price}" type="currency"/></td>
		<td><c:url value="/cart?isbn=${ book.ISBN }" var="remove" /> <a href="<c:out value='${ remove }'/>">Remove</a></td>
	</tr>
</c:forEach>
</table>
<table border="1">
	<tr><td>Subtotal:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"  value="${cart.cartTotal}" /></td>
	<tr><td>tax:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"  value="${cart.salesTax}" /></td>
	<tr><td>shipping:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"  value="${cart.shipping}" /></td>
	<c:set var = "orderTotal" value="${cart.cartTotal + cart.salesTax + cart.shipping}" />
	<tr><td>Total:</td><td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"  value="${orderTotal}" /></td>
	
</table>

	<form action="/">
		<input type="submit" value="Continue Shopping" >
	</form>
	<br>
	
<form:form action="/checkout" method="post" modelAttribute="userinfo">
	<c:choose>
		<c:when test="${empty userinfo}">
			<p><font color="red"><c:out value="No shipping address on record, please add address prior to checkout."></c:out></font></p>
		</c:when>
		<c:otherwise>
		<c:choose>
			<c:when test="${empty userinfo.creditCard}">
			<p><font color="red"><c:out value="No credit card on record, please add one prior to checkout."></c:out></font></p>
			    <a href="<c:url value="/account"/>">Account</a><br>	
			</c:when>
			<c:otherwise>
				<table border="1">
				<thead>Select destination:</thead>
				<c:forEach items="${userinfo.mailingAddresses}" var="mailingAddress">
					<table>
					<tr>
					<td><input type="radio" id="selectedAddress" value="${mailingAddress.index}"></td>
					<td>
					<table>
					<tr>
						<td><c:out value="${mailingAddress.streetAddress}" /></td>
					</tr>
					<tr>
						<td><c:out value="${mailingAddress.city}" />, </td><td>
						<c:out value="${mailingAddress.state}" /> </td><td><c:out value="${mailingAddress.zip}" /></td>
					</tr>
					<br>
					</table>
					</td>	
					</tr>
					</table>
				</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
		</c:otherwise>
	</c:choose>
</form:form>

	
	<c:choose>
		<c:when test="${not empty cart}">
			<form:form action="/confirmPurchase" method="post">
				<div style="width:300px;text-align:right">
			    	<input type="submit" value="Submit Order"/>
				</div>
			</form:form>
		</c:when>
	</c:choose>	

<%@ include file="footer.jsp" %>
</body>
</html>