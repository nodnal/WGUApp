package com.example.wguapp.db;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

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
import java.util.stream.Collectors;

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
    public void insertCourse(Course course, MutableLiveData<Integer> courseId) {
        executor.execute(() -> courseId.postValue(Math.toIntExact(courseDao.insert(course))));
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

    public LiveData<List<Mentor>> getAllAssignedMentorsForCourse(LiveData<Integer> courseId) {

        return Transformations.switchMap(courseId,(id) ->
                    Transformations.switchMap(
                        Transformations.map(allMentorAssignments, (assignments) ->
                                            assignments.stream()
                                                        .filter((assignment) -> assignment.getCourseId() == id)
                                                        .map((assignment)-> assignment.getMentorId())
                                                        .collect(Collectors.toList())
                                            )
                        , (mentorIds) ->
                                Transformations.map(getAllMentors(), (mentors)->
                                                    mentors.stream()
                                                            .filter((mentor) -> mentorIds.contains(mentor.getId()))
                                                            .collect(Collectors.toList()))
                    )
        );
    }

    public LiveData<Assessment> getAssessment(LiveData<Integer> assessmentId) {
        return Transformations.switchMap(assessmentId, (id) -> Transformations.map(assessmentDao.getAssessments(), (assessments) -> assessments.stream()
                .filter((a)-> a.getId() == id)
                .findFirst()
                .get()));
    }

    public void saveAssessment(Assessment assessment, MutableLiveData<Integer> assessmentId) {
        executor.execute(() -> assessmentId.postValue(Math.toIntExact(assessmentDao.insert(assessment))));
    }

    public LiveData<Note> getNote(MutableLiveData<Integer> noteId) {
        return Transformations.switchMap(noteId, (id) -> Transformations.map(noteDao.getNotes(), (notes) -> notes.stream()
                .filter((n)-> n.getId() == id)
                .findFirst()
                .get()));
    }

    public void saveNote(Note note, MutableLiveData<Integer> noteId) {
        executor.execute(() -> noteId.postValue(Math.toIntExact(noteDao.insert(note))));
    }
}
