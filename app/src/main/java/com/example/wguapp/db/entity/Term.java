package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int id;
    public String Title;
    @ColumnInfo(name = "start_date")
    public Date StartDate;
    @ColumnInfo(name = "end_date")
    public Date EndDate;

    public Term(){

    }

    public Term(String title, Date startDate, Date endDate) {
        this.Title = title;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
