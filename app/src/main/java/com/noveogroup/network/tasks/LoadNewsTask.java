package com.noveogroup.network.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.noveogroup.network.R;
import com.noveogroup.network.model.News;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoadNewsTask extends AsyncTask<Void, Void, List<News>> {

    private final OnNewsLoaded onNewsLoaded;
    private final Context context;

    private final static Logger logger = Logger.getLogger(LoadNewsTask.class.getName());

    public LoadNewsTask(OnNewsLoaded onNewsLoaded, Context context) {
        this.onNewsLoaded = onNewsLoaded;
        this.context = context;
    }

    @Override
    protected List<News> doInBackground(Void... strings) {

        NewsLoaderService newsLoaderService = NewsLoaderServiceSingleton.getInstance();

        List<News> news = null;

        try {
            news = newsLoaderService.loadAllNews().execute().body();
        } catch (IOException e) {
            logger.info(context.getString(R.string.data_can_not_be_download));
        }

        return news;
    }

    @Override
    protected void onPostExecute(List<News> news) {
        super.onPostExecute(news);

        onNewsLoaded.onTaskComplete(news);
    }
}
