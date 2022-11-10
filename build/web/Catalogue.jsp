<%-- 
    Document   : ShoppingPage
    Created on : 21/03/2022, 6:41:39 pm
    Author     : Tamati Rudd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<jsp:useBean id="details" class="Entities.Vxz7784users" scope="session"/>
<jsp:useBean id="prodlist" class="java.util.ArrayList" scope="application"/>
<html>
    <style> <%--CSS style to give the Catalogue table a border--%>
    table, th, td { 
        border:1px solid black;
    }
    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Shopping Page</title>
    </head>
    <body>
        <h1>Online Shopping App</h1>
        <h3>Account Details</h3>
        <%--Print the user's name and balance--%>
        <c:out value="User: ${details.username}"/>
        </br >
        <c:out value="Balance: ${details.toString()}"/>
        </br >
        <a href='<%= response.encodeURL("AddBalance.jsp") %>'>
            Credit Funds to Account
        </a>
            
        <h3>Catalogue</h3>
        <%--Print the product list (Catalogue)--%>
        <p>Tick the items you wish to add to your shopping cart</p>
        <p>Items with no stock remaining cannot be taken to checkout</p>
        </br > 
        <c:out value="${catfeedback.message}"/>
        </br > 
        <form method ="post", action ="SetupCheckout">
            <table>
                <tr>
                    <td>Product ID</td>
                    <td>Product Name</td>
                    <td>Price (NZD) </td> 
                    <td>In Stock</td>
                    <td>Add to Cart</td>
                </tr>      
                <c:forEach items="${prodlist}" var="Vxz7784products">
                    <tr>
                        <td>${Vxz7784products.prodID}</td>
                        <td><c:out value="${Vxz7784products.prodName}" /></td>
                        <td><fmt:formatNumber value="${Vxz7784products.price}" type="currency" currencyCode="NZD" /></td> 
                        <td><c:out value="${Vxz7784products.inStock}" /></td>
                        <td><input type="checkbox" name="inCart" value="${Vxz7784products.prodID}"></p></td>
                    </tr>      
                </c:forEach>  
            </table> 
        </br > 
        <button type="submit">Checkout</button>
        </form>
    </body>
</html>
