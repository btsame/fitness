package com.dkjs.fitness.comm;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dkjs.fitness.R;

/**
 * Created by Administrator on 2016/8/27.
 */
public class HeadTitleView extends RelativeLayout {
    private ImageButton leftButton;
    private TextView rightText;
    private TextView titleTextView;
    private OnLeftAndRightClickListener listener;//监听点击事件


    public HeadTitleView(Context context) {
        super(context);
    }


    public HeadTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getAttr(context, attrs);
    }


    public HeadTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        getAttr(context, attrs);
    }


    //设置监听器
    public void setOnLeftAndRightClickListener(OnLeftAndRightClickListener listener) {
        this.listener = listener;
    }

   //设置左边按钮的可见性
    public void setLeftButtonVisibility(boolean flag) {
        if (flag)
            leftButton.setVisibility(View.VISIBLE);
        else
            leftButton.setVisibility(View.GONE);
    }

    //设置右边按钮的可见性
    public void setRightButtonVisibility(boolean flag) {
        if (flag)
            rightText.setVisibility(View.VISIBLE);
        else
            rightText.setVisibility(View.GONE);
    }

    //按钮点击接口
    public interface OnLeftAndRightClickListener {
        public void onLeftButtonClick();

        public void onRightButtonClick();
    }




    private void getAttr(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_head, this);
        leftButton = (ImageButton) findViewById(R.id.left_button);
        rightText = (TextView) findViewById(R.id.right_text);
        titleTextView = (TextView) findViewById(R.id.tv_title);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onLeftButtonClick();//点击回调

            }
        });
        rightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onRightButtonClick();//点击回调
            }
        });
        //获得自定义属性并赋值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadTitle);
        int leftImg = typedArray.getResourceId(R.styleable.HeadTitle_leftImg, 0);
        String rightTextView = typedArray.getString(R.styleable.HeadTitle_rightTextView);
        String titleText = typedArray.getString(R.styleable.HeadTitle_titleText);
      leftButton.setBackgroundResource(leftImg);
        rightText.setText(rightTextView);
        titleTextView.setText(titleText);

        System.err.print("headTitleVIew--"+rightText+"==="+titleText);

        typedArray.recycle();//释放资源


    }
}