package com.jeevasamruddhi.telangana.nlms.android.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

public class TypefaceManager
{
    private final static int AVENIR_NEXT_LT_PRO_DEMI = 1;
    private final static int AVENIR_NEXT_LT_PRO_MEDIUM = 2;
    private final static int AVENIR_NEXT_LT_PRO_REGULAR = 3;
    private final static int HN_MEDIUM  = 4;

    private final static int ROBOTO_REGULAR=5;
    private final static int ROBOTO_MEDIUM=6;
    private final static int ROBOTO_LIGHT=7;
    private final static int ROBOTO_BOLD=8;
    private final static int IMAGE_FONT=9;


    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(20);

    public static Typeface obtaintTypeface(Context context, int typefaceValue) throws IllegalArgumentException
    {
        Typeface typeface = mTypefaces.get(typefaceValue);
        if(typeface == null)
        {
            typeface = createTypeface(context, typefaceValue);
            mTypefaces.put(typefaceValue, typeface);
        }
        return typeface;
    }

    private static Typeface createTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        Typeface typeface;
        switch(typefaceValue)
        {
            //Using
            case AVENIR_NEXT_LT_PRO_DEMI:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
                break;
            //Using
            case AVENIR_NEXT_LT_PRO_MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Linotype-AvenirNextLTProMedium.ttf");
                break;
            case AVENIR_NEXT_LT_PRO_REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Linotype-AvenirNextLTProMedium.ttf");
                break;
            case HN_MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Linotype-AvenirNextLTProMedium.ttf");
                break;
            case ROBOTO_REGULAR:
                typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf");
                break;
            case ROBOTO_MEDIUM:
                typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf");
                break;
            case ROBOTO_LIGHT:
                typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");
                break;
            case ROBOTO_BOLD:
                typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Bold.ttf");
                break;

            default:
                throw new IllegalArgumentException("Unknown `typeface` attribute value " + typefaceValue);
        }
        return typeface;
    }

}