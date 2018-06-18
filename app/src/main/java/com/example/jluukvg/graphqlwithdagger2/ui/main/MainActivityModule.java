package com.example.jluukvg.graphqlwithdagger2.ui.main;

import android.os.Handler;
import android.os.Looper;

import com.apollographql.apollo.ApolloClient;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainActivityModule {

    @Provides
    static Handler provideMainHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    static MainInteractor provideMainInteractor(ApolloClient apolloClient, Handler handler) {
        return new MainInteractor(apolloClient, handler);
    }

    @Provides
    static MainPresenter provideMainPresenter(MainContract.View view, MainInteractor interactor) {
        return new MainPresenter(view, interactor);
    }

    @Binds
    abstract MainContract.View provideMainView(MainActivity mainActivity);


}
