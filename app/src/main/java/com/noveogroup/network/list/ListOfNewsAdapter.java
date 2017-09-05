package com.noveogroup.network.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.noveogroup.network.R;
import com.noveogroup.network.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfNewsAdapter extends RecyclerView.Adapter<ListOfNewsAdapter.ViewHolder> {

    private List<News> listOfTitles;
    private Context context;

    public ListOfNewsAdapter(List<News> listOfTitles, Context context) {
        this.listOfTitles = listOfTitles;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = listOfTitles.get(position).getTitle();
        holder.title.setText(title);

        Glide.with(context)
                .load(listOfTitles.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image_yet)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return listOfTitles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title)
        protected TextView title;

        @BindView(R.id.news_image)
        protected ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
