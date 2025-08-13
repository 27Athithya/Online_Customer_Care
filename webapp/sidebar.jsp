


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sidebar</title>
 <link rel="stylesheet" href="css/admin.css">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

</head>
<body>
<div class="sidebar">
    <div class="profile">
      <img src="img\admin.jpg" alt="Profile">
      <h3>Admin</h3>
    </div>
    <div class="menu">
     <a href="adminDashboard.jsp" id="load-home"><i class="fas fa-home"></i> Home</a>
		<a href="${pageContext.request.contextPath}/admin/manage-users"><i class="fas fa-users"></i> Manage Users</a>
    <a href="${pageContext.request.contextPath}/vehicles"><i class="fas fa-car-side"></i> Manage Vehicles</a>
    <a href="${pageContext.request.contextPath}/VehicleReservationController"><i class="fas fa-calendar-check"></i> Manage Reservations</a>
    <a href="${pageContext.request.contextPath}/notifications" id="load-notifications"><i class="fas fa-bell"></i> Notifications</a>
      <a href="${pageContext.request.contextPath}/auth/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>

  
   </div>
  
  </div>
</body>
</html>