package unicorn.hust.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.activity.LoginActivity;
import unicorn.hust.myapplication.adapter.ImageGridListAdapter;
import unicorn.hust.myapplication.model.ImageObject;
import unicorn.hust.myapplication.utils.AutoFitGridLayoutManager;
import unicorn.hust.myapplication.utils.Constant;

public class ProfileFragment extends Fragment implements ImageGridListAdapter.ItemListener{
    private static final String TAG = "hehehehe";

    RecyclerView recyclerView;
    List<ImageObject> mImages = new ArrayList<>();
    TextView tvName;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvName = view.findViewById(R.id.name);
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(Constant.USER, getContext().MODE_PRIVATE);

        tvName.setText(sharedPreferences.getString(Constant.NAME, "Jinny"));
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
//         Mock data
        mImages.add(new ImageObject(R.drawable.img01));
        mImages.add(new ImageObject(R.drawable.img02));
        mImages.add(new ImageObject(R.drawable.img03));
        mImages.add(new ImageObject(R.drawable.img04));
        mImages.add(new ImageObject(R.drawable.img01));
        mImages.add(new ImageObject(R.drawable.img02));
        mImages.add(new ImageObject(R.drawable.img03));
        mImages.add(new ImageObject(R.drawable.img04));
        mImages.add(new ImageObject(R.drawable.img01));
        mImages.add(new ImageObject(R.drawable.img02));
        mImages.add(new ImageObject(R.drawable.img03));
        mImages.add(new ImageObject(R.drawable.img04));
        mImages.add(new ImageObject(R.drawable.img01));
        mImages.add(new ImageObject(R.drawable.img02));
        mImages.add(new ImageObject(R.drawable.img03));
        mImages.add(new ImageObject(R.drawable.img04));
        mImages.add(new ImageObject(R.drawable.img01));
        mImages.add(new ImageObject(R.drawable.img02));
        mImages.add(new ImageObject(R.drawable.img03));
        mImages.add(new ImageObject(R.drawable.img04));
        mImages.add(new ImageObject(R.drawable.img01));
        mImages.add(new ImageObject(R.drawable.img02));
        mImages.add(new ImageObject(R.drawable.img03));
        mImages.add(new ImageObject(R.drawable.img04));
        mImages.add(new ImageObject(R.drawable.img01));
        mImages.add(new ImageObject(R.drawable.img02));
        mImages.add(new ImageObject(R.drawable.img03));
        mImages.add(new ImageObject(R.drawable.img04));

        // Set Adapter
        ImageGridListAdapter adapter = new ImageGridListAdapter(getContext(), mImages, this);
        recyclerView.setAdapter(adapter);

        // Auto fit grid layout
        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(getContext(), 300);
        recyclerView.setLayoutManager(manager);
    }
    @Override
    public void onItemClick(ImageObject item) {

    }
}
