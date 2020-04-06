package com.example.administrator.langues.activity.Chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.langues.R;
import com.example.administrator.langues.activity.Chat.chat;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<chat> {
    private int resourceId;
    public ChatAdapter(Context context, int textViewResourceId, List<chat> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        chat c = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
            viewHolder.leftText = (TextView)view.findViewById(R.id.left_text);
            viewHolder.rightText = (TextView)view.findViewById(R.id.right_text);
            viewHolder.photoL = (ImageView)view.findViewById(R.id.photo_left);
            viewHolder.photoR= (ImageView)view.findViewById(R.id.photo_right);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(c.getType() == chat.TYPE_RECEIVED) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.photoL.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.photoR.setVisibility(View.GONE);
            viewHolder.leftText.setText(c.getContent());
        } else if(c.getType() == chat.TYPE_SEND) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.photoR.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.photoL.setVisibility(View.GONE);
            viewHolder.rightText.setText(c.getContent());
        }
        return view;
    }

    class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftText;
        TextView rightText;
        ImageView photoL;
        ImageView photoR;
    }
}

