package jingzhao.doordashtakehome.doordashtakehome.ui.restaurantdetails;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jingzhao.doordashtakehome.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailActivity extends AppCompatActivity implements RestaurantDetailContract.View {

    private RestaurantDetailPresenter presenter;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;
    @BindView(R.id.background_imageView) ImageView backgroundIV;
    @BindView(R.id.thumbnail_imageView) ImageView thumbnailIV;
    @BindView(R.id.rest_name_textView) TextView restNameTV;
    @BindView(R.id.rest_rating_textView) TextView ratingTV;
    @BindView(R.id.imageButton) ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

        String restName = getIntent().getExtras().getString("restName");
        int restId = getIntent().getExtras().getInt("restId");

        SharedPreferences sp = getSharedPreferences("MyPreference", MODE_PRIVATE);
        presenter = new RestaurantDetailPresenter(this, sp, restId);

        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(restName);
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showBackgroundBanner(String url) {
        Glide.with(getApplicationContext()).load(url)
                .placeholder(R.mipmap.ic_restaurant_placeholader)
                .into(backgroundIV);
    }

    @Override
    public void showThumbnailImage(String url) {
        Glide.with(getApplicationContext()).load(url)
                .placeholder(R.mipmap.ic_restaurant_placeholader)
                .into(thumbnailIV);
    }

    @Override
    public void showRestaurantName(String name) {
        restNameTV.setText(name);
    }

    @Override
    public void showRating(double rating) {
        ratingTV.setText(rating + "/5.0");
    }

    @Override
    public void showFavorIcon(boolean marked) {
        imageButton.setImageResource(marked?android.R.drawable.btn_star_big_on:android.R.drawable.btn_star);
    }


    @Override
    public void favButtonClick(View v) {
        presenter.markFavor();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
