package com.studentmanagement;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Student {

    private int id;
    private String name;
    private String rollNumber;
    private String department;
    private String email;

    private BooleanProperty present =
            new SimpleBooleanProperty(false);

    // Constructor for database students
    public Student(
            int id,
            String name,
            String rollNumber,
            String department,
            String email) {

        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.email = email;
    }

    // Constructor for new student
    public Student(
            String name,
            String rollNumber,
            String department,
            String email) {

        this(0, name, rollNumber, department, email);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPresent() {
        return present.get();
    }

    public void setPresent(boolean value) {
        present.set(value);
    }

    public BooleanProperty presentProperty() {
        return present;
    }
}