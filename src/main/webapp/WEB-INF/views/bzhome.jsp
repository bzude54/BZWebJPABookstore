<%@ include file="bzinclude.jsp" %>
<html>
<head>
	<title><fmt:message key="title"/></title>
</head>
<body>
<P>  The time on the server is ${serverTime}. </P>

<div style="width:200px; height:240px; margin: 10px; border: 2px solid green; float:right;"><img src="<c:url value='/images/billz1.jpg'/>" /></div>

<h1>
	<fmt:message key="hello"/>  
</h1>

<br style="clear:both;" />

<p>If you already have an account with us, please click <a href="<c:url value="/bzlogin"/>" >here</a> to login.</p>

<p>If you do not have an account, please click <a href="<c:url value="/bzregister"/>" >here</a> to create one.</p>

<p>To browse our complete book inventory, click <a href="<c:url value="/bzbooks"/>" >here.</a></p>


</body>
</html>
