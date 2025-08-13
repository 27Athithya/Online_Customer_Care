/**
 * 
 */

function validateForm() {
    const subject = document.getElementById('subject').value.trim();
    const message = document.getElementById('message').value.trim();

    if (subject === "" || message === "") {
        alert("Subject and Message are required!");
        return false;
    }
	if (subject.length > 50) {
	        alert("Subject cannot exceed 50 characters!");
	        return false;
	    }
	    if (message.length > 100) {
	        alert("Message cannot exceed 100 characters!");
	        return false;
	    }
    return true;
}

function confirmDelete() {
    return confirm("Are you sure you want to delete this notification?");
}


//function toggleNotifications() {
 // const sidebar = document.getElementById("notificationSidebar");

 // if (sidebar.classList.contains("hidden")) {
//    fetch("notifications?action=sidebar")
 //     .then(response => response.text())
  //    .then(html => {
 //       sidebar.innerHTML = html;
 //       sidebar.classList.remove("hidden");
//      });
//  } else {
//    sidebar.classList.add("hidden");
//  }
//}

function toggleNotifications() {
    const sidebar = document.getElementById("notificationSidebar");
    const icon = document.querySelector(".notification-btn ion-icon");
    
    if (sidebar.classList.contains("hidden")) {
        // Show loading state
        sidebar.innerHTML = '<div class="no-notifications">Loading...</div>';
        sidebar.classList.remove("hidden");
        
        fetch("notifications?action=sidebar")
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.text();
            })
            .then(html => {
                sidebar.innerHTML = html;
                // Change icon to "filled" version when sidebar is open
                icon.setAttribute("name", "notifications");
            })
            .catch(error => {
                console.error('Error:', error);
                sidebar.innerHTML = '<div class="no-notifications">Error loading notifications</div>';
            });
    } else {
        sidebar.classList.add("hidden");
        // Change icon back to outline version
        icon.setAttribute("name", "notifications-outline");
    }
}


document.addEventListener('click', function(event) {
    const sidebar = document.getElementById("notificationSidebar");
    const btn = document.querySelector(".notification-btn");
    
    if (!sidebar.classList.contains("hidden") && 
        !sidebar.contains(event.target) && 
        !btn.contains(event.target)) {
        sidebar.classList.add("hidden");
        document.querySelector(".notification-btn ion-icon").setAttribute("name", "notifications-outline");
    }
});
