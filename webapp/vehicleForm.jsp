<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehicle Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .form-container {
            max-width: 800px;
            margin: 30px auto;
        }
        .current-image {
            max-width: 200px;
            max-height: 150px;
        }
    </style>
</head>
<body class="bg-light">

 <%@ include file="sidebar.jsp" %>
<div class="container">
    <div class="form-container">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h4 class="card-title mb-0">
                    <i class="fas ${not empty vehicle ? 'fa-edit' : 'fa-plus'} me-2"></i>
                    ${not empty vehicle ? 'Edit Vehicle' : 'Add New Vehicle'}
                </h4>
            </div>
            <div class="card-body">
                <form action="vehicles" method="post" enctype="multipart/form-data" id="vehicleForm">
                    <c:if test="${not empty vehicle}">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${vehicle.vehicleId}">
                        <input type="hidden" name="existingImage" value="${vehicle.vehicleImage}">
                    </c:if>
                    <c:if test="${empty vehicle}">
                        <input type="hidden" name="action" value="insert">
                    </c:if>

                    <div class="row g-3 mb-4">
                        <div class="col-md-6">
                            <label for="vehicleName" class="form-label">Vehicle Name *</label>
                            <input type="text" class="form-control" id="vehicleName" name="vehicleName"
                                   value="${vehicle.vehicleName}" required>
                        </div>
                        <div class="col-md-6">
                            <label for="manufactureYear" class="form-label">Manufacture Year *</label>
                            <input type="number" class="form-control" id="manufactureYear" name="manufactureYear"
                                   value="${vehicle.manufactureYear}" required min="1900" max="2099">
                        </div>
                        <div class="col-md-6">
                            <label for="personCapacity" class="form-label">Person Capacity *</label>
                            <input type="number" class="form-control" id="personCapacity" name="personCapacity"
                                   value="${vehicle.personCapacity}" required min="1" max="20">
                        </div>
                        <div class="col-md-6">
                            <label for="fuelType" class="form-label">Fuel Type *</label>
                            <select class="form-select" id="fuelType" name="fuelType" required>
                                <option value="">-- Select Fuel Type --</option>
                                <option value="Gasoline" ${vehicle.fuelType == 'Gasoline' ? 'selected' : ''}>Gasoline</option>
                                <option value="Hybrid" ${vehicle.fuelType == 'Hybrid' ? 'selected' : ''}>Hybrid</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="literPerKm" class="form-label">Liter per Km *</label>
                            <input type="number" step="0.1" class="form-control" id="literPerKm" name="literPerKm"
                                   value="${vehicle.literPerKm}" required min="0.1" max="50">
                        </div>
                        <div class="col-md-6">
                            <label for="transmissionType" class="form-label">Transmission Type *</label>
                            <select class="form-select" id="transmissionType" name="transmissionType" required>
                                <option value="">-- Select Transmission --</option>
                                <option value="Auto" ${vehicle.transmissionType == 'Auto' ? 'selected' : ''}>Automatic</option>
                                <option value="Manual" ${vehicle.transmissionType == 'Manual' ? 'selected' : ''}>Manual</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="rentPerDay" class="form-label">Rent per Day ($) *</label>
                            <input type="number" step="0.01" class="form-control" id="rentPerDay" name="rentPerDay"
                                   value="${vehicle.rentPerDay}" required min="1" max="10000">
                        </div>
                        <div class="col-md-6">
                            <label for="vehicleImage" class="form-label">Vehicle Image</label>
                            <input type="file" class="form-control" id="vehicleImage" name="vehicleImage"
                                   accept="image/*">
                            <c:if test="${not empty vehicle.vehicleImage}">
                                <div class="mt-3">
                                    <p class="mb-1">Current image:</p>
                                    <img src="${pageContext.request.contextPath}/${vehicle.vehicleImage}"
                                         class="current-image img-thumbnail">
                                </div>
                            </c:if>
                        </div>
                        <div class="col-12 mt-4">
                            <button type="submit" class="btn btn-primary me-2">
                                <i class="fas fa-save me-1"></i> Save
                            </button>
                            <a href="vehicles" class="btn btn-secondary">
                                <i class="fas fa-times me-1"></i> Cancel
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- JavaScript Form Validation -->
<script>
document.getElementById('vehicleForm').addEventListener('submit', function(e) {
    const name = document.getElementById('vehicleName');
    const year = document.getElementById('manufactureYear');
    const capacity = document.getElementById('personCapacity');
    const fuel = document.getElementById('fuelType');
    const liter = document.getElementById('literPerKm');
    const transmission = document.getElementById('transmissionType');
    const rent = document.getElementById('rentPerDay');

    if (name.value.trim() === "") {
        alert("Vehicle name is required.");
        name.focus();
        e.preventDefault();
        return false;
    }

    if (year.value < 1900 || year.value > 2025) {
        alert("Please enter a valid manufacture year (1900 - 2099).");
        year.focus();
        e.preventDefault();
        return false;
    }

    if (capacity.value < 1 || capacity.value > 20) {
        alert("Person capacity must be between 1 and 20.");
        capacity.focus();
        e.preventDefault();
        return false;
    }

    if (fuel.value === "") {
        alert("Please select a fuel type.");
        fuel.focus();
        e.preventDefault();
        return false;
    }

    if (liter.value < 0.1 || liter.value > 50) {
        alert("Liter per Km must be between 0.1 and 50.");
        liter.focus();
        e.preventDefault();
        return false;
    }

    if (transmission.value === "") {
        alert("Please select a transmission type.");
        transmission.focus();
        e.preventDefault();
        return false;
    }

    if (rent.value < 1 || rent.value > 10000) {
        alert("Rent per day must be between 1 and 10000.");
        rent.focus();
        e.preventDefault();
        return false;
    }

    // Optionally: check image file type if uploaded
    const image = document.getElementById('vehicleImage');
    if (image.files.length > 0) {
        const file = image.files[0];
        const validTypes = ['image/jpeg', 'image/png', 'image/gif'];
        if (!validTypes.includes(file.type)) {
            alert("Only JPEG, PNG, or GIF images are allowed.");
            image.focus();
            e.preventDefault();
            return false;
        }
    }

    return true;
});
</script>
</body>
</html>