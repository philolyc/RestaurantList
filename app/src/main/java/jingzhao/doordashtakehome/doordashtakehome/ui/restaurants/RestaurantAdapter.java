package jingzhao.doordashtakehome.doordashtakehome.ui.restaurants;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jingzhao.doordashtakehome.R;

import jingzhao.doordashtakehome.doordashtakehome.models.Restaurant;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final RestaurantListPresenter mPresenter;
    private Context mContext;
    public static int restPosition;

    public RestaurantAdapter(Context context, RestaurantListPresenter presenter){
        mContext = context;
        mPresenter = presenter;
    }

    private Context getContext(){
        return mContext;
    }


    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View restView = inflater.inflate(R.layout.item_restaurant, parent, false);
        ViewHolder viewHolder = new ViewHolder(restView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.ViewHolder holder, int position) {
        Restaurant rest = mPresenter.getRestaurant(position);

        TextView restNameTV = holder.restName;
        restNameTV.setText(rest.getName());

        TextView restCategoryTV = holder.restCategory;
        restCategoryTV.setText(rest.getDescription());

        TextView restStatus = holder.restStatus;
        restStatus.setText(rest.getStatus());

        if(rest.isLiked())
            holder.likedIMG.setImageResource(android.R.drawable.btn_star_big_on);
        else
            holder.likedIMG.setImageResource(android.R.drawable.btn_star);

        Glide.with(mContext).load(rest.getCoverImgUrl())
                            .placeholder(R.mipmap.ic_restaurant_placeholader)
                            .into(holder.thumbnailIMG);

    }

    @Override
    public int getItemCount() {
        return mPresenter.getRestaurantCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView restCategory;
        private TextView restName;
        private TextView restStatus;
        private ImageView thumbnailIMG, likedIMG;

        public ViewHolder(View itemView){
            super(itemView);

            restName = (TextView) itemView.findViewById(R.id.rest_name);
            restCategory = (TextView) itemView.findViewById(R.id.rest_category);
            restStatus = (TextView) itemView.findViewById(R.id.rest_status);
            thumbnailIMG = (ImageView) itemView.findViewById(R.id.thumbnail_imageview);
            likedIMG = (ImageView) itemView.findViewById(R.id.like_imageview);

            likedIMG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = v.getContext().getSharedPreferences("MyPreference", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sp.edit();
                    restPosition = getAdapterPosition();
                    Restaurant restaurant = mPresenter.getRestaurant(restPosition);
                    int restId = restaurant.getId();
                    if(!sp.getBoolean(restId+"", false)) {
                        likedIMG.setImageResource(android.R.drawable.btn_star_big_on);
                        spEditor.putBoolean(restId+"", true);
                        restaurant.setLiked(true);
                    }
                    else {
                        likedIMG.setImageResource(android.R.drawable.btn_star);
                        spEditor.putBoolean(restId+"", false);
                        restaurant.setLiked(false);
                    }

                    spEditor.commit();


                }
            });

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mPresenter.loadRestaurantDetail(getAdapterPosition());
                }


            });

        }
    }

}
