package com.noveogroup.network.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.noveogroup.network.R;
import com.noveogroup.network.list.ListOfNewsAdapter;
import com.noveogroup.network.model.News;
import com.noveogroup.network.retrofit.NewsLoaderService;
import com.noveogroup.network.retrofit.NewsLoaderServiceSingleton;

import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private final static int sizeOfImage = 70;
    private final static Logger logger = Logger.getLogger(MainActivity.class.getName());

    private ProgressDialog progressDialog;

    @BindView(R.id.list_of_news)
    protected RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.news_downloading_str));
        progressDialog.setCancelable(false);
        progressDialog.show();

        NewsLoaderService newsLoaderService = NewsLoaderServiceSingleton.getInstance();
        Observable<List<News>> newsLoaderServiceObservable =
                newsLoaderService.loadAllNews();

        newsLoaderServiceObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<News>>() {
                    @Override
                    public void onCompleted() {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(List<News> newses) {
                        ListOfNewsAdapter listOfNewsAdapter = new ListOfNewsAdapter(newses, MainActivity.this);
                        recyclerView.setAdapter(listOfNewsAdapter);
                    }
                });

        newsLoaderServiceObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .flatMap(new Func1<List<News>, Observable<News>>() {
                    @Override
                    public Observable<News> call(List<News> newses) {
                        return Observable.from(newses);
                    }
                })
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {
                        logger.info("Pictures are download");
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info("Error: " + e.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, getString(R.string.data_can_not_be_download), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(News news) {
                        Glide
                                .with(MainActivity.this)
                                .load(news.getImageUrl())
                                .downloadOnly(sizeOfImage, sizeOfImage);
                    }
                });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
