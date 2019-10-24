package com.example.wguapp.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wguapp.db.dao.TermDao;
import com.example.wguapp.db.entity.Term;

import java.util.List;

public class Repository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;

    public Repository(Application application){
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        termDao = appDatabase.termDao();
        allTerms = termDao.getTerms();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    public void insertTerm (Term term) {
        new insertAsyncTask(termDao).execute(term);
    }

    private static class insertAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskDao;

        insertAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
