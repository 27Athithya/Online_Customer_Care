<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/userManage.css">
    
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
   
</head>
<body>
       <%@ include file="sidebar.jsp" %>
 
<h1 style="text-align: center;">Manage Users</h1>

<div class="container">
    <c:forEach var="user" items="${users}">
        <div class="user-card">

				<img src="${pageContext.request.contextPath}/user/image?id=${user.userId}" alt="${user.name}'s image">
            <div class="user-info">
                <p><strong>Name:</strong> ${user.name}</p>
                <p><strong>Email:</strong> ${user.email}</p>
                <p><strong>Phone:</strong> ${user.phoneNumber}</p>
                <p><strong>License:</strong> ${user.licenseNumber}</p>
                <p><strong>Gender:</strong> ${user.gender}</p>
                <p><strong>Address:</strong> ${user.address}</p>
            </div>
            <a href="${pageContext.request.contextPath}/admin/delete?id=${user.userId}" 
               class="delete-btn" onclick="return confirm('Are you sure you want to delete this user?')">
                Delete
            </a>
        </div>
    </c:forEach>
</div>

</body>
</html>
