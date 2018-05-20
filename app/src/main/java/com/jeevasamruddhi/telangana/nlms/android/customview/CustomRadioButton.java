package com.jeevasamruddhi.telangana.nlms.android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.jeevasamruddhi.telangana.nlms.android.R;

public class CustomRadioButton extends AppCompatRadioButton {

    public CustomRadioButton(Context context) {
        super(context);
        onInitTypeface(context, null, 0);
        setAllCaps(true);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitTypeface(context, attrs, 0);
        setAllCaps(false);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitTypeface(context, attrs, defStyleAttr);
        setAllCaps(false);
    }

    public void setMyButtonAllCaps(boolean isallCaps) {
        if (!isallCaps)
            setAllCaps(false);

    }

    private void onInitTypeface(Context context, AttributeSet attrs, int defStyle) {

        if (isInEditMode()) {
            return;
        }

        int typefaceValue = 0;
        if (attrs != null) {
            TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustButton, defStyle, 0);
            typefaceValue = values.getInt(R.styleable.CustButton_typeface, 0);

            values.recycle();
        }

        Typeface robotoTypeface = TypefaceManager.obtaintTypeface(context, typefaceValue);
        setTypeface(robotoTypeface);

    }
} 
