package com.ofalvai.aircard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CardAdapter extends ArrayAdapter<Card> {


    public CardAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_card, parent, false);
        }

        final TextView cardContent = (TextView) convertView.findViewById(R.id.list_item_card_content);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(card.getName());
        stringBuilder.append("\n");
        for (CustomField field : card.getCustomFields()) {
            stringBuilder.append(field.getLabel());
            stringBuilder.append(": ");
            stringBuilder.append(field.getValue());
            stringBuilder.append("\n");
        }

        cardContent.setText(stringBuilder.toString());
        return convertView;

    }
}
