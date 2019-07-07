package com.android.firebasemvvmlivedata;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class PostViewModel extends ViewModel {

    private MutableLiveData<Boolean> isLivePostModelsEmpty;
    private MutableLiveData<List<PostModel>> livePostModels;
    MutableLiveData<Boolean> isLoading;
    MutableLiveData<Boolean> failedToLoad;

    private PostRepository postRepository;


    public void init(){
        if(livePostModels != null){
            return;
        }
        postRepository = PostRepository.getInstance();
        livePostModels = postRepository.getPosts();
        isLoading = postRepository.getIsLoading();
        failedToLoad = postRepository.getFailedToLoad();


    }



    public LiveData<List<PostModel>> getLivePostModels() {
        return livePostModels;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getFailedToLoad() {
        return failedToLoad;
    }

    public void add() {

        isLoading.setValue(true);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                PostModel postModel = new PostModel("1","Hello", "World");
                ArrayList list = new ArrayList();
                list.add(postModel);
                livePostModels.postValue(list);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };


    }
}
