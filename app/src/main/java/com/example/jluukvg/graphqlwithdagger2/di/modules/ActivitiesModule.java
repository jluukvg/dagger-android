package com.example.jluukvg.graphqlwithdagger2.di.modules;

import com.example.jluukvg.graphqlwithdagger2.ui.main.MainActivity;
import com.example.jluukvg.graphqlwithdagger2.ui.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

}
