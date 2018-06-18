package com.example.jluukvg.graphqlwithdagger2.ui.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.fetcher.ApolloResponseFetchers;
import com.apollographql.apollo.rx2.Rx2Apollo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import api.SearchByCategoryQuery;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainInteractor implements MainContract.Interactor {

    private final ApolloClient apolloClient;
    private ApolloCall<SearchByCategoryQuery.Data> dataApolloCall;

    @Inject
    MainInteractor(ApolloClient apolloClient, Handler handler) {
        this.apolloClient = apolloClient;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getFeedFromApollo(String category, final MainContract.Callback callback) {

        final SearchByCategoryQuery searchByCategoryQuery = SearchByCategoryQuery.builder()
                .category(category)
                .build();

        // Without Handler
        dataApolloCall = apolloClient.query(searchByCategoryQuery)
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK);

        Rx2Apollo.from(dataApolloCall)
                .subscribeOn(Schedulers.io())
                .map((Function<Response<SearchByCategoryQuery.Data>,
                                        List<SearchByCategoryQuery.AsNodePlace>>) dataResponse -> {
                    Log.d("APOLLO", dataResponse.toString());

                    ArrayList<SearchByCategoryQuery.AsNodePlace> placesList = new ArrayList<>();
                    int placesListSize = Objects.requireNonNull(Objects.requireNonNull(dataResponse.data()).nodeQuery().entities()).size();

                    for (int i = 0; i < placesListSize; i++) {
                            SearchByCategoryQuery.AsNodePlace place = (SearchByCategoryQuery.AsNodePlace) Objects.requireNonNull(Objects.requireNonNull(dataResponse.data()).nodeQuery().entities()).get(i);
                            placesList.add(place);
                        }

                    return placesList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::getPlacesSuccess, Throwable::printStackTrace);
//                .subscribe(new Observer<List<SearchByCategoryQuery.AsNodePlace>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<SearchByCategoryQuery.AsNodePlace> asNodePlaces) {
//                        callback.getPlacesSuccess(asNodePlaces);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.getPlacesError("Empty RESPONSE");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });



//        dataApolloCall.enqueue(new ApolloCall.Callback<SearchByCategoryQuery.Data>() {
//            @Override
//            public void onResponse(@Nonnull Response<SearchByCategoryQuery.Data> response) {
//                if (response.data() != null) {
//                    Log.d("APOLLO", "onResponse: " + response.data());
//
//                    ArrayList<SearchByCategoryQuery.AsNodePlace> placesList = new ArrayList<>();
//                    int placesListSize = Objects.requireNonNull(Objects.requireNonNull(response.data()).nodeQuery().entities()).size();
//
//                    for (int i = 0; i < placesListSize; i++) {
//                        SearchByCategoryQuery.AsNodePlace place = (SearchByCategoryQuery.AsNodePlace) Objects.requireNonNull(Objects.requireNonNull(response.data()).nodeQuery().entities()).get(i);
//                        placesList.add(place);
//                    }
//                    callback.getPlacesSuccess(placesList);
//                } else {
//                    callback.getPlacesError("Empty RESPONSE");
//                }
//            }
//
//            @Override
//            public void onFailure(@Nonnull ApolloException e) {
//                callback.getPlacesError(e.getMessage());
//            }
//        });
    }

    @Override
    public void cancelCalls() {
        if (dataApolloCall != null) {
            dataApolloCall.cancel();
        }
    }
}
