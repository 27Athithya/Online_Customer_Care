<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Notification" %>
<%@ page import="java.util.List" %>

<%
    List<Notification> notifications = (List<Notification>) request.getAttribute("notifications");
%>

<div class="sidebar-content">
    <h3>Notifications</h3>

    <c:choose>
        <c:when test="${not empty notifications}">
            <ul class="notification-list">
                <c:forEach var="n" items="${notifications}">
                    <li class="notification-item">
                        <h4>${n.subject}</h4>
                        <p>${n.message}</p>
                        <small>${n.createdAt}</small>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <p>No notifications found.</p>
        </c:otherwise>
    </c:choose>
</div>

<style>
    .sidebar-content {
        padding: 1rem;
        background: #fff;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        max-height: 400px;
        overflow-y: auto;
    }

    .notification-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .notification-item {
        margin-bottom: 1rem;
        padding-bottom: 0.5rem;
        border-bottom: 1px solid #ccc;
    }

    .notification-item h4 {
        margin: 0 0 0.25rem;
    }

    .notification-item small {
        color: #666;
    }
</style>

<script>
  function toggleNotifications() {
    const sidebar = document.getElementById("notificationSidebar");

    if (sidebar.classList.contains("hidden")) {
      fetch("./notifications?action=sidebar")
        .then(response => response.text())
        .then(data => {
          sidebar.innerHTML = data;
          sidebar.classList.remove("hidden");
        })
        .catch(error => console.error("Error loading notifications:", error));
    } else {
      sidebar.classList.add("hidden");
    }
  }
</script>

