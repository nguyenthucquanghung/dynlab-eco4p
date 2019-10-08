package unicorn.hust.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.model.PostObject;
import unicorn.hust.myapplication.utils.Constant;

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
        return (mPosts == null)? 0:mPosts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        ImageView ivPhoto;
        TextView tvUsername;
        TextView tvLocationAndTime;
        TextView tvContent;

        PostObject postObject;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_item);
            ivPhoto = itemView.findViewById(R.id.iv_images);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvLocationAndTime = itemView.findViewById(R.id.tv_location);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        void setData(PostObject post) {
            this.postObject = post;

            if (post.getBase64Image().equals(""))
                ivPhoto.setVisibility(View.GONE);
            else {
                byte[] decodedString = Base64.decode(post.getBase64Image().replaceAll(Constant.NEW_LINE_REPLACEMENT, "\n"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivPhoto.setImageBitmap(decodedByte);
                ivPhoto.setMaxHeight(decodedByte.getHeight());
                ivPhoto.setVisibility(View.VISIBLE);

            }
            ivAvatar.setImageResource(post.getAvatarId());
            tvUsername.setText(post.getUsername());
            tvLocationAndTime.setText(post.getLocationWithTime());
            tvContent.setText(postObject.getContent().replaceAll(Constant.NEW_LINE_REPLACEMENT, "\n"));
        }
    }

    public void updateItems(List<PostObject> postObjects) {
        mPosts.clear();
        mPosts.addAll(postObjects);
        this.notifyDataSetChanged();
    }
}
