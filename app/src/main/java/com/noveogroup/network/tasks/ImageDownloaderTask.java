package com.noveogroup.network.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.noveogroup.network.model.News;

import java.util.List;

public class ImageDownloaderTask extends AsyncTask<List<News>, Void, Void> {

    private static int sizeOfImage = 70;

    private RequestManager glide;

    public ImageDownloaderTask(Context context) {
        glide = Glide.with(context);
    }

    @Override
    protected final Void doInBackground(List<News>... news) {

        if (null != news[0] && news[0].size() != 0) {
            for (News currentNews : news[0]) {
                glide
                        .load(currentNews.getImageUrl())
                        .downloadOnly(sizeOfImage, sizeOfImage);
            }
        }

        return null;
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }
}
