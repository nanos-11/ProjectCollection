package com.nan.projectcollection;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.nan.projectcollection.Utils.UIUtils;
import com.nan.projectcollection.lottery.LotteryRelativLayout;


public class GuaGuaLe extends ActionBarActivity {

    private Button lottery;
    private LotteryRelativLayout lotteryRelativLayout;
    private RelativeLayout container;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gua_gua_le);

        lottery = (Button) findViewById(R.id.lottery);
        container = (RelativeLayout) findViewById(R.id.container);


        lottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnrie();
            }
        });
    }

    private void showEnrie() {
        // TODO Auto-generated method stub
        int width = UIUtils.getScreenWidth(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, -2);
        // 移除所有子元素
        container.removeAllViews();
        // 产生一个彩票
        int level = getLevel();
        lotteryRelativLayout = new LotteryRelativLayout(this, level);
        container.addView(lotteryRelativLayout, params);
    }


    private int getLevel() {
        int level = (int) (Math.random() * 100);
        if (level < 50) {
            return 0;
        }
        if (level < 80) {
            return 2;
        }
        if (level < 95) {
            return 1;
        }
        return 0;
    }

}
