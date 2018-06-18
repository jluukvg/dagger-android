package com.example.jluukvg.graphqlwithdagger2.ui.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jluukvg.graphqlwithdagger2.R;

import java.util.List;

import javax.inject.Inject;

import api.SearchByCategoryQuery;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View {

    @Inject
    Lazy<MainPresenter> presenter;

    private MainAdapter mainAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mainAdapter = new MainAdapter();
        recyclerView.setAdapter(mainAdapter);

        progressBar = findViewById(R.id.progressBar);

        presenter.get().getResults("Restaurants");

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        //MainActivity.this.runOnUiThread(() -> progressBar.setVisibility(View.GONE));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        //MainActivity.this.runOnUiThread(() -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResult(final List<SearchByCategoryQuery.AsNodePlace> places) {
        //MainActivity.this.runOnUiThread(() -> mainAdapter.addData(places));
        mainAdapter.addData(places);
    }

    @Override
    protected void onDestroy() {
        presenter.get().detachView();
        super.onDestroy();
    }
}
