<%@ include file="include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${book.title} Details</title>
<style>
table, th, td {
    border: 1px solid black;
    text-align: left;
}
table th {
    color: white;
    background-color: black;
} 
</style>

</head>
<body>
<%@ include file="header.jsp" %>

<table >
    <tr>
        <td>
            <c:set var="book_image" value="${fn:toLowerCase(fn:replace(book.title, \" \", \"_\"))}" />
            <img src="<c:url value='/img/${book_image}.jpg' />"/>
            <!- Could store the image url in the Book object instead...  -->
        </td>
        <td>
    <table>
        <tr><td><c:out value="${book.ISBN}"/></td></tr>
        <tr><td><c:out value="${book.genre}"/></td></tr>
        <tr><td><c:out value="${book.title}"/></td></tr>
        <tr><td><c:out value="${book.author}"/></td></tr>
        <tr><td><c:out value="${book.description}"/></td></tr>
        <tr><td><fmt:formatNumber value="${book.price}" type="currency"/></td></tr>
    </table>
        </td>
    </tr>
</table>
	<form action="/">
		<input type="submit" value="Continue Shopping" >
	</form>

<c:choose>
	<c:when test="${not empty username}">
		<form:form action="/addToCart?isbn=${book.ISBN}" method="post" modelAttribute="book" >
			<div style="width:300px;text-align:right">
			    <form:hidden path="ISBN" id="isbn" />
			    <input type="submit" value="Add to Cart"/>
			</div>
		</form:form>
	</c:when>
</c:choose>
	
</body>
</html>