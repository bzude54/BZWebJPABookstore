<%@ include file="include.jsp" %>
<html>
<head>
	<title><fmt:message key="checkouttitle"/></title>
</head>
<body>
<%@ include file="header.jsp"%>

<br />

<h1>
	<fmt:message key="checkout"/>  
</h1>
<p>This is your current pending order and the information we have on file for your shipping address and credit card.
Please update your shipping address and credit card as necessary.</p>
<form:form method="post" modelAttribute="checkoutInfo">
<table style="width:60%; border:2px solid black">
	<tr><td>Book Title</td><td align="center">Item Quantity</td><td align="center">Total Item Cost</td></tr>
<c:forEach var="cartItem" items="${ checkoutInfo.cart.cartItems }">
<tr><td><c:out value="${ cartItem.cartItemBook.title }"/></td><td align="center"><c:out value="${ cartItem.cartItemQty }"/></td><td align="center"><fmt:formatNumber value="${ cartItem.cartItemTotalPrice }" type="currency"/></td></tr>
<br />
</c:forEach>
</table>
<br />
<table style="width:30%; border:2px solid black">
	<tr><td>Cart subtotal: </td><td><fmt:formatNumber value="${ checkoutInfo.cart.cartSubtotal }" type="currency"/></td></tr>
	<tr><td>Cart tax: </td><td><fmt:formatNumber value="${ checkoutInfo.cart.cartTax }" type="currency"/></td></tr>
	<tr><td>Cart shipping: </td><td><fmt:formatNumber value="${ checkoutInfo.cart.cartShippingCost }" type="currency"/></td></tr>
	<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
	<tr><td>Cart total: </td><td><fmt:formatNumber value="${ checkoutInfo.cart.cartTotal }" type="currency"/></td></tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr><td>&nbsp;</td><td><input type="submit" value="Confirm Checkout"/></td></tr>
</table>
<br />


</form:form>
<p>If you're ready to complete your order, click <a href="<c:url value="/bzthankyou"/>">here.</a></p>
<p>If you would like to continue shopping, 	click <a href="<c:url value="/bzbooks"/>">here</a> to continue to search our inventory.</p>
</body>
</html>