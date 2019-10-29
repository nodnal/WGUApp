package com.example.wguapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.wguapp.db.Repository;
import com.example.wguapp.db.entity.Assessment;
import com.example.wguapp.db.entity.Course;
import com.example.wguapp.db.entity.Mentor;
import com.example.wguapp.db.entity.Note;

import java.util.List;
import java.util.stream.Collectors;

public class CourseDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> courseId;
    private LiveData<Course> course;
    private LiveData<List<Assessment>> assessments;
    private LiveData<List<Mentor>> mentors;
    private LiveData<List<Note>> notes;
    private Repository repo;

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        courseId = new MutableLiveData<>();
        course = Transformations.switchMap(courseId,(id) -> repo.getCourse(id));
        assessments = Transformations.switchMap(courseId, (id) -> Transformations.map(repo.getAllAssessments(), (list) -> list.stream()
                .filter(assessment -> assessment.getCourseId()==id)
                .collect(Collectors.toList())));
        mentors = repo.getAllAssignedMentorsForCourse(courseId);
        notes = Transformations.switchMap(courseId, (id) -> Transformations.map(repo.getAllNotes(), (list) -> list.stream()
                .filter(note -> note.getCourseId()==id)
                .collect(Collectors.toList())));
    }

    public void LoadCourse(int id){
        courseId.postValue(id);
    }

    public void SaveCourse(Course course){
        repo.insertCourse(course, courseId);
    }

    public LiveData<Course> getCourse() {
        return course;
    }

    public LiveData<List<Assessment>> getAssessments() {
        return assessments;
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public LiveData<List<Mentor>> getMentors() {
        return mentors;
    }
}
