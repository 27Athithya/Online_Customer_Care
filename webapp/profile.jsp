<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style1.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><i class="fas fa-user-circle"></i> User Profile</h1>
            <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-danger">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        
        <div class="profile-container">
				<form method="post" action="${pageContext.request.contextPath}/user/update" enctype="multipart/form-data">
              
                <div class="profile-picture">
                    
                    
                   <img src="${not empty user.profileImage ? user.profileImage : 'https://via.placeholder.com/150'}" 
                         alt="Profile Picture" id="profileImagePreview">
                   
                   
                    <label for="profileImage" class="upload-btn">
                        <i class="fas fa-camera"></i> Change Photo
                    </label>
                    <input type="file" id="profileImage" name="profileImage" accept="image/*" style="display: none;">
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="name"><i class="fas fa-user"></i> Full Name</label>
                        <input type="text" id="name" name="name" value="${user.name}" required>
                    </div>
                    <div class="form-group">
                        <label><i class="fas fa-envelope"></i> Email</label>
                        <input type="email" value="${user.email}" readonly>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="phoneNumber"><i class="fas fa-phone"></i> Phone Number</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
                    </div>
                    <div class="form-group">
                        <label><i class="fas fa-venus-mars"></i> Gender</label>
                        <select name="gender" class="form-control">
                            <option value="Male" ${user.gender eq 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${user.gender eq 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${user.gender eq 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="address"><i class="fas fa-home"></i> Address</label>
                    <textarea id="address" name="address" rows="3">${user.address}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="licenseNumber"><i class="fas fa-id-card"></i> License Number</label>
                    <input type="text" id="licenseNumber" name="licenseNumber" value="${user.licenseNumber}">
                </div>
                
                
                 <div class="form-group">
                    <label for="DOB"><i class="fas fa-id-card"></i> DOB</label>
                    <input type="hidden" id="DOB" name="DOB" value="${user.getDob()}">
                    ${user.getDob()}
                </div>
                
             	  <div class="form-group">
   				 		<label><i class="fas fa-lock"></i> Password</label><br>
    					<a href="${pageContext.request.contextPath}/user/changePassword" class="btn btn-warning">
   						 <i class="fas fa-key"></i> Change Password
						</a>

				</div>
                
                
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Save Changes
                </button>
            </form>
        </div>
    </div>
    
    <script>
        // Preview profile image before upload
        document.getElementById('profileImage').addEventListener('change', function(e) {
            if (this.files && this.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('profileImagePreview').src = e.target.result;
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    </script>
</body>
</html>