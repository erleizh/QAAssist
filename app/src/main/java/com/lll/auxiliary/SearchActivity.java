package com.lll.auxiliary;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private WebViewPagerAdapter mPagerAdapter;
    private android.support.design.widget.TabLayout mTabLayout;
    private ArrayList<String> mUrls;
    private ArrayList<String> mTitles;

    public static void start(Context context, ArrayList<String> urls, ArrayList<String> titles) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putStringArrayListExtra("urls", urls);
        intent.putStringArrayListExtra("titles", titles);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent, ActivityOptions.makeBasic().toBundle());
            } else {
                context.startActivity(intent);
            }
        } else {
            context.startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        findViewById(R.id.ivClose).setOnClickListener(v -> finish());

        init(getIntent());

//        new Handler().postDelayed(this::finish, 7000);
    }

    private void init(Intent intent) {
        if (initData(intent)) {
            initPager();
            initTabLayoutIndicator();
        } else {
            Toast.makeText(this, R.string.the_url_is_empty, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean initData(Intent intent) {
        if (intent == null) return false;
        mUrls = intent.getStringArrayListExtra("urls");
        mTitles = intent.getStringArrayListExtra("titles");
        return !(mUrls == null || mUrls.size() == 0);
    }

    private void initTabLayoutIndicator() {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @SuppressWarnings("unchecked")
    private void initPager() {
        mPagerAdapter = new WebViewPagerAdapter(mUrls, mTitles);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(mUrls.size());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(WebView webView) {
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setGeolocationEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setTextZoom(70);
        webView.setWebViewClient(mWebViewClient);
        webView.setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        if (mPagerAdapter == null) {
            super.onBackPressed();
        } else {
            WebView currentWebView = mPagerAdapter.getCurrentWebView();
            if (currentWebView != null && currentWebView.canGoBack()) {
                currentWebView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    private class WebViewPagerAdapter extends PagerAdapter {

        private final ArrayList<String> mUrls;
        private final ArrayList<String> mTitles;

        WebViewPagerAdapter(ArrayList<String> urls, ArrayList<String> titles) {
            mUrls = urls;
            mTitles = titles;
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            WebView webView = new WebView(container.getContext());
            webView.setTag(position);
            initWebView(webView);
            webView.loadUrl(mUrls.get(position));
            container.addView(webView);
            return webView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        WebView getCurrentWebView() {
            return mViewPager.findViewWithTag(mViewPager.getCurrentItem());
        }
    }
}
