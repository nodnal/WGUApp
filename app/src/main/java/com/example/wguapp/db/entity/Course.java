package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Date;

@Entity(tableName = "courses", indices = {@Index("term_id")}, foreignKeys = @ForeignKey(entity = Term.class, parentColumns = "id", childColumns = "term_id", onDelete = ForeignKey.RESTRICT))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "term_id")
    private int termId;
    private String title;
    @ColumnInfo(name = "start_date")
    private Date startDate;
    @ColumnInfo(name = "end_date")
    private Date endDate;
    private String status; //TODO: add enum

    public Course(String title, Date startDate, Date endDate, String status, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termId = termId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTermId(){
        return termId;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }
}
