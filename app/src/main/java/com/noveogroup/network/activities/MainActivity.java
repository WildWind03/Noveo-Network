package com.noveogroup.network.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.noveogroup.network.R;
import com.noveogroup.network.adapter.ListOfNewsAdapter;
import com.noveogroup.network.model.News;
import com.noveogroup.network.tasks.ImageDownloaderTask;
import com.noveogroup.network.tasks.LoadNewsTask;
import com.noveogroup.network.tasks.OnNewsLoaded;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.list_of_news)
    protected RecyclerView recyclerView;

    private LoadNewsTask loadNewsTask;
    private ImageDownloaderTask imageDownloaderTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.news_downloading_str));
        progressDialog.setCancelable(false);
        progressDialog.show();

        imageDownloaderTask = new ImageDownloaderTask(this) {
            @Override
            protected void onCancelled() {
                super.onCancelled();

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        };

        loadNewsTask = new LoadNewsTask(new OnNewsLoaded() {
            @Override
            public void onTaskComplete(List<News> news) {
                if (null != news) {
                    ListOfNewsAdapter listOfNewsAdapter = new ListOfNewsAdapter(news, MainActivity.this);
                    recyclerView.setAdapter(listOfNewsAdapter);
                    imageDownloaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, news);
                } else {
                    Toast.makeText(MainActivity.this, getText(R.string.data_can_not_be_download), Toast.LENGTH_SHORT).show();
                }

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, MainActivity.this) {
            @Override
            protected void onCancelled() {
                super.onCancelled();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        };

        loadNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (loadNewsTask != null) {
            loadNewsTask.cancel(true);
        }

        if (imageDownloaderTask != null) {
            imageDownloaderTask.cancel(true);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
