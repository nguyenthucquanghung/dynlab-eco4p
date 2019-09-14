package unicorn.hust.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import unicorn.hust.myapplication.R;
import unicorn.hust.myapplication.model.ImageObject;

public class ImageGridListAdapter extends RecyclerView.Adapter<ImageGridListAdapter.ViewHolder> {
    private static final String TAG = "hehehehe";

    private List<ImageObject> mImages;
    private Context mContext;
    private ItemListener mListener;

    public ImageGridListAdapter(Context context, List<ImageObject> imageObjects, ItemListener itemListener) {

        mImages = imageObjects;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        ImageObject item;

        ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            imageView = v.findViewById(R.id.imageView);

        }

        void setData(ImageObject item) {
            this.item = item;
            imageView.setImageResource(item.drawable);
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @NonNull
    @Override
    public ImageGridListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_image_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setData(mImages.get(position));

    }

    @Override
    public int getItemCount() {

        return mImages.size();
    }

    public interface ItemListener {
        void onItemClick(ImageObject item);
    }
}
