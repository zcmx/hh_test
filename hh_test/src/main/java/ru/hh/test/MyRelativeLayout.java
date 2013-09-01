package ru.hh.test;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MyRelativeLayout extends RelativeLayout{
    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();
        final ScrollView mScrollView = (ScrollView)findViewById(R.id.scroll_send_form);

        if (actualHeight > proposedheight){
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(0, mScrollView.getBottom());
                }
            });
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
