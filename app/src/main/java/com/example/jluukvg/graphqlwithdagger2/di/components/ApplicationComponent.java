package com.example.jluukvg.graphqlwithdagger2.di.components;

import android.app.Application;

import com.example.jluukvg.graphqlwithdagger2.GraphApplication;
import com.example.jluukvg.graphqlwithdagger2.di.annotations.PerActivity;
import com.example.jluukvg.graphqlwithdagger2.di.modules.ActivitiesModule;
import com.example.jluukvg.graphqlwithdagger2.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        NetworkModule.class,
        ActivitiesModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(GraphApplication graphApplication);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
