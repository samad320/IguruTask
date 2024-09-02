package com.app.igurutask;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;
    private UserDao userDao;

    public UserRepository(Context context) {
        apiService = RetrofitClient.getClient().create(ApiService.class);
        userDao = AppDatabase.getDatabase(context).userDao();
    }

    public LiveData<List<User>> getUsers(int page) {
        MutableLiveData<List<User>> users = new MutableLiveData<>();
        apiService.getUsers(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    users.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("UserRepository", "Location permission denied");

            }
        });
        return users;
    }
}
