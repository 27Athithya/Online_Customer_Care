<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehicle Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .sidebar {
            min-height: 100vh;
        }
        .card-img-top {
            height: 150px;
            object-fit: cover;
        }
        .search-box {
            max-width: 400px;
        }
        .action-buttons .btn {
            margin-right: 5px;
        }
    </style>
</head>
<body class="bg-light">

		       <%@ include file="sidebar.jsp" %>
		
    <div class="container-fluid">
        <div class="row">
           
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">
                        <i class="fas fa-car me-2"></i>Vehicle Management
                    </h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <a href="vehicles?action=new" class="btn btn-success">
                            <i class="fas fa-plus me-1"></i> Add Vehicle
                        </a>
                    </div>
                </div>

                <c:if test="${not empty message}">
                    <div class="alert alert-info">${message}</div>
                </c:if>

                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-search me-1"></i> Search Vehicles
                    </div>
                    <div class="card-body">
                        <form action="vehicles" method="get" class="search-box">
                            <input type="hidden" name="action" value="search">
                            <div class="input-group">
                                <input type="number" name="searchId" class="form-control" placeholder="Enter Vehicle ID">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-search"></i> Search
                                </button>
                                <a href="vehicles" class="btn btn-secondary">
                                    <i class="fas fa-sync-alt"></i> Reset
                                </a>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i> Vehicle List
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Image</th>
                                        <th>Name</th>
                                        <th>Year</th>
                                        <th>Capacity</th>
                                        <th>Fuel Type</th>
                                        <th>Liter/Km</th>
                                        <th>Transmission</th>
                                        <th>Rent/Day</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="vehicle" items="${vehicles}">
                                        <tr>
                                            <td>${vehicle.vehicleId}</td>
                                            <td>
                                                <c:if test="${not empty vehicle.vehicleImage}">
                                                    <img src="${pageContext.request.contextPath}/${vehicle.vehicleImage}" 
                                                         class="img-thumbnail" width="80" alt="${vehicle.vehicleName}">
                                                </c:if>
                                            </td>
                                            <td>${vehicle.vehicleName}</td>
                                            <td>${vehicle.manufactureYear}</td>
                                            <td>${vehicle.personCapacity}</td>
                                            <td>
                                                <span class="badge ${vehicle.fuelType == 'Hybrid' ? 'bg-success' : 'bg-primary'}">
                                                    ${vehicle.fuelType}
                                                </span>
                                            </td>
                                            <td>${vehicle.literPerKm}</td>
                                            <td>${vehicle.transmissionType}</td>
                                            <td>$${vehicle.rentPerDay}</td>
                                            <td class="action-buttons">
                                                <a href="vehicles?action=edit&id=${vehicle.vehicleId}" 
                                                   class="btn btn-sm btn-warning" title="Edit"
                                                   onclick="return validateAction('edit', ${vehicle.vehicleId})">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="vehicles?action=delete&id=${vehicle.vehicleId}" 
                                                   class="btn btn-sm btn-danger" title="Delete"
                                                   onclick="return validateAction('delete', ${vehicle.vehicleId})">
                                                    <i class="fas fa-trash-alt"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Client-side validation -->
    <script>
    function validateAction(action, id) {
        if (!id || isNaN(id)) {
            alert("Invalid Vehicle ID");
            return false;
        }
        if (action === 'delete') {
            return confirm('Are you sure you want to delete this vehicle?');
        }
        return true;
    }
    </script>
</body>
</html>