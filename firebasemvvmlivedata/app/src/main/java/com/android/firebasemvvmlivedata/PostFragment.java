package com.android.firebasemvvmlivedata;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;

import java.util.List;


public class PostFragment extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private Button mAdd;
    private PostAdapter postAdapter;
    private PostViewModel postViewModel;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViewModel();
        FirebaseApp.initializeApp(getActivity());
    }

    public static PostFragment newInstance(){
        return new PostFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeWidgets(view, inflater, container);
        observe();

        return view;
    }

    private void observe() {
        postViewModel.getLivePostModels().observe(getActivity(), new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                postAdapter = new PostAdapter(getActivity(), postModels);
                postAdapter.notifyDataSetChanged();
            }
        });

        postViewModel.getFailedToLoad().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Toast.makeText(getActivity(), "Failed To Load", Toast.LENGTH_SHORT).show();
                }
            }
        });


        postViewModel.getIsLoading().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Toast.makeText(getActivity(), "Observing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeViewModel() {
        postViewModel = ViewModelProviders.of(getActivity()).get(PostViewModel.class);
        postViewModel.init();
    }

    private void initializeWidgets(View view, LayoutInflater inflater, ViewGroup container) {
        view =  inflater.inflate(R.layout.fragment_post, container, false);
        recyclerView = view.findViewById(R.id.post_rv);
        mAdd = view.findViewById(R.id.add);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postViewModel.add();
            }
        });
    }


}
