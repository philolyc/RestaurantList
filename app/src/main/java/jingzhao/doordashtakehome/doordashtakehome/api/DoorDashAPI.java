package jingzhao.doordashtakehome.doordashtakehome.api;

/**
 * Created by jingzhao on 07/11/2017.
 */

import java.util.LinkedList;

import io.reactivex.Observable;
import jingzhao.doordashtakehome.doordashtakehome.models.Restaurant;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DoorDashAPI {
    @GET("v2/restaurant/?lat=37.422740&lng=-122.139956")
    Observable<LinkedList<Restaurant>> getRestaurants() ;

    @GET("v2/restaurant/{id}/")
    Observable<Restaurant> getRestaurantByID(@Path("id") int id) ;
}
