package com.example.wguapp.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wguapp.db.dao.AssessmentDao;
import com.example.wguapp.db.dao.CourseDao;
import com.example.wguapp.db.dao.NoteDao;
import com.example.wguapp.db.dao.TermDao;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.Note;
import com.example.wguapp.db.entity.Term;

import java.util.List;

public class Repository {
    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;
    private NoteDao noteDao;

    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Note>> allNotes;

    public Repository(Application application){
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        termDao = appDatabase.termDao();
        courseDao = appDatabase.courseDao();
        assessmentDao = appDatabase.assessmentDao();
        noteDao = appDatabase.noteDao();

        allTerms = termDao.getTerms();
        allCourses = courseDao.getCourses();
        allAssessments = assessmentDao.getAssessments();
        allNotes = noteDao.getNotes();
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

    public void insertTerm (Term term) {
        new insertTermAsyncTask(termDao).execute(term);
    }
    public void insertCourse (Course course) {
        new insertCourseAsyncTask(courseDao).execute(course);
    }
    public void insertAssessment (Assessment assessment) {
        new insertAssessmentAsyncTask(assessmentDao).execute(assessment);
    }
    public void insertNote (Note note) {
        new insertNoteAsyncTask(noteDao).execute(note);
    }

    private static class insertTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskTermDao;

        insertTermAsyncTask(TermDao dao) {
            mAsyncTaskTermDao = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskTermDao.insert(params[0]);
            return null;
        }
    }
    private static class insertCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskCourseDao;

        insertCourseAsyncTask(CourseDao dao) {
            mAsyncTaskCourseDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskCourseDao.insert(params[0]);
            return null;
        }
    }
    private static class insertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao mAsyncTaskAssessmentDao;

        insertAssessmentAsyncTask(AssessmentDao dao) {
            mAsyncTaskAssessmentDao = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskAssessmentDao.insert(params[0]);
            return null;
        }
    }
    private static class insertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskNoteDao;

        insertNoteAsyncTask(NoteDao dao) {
            mAsyncTaskNoteDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            mAsyncTaskNoteDao.insert(params[0]);
            return null;
        }
    }
}
