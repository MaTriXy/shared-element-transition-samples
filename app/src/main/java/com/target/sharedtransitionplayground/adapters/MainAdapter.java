package com.target.sharedtransitionplayground.adapters;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.target.sharedtransitionplayground.R;
import com.target.sharedtransitionplayground.util.ImageUtil;
import com.target.sharedtransitionplayground.view.GlideImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Aidan Follestad (afollestad)
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainVH> {

    public interface ClickListener {

        void onItemClick(int index, GlideImageView imageView);
    }

    private final int thumbnailSize;
    private final ClickListener clickListener;

    public MainAdapter(Context context, ClickListener clickListener) {
        this.thumbnailSize = context.getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        this.clickListener = clickListener;
    }

    @Override
    public MainVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.source_list_item, parent, false);
        return new MainVH(view, this);
    }

    @Override
    public void onBindViewHolder(MainVH holder, int position) {
        final String imageUrl = ImageUtil.getUrl(thumbnailSize, 80);
        holder.imageView.setImageUrl(imageUrl, null);
        ViewCompat.setTransitionName(holder.imageView, "image_view_" + position);
        holder.titleView.setText(holder.itemView.getResources().getString(
                R.string.item_x_of_x, position + 1, getItemCount()));
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    static class MainVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MainAdapter adapter;

        @BindView(R.id.image_view)
        GlideImageView imageView;
        @BindView(R.id.title_view)
        TextView titleView;

        MainVH(View itemView, MainAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (adapter != null && adapter.clickListener != null) {
                adapter.clickListener.onItemClick(getAdapterPosition(), imageView);
            }
        }
    }
}
