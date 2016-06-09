package com.bateeqshop.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bateeqshop.R;
import com.bateeqshop.views.ZoomableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by yosua.petra on 5/30/2016.
 */
public class ImageSlideFragment extends Fragment {
    private static final String KEY_POSITION="position";
    private static final String KEY_PATH="path";
    private static final String KEY_HAS_NO_IMAGE ="has_no_image";
    private String path;
    private View mView;
    private Bitmap currentBitmap;
    private boolean hasNoImage = false;
    private int position;

    public Bitmap getCurrentBitmap(){
        return this.currentBitmap;
    }


    public static ImageSlideFragment newInstance(int position, boolean hasNoImage)
    {
        ImageSlideFragment frag  = new ImageSlideFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return frag;
    }

    public static ImageSlideFragment newInstance(int position, String imagePath, boolean hasNoImage)
    {
        ImageSlideFragment frag  = new ImageSlideFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        args.putString(KEY_PATH, imagePath);
        args.putBoolean(KEY_HAS_NO_IMAGE, hasNoImage);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            path = getArguments().getString(KEY_PATH);
            hasNoImage = getArguments().getBoolean(KEY_HAS_NO_IMAGE);
            position = getArguments().getInt(KEY_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View result=inflater.inflate(R.layout.swipe_fragment, container, false);
        mView = result;

        return(result);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            try {

                ZoomableImageView editor = (ZoomableImageView)  mView.findViewById(R.id.zoomableImageView);
                editor.setImageBitmap(bitmap);
                ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.imageSliderLoadingBar);
                _progressBar.setVisibility(View.INVISIBLE);
            }
            catch (NullPointerException ex)
            {}
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            try {
                ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.imageSliderLoadingBar);
                _progressBar.setVisibility(View.INVISIBLE);

                ZoomableImageView editor = (ZoomableImageView)  mView.findViewById(R.id.zoomableImageView);
                editor.setImageBitmap( BitmapFactory.decodeResource(getContext().getResources(),
                        R.drawable.noimage));
                editor.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            catch (NullPointerException ex)
            {}
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            try {

                ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.imageSliderLoadingBar);
                _progressBar.setVisibility(View.VISIBLE);
            }
            catch (NullPointerException ex)
            {}
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!hasNoImage)
            Picasso.with(getContext()).load(this.path).into(target);
        else
        {
            ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.imageSliderLoadingBar);
            _progressBar.setVisibility(View.INVISIBLE);
            ZoomableImageView editor = (ZoomableImageView)  mView.findViewById(R.id.zoomableImageView);
            editor.setImageResource(R.drawable.noimage);
        }
    }
}
