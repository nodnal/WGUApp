package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses", indices = {@Index("term_id")}, foreignKeys = @ForeignKey(entity = Term.class, parentColumns = "id", childColumns = "term_id", onDelete = ForeignKey.RESTRICT))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "term_id")
    private int termId;
    @ColumnInfo(name = "title")
    public String Title;
    @ColumnInfo(name = "start_date")
    public Date StartDate;
    @ColumnInfo(name = "end_date")
    public Date EndDate;
    @ColumnInfo(name = "status")
    public String Status; //TODO: add enum
    @ColumnInfo(name = "start_alert")
    public boolean StartAlert;
    @ColumnInfo(name = "end_alert")
    public boolean EndAlert;

    public Course(){}

    public Course(String title, Date startDate, Date endDate, String status, boolean startAlert, boolean endAlert, int termId) {
        this.Title = title;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Status = status;
        this.StartAlert = startAlert;
        this.EndAlert = endAlert;
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
    public void setTermId(int termId){
        this.termId = termId;
    }


}
