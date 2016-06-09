package com.bateeqshop.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = BateeqShopContract.SlideShowDataEntry.TABLE_NAME)
public class SlideShow extends Model
{
    @Column(name = BateeqShopContract.SlideShowDataEntry.COLUMN_CODE)
    private String code;
    @Column(name = BateeqShopContract.SlideShowDataEntry.COLUMN_IMAGE_URL)
    private String imageUrl;
    @Column(name = BateeqShopContract.SlideShowDataEntry.COLUMN_THUMBNAIL_IMAGE_URL)
    private String thumbImageUrl;
    @Column(name = BateeqShopContract.SlideShowDataEntry.COLUMN_TARGET_URL)
    private String targetUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public static List<SlideShow> getSlideShows()
    {
        return new Select().from(SlideShow.class).execute();
    }
}
