package com.bateeqshop.fragments;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bateeqshop.MainActivity;
import com.bateeqshop.MainNavigator;
import com.bateeqshop.R;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.model.SlideShow;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private Context mContext;
    private View mView;

    private SliderLayout m_slider;

    private ImageView m_img_slide;
    private ImageView m_img_01;
    private ImageView m_img_02;
    private ImageView m_img_03;
    private ImageView m_img_04;
    private ProgressBar m_progress_slide;
    private ProgressBar m_progress_01;
    private ProgressBar m_progress_02;
    private ProgressBar m_progress_03;
    private ProgressBar m_progress_04;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
//        m_img_slide = (ImageView) view.findViewById(R.id.img_slide_show);
        m_img_01 = (ImageView) view.findViewById(R.id.img_01);
        m_img_02 = (ImageView) view.findViewById(R.id.img_02);
        m_img_03 = (ImageView) view.findViewById(R.id.img_03);
        m_img_04 = (ImageView) view.findViewById(R.id.img_04);
//        m_progress_slide = (ProgressBar) view.findViewById(R.id.progress_bar_slide_show);
        m_progress_01 = (ProgressBar) view.findViewById(R.id.progress_bar_01);
        m_progress_02 = (ProgressBar) view.findViewById(R.id.progress_bar_02);
        m_progress_03 = (ProgressBar) view.findViewById(R.id.progress_bar_03);
        m_progress_04 = (ProgressBar) view.findViewById(R.id.progress_bar_04);
//        hideImageSlide();
//        hideImage01();
//        hideImage02();
//        hideImage03();
//        hideImage04();

        final String slideImageUrl01 = "http://mobworld.co.in/BestWallpaper/3D/image1.jpg";
        String slideImageUrl02 = "http://11.11.1.42:36/Uploads/Slideshow/Mobile/e600c7a0-8ee9-4bd2-b6fb-565a945b88a1.png";
        String internetUrl = "http://i.imgur.com/DvpvklR.png";
//        Picasso.with(mContext)
//                .load(slideImageUrl01)
//                .error(R.mipmap.ic_launcher)
//                .into(m_img_slide, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        showImageSlide();
//                        Log.i("PICASSO_LOG_SUCCESS", "img : " + slideImageUrl01);
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.i("PICASSO_LOG_FAILED", "img : " + slideImageUrl01);
//                    }
//                });
//        Picasso.with(mContext)
//                .load(slideImageUrl01)
//                .error(R.mipmap.ic_launcher)
//                .into(m_img_01, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        showImage01();
//                        Log.i("PICASSO_LOG_SUCCESS", "img : " + slideImageUrl01);
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.i("PICASSO_LOG_FAILED", "img : " + slideImageUrl01);
//                    }
//                });
//        Picasso.with(mContext)
//                .load(slideImageUrl01)
//                .error(R.mipmap.ic_launcher)
//                .into(m_img_02, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        showImage02();
//                        Log.i("PICASSO_LOG_SUCCESS", "img : " + slideImageUrl01);
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.i("PICASSO_LOG_FAILED", "img : " + slideImageUrl01);
//                    }
//                });
//        Picasso.with(mContext)
//                .load(slideImageUrl01)
//                .error(R.mipmap.ic_launcher)
//                .into(m_img_03, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        showImage03();
//                        Log.i("PICASSO_LOG_SUCCESS", "img : " + slideImageUrl01);
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.i("PICASSO_LOG_FAILED", "img : " + slideImageUrl01);
//                    }
//                });
//        Picasso.with(mContext)
//                .load(slideImageUrl01)
//                .error(R.mipmap.ic_launcher)
//                .into(m_img_04, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        showImage04();
//                        Log.i("PICASSO_LOG_SUCCESS", "img : " + slideImageUrl01);
//                    }
//
//                    @Override
//                    public void onError() {
//                        Log.i("PICASSO_LOG_FAILED", "img : " + slideImageUrl01);
//                    }
//                });
        initSlider();
    }

    @Override
    public void onStart() {
        super.onStart();
        initEvent();
    }

    @Override
    public void syncNavigationViewSelection() {

    }

    @Override
    public ViewGroup getContentLayout() {
        return null;
    }

    @Override
    public void loadContent() {

    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        m_slider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        showToast(slider.getBundle().get("extra") + "");
//        MainNavigator.showCatalogueFragment(mContext, "Kepala");
//        MainNavigator.showCatalogueFragment(mContext, "","");

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initSlider()
    {
        m_slider = (SliderLayout)mView.findViewById(R.id.slider);
        List<SlideShow> slideShows = SlideShow.getSlideShows();

        HashMap<String,String> url_maps = new HashMap<>();
        for (SlideShow slideShowItem : slideShows)
        {
            url_maps.put(slideShowItem.getCode(), ApiConfig.IMAGE_BASE_URL + slideShowItem.getImageUrl());
        }
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");


        for(String name : url_maps.keySet()){
//            TextSliderView textSliderView = new TextSliderView(mContext);
//            // initialize a SliderLayout
//
//            textSliderView
//                    .description("")
//                    .image(url_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(this);
//
//            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
//
//            m_slider.addSlider(textSliderView);
            DefaultSliderView sliderView = new DefaultSliderView(mContext);
            sliderView.image(url_maps.get(name))
//            sliderView.image("https://dk85f78xh5nsf.cloudfront.net/attachments/store/d2dd5c574a588f9ce29bd9c26cfee332fc47a4067ef037846c522c924595/jif3cover.jpg")
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", name);
            m_slider.addSlider(sliderView);
        }
        m_slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        m_slider.setCustomAnimation(new DescriptionAnimation());
        m_slider.addOnPageChangeListener(this);

        //AutoCycle additional code to make ImageSlider work inside fragment
        m_slider.stopAutoCycle();
//        m_slider.setCurrentPosition(0, true);
        // play from first image, when all images loaded
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                m_slider.startAutoCycle();
            }
        }, 4000);
    }

    private void showImageSlide()
    {
        m_img_slide.setVisibility(View.VISIBLE);
        m_progress_slide.setVisibility(View.GONE);
    }

    private void hideImageSlide()
    {
        m_img_slide.setVisibility(View.INVISIBLE);
        m_progress_slide.setVisibility(View.VISIBLE);
    }

    private void showImage01()
    {
        m_img_01.setVisibility(View.VISIBLE);
        m_progress_01.setVisibility(View.GONE);
    }

    private void hideImage01()
    {
        m_img_01.setVisibility(View.GONE);
        m_progress_01.setVisibility(View.VISIBLE);
    }

    private void showImage02()
    {
        m_img_02.setVisibility(View.VISIBLE);
        m_progress_02.setVisibility(View.GONE);
    }

    private void hideImage02()
    {
        m_img_02.setVisibility(View.GONE);
        m_progress_02.setVisibility(View.VISIBLE);
    }

    private void showImage03()
    {
        m_img_03.setVisibility(View.VISIBLE);
        m_progress_03.setVisibility(View.GONE);
    }

    private void hideImage03()
    {
        m_img_03.setVisibility(View.GONE);
        m_progress_03.setVisibility(View.VISIBLE);
    }

    private void showImage04()
    {
        m_img_04.setVisibility(View.VISIBLE);
        m_progress_04.setVisibility(View.GONE);
    }

    private void hideImage04()
    {
        m_img_04.setVisibility(View.GONE);
        m_progress_04.setVisibility(View.VISIBLE);
    }

    private void initEvent()
    {
        m_img_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNavigator.showCatalogueFragment(mContext, "men","");
            }
        });
        m_img_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNavigator.showCatalogueFragment(mContext, "women","");
            }
        });
        m_img_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNavigator.showCatalogueFragment(mContext, "new arrivals","");
            }
        });
        m_img_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNavigator.showCatalogueFragment(mContext, "sale","");
            }
        });
    }

    private void getScreenSize()
    {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
    }
}
