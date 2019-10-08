package unicorn.hust.myapplication.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.adapter.PostListAdapter;
import unicorn.hust.myapplication.model.Feed;
import unicorn.hust.myapplication.model.PostObject;
import unicorn.hust.myapplication.model.RegisterResponse;
import unicorn.hust.myapplication.utils.Constant;
import unicorn.hust.myapplication.model.Feed.*;

import static android.app.Activity.RESULT_OK;

public class FeedFragment extends Fragment {

    private static final String TAG = "hehehe";
    PostListAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    String encodedStringImage = "";
    EditText etPostContent;
    TextView tvPost;
    RecyclerView mRecyclerView;
    ImageView ivPendingImage;
    ImageView ivAddPhotos;
    TextView tvEmpty;
    List<PostObject> mPosts = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        
        findViewById(view);

        setupRecyclerView();
        return view;
    }

    private void findViewById(View view) {
        mRecyclerView = view.findViewById(R.id.rv_posts);
        tvEmpty = view.findViewById(R.id.tv_empty);
        ivAddPhotos = view.findViewById(R.id.iv_add_images);
        tvPost = view.findViewById(R.id.tv_post);
        ivPendingImage = view.findViewById(R.id.iv_pending_image);
        etPostContent = view.findViewById(R.id.et_post_content);
        swipeRefreshLayout = view.findViewById(R.id.layout_swipe_refresh);

        // Call shared preferences
        sharedPreferences = getContext()
                .getSharedPreferences(Constant.USER, getContext().MODE_PRIVATE);

        // Set on click listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPostsFromServer(
                        sharedPreferences.getString(Constant.USERNAME, ""),
                        sharedPreferences.getString(Constant.PASSWORD, ""),
                        1
                );
            }
        });
        ivAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                else {
                    startGallery();
                }
            }
        });

        tvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPostContent.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter caption!", Toast.LENGTH_LONG).show();
                } else if (!etPostContent.getText().toString().contains("@")) {
                    Toast.makeText(getContext(),"Please tag a friend!", Toast.LENGTH_LONG).show();
                } else {
                    postStatus();
                }
            }
        });
    }

    private void postStatus() {
        Toast.makeText(getContext(), "Posting", Toast.LENGTH_LONG).show();
        String content = etPostContent.getText().toString();
        String[] splitStrings = content.split("@");
        List<String> receiversList = new ArrayList<>();
        int i;
        for (i = 1; i < splitStrings.length; i++) {
                receiversList.add(splitStrings[i].split(" ")[0].split("\n")[0]);
        }
        String receivers = "";
        receivers += "\"" + receiversList.get(0) + "\"";
        if (receiversList.size() != 1){
            for (i = 1; i < receiversList.size(); i++) {
                receivers = new StringBuffer("").
                        append("\"").
                        append(receiversList.get(i)).
                        append("\",").append(receivers).
                        toString();
            }
        }
        content = content.replaceAll("\n", Constant.NEW_LINE_REPLACEMENT);
        String json = "{\n" +
                "\t\"sendID\":\"" + sharedPreferences.getString(Constant.USERNAME, "") + "\",\n" +
                "\t\"passWord\":\"" + sharedPreferences.getString(Constant.PASSWORD, "") + "\",\n" +
                "\t\"time\":\"14:00 - 06/10/2019\",\n" +
                "\t\"contentText\":\"" + content + "\",\n" +
                "\t\"base64Image\":\"" + encodedStringImage + "\",\n" +
                "\t\"receiveId\":[" + receivers + "]\n" +
                "}";

        RequestBody body = RequestBody.create(json, Constant.JSON);
        final Request request = new Request.Builder()
                .url(Constant.URL_POST_STATUS)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getContext(), "Server error! Please log in again!", Toast.LENGTH_LONG).show();
                    }
                });
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                final Gson gson = new Gson();
                final RegisterResponse postResponse =
                        gson.fromJson(response.body().charStream(), RegisterResponse.class);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getContext(), postResponse.getMessage(), Toast.LENGTH_LONG).show();
                        if (postResponse.getType().equals("done")) {
                            ivPendingImage.setVisibility(View.GONE);
                            etPostContent.setText("");
                            etPostContent.setHint("What\'s on your mind");
                            getPostsFromServer(
                                    sharedPreferences.getString(Constant.USERNAME, ""),
                                    sharedPreferences.getString(Constant.PASSWORD, ""),
                                    1
                            );
                        }
                    }
                });
            }
        });
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }



    private void setupRecyclerView() {
        getPostsFromServer(
                sharedPreferences.getString(Constant.USERNAME, ""),
                sharedPreferences.getString(Constant.PASSWORD, ""),
                1
        );
    }

    private void getPostsFromServer(String username, String password, int page) {
        swipeRefreshLayout.setRefreshing(true);
        String json = "{\n" +
                "\t\"account\":\"" + username + "\",\n" +
                "\t\"password\":\"" + password + "\",\n" +
                "\t\"getPage\":\"" + page + "\"\n" +
                "}";

        RequestBody body = RequestBody.create(json, Constant.JSON);
        final Request request = new Request.Builder()
                .url(Constant.URL_GET_POSTS)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getContext(), "Server error! Please log in again!", Toast.LENGTH_LONG).show();
                    }
                });

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // Set adapter
                        mRecyclerView.setAdapter(adapter);

                        // Set layout
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                    }
                });

            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                final Gson gson = new Gson();
                final Feed feed = gson.fromJson(response.body().charStream(), Feed.class);
                if (feed.getPosts() == null || feed.getPosts().size() == 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    mPosts.clear();
                    for (Post post: feed.getPosts()) {
                        mPosts.add(new PostObject(
                                post.getBase64Image(),
                                R.drawable.img00_round,
                                post.getSendID(),
                                post.getTime(),
                                post.getContentText()
                        ));
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // Set adapter
                        PostListAdapter adapter = new PostListAdapter(mPosts, getContext());
                        mRecyclerView.setAdapter(adapter);

                        // Set layout
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == 1000){
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Set bitmap to image view
                ivPendingImage.setImageBitmap(bitmapImage);
                ivPendingImage.setVisibility(View.VISIBLE);

                // Encode bitmap to base64
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                encodedStringImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedStringImage = encodedStringImage.replaceAll("\n", Constant.NEW_LINE_REPLACEMENT);
            }
        }
    }

}
