package com.example.wguapp.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;
    private NoteDao noteDao;
    private MentorDao mentorDao;
    private CourseMentorDao courseMentorDao;

    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Mentor>> allMentors;
    private LiveData<List<CourseMentorJoin>> allMentorAssignments;

    private Executor executor = Executors.newSingleThreadExecutor();

    private static Repository instance;

    public static Repository getInstance(Application application){
        if (instance == null) {
            instance = new Repository(application);
        }
        return instance;
    }

    private Repository(Application application){
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        termDao = appDatabase.termDao();
        courseDao = appDatabase.courseDao();
        assessmentDao = appDatabase.assessmentDao();
        noteDao = appDatabase.noteDao();
        mentorDao = appDatabase.mentorDao();
        courseMentorDao = appDatabase.courseMentorDao();

        allTerms = termDao.getTerms();
        allCourses = courseDao.getCourses();
        allAssessments = assessmentDao.getAssessments();
        allNotes = noteDao.getNotes();
        allMentors = mentorDao.getAllMentors();
        allMentorAssignments = courseMentorDao.getAllCourseMentorJoins();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }
    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    public LiveData<List<Mentor>> getAllMentors() {return allMentors;}

    public void insertTerm (Term term) {
        executor.execute(() -> termDao.insert(term));
    }
    public void insertCourse (Course course) {
        executor.execute(() -> courseDao.insert(course));
    }
    public void insertAssessment (Assessment assessment) {
        executor.execute(() -> assessmentDao.insert(assessment));
    }
    public void insertNote (Note note) {
        executor.execute(() -> noteDao.insert(note));
    }

    public LiveData<Term> getTerm(int termId) {
        return termDao.getTerm(termId);
    }

    public LiveData<Course> getCourse(Integer id) {
        return courseDao.getCourse(id);
    }

}
