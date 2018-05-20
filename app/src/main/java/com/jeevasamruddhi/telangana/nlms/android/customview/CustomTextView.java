package com.jeevasamruddhi.telangana.nlms.android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.jeevasamruddhi.telangana.nlms.android.R;


public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        onInitTypeface(context, null, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitTypeface(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onInitTypeface(context, attrs, defStyle);
    }

    private void onInitTypeface(Context context, AttributeSet attrs, int defStyle) {

        if (isInEditMode()) {
            return;
        }

        int typefaceValue = 0;
        if (attrs != null) {
            TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustTextView, defStyle, 0);
            typefaceValue = values.getInt(R.styleable.CustTextView_typeface, 0);
            values.recycle();
        }

        Typeface robotoTypeface = TypefaceManager.obtaintTypeface(context, typefaceValue);
        setTypeface(robotoTypeface);
    }

}