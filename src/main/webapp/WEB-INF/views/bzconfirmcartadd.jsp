<%@ include file="include.jsp" %>
<html>
<head>
	<title><fmt:message key="confirmationpage"/></title>
</head>
<body>
<%@ include file="header.jsp"%>

<br />
<h1>
	<fmt:message key="confirm"/> <c:out value="${ itemBookTitle }"/> to your cart.
</h1>

<p>Click <a href="<c:url value="/bzbooks"/>" >here</a> to return to view our inventory.</p>

</body>
</html>