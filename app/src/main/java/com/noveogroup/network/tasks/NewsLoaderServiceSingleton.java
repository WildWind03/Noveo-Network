package com.noveogroup.network.tasks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsLoaderServiceSingleton {

    private static class SingletonHolder {
        private final static String DEFAULT_SITE = "http://androidtraining.noveogroup.com";
        public static NewsLoaderService instance;

        static {
            Retrofit builder = new Retrofit.Builder()
                    .baseUrl(DEFAULT_SITE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = builder.create(NewsLoaderService.class);
        }
    }

    public static NewsLoaderService getInstance() {
        return SingletonHolder.instance;
    }

    private NewsLoaderServiceSingleton() {

    }
}
