package com.android.firebasemvvmlivedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private FirebaseFirestore db;
    private CollectionReference posts;
    private static PostRepository instance;
    private ArrayList<PostModel> dataFromFirebase = new ArrayList<>();

    MutableLiveData<List<PostModel>> data;
    MutableLiveData<Boolean> isLoading;
    MutableLiveData<Boolean> failedToLoad;

    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }

    public MutableLiveData<List<PostModel>> getPosts() {
        setDataFromFirebase();
        data = new MutableLiveData<>();
        data.setValue(dataFromFirebase);
        return data;
    }

    public void setDataFromFirebase() {

        db = FirebaseFirestore.getInstance();
        posts = db.collection("POSTS");
        isLoading.setValue(true);
        failedToLoad.setValue(false);
        posts.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PostModel postModel = document.toObject(PostModel.class);
                        dataFromFirebase.add(postModel);
                        isLoading.setValue(false);
                        failedToLoad.setValue(false);
                    }
                }else {
                    failedToLoad.setValue(true);
                }
            }
        });
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getFailedToLoad() {
        return failedToLoad;
    }
}