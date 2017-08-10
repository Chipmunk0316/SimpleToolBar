package com.kirito.simpletoolbar;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Chipmunk on 2017/8/3.
 */

public class SimpleToolBar extends Toolbar {

    private static final String TAG = "mToolBar";

    public static final int MIDDLE = -1;
    public static final int LEFT = -2;
    public static final int RIGHT = -3;

    private LayoutParams Titleparams;//主标题或者返回按钮旁标题尺寸参数
    private LayoutParams NavigationIconparams;//返回按钮尺寸参数
    private TextView title;
    private ImageView NavigationIcon;
    private TextView NavigationTitle;
    private boolean hasTitle = true;
    private String titleContent;
    private float titleTextSize = 20;//主标题字体大小
    private float NavigationTitleTextSize = 15;//返回按钮旁的标题字体大小
    private int titleTextColor = Color.BLACK;
    private int titlePosition = MIDDLE;

    private Context mContext;

    public SimpleToolBar(Context context) {
        super(context, null);
        this.mContext = context;
    }

    public SimpleToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.titleContent = getApplicationName();
        setToolBarTitle(this.titleContent,MIDDLE);
        setContentInsetsAbsolute(0,0);
    }

    public SimpleToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    /**
     * 获取applicationName
     */
    private String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = mContext.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    public void setToolBarTitle(boolean hasTitle){
        this.hasTitle = hasTitle;
    }

    public void setToolBarTitle(String titleContent){
        setToolBarTitle(titleContent,MIDDLE);
    }

    public void setToolBarTitle(int titlePosition){
        setToolBarTitle(getApplicationName(),titlePosition);
    }

    public void setToolBarTitle(String titleContent,int titlePosition){

        if(this.hasTitle){
            title = new TextView(getContext());
            title.setMaxLines(1);
            setTitleTextSize(this.titleTextSize);
            setTitleTextColor(this.titleTextColor);

            this.titlePosition = titlePosition;
            this.titleContent = titleContent;
            title.setText(titleContent);

            Titleparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setTitlePositon(titlePosition);
            addView(title, Titleparams);
        }

    }

    /**
     * 设置标题的位置
     * @param titlePostion 标题位置
     */
    private void setTitlePositon(int titlePostion) {
        if (titlePostion == SimpleToolBar.MIDDLE) {
            Titleparams.gravity = Gravity.CENTER;
        } else if (titlePostion == SimpleToolBar.LEFT) {
            Titleparams.gravity = Gravity.START;
        } else if (titlePostion == SimpleToolBar.RIGHT) {
            Titleparams.gravity = Gravity.END;
        }
    }

    /**
     * 获取标题内容
     * @return 标题内容
     */
    public String getTitleContent() {
        return this.titleContent;
    }

    /**
     * 设置标题尺寸,默认20sp
     * @param titleTextSize
     */
    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
        title.setTextSize(titleTextSize);
    }

    /**
     * 设置标题颜色,默认是黑色
     *
     * @param titleTextColor
     */
    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        title.setTextColor(titleTextColor);
    }

    public void setNavigationTitle(int NavigationIconRes){
        setNavigationTitle(NavigationIconRes,"Back",this.NavigationTitleTextSize);
    }

    public void setNavigationTitle(String NavigationTitleContent){
        setNavigationTitle(R.drawable.back,NavigationTitleContent,this.NavigationTitleTextSize);
    }

    public void setNavigationTitle(float NavigationTitleTextSize){
        setNavigationTitle(R.drawable.back,"Back",NavigationTitleTextSize);
    }

    public void setNavigationTitle(String NavigationTitleContent,float NavigationTitleTextSize){
        setNavigationTitle(R.drawable.back,NavigationTitleContent,NavigationTitleTextSize);
    }

    public void setNavigationTitle(int NavigationIconRes,float NavigationTitleTextSize){
        setNavigationTitle(NavigationIconRes,"Back",NavigationTitleTextSize);
    }

    public void setNavigationTitle(int NavigationIconRes,String NavigationTitleContent){
        setNavigationTitle(NavigationIconRes,NavigationTitleContent,this.NavigationTitleTextSize);
    }

    /**
     * 设置返回按钮及右边的文字
     * @param NavigationIconRes 返回按钮图片资源,默认 R.drawable.back
     * @param NavigationTitleContent 返回按钮右边文字内容,默认 "Back"
     * @param NavigationTitleTextSize 返回按钮右边文字大小,默认 15sp
     */
    public void setNavigationTitle(int NavigationIconRes,String NavigationTitleContent,float NavigationTitleTextSize){

        NavigationTitle = new TextView(getContext());
        NavigationIcon = new ImageView(getContext());

        NavigationTitle.setMaxLines(1);
        NavigationTitle.setText(NavigationTitleContent);
        NavigationIcon.setImageResource(NavigationIconRes);
        setNavigationTitleTextSize(NavigationTitleTextSize);

        Titleparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        NavigationIconparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        NavigationIconparams.setMargins(25,0,0,0);
        Titleparams.setMargins(10,0,0,0);

        addView(NavigationIcon,NavigationIconparams);
        addView(NavigationTitle,Titleparams);

    }

    /**
     * 调整返回按钮右边文字大小
     * @param NavigationTitleTextSize 返回按钮右边文字大小,默认 15sp
     */
    private void setNavigationTitleTextSize(float NavigationTitleTextSize){
        this.NavigationTitleTextSize = NavigationTitleTextSize;
        NavigationTitle.setTextSize(NavigationTitleTextSize);
    }


    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}