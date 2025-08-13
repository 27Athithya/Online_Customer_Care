/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const email = document.getElementById("email");
    const phone = document.getElementById("phoneNumber");
    const license = document.getElementById("licenseNumber");
    const password = document.getElementById("password");

    form.addEventListener("submit", function (e) {
        let isValid = true;
        let messages = [];

        // Email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email.value)) {
            isValid = false;
            messages.push("Enter a valid email address.");
        }

        // Phone number validation (10 digits)
        const phoneRegex = /^\d{10}$/;
        if (!phoneRegex.test(phone.value)) {
            isValid = false;
            messages.push("Enter a valid 10-digit phone number.");
        }

        // License number validation (at least 6 characters, alphanumeric)
        const licenseRegex = /^[a-zA-Z0-9]{6,}$/;
        if (!licenseRegex.test(license.value)) {
            isValid = false;
            messages.push("License number must be at least 6 alphanumeric characters.");
        }

        // Password validation (minimum 6 characters, must include number)
        const passwordRegex = /^(?=.*[0-9]).{6,}$/;
        if (!passwordRegex.test(password.value)) {
            isValid = false;
            messages.push("Password must be at least 6 characters and include a number.");
        }

        if (!isValid) {
            e.preventDefault();
            alert(messages.join("\n"));
        }
    });
});
