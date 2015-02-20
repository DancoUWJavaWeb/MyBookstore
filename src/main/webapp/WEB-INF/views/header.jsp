<%@ include file="include.jsp" %>
<style type="text/css">
    .formfield {
    }

    .errors {
        color: red;
        font-size: small;
    }
</style>

<style type="text/css">
    body {
        margin-left: 20px;
        width: 70%;
    }

    .link {
        margin-top: 2em;
    }

    .details {
        margin-top: 2em;
    }

    .image {
        float: left;
        margin-right: 1em;
    }

    .title {
        font-style: bold;
        font-size: 1.5em;
        color: red;
    }

    .info {
        height: 2em;
        max-width: 40em;
        word-wrap: break-word;
    }
</style>


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