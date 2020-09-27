package com.example.wguapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Term[] populateData() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Term[] terms = null;
        try {
            terms = new Term[]{
                    new Term("Term 1", sdf.parse("1/6/2020"), sdf.parse("6/6/2020")),
                    new Term("Term 2", sdf.parse("6/6/2020"), sdf.parse("12/6/2020")),
                    new Term("Term 3", sdf.parse("1/6/2021"), sdf.parse("6/6/2021")),
                    new Term("Term 4", sdf.parse("6/6/2021"), sdf.parse("12/6/2021")),
                    new Term("Term 5", sdf.parse("1/6/2022"), sdf.parse("12/6/2022"))
            };

        }catch(ParseException e){}
        return terms;
    }
}
