<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style1.css">
	<script src="${pageContext.request.contextPath}/js/register-validation.js"></script>
	
</head>
<body>
    <div class="container">
        <div class="auth-box">
            <h2>Register</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/auth/register" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name"><i class="fas fa-user"></i> Full Name</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div class="form-group">
                    <label for="email"><i class="fas fa-envelope"></i> Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password"><i class="fas fa-lock"></i> Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="phoneNumber"><i class="fas fa-phone"></i> Phone Number</label>
                    <input type="text" id="phoneNumber" name="phoneNumber">
                </div>
                <div class="form-group">
                    <label><i class="fas fa-venus-mars"></i> Gender</label>
                    <select name="gender" class="form-control">
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                
                <div class="form-group">
    			<label for="dob"><i class="fas fa-calendar-alt"></i> Date of Birth</label>
   				 <input type="date" id="dob" name="dob" required>
				</div>
				
				<div class="form-group">
    			<label for="licenseNumber"><i class="fas fa-id-card"></i> License Number</label>
   				 <input type="text" id="licenseNumber" name="licenseNumber" required>
				</div>
                
                <div class="form-group">
                    <label for="address"><i class="fas fa-home"></i> Address</label>
                    <textarea id="address" name="address" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <label for="profileImage"><i class="fas fa-image"></i> Profile Image</label>
                    <input type="file" id="profileImage" name="profileImage" accept="image/*">
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
                <div class="auth-footer">
                    Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>