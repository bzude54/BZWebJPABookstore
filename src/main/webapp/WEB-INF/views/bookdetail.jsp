<%@ include file="include.jsp" %>
<html>
<head>
	<title><fmt:message key="detailspage"/></title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<style type="text/css">

.readreviews {
	font-size:14px;
	color: green;
	margin:5px 0;
}

.reviewtext {
	border:1px dashed green;
	margin:5px 0;
	padding:5px;
	font-size:14px;
	color:blue;
}

</style>

<script type="text/javascript">
    $(document).ready(function($) {
        $('div.readreviews').click(function() {
            $.ajax({
                type : 'GET',
                url : 'reviews',
                data : {
                    'isbn':'${ book.isbn }'
                },
                dataType : "json",
                cache : 'false',
                success : function(response) { 
                	$('div.reviews').html('');
                	$.each(response, function(idx, obj) {
                		var reviewtext = $("<div style='width:500px; float:left; border: 1px solid black; margin: 5px 0;'></div>").text(obj.reviewText);
                		var date = new Date(obj.timeStamp);
                		var reviewtimestamp = $("<div style='width:300px; color:red; float:left; text-align:right; border: 1px solid black; margin: 5px 0;'></div><br style='clear:both;'/>").text(date.toLocaleString());
                		$('div.reviews').append(reviewtext, reviewtimestamp);
                	});                   
                },
                error : function() {
                    alert('No reviews found for this book.');
                }
            });  
        });
    });
    
    
    $(document).ready(function($) {
        $('div.postreview').click(function() {
            $.ajax({
                type : 'POST',
                url : 'reviews',
                data : {
					'id':'${ book.id }',
                    'isbn':'${ book.isbn }',
                    'reviewText': $("#customerreview").val()
                },
                dataType : "json",
                cache : 'false',
                success : function(response) {
					alert('SUCCESS!!!');
					$('div.reviews').html('');
                	$.each(response, function(idx, obj) {
                		var reviewtext = $("<div style='width:500px; float:left; border: 1px solid black; margin: 5px 0;'></div>").text(obj.reviewText);
                		var date = new Date(obj.timeStamp);
						var reviewtimestamp = $("<div style='width:300px; color:red; float:left; text-align:right; border: 1px solid black; margin: 5px 0;'></div><br style='clear:both;'/>").text(date.toLocaleString());
                		$('div.reviews').append(reviewtext, reviewtimestamp);
                	});                   
                },
                error : function() {
                    alert('Your review was not accepted.');
                }
            });  
        });
    });

</script>

</head>
<body>
<%@ include file="header.jsp"%>

<br />
<h1>
	<fmt:message key="details"/> <c:out value="${book.title}"/>
</h1>

<br style="clear:both;" />

<div style="width:200px; margin: 0 20px 20px 20px; float:right;"><img width="200" src="<c:url value='${book.image}' />"/></div>


<table border="1">
	<tr><td width="15%">Book Title:</td><td><c:out value="${ book.title }"/></td></tr>
	<tr><td>Book Author:</td><td><c:forEach items="${book.authors}" var="author"><c:out value="${author.firstName} "/><c:out value="${author.lastName}"/>, </c:forEach></td></tr>
	<tr><td>Book Description:</td><td><c:out value="${ book.description }"/></td></tr>
	<tr><td>Book Genre:</td><td><c:out value="${ book.genre.name }"/></td></tr>
	<tr><td>Book ISBN:</td><td><c:out value="${ book.isbn }"/></td></tr>
	<tr><td>Book Price:</td><td><fmt:formatNumber value="${ book.price }" type="currency"/></td></tr>
</table>

<c:if test="${not empty username}">	
	<p><a href="<c:url value="/addtocart/${ book.isbn }"/>">Add this book to your cart.</a></p>
</c:if>
	<p><a href="<c:url value="/books"/>">return to book list</a></p>

<br style="clear:both;" /><br />


<div class="readreviews">Click here to read reviews.</div>


	<br style="clear:both;" /><br />

<div class="reviews"></div>

<br style="clear:both;" /><br />

	<form name="postreview" method="POST" onsubmit="return postreview()" action="">
		<table>
		<tr><td>Enter your book review.</td><td><textarea name="customerreview" id="customerreview" rows="5" cols="60"></textarea></td></tr>
		<tr><td></td><td></td></tr>
		<tr><td colspan="2"><div class="postreview">Click here to post your review.</div></td></tr>
		</table>
	</form>


</body>
</html>
