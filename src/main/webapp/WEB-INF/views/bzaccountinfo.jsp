<%@ include file="bzinclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="accountinfotitle"/></title>
</head>
<body>
 <%@ include file="bzheader.jsp"%>
 
<p>Welcome ${ username }, here the info we have on file for you. Please update your information as necessary and submit.</p>

<c:choose>
<c:when test="${ !checkout }">
<p>If all your information is correct, please click <a href="<c:url value="/bzbooks"/>">here</a> to return to our book inventory.</p>
</c:when>
<c:otherwise>
<p>You must have a valid, complete shipping address and credit card number in your account information to checkout.</p>
<p>Once you have completed your information, click <a href="<c:url value="/bzcart"/>">here</a> to checkout.</p>
</c:otherwise>
</c:choose>

<form:form method="post" modelAttribute="bZUserInfo" action="bzaccountinfo">
<div style="width:300px;text-align:right">
	<form:hidden path="userId"/>
    <form:label path="userName">User Name:</form:label><form:input path="userName"/><br>
    <form:label path="firstName">First Name:</form:label><form:input path="firstName"/><br>
    <form:label path="lastName">Last Name:</form:label><form:input path="lastName"/><br>
    <form:label path="emailAddress">Email address:</form:label><form:input path="emailAddress"/><br>
    <form:label path="phoneNumber1">Home phone:</form:label><form:input path="phoneNumber1"/><br>
    <form:label path="phoneNumber2">Mobile phone:</form:label><form:input path="phoneNumber2"/><br>
    <form:label path="password">Password:</form:label><form:password path="password"/><br>
    <input type="submit"/>
</div>
</form:form>

</body>
</html>
