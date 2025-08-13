<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .error-container {
            margin-top: 100px;
        }
        .error-card {
            max-width: 600px;
        }
    </style>
</head>
<body>
    <div class="container error-container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card error-card border-danger">
                    <div class="card-header bg-danger text-white">
                        <h3><i class="fas fa-exclamation-triangle me-2"></i> Error Occurred</h3>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-danger">
                            <h4>${errorMessage}</h4>
                            <p>Please check your input and try again.</p>
                        </div>
                        <div class="d-grid gap-2">
                            <a href="vehicles" class="btn btn-primary">
                                <i class="fas fa-arrow-left me-2"></i> Back to Vehicle List
                            </a>
                        </div>
                    </div>
                    <div class="card-footer text-muted">
                        Vehicle Management System
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>