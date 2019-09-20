package unicorn.hust.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.adapter.PostListAdapter;
import unicorn.hust.myapplication.model.PostObject;
import unicorn.hust.myapplication.utils.Constant;

public class FeedFragment extends Fragment {

    RecyclerView mRecyclerView;
    List<PostObject> mPosts;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerView = view.findViewById(R.id.rv_posts);

        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        // Mock data
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(Constant.USER, getContext().MODE_PRIVATE);
        String name = sharedPreferences.getString(Constant.NAME, "Jinny");
        mPosts = new ArrayList<>();
        PostObject fakeObject = new PostObject(R.drawable.img00_round, R.drawable.mock_data,
                name, "Trường Mầm Non Láng Thượng - 15:30");
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);
        mPosts.add(fakeObject);

        // Set adapter
        PostListAdapter adapter = new PostListAdapter(mPosts, getContext());
        mRecyclerView.setAdapter(adapter);

        // Set layout
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
    }
}
