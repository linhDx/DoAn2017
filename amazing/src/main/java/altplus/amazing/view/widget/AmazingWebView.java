package altplus.amazing.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import altplus.amazing.R;
import altplus.amazing.util.LogUtil;

/**
 * Created by Nguyen Tien Hoang on 03/02/2016.
 */
public class AmazingWebView extends RelativeLayout {
    private WebView webView;
    private View progressBar;
    private View mCustomView;
    public boolean isFirstLoadUrl;
    private FrameLayout mCustomViewContainer;

    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    public AmazingWebView(Context context) {
        super(context);
        setLayout();
        initCompoundView();
        initListener();
    }

    public AmazingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout();
        initCompoundView();
        initListener();
    }

    private void setLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_amazing_web_view, this, true);
    }


    private void initCompoundView() {
        webView = (WebView) findViewById(R.id.web_view_real);
        progressBar = findViewById(R.id.progress_bar_waiting);
        mCustomViewContainer = (FrameLayout) findViewById(R.id.fullscreen_custom_content);

        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        webView.setLayerType(Build.VERSION.SDK_INT >= 19 ? View.LAYER_TYPE_HARDWARE : View.LAYER_TYPE_SOFTWARE, null);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // Configure the web view
        WebSettings s = webView.getSettings();
        s.setBuiltInZoomControls(true);
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        isFirstLoadUrl = false;
    }

    private void initListener() {
        mCustomViewContainer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if ((mCustomView == null) && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return onKey(v, keyCode, event);
            }
        });
    }

    /**
     * Loads the given URL.
     *
     * @param url the URL of the resource to load
     */
    public void loadUrl(String url) {
        webView.loadUrl(url);
        LogUtil.i("Load url: " + url);
    }


    private class MyWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            webView.setVisibility(GONE);

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
        }


        @Override
        public void onHideCustomView() {

            if (mCustomView == null)
                return;

            // Hide the custom view.
            mCustomView.setVisibility(GONE);

            // Remove the custom view from its container.
            mCustomViewContainer.removeView(mCustomView);
            mCustomView = null;
            mCustomViewContainer.setVisibility(GONE);
            mCustomViewCallback.onCustomViewHidden();

            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public Bitmap getDefaultVideoPoster() {
            if (mDefaultVideoPoster == null) {
                mDefaultVideoPoster = BitmapFactory.decodeResource(getResources(), R.drawable.default_video_poster);
            }
            return mDefaultVideoPoster;
        }

        @Override
        public View getVideoLoadingProgressView() {
            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                mVideoProgressView = inflater.inflate(R.layout.video_loading_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress > 50) {
                progressBar.setVisibility(GONE);
                isFirstLoadUrl = true;
            }
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.i("shouldOverrideUrlLoading: " + url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(GONE);
        }
    }

    public WebView getWebView() {
        return webView;
    }
}