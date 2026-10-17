package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // =====================================================
    // ADD STUDENT
    // =====================================================

    public static boolean addStudent(
            String name,
            String rollNumber,
            String department,
            String email) {

        String sql = """
                INSERT INTO students
                (name, roll_number, department, email)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, rollNumber);
            statement.setString(3, department);
            statement.setString(4, email);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            // Duplicate roll number
            if (e.getErrorCode() == 1062) {
                return false;
            }

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // GET ALL STUDENTS
    // =====================================================

    public static List<Student> getAllStudents() {

        List<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT * FROM students ORDER BY id DESC";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Student student =
                        new Student(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("roll_number"),
                                resultSet.getString("department"),
                                resultSet.getString("email")
                        );

                students.add(student);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return students;
    }


    // =====================================================
    // SEARCH STUDENTS
    // =====================================================

    public static List<Student> searchStudents(
            String keyword) {

        List<Student> students =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM students
                WHERE name LIKE ?
                   OR roll_number LIKE ?
                   OR department LIKE ?
                   OR email LIKE ?
                ORDER BY id DESC
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            String search =
                    "%" + keyword.trim() + "%";

            statement.setString(1, search);
            statement.setString(2, search);
            statement.setString(3, search);
            statement.setString(4, search);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Student student =
                            new Student(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("roll_number"),
                                    resultSet.getString("department"),
                                    resultSet.getString("email")
                            );

                    students.add(student);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return students;
    }


    // =====================================================
    // TOTAL STUDENT COUNT
    // =====================================================

    public static int getStudentCount() {

        String sql =
                "SELECT COUNT(*) FROM students";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }


    // =====================================================
    // PRESENT COUNT TODAY
    // =====================================================

    public static int getPresentCount() {

        String sql = """
                SELECT COUNT(*)
                FROM attendance
                WHERE attendance_date = CURDATE()
                AND present = TRUE
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }


    // =====================================================
    // ABSENT COUNT TODAY
    // =====================================================

    public static int getAbsentCount() {

        String sql = """
                SELECT COUNT(*)
                FROM students
                WHERE id NOT IN (
                    SELECT student_id
                    FROM attendance
                    WHERE attendance_date = CURDATE()
                    AND present = TRUE
                )
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }


    // =====================================================
    // SAVE ATTENDANCE
    // =====================================================

    public static boolean saveAttendance(
            int studentId,
            boolean present) {

        String sql = """
                INSERT INTO attendance
                (student_id, attendance_date, present)
                VALUES (?, CURDATE(), ?)
                ON DUPLICATE KEY UPDATE
                present = VALUES(present)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setBoolean(2, present);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // UPDATE STUDENT
    // =====================================================

    public static boolean updateStudent(
            int id,
            String name,
            String rollNumber,
            String department,
            String email) {

        String sql = """
                UPDATE students
                SET name = ?,
                    roll_number = ?,
                    department = ?,
                    email = ?
                WHERE id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, rollNumber);
            statement.setString(3, department);
            statement.setString(4, email);
            statement.setInt(5, id);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {
                return false;
            }

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // DELETE STUDENT
    // =====================================================

    public static boolean deleteStudent(int id) {

        String sql =
                "DELETE FROM students WHERE id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =====================================================
    // ATTENDANCE HISTORY
    // =====================================================

    public static List<String[]> getAttendanceHistory() {

        List<String[]> history =
                new ArrayList<>();

        String sql = """
                SELECT
                    s.name,
                    s.roll_number,
                    a.attendance_date,
                    a.present
                FROM attendance a
                JOIN students s
                    ON a.student_id = s.id
                ORDER BY
                    a.attendance_date DESC,
                    s.name ASC
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                String name =
                        resultSet.getString("name");

                String rollNumber =
                        resultSet.getString("roll_number");

                String date =
                        resultSet.getDate(
                                "attendance_date"
                        ).toString();

                String status =
                        resultSet.getBoolean("present")
                                ? "Present"
                                : "Absent";

                history.add(
                        new String[]{
                                name,
                                rollNumber,
                                date,
                                status
                        }
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return history;
    }


    // =====================================================
    // ATTENDANCE HISTORY BY DATE
    // =====================================================

    public static List<String[]> getAttendanceHistoryByDate(
            String date) {

        List<String[]> history =
                new ArrayList<>();

        String sql = """
                SELECT
                    s.name,
                    s.roll_number,
                    a.attendance_date,
                    a.present
                FROM attendance a
                JOIN students s
                    ON a.student_id = s.id
                WHERE a.attendance_date = ?
                ORDER BY s.name ASC
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDate(
                    1,
                    java.sql.Date.valueOf(date)
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    String name =
                            resultSet.getString("name");

                    String rollNumber =
                            resultSet.getString("roll_number");

                    String attendanceDate =
                            resultSet.getDate(
                                    "attendance_date"
                            ).toString();

                    String status =
                            resultSet.getBoolean("present")
                                    ? "Present"
                                    : "Absent";

                    history.add(
                            new String[]{
                                    name,
                                    rollNumber,
                                    attendanceDate,
                                    status
                            }
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return history;
    }
}