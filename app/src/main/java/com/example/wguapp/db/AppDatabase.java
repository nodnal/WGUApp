package com.example.wguapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.wguapp.db.converter.DateConverter;
import com.example.wguapp.db.dao.AssessmentDao;
import com.example.wguapp.db.dao.CourseDao;
import com.example.wguapp.db.dao.CourseMentorDao;
import com.example.wguapp.db.dao.MentorDao;
import com.example.wguapp.db.dao.NoteDao;
import com.example.wguapp.db.dao.TermDao;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.CourseMentorJoin;
import com.example.wguapp.db.entity.Mentor;
import com.example.wguapp.db.entity.Note;
import com.example.wguapp.db.entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Note.class, Mentor.class, CourseMentorJoin.class}, version = 5, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract NoteDao noteDao();
    public abstract MentorDao mentorDao();
    public abstract CourseMentorDao courseMentorDao();


    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
