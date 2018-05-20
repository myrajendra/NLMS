package com.jeevasamruddhi.telangana.nlms.android.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.fragment.BmisFragment;

/**
 * Created by jayalakshmi on 4/3/2018.
 */

public class MandalSpinnerAdapter extends RecyclerView.Adapter<MandalSpinnerAdapter.ViewHolder> {
String[] values = {"Nalgonda","Miryalaguda","Suryapet","Kodad","Bhongir","Devarakonda","Mella Cheruvu","Choutuppal","Peddavoora",
                                 "Dameracherla","Neredcherla","Anumula","Nakrekal","Huzurnagar","Thungathurthi","Nuthankal","GaridePalle","Valigonda",
                                  "Mothkur","Chityala","Yadagirigutta","Nidamanur","Ramannapeta","Chandam Pet","Pochampalle","Atmakur (S)","Narketpalle",
                                  "Pedda Adiserla Palle","Chandur','Bibinagar","hivvemla","Thipparthi","Thirumalgiri","Alair","Sali Gouraram","Kattangoor",
                                   "Thripuraram","Gundla Palle","Munugode"," ChinthaPalle","MattamPalle","Vemulapalle","Narayanapur","Mothey","Kangal",
                                  "Munagala","Gurrampode","Nampalle","JajiReddiGudem","Penpahad,Nadigudem","Chilkur","Atmakur","Rajapet","KethePalle",
                                  "Bommalaramaram","arriguda","Gundala","M.Turkapalle"};
    AppCompatActivity mActivity;
    ViewHolder viewHolder1;
    View mRootview;
    BmisFragment bmisFragment;

    PopupWindow mProfilePicDialog;
    public MandalSpinnerAdapter(AppCompatActivity activity, PopupWindow mProfilePicDialog, View view, BmisFragment bmisFragment) {
        this.mProfilePicDialog =mProfilePicDialog;
        this.mActivity = activity;
        this.mRootview = view;
        this.bmisFragment = bmisFragment;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public FrameLayout tickMarkView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.txtone);
            tickMarkView = (FrameLayout) v.findViewById(R.id.selected_arrow);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView itemSpinnerTextViewObj = (TextView) mRootview.findViewById(R.id.filter);
                    itemSpinnerTextViewObj.setText(values[getAdapterPosition()]);
                    bmisFragment.FilterOperation(values[getAdapterPosition()]);
                    mProfilePicDialog.dismiss();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.mandal_category_recycler_textview, parent, false);
        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(values[position]);
        TextView itemSpinnerTextViewObj = (TextView) mRootview.findViewById(R.id.filter);
        String spinnerText = itemSpinnerTextViewObj.getText().toString();
        if (!TextUtils.isEmpty(spinnerText)) {
            if (values[position].equalsIgnoreCase(spinnerText)) {
                holder.tickMarkView.setVisibility(View.VISIBLE);
                holder.textView.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            } else {
                holder.tickMarkView.setVisibility(View.GONE);
                holder.textView.setTextColor(mActivity.getResources().getColor(R.color.black_overlay));
            }
        }
    }

    @Override
    public int getItemCount() {
        //OmniLog.loge("length=====>",rewardCategories.size()+"<=====");
        return values.length;
    }
}
