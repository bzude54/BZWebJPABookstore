<%@ include file="bzinclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="newreg"/></title>
</head>
<body>
<%@ include file="bzheader.jsp"%>

<p>Welcome to <fmt:message key="newregtitle"/>, please enter your information below to create a new account.</p>
<form:form method="post" modelAttribute="newUserInfo">
<div style="width:300px;text-align:right">
    <form:label path="userName">User Name:</form:label><form:input path="userName"/><br>
    <form:label path="firstName">First Name:</form:label><form:input path="firstName"/><br>
    <form:label path="lastName">Last Name:</form:label><form:input path="lastName"/><br>
    <form:label path="emailAddress">Email address:</form:label><form:input path="emailAddress"/><br>
    <form:label path="mailingStreetAddress">Mailing Address street:</form:label><form:input path="mailingStreetAddress"/><br>
    <form:label path="mailingCity">Mailing address city:</form:label><form:input path="mailingCity"/><br>
    <form:label path="mailingState">Mailing address state:</form:label><form:input path="mailingState"/><br>
    <form:label path="mailingZip">Mailing address zipcode:</form:label><form:input path="mailingZip"/><br>
    <form:label path="shippingStreetAddress">Shipping Address street:</form:label><form:input path="shippingStreetAddress"/><br>
    <form:label path="shippingCity">Shipping address city:</form:label><form:input path="shippingCity"/><br>
    <form:label path="shippingState">Shipping address state:</form:label><form:input path="shippingState"/><br>
    <form:label path="shippingZip">Shipping address zipcode:</form:label><form:input path="shippingZip"/><br>
    <form:label path="phoneNumber1">Home phone:</form:label><form:input path="phoneNumber1"/><br>
    <form:label path="phoneNumber2">Mobile phone:</form:label><form:input path="phoneNumber2"/><br>
    <form:label path="creditCard1">Credit card number 1:</form:label><form:input path="creditCard1"/><br>
    <form:label path="creditCard2">Credit card number 2:</form:label><form:input path="creditCard2"/><br>
    <form:label path="password">Password:</form:label><form:password path="password"/><br>
    <input type="submit"/>
</div>
</form:form>

</body>
</html>