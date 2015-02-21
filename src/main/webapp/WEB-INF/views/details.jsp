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
.hidden
{
    display: none;
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
                        $('div.reviews').empty();
                        console.log(response.length);
                        $.each(response, function(idx) {
                            $('div.reviews').append(response[idx].text).append("<br>");
                            $('div.reviews').append("Review posted: " + new Date(response[idx].addedDate)).append("<br>");
                        });
                    },
                    error : function() {
                        alert('Something bad happened');
                    }
                });
            });

            $('div.addReview').click(function() {
                $('#reviewForm').show();
            });

            $('div.submitReview').click(function() {
                var rev = $('#reviewText').val();
                console.log("adding review text: " + rev);
                $.ajax({
                    type : 'POST',
                    url : 'reviews',
                    data : {
                        'isbn':'${ book.ISBN }',
                        'text': rev
                    },
                    cache : 'false',
                    success : function(response) {
                        $('div.reviews').append(response.text).append("<br>");
                        $('div.reviews').append("Review posted: " + new Date(response[idx].addedDate)).append("<br>");
                    },
                    error : function() {
                        alert('Something bad happened');
                    }
                });
                $('#reviewForm').hide();
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

<div class="getReviews">Show all reviews for this book</div>
<div class="addReview">Add review for this book</div>
<div class="reviews"></div>
<br>
<form:form id="reviewForm" class="hidden">
    <div><label for="reviewText">Add your review:</label><textarea id="reviewText" rows="3" maxlength="240"></textarea>
    </div>
    <div class="submitReview" style="text-decoration: underline">Submit</div>
</form:form>

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