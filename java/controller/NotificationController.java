package controller;

import dao.NotificationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Notification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/notifications")
public class NotificationController extends HttpServlet {

    private NotificationDAO dao;

    @Override
    public void init() {
        dao = new NotificationDAO();
    }

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Inside doGet() method");

        try {
            String action = req.getParameter("action");
            if (action == null) action = "list";  // Default action is 'list'
            
            switch (action) {
                case "edit":
                    showEditForm(req, resp);
                    break;
                case "delete":
                    deleteNotification(req, resp);
                    break;
                    
                case "sidebar":
                    listNotificationsForSidebar(req, resp);
                    break;

                default:
                    listNotifications(req, resp);  // This will get the notifications
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

//    
//    private void listNotificationsForSidebar(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException, SQLException {
//        List<Notification> notifications = dao.getAll(); // Or limit to latest 5
//        req.setAttribute("notifications", notifications);
//        req.getRequestDispatcher("notificationList.jsp").forward(req, resp);
//    }
//
//    

    
    private void listNotificationsForSidebar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {
        try {
            // Get only the most recent 5 notifications
            List<Notification> notifications = dao.getAll();
            req.setAttribute("notifications", notifications);
            
            resp.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/notificationSidebar.jsp").include(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("notificationId");
            Notification n = new Notification();
            n.setSubject(req.getParameter("subject"));
            n.setMessage(req.getParameter("message"));
            n.setCreatedAt(new Date());

            if (id == null || id.isEmpty()) {
                dao.insert(n);
            } else {
                n.setNotificationId(Integer.parseInt(id));
                dao.update(n);
            }
            resp.sendRedirect("notifications");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

 
    
    private void listNotifications(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Notification> notifications = dao.getAll();  // This should get the data
        req.setAttribute("notifications", notifications);  // Pass it to the JSP
        req.getRequestDispatcher("notifications.jsp").forward(req, resp);  // Forward to the JSP
        System.out.println("Notifications: " + notifications.size()); // To verify how many notifications are returned
        
    	
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Notification n = dao.getById(id);
        req.setAttribute("notification", n);

        // Fetch the full list too, so table shows below form
        List<Notification> notifications = dao.getAll();
        req.setAttribute("notifications", notifications);

        // Forward to the same JSP
        req.getRequestDispatcher("notifications.jsp").forward(req, resp);
    }

    
    

    private void deleteNotification(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        dao.delete(id);
        resp.sendRedirect("notifications");
    }
}

