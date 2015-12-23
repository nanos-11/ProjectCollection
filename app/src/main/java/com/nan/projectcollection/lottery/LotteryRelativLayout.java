package com.nan.projectcollection.lottery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nan.projectcollection.Utils.UIUtils;

/**
 * Created by nan on 2015/12/22.
 */
public class LotteryRelativLayout extends RelativeLayout {
    private int level;
    private Context mContext;
    private RelativeLayout rubberBG;// 最底层奖励等级
    private ScrapeLottery mScrapeLottery;// 橡皮擦
    private Button mButton;
    private final int rubberBGID = 0x10001;
    private final int mButtonID = 0x10002;
    private int width;


    public LotteryRelativLayout(Context context, int level) {
        super(context);
        this.level = level;
        this.mContext = context;
        width = UIUtils.getScreenWidth((Activity) context);
        getElement();// 得到子元素
        setElementLP();// 设置布局参数
        setElementStyle();// 初始化彩票了
        setElement();// 设置橡皮檫了
    }
    
    private void setElementStyle() {
        switch (level) {
            case 0:
                rubberBG.setBackgroundColor(Color.BLACK);
                break;
            case 1:
                rubberBG.setBackgroundColor(Color.WHITE);
                break;
            case 2:
                rubberBG.setBackgroundColor(Color.BLUE);
                break;
            default:
                rubberBG.setBackgroundColor(Color.YELLOW);
                break;
        }
    }
    
    private void setElementLP() {
        RelativeLayout.LayoutParams rubber_bg_lp = new RelativeLayout.LayoutParams(width, 500);
        RelativeLayout.LayoutParams mScrapeLotteryParams = new RelativeLayout.LayoutParams(width, 500);
        rubberBG.setLayoutParams(rubber_bg_lp);
        mScrapeLottery.setLayoutParams(mScrapeLotteryParams);
        // rubber_bg_lp正下方
        RelativeLayout.LayoutParams rubber_btn_lp = new RelativeLayout.LayoutParams(-2, -2);
        rubber_btn_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rubber_btn_lp.addRule(RelativeLayout.BELOW, rubberBGID);
        mButton.setLayoutParams(rubber_btn_lp);
        mButton.setClickable(false);
    }
    
    
    public LotteryRelativLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryRelativLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setElement() {
        // 第一步在彩票上面画一个图层
        mScrapeLottery.beginScrapeLottery(Color.parseColor("#d3d3d3"), 30, 10);
    }

    public void getElement() {
        rubberBG = new RelativeLayout(mContext);
        mScrapeLottery = new ScrapeLottery(mContext);
        mButton = new Button(mContext);
        rubberBG.setId(rubberBGID);
        mButton.setId(mButtonID);
        mButton.setText("领奖");
        mButton.setClickable(true);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        rubberBG.addView(mScrapeLottery);
        addView(rubberBG);
        addView(mButton);
    }
}













