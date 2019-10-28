package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentors")
public class Mentor {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "first_name")
    public String FirstName;
    @ColumnInfo(name = "last_name")
    public String LastName;
    @ColumnInfo(name = "phone_number")
    public String PhoneNumber;
    @ColumnInfo(name = "email_address")
    public String EmailAddress;

    public Mentor(){}

    public Mentor(String firstName, String lastName, String phoneNumber, String emailAddress) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
        this.EmailAddress = emailAddress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
