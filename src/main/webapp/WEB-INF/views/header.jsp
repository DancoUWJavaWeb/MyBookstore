<%@ include file="include.jsp" %>
<div style="text-align:right">
<c:choose>
	<c:when test="${empty username}">
	    <p><a href="<c:url value="/login"/>">Login</a></p>
	</c:when>
	<c:otherwise>
	    <p><fmt:message key="welcome"/> ${ username } (<a href="<c:url value="/logout"/>">logout</a>)<br>
	    <a href="<c:url value="/cart"/>">Shopping Cart</a><br>
	    <a href="<c:url value="/account"/>">Account</a><br>
	    <c:choose>
	    	<c:when test="${not empty cart}"><a href="<c:url value="/checkout"/>">Checkout</a></c:when>	
	    	<c:otherwise>Cart is empty</c:otherwise>
	    </c:choose>
	    </p>
	</c:otherwise>
</c:choose>
</div>
<hr/>