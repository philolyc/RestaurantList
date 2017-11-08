package jingzhao.doordashtakehome.doordashtakehome.ui.restaurants;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
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

public class RestaurantListPresenter implements RestaurantListContract.Presenter {
    private LinkedList<Restaurant> restList;
    private RestaurantListContract.View mView;
    private Retrofit retrofit;
    private SharedPreferences mSharedPref;
    private CompositeDisposable compositeDisposable;

    public RestaurantListPresenter(RestaurantListContract.View view, SharedPreferences sp){
        restList = new LinkedList<>();
        mView = view;
        mSharedPref = sp;
        //configure Retrofit using Retrofit Builder
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadRestaurantList();
    }

    @Override
    public void markFavor(boolean marked) {

    }

    @Override
    public void loadRestaurantList(){
        DoorDashAPI networkAPI = retrofit.create(DoorDashAPI.class);
        Observable<LinkedList<Restaurant>> restaurantObservable =  networkAPI.getRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        restaurantObservable.subscribeWith(new DisposableObserver<LinkedList<Restaurant>>() {
            @Override
            public void onNext(LinkedList<Restaurant> restaurants) {
                restList = new LinkedList<Restaurant>(restaurants);
                for (Restaurant restaurant: restaurants){
                    if (mSharedPref.getBoolean(restaurant.getId()+"", false)){
                        restaurant.setLiked(true);
                        restList.remove(restaurant);
                        restList.addFirst(restaurant);
                    }
                }

                mView.refreshView();
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

    @Override
    public void loadRestaurantDetail(int position) {
        mView.navigateToRestaurantDetail(restList.get(position).getName(),restList.get(position).getId());
    }

    @Override
    public int getRestaurantCount() {
        return restList == null? 0 : restList.size();
    }

    @Override
    public Restaurant getRestaurant(int position) {
        return restList.get(position);
    }

    protected void onDestroy() {
        //dispose subscriptions
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

}
