<%@ include file="bzinclude.jsp" %>
<html>
<head>
	<title><fmt:message key="bookspage"/></title>
</head>
<body>
<%@ include file="bzheader.jsp"%>

<br />

<h1>
	<fmt:message key="books"/>  
</h1>

<br style="clear:both;" />

<table border="1">
<c:forEach items="${books}" var="book">
	<tr>
		<td><c:out value="${book.title}"/></td>
		<td><c:out value="${book.description}"/></td>
		<td><fmt:formatNumber value="${book.price}" type="currency"/></td>
		<td><a href="bzbookdetails?id=${book.isbn}">Book Details</a></td>
	</tr>
</c:forEach>
</table>

</body>
</html>
