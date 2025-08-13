<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Vehicle Reservation</title>
<link rel="stylesheet" href="css/VehicleReservation.css" />
</head>
<body>
    <div class="center-wrapper">
        <div class="card">
            <h2>Reserve a Vehicle</h2>

            <c:if test="${not empty error}">
                <div style="color: red;">${error}</div>
            </c:if>

            <form id="reservationForm"
                action="${pageContext.request.contextPath}/VehicleReservationController"
                method="post">
                <label for="vehicle">Select Vehicle:</label>
                <select name="vehicleId" id="vehicle" required>
                    <c:forEach var="vehicle" items="${vehicles}">
                        <option value="${vehicle.vehicleId}" data-rate="${vehicle.rentPerDay}"
                            <c:if test="${selectedVehicleId != null && selectedVehicleId == vehicle.vehicleId.toString()}">selected</c:if>>
                            ${vehicle.vehicleName} - $${vehicle.rentPerDay}/day
                        </option>
                    </c:forEach>
                </select>

                <label for="pickupDate">Pickup Date:</label>
                <input type="date" name="pickupDate" id="pickupDate" required />

                <label for="dropDate">Drop Date:</label>
                <input type="date" name="dropDate" id="dropDate" required />

                <label for="userId">User ID:</label>
                <input type="text" name="userId" id="userId" required pattern="\d+" title="Please enter numeric User ID" />

                <label for="totalRent">Total Rent ($):</label>
                <input type="text" name="totalRent" id="totalRent" readonly />

                <button type="submit">Reserve</button>
            </form>

            <h3>Your Reservations</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Vehicle</th>
                        <th>Pickup</th>
                        <th>Drop</th>
                        <th>Days</th>
                        <th>Total Rent</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty reservations}">
                            <c:forEach var="res" items="${reservations}">
                                <tr>
                                    <td>${res.reservationId}</td>
                                    <td>
                                        <c:forEach var="vehicle" items="${vehicles}">
                                            <c:if test="${vehicle.vehicleId == res.vehicleId}">
                                                ${vehicle.vehicleName}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td><fmt:formatDate value="${res.pickupDate}" pattern="yyyy-MM-dd" /></td>
                                    <td><fmt:formatDate value="${res.dropDate}" pattern="yyyy-MM-dd" /></td>
                                    <td>${res.durationDays}</td>
                                    <td>$${res.totalRent}</td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr><td colspan="6">No reservations found.</td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

<script>
    const vehicleSelect = document.getElementById('vehicle');
    const pickupInput = document.getElementById('pickupDate');
    const dropInput = document.getElementById('dropDate');
    const totalRentInput = document.getElementById('totalRent');

    function calculateTotalRent() {
        const selectedOption = vehicleSelect.selectedOptions[0];
        if (!selectedOption) {
            totalRentInput.value = '';
            return;
        }
        const rate = parseFloat(selectedOption.getAttribute('data-rate'));
        const pickupDate = new Date(pickupInput.value);
        const dropDate = new Date(dropInput.value);

        if (pickupDate && dropDate && dropDate >= pickupDate) {
            const diffTime = dropDate - pickupDate;
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) || 1;
            totalRentInput.value = (rate * diffDays).toFixed(2);
        } else {
            totalRentInput.value = '';
        }
    }

    vehicleSelect.addEventListener('change', calculateTotalRent);
    pickupInput.addEventListener('change', calculateTotalRent);
    dropInput.addEventListener('change', calculateTotalRent);
</script>
</body>
</html>
