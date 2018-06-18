package com.example.jluukvg.graphqlwithdagger2.di.modules;

import android.app.Application;
import android.support.annotation.NonNull;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.cache.http.HttpCachePolicy;
import com.apollographql.apollo.cache.http.ApolloHttpCache;
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore;
import com.example.jluukvg.graphqlwithdagger2.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetworkModule {


    @Provides
    @Singleton
    DiskLruHttpCacheStore provideCacheStore(Application application) {
        File file = new File(application.getCacheDir(), "apollo-cache");
        int size = 1024*1024;

        return new DiskLruHttpCacheStore(file, size);
    }

    @Provides
    @Singleton
    ApolloHttpCache provideApolloHttpCache(DiskLruHttpCacheStore cacheStore) {
        return new ApolloHttpCache(cacheStore);
    }


    @Provides
    @Singleton
    HttpLoggingInterceptor provideInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG
                        ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    ApolloClient provideApolloClient(@NonNull OkHttpClient okHttpClient, ApolloHttpCache cacheStore) {
        return ApolloClient.builder()
                .serverUrl("http://whereapp.getluxbox.com/graphql/")
                .httpCache(cacheStore)
                .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST.expireAfter(5, TimeUnit.MINUTES))
                .okHttpClient(okHttpClient)
                .build();
    }
}
