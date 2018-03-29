package com.jeevasamruddhi.telangana.nlms.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jeevasamruddhi.telangana.nlms.android.R;
import com.jeevasamruddhi.telangana.nlms.android.model.BasicCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jaganmohan on 05-03-2018.
 */

public class BasicCardAdapter extends ArrayAdapter<BasicCard> {

    Context context;
    List<BasicCard> basicCards;
    ArrayList<BasicCard> basicCardList;
    private static LayoutInflater inflater = null;

    public BasicCardAdapter(@NonNull Context context, int resource, List<BasicCard> _basicCards) {
        super(context, resource);
        try {
            this.context = context;
            this.basicCards = _basicCards;
            this.basicCardList = new ArrayList<BasicCard>();
            this.basicCardList.addAll(_basicCards);
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }

    @Override
    public int getCount() {
        return this.basicCards.size();
    }

    @Override
    public BasicCard getItem(int position) {
        return this.basicCards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) this.basicCards.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_basiccard, null);
            // Locate the TextViews in listview_employee.xml
            holder.name = (TextView) view.findViewById(R.id.basicCardName);
            holder.aadhaar = (TextView) view.findViewById(R.id.basicCardAadhaar);
            holder.village = (TextView) view.findViewById(R.id.basicCardVillage);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BasicCard basicCard = (BasicCard) getItem(position);

        int length = basicCard.getAadhaar().length();

        holder.name.setText(basicCard.getName());
        holder.aadhaar.setText(basicCard.getAadhaar());
        holder.village.setText(basicCard.getVillage());

        return view;
    }

    private class ViewHolder {
        TextView name;
        TextView aadhaar;
        TextView village;

        private ViewHolder() {
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        basicCards.clear();
        if (charText.length() == 0) {
            basicCards.addAll(basicCardList);
        } else {
            for (BasicCard basicCard : basicCardList) {
                if (basicCard.getAadhaar().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    basicCards.add(basicCard);
                }
            }
        }
        notifyDataSetChanged();
    }
}
