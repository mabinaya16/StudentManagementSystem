package com.studentmanagement;

import java.util.ArrayList;
import java.util.List;

public class StudentData {

    private static final List<Student> students = new ArrayList<>();

    public static void addStudent(
            String name,
            String rollNumber,
            String department,
            String email) {

        students.add(
                new Student(
                        name,
                        rollNumber,
                        department,
                        email
                )
        );
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static int getStudentCount() {
        return students.size();
    }

    public static int getPresentCount() {

        int count = 0;

        for (Student student : students) {
            if (student.isPresent()) {
                count++;
            }
        }

        return count;
    }

    public static int getAbsentCount() {

        return students.size() - getPresentCount();
    }
}