<%@ include file="bzinclude.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="newreg"/></title>
    
    <style>
        .formfield {
        }
        .errors {
            color: red;
            font-size: small;
        }
    </style>
    
    
</head>
<body>
<%@ include file="bzheader.jsp"%>

<p>Welcome to <fmt:message key="newregtitle"/>, please enter your information below to create a new account.</p>
<form:form method="post" modelAttribute="bZUserInfo">
<div style="width:300px;text-align:right">
	<form:hidden path="userId"/>
    <form:label path="userName">User Name:</form:label><form:input path="userName"/><br>
    <div class="errors"><form:errors path="userName" /></div>
    <form:label path="firstName">First Name:</form:label><form:input path="firstName"/><br>
    <div class="errors"><form:errors path="firstName" /></div>
    <form:label path="lastName">Last Name:</form:label><form:input path="lastName"/><br>
    <div class="errors"><form:errors path="lastName" /></div>
    <form:label path="emailAddress">Email address:</form:label><form:input path="emailAddress"/><br>
    <div class="errors"><form:errors path="emailAddress" /></div>
    <form:label path="phoneNumber1">Home phone:</form:label><form:input path="phoneNumber1"/><br>
    <div class="errors"><form:errors path="phoneNumber1" /></div>
    <form:label path="password">Password:</form:label><form:password path="password"/><br>
    <div class="errors"><form:errors path="password" /></div>
    <input type="submit"/>
</div>
</form:form>

</body>
</html>