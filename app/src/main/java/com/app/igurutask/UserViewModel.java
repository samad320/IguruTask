package com.app.igurutask;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<List<User>> getUsers(int page) {
        if (users == null) {
            users = userRepository.getUsers(page);
        }
        return users;
    }
}
