package jingzhao.doordashtakehome.doordashtakehome.ui.restaurantdetails;

import android.content.SharedPreferences;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jingzhao.doordashtakehome.doordashtakehome.api.DoorDashAPI;
import jingzhao.doordashtakehome.doordashtakehome.models.Restaurant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static jingzhao.doordashtakehome.doordashtakehome.utils.Constants.BASE_URL;

/**
 * Created by jingzhao on 07/11/2017.
 */

public class RestaurantDetailPresenter implements RestaurantDetailContract.Presenter {
    private RestaurantDetailContract.View mView;
    private Retrofit retrofit;
    private SharedPreferences mSharedPref;
    private int mRestaurantID;
    private boolean isFavored;
    private CompositeDisposable compositeDisposable;

    public RestaurantDetailPresenter(RestaurantDetailContract.View view, SharedPreferences sp, int restID){
        mView = view;
        mSharedPref = sp;
        mRestaurantID = restID;
        //configure Retrofit using Retrofit Builder
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadRestaurantDetail();
    }

    @Override
    public void markFavor() {
        isFavored = !isFavored;
        SharedPreferences.Editor spEditor = mSharedPref.edit();
        spEditor.putBoolean(mRestaurantID+"", isFavored);
        spEditor.commit();
        mView.showFavorIcon(isFavored);
    }


    @Override
    public void loadRestaurantDetail() {
        DoorDashAPI networkAPI = retrofit.create(DoorDashAPI.class);
        Observable<Restaurant> restaurantObservable =  networkAPI.getRestaurantByID(mRestaurantID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        restaurantObservable.subscribe(new Observer<Restaurant>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Restaurant restaurant) {
                mView.showBackgroundBanner(restaurant.getCoverImgUrl());
                mView.showThumbnailImage(restaurant.getHeaderImgUrl());
                mView.showRestaurantName(restaurant.getName());
                mView.showRating(restaurant.getAverageRating());
                isFavored = mSharedPref.getBoolean(restaurant.getId()+"", false);
                mView.showFavorIcon(isFavored);
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError", e.toString());
            }

            @Override
            public void onComplete() {
            }
        });
    }

    protected void onDestroy() {
        //dispose subscriptions
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

}
