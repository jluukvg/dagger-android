package com.example.jluukvg.graphqlwithdagger2.ui.main;

import java.util.List;

import api.SearchByCategoryQuery;

public interface MainContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showError(String error);

        void showResult(List<SearchByCategoryQuery.AsNodePlace> places);
    }

    interface Presenter {

        void getResults(String category);

        void detachView();
    }

    interface Interactor {

        void getFeedFromApollo(String category, Callback callback);

        void cancelCalls();
    }

    interface Callback {

        void getPlacesSuccess(List<SearchByCategoryQuery.AsNodePlace> places);

        void getPlacesError(String error);
    }

}
