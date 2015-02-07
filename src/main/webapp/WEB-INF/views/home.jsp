<%@ include file="include.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><fmt:message key="title"/></title>
</head>
<body>
<%@ include file="header.jsp" %>

<img src="<c:url value='/img/homer_yeah.jpg' />"/>
<h1>
	<fmt:message key="hello"/>  
</h1>

<table border="1">
<c:forEach items="${books}" var="book">
	<tr>
		<c:url value="/details?title=${book.title}" var="url"/>
		<td><a href="<c:out value='${url}'/>"><c:out value="${book.title}"/></a></td>
		<td><c:out value="${book.author}"/></td>
		<td><fmt:formatNumber value="${book.price}" type="currency"/></td>
	</tr>
</c:forEach>
</table>
<%@ include file="footer.jsp" %>
</body>
</html>
