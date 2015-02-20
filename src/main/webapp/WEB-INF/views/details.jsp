<%@ include file="include.jsp" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${book.title} Details</title>
<style type="text/css">
table, th, td {
    border: 1px solid black;
    text-align: left;
}
table th {
    color: white;
    background-color: black;
} 
</style>

    <script type="text/javascript">
        $(document).ready(function($) {
            $('div.getReviews').click(function() {
                $.ajax({
                    type : 'GET',
                    url : 'reviews',
                    data : {
                        'isbn':'${ book.ISBN }'
                    },
                    cache : 'false',
                    success : function(response) {
                        $.each(response, function(idx) {
                            $('div.reviews').append(response[idx].text).append("<br>")
                        });
                    },
                    error : function() {
                        alert('Something bad happened');
                    }
                });
            });

            $('div.addReview').click(function() {
                $.ajax({
                    type : 'POST',
                    url : 'reviews',
                    data : {
                        'isbn':'${ book.ISBN }',
                        'text': 'this is a good book',
                        'addedDate': '2015-02-28'
                    },
                    cache : 'false',
                    success : function(response) {
                        $('div.reviews').append(response.text).append("<br>");
                    },
                    error : function() {
                        alert('Something bad happened');
                    }
                });
            });
        });
    </script>

</head>
<body>

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

<div class="getReviews">Show reviews for this book</div>
<div class="addReview">Add review for this book</div>
<div class="reviews"></div>

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