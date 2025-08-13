<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<%@ page import="model.User" %>

<c:choose>
    <c:when test="${empty sessionScope.user || sessionScope.user.email ne 'admin@gmail.com'}">
        <c:redirect url="/auth/login"/>
    </c:when>
</c:choose>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>

    <body>

   <%@ include file="sidebar.jsp" %>
  
  
<div>
  <div class="main">
    <div class="top-bar">
      <h1>Dashboard</h1>
      <button class="toggle-btn" onclick="toggleMode()"> Dark/Light Mode</button>
    </div>

    <div id="content-area" class="summary">
    
    
      <div class="card">
        <img src="https://cdn-icons-png.flaticon.com/512/1946/1946429.png" alt="Users">
        <h3>Users</h3>
        <p>2500 registered</p>
      </div>
      <div class="card">
        <img src="https://cdn-icons-png.flaticon.com/512/1883/1883601.png" alt="Vehicles">
        <h3>Vehicles</h3>
        <p>158 Vehicles</p>
        
      </div>
      <div class="card">
        <img src="https://cdn-icons-png.flaticon.com/512/747/747310.png" alt="Reservations">
        <h3>Reservations</h3>
        <p>120 Bookings</p>
      </div>
    </div>
  </div>
</div>

    <script src="js/admin.js"></script>
</body>
</html>
