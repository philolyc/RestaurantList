package jingzhao.doordashtakehome.doordashtakehome.ui.restaurants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jingzhao.doordashtakehome.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jingzhao.doordashtakehome.doordashtakehome.ui.restaurantdetails.RestaurantDetailActivity;

public class RestaurantListActivity extends AppCompatActivity implements RestaurantListContract.View {

    RestaurantAdapter adapter;
    private RestaurantListPresenter presenter;

    @BindView(R.id.restList_toolbar) Toolbar mToolbar;
    @BindView(R.id.restList_recyclerView) RecyclerView rv;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Discover");
        SharedPreferences sp = getSharedPreferences("MyPreference", MODE_PRIVATE);
        presenter = new RestaurantListPresenter(this, sp);
        adapter = new RestaurantAdapter(this, presenter);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToRestaurantDetail(String restName, int restID) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("restId", restID);
        intent.putExtra("restName", restName);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
