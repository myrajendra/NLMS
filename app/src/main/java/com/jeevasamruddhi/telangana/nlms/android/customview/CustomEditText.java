package com.jeevasamruddhi.telangana.nlms.android.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.jeevasamruddhi.telangana.nlms.android.R;


public class CustomEditText extends android.support.v7.widget.AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);
        onInitTypeface(context, null, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitTypeface(context, attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
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