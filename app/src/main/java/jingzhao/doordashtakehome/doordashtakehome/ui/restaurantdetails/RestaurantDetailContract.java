package jingzhao.doordashtakehome.doordashtakehome.ui.restaurantdetails;

/**
 * Created by jingzhao on 07/11/2017.
 */

public interface RestaurantDetailContract {
    interface View{
        //handle display
        void showLoading();
        void hideLoading();
        void showBackgroundBanner(String url);
        void showThumbnailImage(String url);
        void showRestaurantName(String name);
        void showRating(double rating);
        void showFavorIcon(boolean marked);
        void favButtonClick(android.view.View view);
    }

    interface Presenter{
        void markFavor();
        void loadRestaurantDetail();
    }
}
