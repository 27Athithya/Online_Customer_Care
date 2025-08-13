/**
 * 
 */
'use strict';

/**
 * navbar toggle
 */

const overlay = document.querySelector("[data-overlay]");
const navbar = document.querySelector("[data-navbar]");
const navToggleBtn = document.querySelector("[data-nav-toggle-btn]");
const navbarLinks = document.querySelectorAll("[data-nav-link]");

const navToggleFunc = function () {
  navToggleBtn.classList.toggle("active");
  navbar.classList.toggle("active");
  overlay.classList.toggle("active");
}

navToggleBtn.addEventListener("click", navToggleFunc);
overlay.addEventListener("click", navToggleFunc);

for (let i = 0; i < navbarLinks.length; i++) {
  navbarLinks[i].addEventListener("click", navToggleFunc);
}



/**
 * header active on scroll
 */

const header = document.querySelector("[data-header]");

window.addEventListener("scroll", function () {
  window.scrollY >= 10 ? header.classList.add("active")
    : header.classList.remove("active");
});




// Image preview for file uploads
document.addEventListener('DOMContentLoaded', function() {
    // Profile image preview
    const profileImageInput = document.getElementById('profileImage');
    if (profileImageInput) {
        profileImageInput.addEventListener('change', function(e) {
            const preview = document.getElementById('profileImagePreview');
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    preview.src = e.target.result;
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    }

    // Close button functionality for alerts
    document.querySelectorAll('.alert').forEach(alert => {
        const closeBtn = document.createElement('button');
        closeBtn.innerHTML = '&times;';
        closeBtn.style.marginLeft = '15px';
        closeBtn.style.background = 'none';
        closeBtn.style.border = 'none';
        closeBtn.style.cursor = 'pointer';
        closeBtn.style.fontSize = '18px';
        closeBtn.addEventListener('click', () => {
            alert.style.display = 'none';
        });
        alert.appendChild(closeBtn);
    });

    // Form validation
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function(e) {
            let valid = true;
            
            // Check required fields
            this.querySelectorAll('[required]').forEach(field => {
                if (!field.value.trim()) {
                    valid = false;
                    field.style.borderColor = 'red';
                    
                    // Add error message if not exists
                    if (!field.nextElementSibling || !field.nextElementSibling.classList.contains('error-message')) {
                        const errorMsg = document.createElement('div');
                        errorMsg.className = 'error-message';
                        errorMsg.style.color = 'red';
                        errorMsg.style.fontSize = '12px';
                        errorMsg.style.marginTop = '5px';
                        errorMsg.textContent = 'This field is required';
                        field.parentNode.insertBefore(errorMsg, field.nextSibling);
                    }
                } else {
                    field.style.borderColor = '';
                    const errorMsg = field.nextElementSibling;
                    if (errorMsg && errorMsg.classList.contains('error-message')) {
                        errorMsg.remove();
                    }
                }
            });
            
            if (!valid) {
                e.preventDefault();
            }
        });
    });
});
















