package com.studentmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField rollNumberField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField emailField;

    @FXML
    private void saveStudent() {

        // Get values
        String name = nameField.getText().trim();
        String rollNumber = rollNumberField.getText().trim();
        String department = departmentField.getText().trim();
        String email = emailField.getText().trim();

        // ==========================================
        // EMPTY FIELD VALIDATION
        // ==========================================

        if (name.isEmpty()
                || rollNumber.isEmpty()
                || department.isEmpty()
                || email.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Missing Details",
                    "Please fill all fields."
            );

            return;
        }

        // ==========================================
        // NAME VALIDATION
        // ==========================================

        if (!name.matches("[a-zA-Z ]+")) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Name",
                    "Name should contain letters and spaces only."
            );

            return;
        }

        // ==========================================
        // ROLL NUMBER VALIDATION
        // ==========================================

        if (!rollNumber.matches("[a-zA-Z0-9]+")) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Roll Number",
                    "Roll number should contain only letters and numbers."
            );

            return;
        }

        // ==========================================
        // EMAIL VALIDATION
        // ==========================================

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Email",
                    "Please enter a valid email address."
            );

            return;
        }

        // ==========================================
        // SAVE TO DATABASE
        // ==========================================

        boolean saved = StudentDAO.addStudent(
                name,
                rollNumber,
                department,
                email
        );

        // ==========================================
        // SAVE RESULT
        // ==========================================

        if (saved) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Student added successfully."
            );

            // Refresh dashboard
            DashboardController.refreshDashboard();

            // Close Add Student window
            Stage stage = (Stage) nameField
                    .getScene()
                    .getWindow();

            stage.close();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Unable to Save",
                    "Student could not be saved to the database."
            );
        }
    }

    // ==========================================
    // ALERT METHOD
    // ==========================================

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}