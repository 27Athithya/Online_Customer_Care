,xs```````````<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Manage Notifications</title>
    <link rel="stylesheet" type="text/css" href="css/notifications.css">
    <script src="js/notifications.js"></script>
  
</head>
<body>
      <%@ include file="sidebar.jsp" %>
    <div class="container">
        <h1>Notifications</h1>

        <form method="post" action="notifications" onsubmit="return validateForm()">
            <input type="hidden" name="notificationId" value="${notification.notificationId}" />
            
            <label>Subject:</label>
            <input type="text" name="subject" id="subject" value="${notification.subject}" required />

            <label>Message:</label>
            <textarea name="message" id="message" required>${notification.message}</textarea>

            <button type="submit">${notification.notificationId != null ? "Update" : "Add"} Notification</button>
        </form>

        <hr>
       

        <h2>All Notifications</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Subject</th>
                    <th>Message</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            
                <c:forEach var="n" items="${notifications}">
                    <tr>
                        <td>${n.notificationId}</td>
                        <td>${n.subject}</td>
                        <td>${n.message}</td>
                        <td>${n.createdAt}</td>
                        
     
                         <td>
  					  <form action="notifications" method="get" style="display: inline;">
    				    	<input type="hidden" name="action" value="edit">
      					  	<input type="hidden" name="id" value="${n.notificationId}">
       						<button type="submit" class="edit-btn">Edit</button>
    				</form>

    				<form action="notifications" method="get" style="display: inline;" onsubmit="return confirmDelete();">
        					<input type="hidden" name="action" value="delete">
       						 <input type="hidden" name="id" value="${n.notificationId}">
       						 <button type="submit" class="delete-btn">Delete</button>
   					 </form>
					</td>
                         
                         
                         
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
