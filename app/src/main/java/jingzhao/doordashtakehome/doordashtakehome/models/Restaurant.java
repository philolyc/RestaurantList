package jingzhao.doordashtakehome.doordashtakehome.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {
    @SerializedName("delivery_fee")
    @Expose
    private Integer deliveryFee;
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("average_rating")
    @Expose
    private Double averageRating;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cover_img_url")
    @Expose
    private String coverImgUrl;

    @SerializedName("header_img_url")
    @Expose
    private String headerImgUrl;

    @SerializedName("name")
    @Expose
    private String name;

    private boolean isLiked;


    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getHeaderImgUrl() {
        return headerImgUrl;
    }

    public void setHeaderImgUrl(String headerImgUrl) {
        this.headerImgUrl = headerImgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isLiked(){
        return isLiked;
    }
}