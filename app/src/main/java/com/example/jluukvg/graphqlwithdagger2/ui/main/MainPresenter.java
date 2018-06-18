package com.example.jluukvg.graphqlwithdagger2.ui.main;

import java.util.List;

import javax.inject.Inject;

import api.SearchByCategoryQuery;

public class MainPresenter implements MainContract.Presenter, MainContract.Callback {

    private MainContract.View view;
    private final MainInteractor interactor;

    @Inject
    MainPresenter(MainContract.View view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void getResults(String category) {
        view.showLoading();
        interactor.getFeedFromApollo(category, this);
    }

    @Override
    public void detachView() {
        interactor.cancelCalls();
        this.view = null;
    }

    @Override
    public void getPlacesSuccess(List<SearchByCategoryQuery.AsNodePlace> places) {
        view.hideLoading();
        view.showResult(places);
    }

    @Override
    public void getPlacesError(String error) {
        view.hideLoading();
        view.showError(error);
    }
}
