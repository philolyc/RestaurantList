package jingzhao.doordashtakehome.doordashtakehome.ui.restaurants;

import jingzhao.doordashtakehome.doordashtakehome.models.Restaurant;

/**
 * Created by jingzhao on 07/11/2017.
 */

public interface RestaurantListContract {
    interface View{
        //handle display
        void showLoading();
        void hideLoading();
        void refreshView();
        void navigateToRestaurantDetail(String restName, int restID);
    }

    interface Presenter{
        void markFavor(boolean marked);
        void loadRestaurantList();
        void loadRestaurantDetail(int position);
        int getRestaurantCount();
        Restaurant getRestaurant(int position);
    }
}
