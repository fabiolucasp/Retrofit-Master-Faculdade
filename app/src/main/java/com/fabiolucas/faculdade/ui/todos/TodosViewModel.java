package com.fabiolucas.faculdade.ui.todos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TodosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is todos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}