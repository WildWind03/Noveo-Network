package com.noveogroup.network.retrofit;

import com.noveogroup.network.model.News;

import java.util.List;

import retrofit2.http.POST;
import rx.Observable;


public interface NewsLoaderService {

    @POST("/news/getAll")
    Observable<List<News>> loadAllNews();
}