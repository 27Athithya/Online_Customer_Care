<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Available Vehicles</title>
    <style>
        .vehicle-container { display: flex; flex-wrap: wrap; gap: 20px; }
        .vehicle-card { 
            border: 1px solid #ddd; border-radius: 8px; 
            padding: 15px; width: 300px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .vehicle-image { width: 100%; height: 180px; object-fit: cover; }
        .rent-button {
            background-color: #4CAF50; color: white; 
            padding: 8px 16px; border: none; border-radius: 4px;
            cursor: pointer; margin-top: 10px;
        }
    </style>
</head>
<body>
    <h1>Available Vehicles</h1>
    
    <div class="vehicle-container">
        <c:forEach var="vehicle" items="${vehicles}">
            <div class="vehicle-card">
                <c:if test="${not empty vehicle.vehicleImage}">
                    <img src="${pageContext.request.contextPath}/${vehicle.vehicleImage}" 
                         alt="${vehicle.vehicleName}" class="vehicle-image">
                </c:if>
                <h2>${vehicle.vehicleName}</h2>
                <p>${vehicle.personCapacity} People</p>
                <p>${vehicle.literPerKm}km / 1-litre</p>
                <p>${vehicle.fuelType}</p>
                <p>${vehicle.transmissionType}</p>
                <p>${vehicle.manufactureYear}</p>
                <p>$${vehicle.rentPerDay} / month</p>
                <button class="rent-button">Rent now</button>
            </div>
        </c:forEach>
    </div>
</body>
</html>