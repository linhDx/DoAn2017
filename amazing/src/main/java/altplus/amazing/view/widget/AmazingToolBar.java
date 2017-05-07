package altplus.amazing.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import altplus.amazing.R;

/**
 * Created by Nguyen Tien Hoang on 04/01/2016.
 */
public class AmazingToolBar extends RelativeLayout {
    private View layoutRoot;
    public ImageButton btnLeft, btnRight, btnFavorite;
    public TextView tvLeft, tvTitle, tvRight;

    private int mResourceBackground, mResourceBackgroundColor;
    private int mIconLeftButton, mIconRightButton;
    private boolean mIsFavoriteButtonVisible, mIsFavorite;
    private String mLeftText, mRightText, mTitleText;
    private OnClickToolBarListener mOnClickToolBarListener;

    public AmazingToolBar(Context context) {
        super(context);
    }

    public AmazingToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setLayout();
        initCompoundView();
        setData();
        setListener();
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AmazingToolBar, 0, 0);

        try {
            mResourceBackground = a.getResourceId(R.styleable.AmazingToolBar_background_res, 0);
            mResourceBackgroundColor = a.getColor(R.styleable.AmazingToolBar_background_color, 0);

            mIsFavoriteButtonVisible = a.getBoolean(R.styleable.AmazingToolBar_favorite_button, false);

            mIconLeftButton = a.getResourceId(R.styleable.AmazingToolBar_icon_left_button, 0);
            mIconRightButton = a.getResourceId(R.styleable.AmazingToolBar_icon_right_button, 0);

            mLeftText = a.getString(R.styleable.AmazingToolBar_text_left);
            mRightText = a.getString(R.styleable.AmazingToolBar_text_right);
            mTitleText = a.getString(R.styleable.AmazingToolBar_text_title);
        } catch (Exception e) {
            Log.e("AmazingToolBar", "Cannot init view");
        } finally {
            a.recycle();
        }
    }

    private void setLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_amazing_tool_bar, this, true);
    }

    private void initCompoundView() {
        layoutRoot = findViewById(R.id.layout_root);
        btnLeft = (ImageButton) findViewById(R.id.btn_left);
        btnRight = (ImageButton) findViewById(R.id.btn_right);
        btnFavorite = (ImageButton) findViewById(R.id.btn_favorite);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
    }

    private void setData() {
        if (mResourceBackground != 0) {
            layoutRoot.setBackgroundResource(mResourceBackground);
        } else {
            layoutRoot.setBackgroundColor(mResourceBackgroundColor);
        }

        setFavoriteButtonVisible(mIsFavoriteButtonVisible);
        mIsFavorite = false;

        setDataResource(btnLeft, mIconLeftButton);
        setDataResource(btnRight, mIconRightButton);
        setTextView(tvLeft, mLeftText);
        setTextView(tvTitle, mTitleText);
        setTextView(tvRight, mRightText);

        if (mIconLeftButton > 0) {
            btnLeft.setImageResource(mIconLeftButton);
            btnLeft.setVisibility(VISIBLE);
        } else {
            btnLeft.setVisibility(GONE);
        }
    }

    private void setDataResource(View view, int resId) {
        if (resId == 0) {
            view.setVisibility(GONE);
        } else {
            view.setVisibility(VISIBLE);
            if (view instanceof ImageButton) {
                ((ImageButton) view).setImageResource(resId);
            } else if (view instanceof TextView) {
                ((TextView) view).setText(resId);
            }
        }
    }

    private void setTextView(TextView tv, String s) {
        if (s == null) {
            tv.setVisibility(GONE);
        } else {
            tv.setVisibility(VISIBLE);
            tv.setText(s);
        }
    }

    private void setListener() {
        if (btnLeft.getVisibility() == VISIBLE) {
            btnLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickToolBarListener != null) {
                        mOnClickToolBarListener.onClickLeftButton();
                    }
                }
            });
        }

        if (btnRight.getVisibility() == VISIBLE) {
            btnRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickToolBarListener != null) {
                        mOnClickToolBarListener.onClickRightButton();
                    }
                }
            });
        }

        if (tvLeft.getVisibility() == VISIBLE) {
            tvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickToolBarListener != null) {
                        mOnClickToolBarListener.onClickLeftText();
                    }
                }
            });
        }

        if (tvRight.getVisibility() == VISIBLE) {
            tvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickToolBarListener != null) {
                        mOnClickToolBarListener.onClickRightText();
                    }
                }
            });
        }

//        if (btnFavorite.getVisibility() == VISIBLE) {
        btnFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickToolBarListener != null) {
                    mOnClickToolBarListener.onClickFavoriteButton();
                }
            }
        });
//        }
    }

    public void setOnClickToolBarListener(OnClickToolBarListener listener) {
        mOnClickToolBarListener = listener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public void setTitle(int resTitle) {
        tvTitle.setText(resTitle);
    }

    public void setTextRight(String s) {
        this.tvRight.setText(s);
    }

    public void setTextRight(int resString) {
        this.tvRight.setText(resString);
    }
    public  void setTvLeft (String tvLeft){
        this.tvLeft.setText(tvLeft);
    }
    public void setImgIconLeft (int src){
        this.btnLeft.setImageResource(src);
    }
    public void setFavoriteButtonVisible(boolean isVisible) {
        this.mIsFavoriteButtonVisible = isVisible;
        btnFavorite.setVisibility(mIsFavoriteButtonVisible ? VISIBLE : GONE);
    }

    public void setFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
        btnFavorite.setImageResource(mIsFavorite ? R.drawable.btn_favorite_full : R.drawable.btn_favorite);
    }

    public boolean toggleFavorite() {
        mIsFavorite = !mIsFavorite;
        btnFavorite.setImageResource(mIsFavorite ? R.drawable.btn_favorite_full : R.drawable.btn_favorite);
        return mIsFavorite;
    }

    public void setVisibilityTextRight(boolean isVisible) {
        tvRight.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public interface OnClickToolBarListener {
        void onClickLeftText();

        void onClickRightText();

        void onClickLeftButton();

        void onClickRightButton();

        void onClickFavoriteButton();
    }
}