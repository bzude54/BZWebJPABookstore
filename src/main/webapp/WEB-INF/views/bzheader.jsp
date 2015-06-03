<%@ include file="bzinclude.jsp" %>
<div style="text-align:left">
<c:choose>
<c:when test="${empty username}">
    <p><a href="<c:url value="/bzlogin"/>">Login</a></p>
</c:when>
<c:otherwise>
	<p style="float:right;"><a href="<c:url value="/bzlogout"/>">logout</a></p>
    <p><fmt:message key="welcome"/> ${ username }. You have ${ numCartItems } item<c:if test="${ numCartItems != 1 }"><c:out value="s" /></c:if> in your cart. Click <a href="<c:url value="/bzcart"/>">here</a> to view your cart.</p> 
    <p>Click <a href="<c:url value="/bzaccountinfo/${ userid }"/>">here</a> to view and edit your account info.</p>
	<br style="clear:both;" />
</c:otherwise>
</c:choose>
</div>
<hr/>
