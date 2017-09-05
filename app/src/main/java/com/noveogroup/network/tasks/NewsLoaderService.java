package com.noveogroup.network.tasks;

import com.noveogroup.network.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;


public interface NewsLoaderService {
    @POST("/news/getAll")
    Call<List<News>> loadAllNews();
}