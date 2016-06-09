package com.bateeqshop;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bateeqshop.adapter.NavViewPagerAdapter;
import com.bateeqshop.config.MessageConfig;
import com.bateeqshop.model.ModelLoader;
import com.bateeqshop.model.ProductCategory;
import com.bateeqshop.model.Query;
import com.bateeqshop.model.SessionModel;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.UserProfileData;
import com.bateeqshop.model.WishList;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.BadgeStyle;

import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private static final int USER_PROFILE_DATA_LOADER_ID = 100;
    private static final int PRODUCT_CATEGORY_LOADER_ID = 101;
    private static final int SHOPPING_CART_LOADER_ID = 102;
    private static final int WISH_LIST_LOADER_ID = 103;

    private View mNavigationHeaderView;
    private FrameLayout fl_drawer_login;
    private FrameLayout fl_drawer_user_profile;
    private FrameLayout fl_drawer_home_dashboard;
    private Context mContext;

    private EditText m_edit_text_search;
    private ImageView m_img_logo;
    private MenuItem m_menu_item_shopping_bag;
    private MenuItem m_menu_item_wish_list;

    private TabLayout m_nav_tab_layout;
    private ViewPager m_nav_viewpager;
    private String[] mTabStringArray;

    private int mShoppingBagBadgeCount = 10;
    private int mWishListBadgeCount = 5;
    private BadgeStyle mBadgeStyle;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mContext = this;
        MainNavigator.navigateToLoadingActivity(mContext);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        m_edit_text_search = (EditText) findViewById(R.id.edit_text_search);
        m_img_logo = (ImageView) findViewById(R.id.img_logo);

        ImageView img_search = (ImageView) findViewById(R.id.img_search);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditTextSearch();
                hideLogo();
                m_edit_text_search.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                if (m_edit_text_search.hasFocus() && !m_edit_text_search.getText().toString().equals(""))
                {
                    if (m_edit_text_search.getText().toString().equals("dev")) {
                        MainNavigator.showDevFragment(mContext);
                    }
                    else
                    {
                        showCatalogueFragment(m_edit_text_search.getText().toString());
                    }
                }
            }
        });

        m_edit_text_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    showLogo();
                    hideEditTextSearch();
                }
            }
        });

        m_edit_text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE && !m_edit_text_search.getText().toString().equals("")){
                    showCatalogueFragment(m_edit_text_search.getText().toString());
                }
                return false;
            }
        });

        hideEditTextSearch();
        showLogo();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        m_nav_tab_layout = (TabLayout) findViewById(R.id.nav_tabs);
        m_nav_viewpager = (ViewPager) findViewById(R.id.nav_viewpager);

        fl_drawer_home_dashboard = (FrameLayout) findViewById(R.id.fl_home_dashboard);
        fl_drawer_login = (FrameLayout) findViewById(R.id.fl_login);
        fl_drawer_user_profile = (FrameLayout) findViewById(R.id.fl_user_profile);
        fl_drawer_home_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNavigator.showDashboardFragment(mContext);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        fl_drawer_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginNavigator.navigateToLoginActivity(mContext);
            }
        });
//        fl_drawer_user_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//            }
//        });
        ImageView img_logout = (ImageView) findViewById(R.id.img_logout);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        showDrawerLogin();
        hideDrawerUserProfile();
//        hideDrawerLogin();
//        showDrawerUserProfile();

        getSupportLoaderManager().initLoader(USER_PROFILE_DATA_LOADER_ID, null, mUserProfileDataLoader);
        getSupportLoaderManager().initLoader(PRODUCT_CATEGORY_LOADER_ID, null, mProductCategoryLoader);
        getSupportLoaderManager().initLoader(SHOPPING_CART_LOADER_ID, null, mShoppingCartLoader);
        getSupportLoaderManager().initLoader(WISH_LIST_LOADER_ID, null, mWishListLoader);

        MainNavigator.showDashboardFragment(this);
//        MainNavigator.showDevFragment(this);
//        Intent i = new Intent();
//        i.setAction("android.intent.action.VIEW");
//        i.setPackage("com.bateeqshop");
//        Log.d(getClass().getSimpleName(), i.toUri(Intent.URI_INTENT_SCHEME));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            MainNavigator.showDashboardFragment(mContext);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        m_menu_item_shopping_bag = menu.findItem(R.id.menu_item_shopping_bag);
        m_menu_item_wish_list = menu.findItem(R.id.menu_item_wish_list);
        mBadgeStyle = new BadgeStyle(BadgeStyle.Style.DEFAULT,
                R.layout.custom_menu_action_item_badge,
                ContextCompat.getColor(mContext, R.color.colorPrimaryDark),
                ContextCompat.getColor(mContext, R.color.colorPrimaryDark),
                ContextCompat.getColor(mContext, R.color.colorPrimary),
                72);
        updateShoppingCart();
        updateWishList();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.menu_item_shopping_bag)
        {
            MainNavigator.showShoppingCartFragment(mContext);
        }
//        else if (id == R.id.log_out) {
////            LoginNavigator.navigateToLoginActivity(this);
//            UserProfileData userProfileData = (UserProfileData) Query.getSingle(UserProfileData.class);
//            if (userProfileData != null)
//            {
//                logout();
//            }
//            else
//            {
//                showToast("Please login first");
//            }
//
//            return true;
//        }
//        else if (id == R.id.user_profile)
//        {
//            UserProfileData userProfileData = (UserProfileData) Query.getSingle(UserProfileData.class);
//            if (userProfileData != null)
//            {
////                showToast("First name : " + userProfileData.getFirstName());
//                showToast(Session.userInfo());
//            }
//            else
//            {
//                showToast("EMPTY");
//            }
//        }
//        else if (id == R.id.session)
//        {
//            SessionModel session = (SessionModel) Query.getSingle(SessionModel.class);
//            if (session != null)
//            {
//                showToast("Access token : " + session.getAccessToken());
//            }
//            else
//            {
//                showToast("EMPTY");
//            }
//        }
//        else if (id == R.id.deleteUserProfile)
//        {
//            UserProfileData userProfileData = (UserProfileData) Query.getSingle(UserProfileData.class);
//            if (userProfileData != null)
//            {
//                UserProfileData.delete(UserProfileData.class, userProfileData.getId());
//            }
//            else
//            {
//                showToast("EMPTY");
//            }
//        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void showCatalogueFragment(String queryKey)
    {
//        showToast("Catalogue Fragment : " + queryKey);
        MainNavigator.showCatalogueFragment(mContext, queryKey,"");
    }

    private void updateNavigationDrawerProfile()
    {
        TextView drawerUserName = (TextView) findViewById(R.id.text_view_user_name);
        String userName = "Hi, " + Session.firstName + " " + Session.lastName;
        drawerUserName.setText(userName);
    }

    private void updateNavigationDrawerMenuItem()
    {
        m_nav_viewpager.setAdapter(new NavViewPagerAdapter(getSupportFragmentManager()));
        m_nav_tab_layout.setupWithViewPager(m_nav_viewpager);
    }

    private void updateShoppingCart()
    {
        ActionItemBadge.update(this, m_menu_item_shopping_bag, ContextCompat.getDrawable(mContext, R.drawable.ic_action_shopping_bag), mBadgeStyle, Session.getShoppingCartOrderDetailNumber());
    }

    private void updateWishList()
    {
        ActionItemBadge.update(this, m_menu_item_wish_list, ContextCompat.getDrawable(mContext, R.drawable.ic_action_wish_list), mBadgeStyle, Session.getWishListNumber());
    }

    private void logout()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Log Out");
        alert.setMessage(MessageConfig.LOG_OUT_MESSAGE);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Query.deleteAll(UserProfileData.class);
                Query.deleteAll(SessionModel.class);
                Query.deleteAll(ShoppingCartOrderDetail.class);
                Query.deleteAll(WishList.class);
                Query.truncate(UserProfileData.class);
                Query.truncate(SessionModel.class);
                Query.truncate(ShoppingCartOrderDetail.class);
                Query.truncate(WishList.class);
                LoginManager.getInstance().logOut();
                Session.logOut();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
            }
        });

        alert.show();
    }

    public int getShoppingBagBadgeCount() {
        return mShoppingBagBadgeCount;
    }

    public int getWishListBadgeCount() {
        return mWishListBadgeCount;
    }

    private void showDrawerLogin()
    {
        fl_drawer_login.setVisibility(View.VISIBLE);
    }

    private void hideDrawerLogin()
    {
        fl_drawer_login.setVisibility(View.GONE);
    }

    private void showDrawerUserProfile()
    {
        fl_drawer_user_profile.setVisibility(View.VISIBLE);
    }

    private void hideDrawerUserProfile()
    {
        fl_drawer_user_profile.setVisibility(View.GONE);
    }

    private void showEditTextSearch()
    {
        m_edit_text_search.setVisibility(View.VISIBLE);
    }

    private void hideEditTextSearch()
    {
        m_edit_text_search.setVisibility(View.GONE);
    }

    private void showLogo()
    {
        m_img_logo.setVisibility(View.VISIBLE);
    }

    private void hideLogo()
    {
        m_img_logo.setVisibility(View.GONE);
    }

    private void devTestDBLocal(List<UserProfileData> data)
    {
        int userCount = Query.count(UserProfileData.class);
        int shopcartOrderDetailCount = Query.count(ShoppingCartOrderDetail.class);
        int wishListCount = Query.count(WishList.class);
        Log.i("HAY", "HAY");
    }

    /** LOADER */
    private final LoaderManager.LoaderCallbacks<List<UserProfileData>> mUserProfileDataLoader = new LoaderManager.LoaderCallbacks<List<UserProfileData>>() {
        @Override
        public Loader<List<UserProfileData>> onCreateLoader(int id, Bundle args) {
            return new ModelLoader<>(mContext, UserProfileData.class,  true);
        }
        @Override
        public void onLoadFinished(Loader<List<UserProfileData>> loader, List<UserProfileData> data) {
            if (data.size() > 0) {
                devTestDBLocal(data);

                Session.setUserProfile(data.get(0));
                updateNavigationDrawerProfile();
                hideDrawerLogin();
                showDrawerUserProfile();
            }
            else
            {
                showDrawerLogin();
                hideDrawerUserProfile();
            }
        }
        @Override
        public void onLoaderReset(Loader<List<UserProfileData>> loader) {}
    };

    private final LoaderManager.LoaderCallbacks<List<ProductCategory>> mProductCategoryLoader = new LoaderManager.LoaderCallbacks<List<ProductCategory>>() {
        @Override
        public Loader<List<ProductCategory>> onCreateLoader(int id, Bundle args) {
            return new ModelLoader<>(mContext, ProductCategory.class, true);
        }

        @Override
        public void onLoadFinished(Loader<List<ProductCategory>> loader, List<ProductCategory> data) {
            if (data.size() > 0 )
            {
                updateNavigationDrawerMenuItem();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<ProductCategory>> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<List<ShoppingCartOrderDetail>> mShoppingCartLoader = new LoaderManager.LoaderCallbacks<List<ShoppingCartOrderDetail>>() {
        @Override
        public Loader<List<ShoppingCartOrderDetail>> onCreateLoader(int id, Bundle args) {
            return new ModelLoader<>(mContext, ShoppingCartOrderDetail.class, true);
        }

        @Override
        public void onLoadFinished(Loader<List<ShoppingCartOrderDetail>> loader, List<ShoppingCartOrderDetail> data) {
            if (data.size() > 0)
            {
                ShoppingCart shoppingCart = new ShoppingCart(data.get(0));
                shoppingCart.setShoppingCartOrderDetails(data);
                Session.setShoppingCart(shoppingCart);
            }
            updateShoppingCart();
        }

        @Override
        public void onLoaderReset(Loader<List<ShoppingCartOrderDetail>> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<List<WishList>> mWishListLoader = new LoaderManager.LoaderCallbacks<List<WishList>>() {
        @Override
        public Loader<List<WishList>> onCreateLoader(int id, Bundle args) {
            return new ModelLoader<>(mContext, WishList.class, true);
        }

        @Override
        public void onLoadFinished(Loader<List<WishList>> loader, List<WishList> data) {
            if (data.size() > 0)
            {
                Session.setWishlists(data);
            }
            updateWishList();
        }

        @Override
        public void onLoaderReset(Loader<List<WishList>> loader) {

        }
    };
}
