package unicorn.hust.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.model.PostObject;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private List<PostObject> mPosts;
    private Context mContext;

    public PostListAdapter(List<PostObject> mPosts, Context mContext) {
        this.mPosts = mPosts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_post_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(mPosts.get(i));
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        ImageView ivPhoto;
        TextView tvUsername;
        TextView tvLocationAndTime;

        PostObject postObject;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_item);
            ivPhoto = itemView.findViewById(R.id.iv_images);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvLocationAndTime = itemView.findViewById(R.id.tv_location);

        }

        void setData(PostObject post) {
            this.postObject = post;
            ivAvatar.setImageResource(post.getAvatarId());
//            ivPhoto.setImageResource(post.getImageId());
            tvUsername.setText(post.getUsername());
            tvLocationAndTime.setText(post.getLocationWithTime());
        }
    }
}
