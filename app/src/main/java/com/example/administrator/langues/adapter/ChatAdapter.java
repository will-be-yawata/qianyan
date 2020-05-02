package com.example.administrator.langues.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.langues.MediaManager;
import com.example.administrator.langues.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;

import entry.Message;
import entry.User;
import util.Url;

public class ChatAdapter extends RecyclerView.Adapter {
    private static final int TYPE_TEXT=0;    //文本类型
    private static final int TYPE_IMAGE=1;   //图片类型
    private static final int TYPE_VOICE=2;   //语音类型
    private static final int TYPE_VIDEO=3;   //视频类型
    private ArrayList<Message> msgList=new ArrayList<>();
    private String self= User.getInstance().getPhone();
    private LayoutInflater mInflater=null;
    private String friend_photo;
    private int mMinItemWidth;
    private int mMaxItemWidth;
    private View mAnimView;
    public ChatAdapter(Context context,String friend_photo){
        mInflater=LayoutInflater.from(context);
        this.friend_photo=friend_photo;
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mMaxItemWidth=(int)(outMetrics.widthPixels*0.7f);
        mMinItemWidth=(int)(outMetrics.widthPixels*0.15f);
    }
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view;
        if(viewType==TYPE_TEXT) {
            view = mInflater.inflate(R.layout.friends_chat_items_text, parent, false);
            viewHolder = new TextViewHolder(view);
        }else if(viewType==TYPE_VOICE){
            view=mInflater.inflate(R.layout.friends_chat_items_voice,parent,false);
            viewHolder=new VoiceViewHolder(view);
        }
        return viewHolder;
    }
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int type=msgList.get(position).getType();
        String from=msgList.get(position).getFrom();
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setRadius(DensityUtil.dip2px(100))
                .build();
        String userimg=msgList.get(position).getFrom().equals(self)?User.getInstance().getImg():friend_photo;
        if(type==TYPE_TEXT){
            TextViewHolder holder= (TextViewHolder) viewHolder;
            if(from.equals(self)){
                holder.right.setVisibility(View.VISIBLE);
                holder.left.setVisibility(View.GONE);
                holder.right_text.setText(msgList.get(position).getContent());
                x.image().bind(holder.right_photo,Url.USER_IMG+userimg,imageOptions);
            }else{
                holder.right.setVisibility(View.GONE);
                holder.left.setVisibility(View.VISIBLE);
                holder.left_text.setText(msgList.get(position).getContent());
                x.image().bind(holder.left_photo,Url.USER_IMG+userimg,imageOptions);
            }
        }else if(type==TYPE_VOICE){
            VoiceViewHolder holder=(VoiceViewHolder) viewHolder;
            if(from.equals(self)){
                holder.right.setVisibility(View.VISIBLE);
                holder.left.setVisibility(View.GONE);
                holder.right_anim.setOnClickListener(view->onPlayAnim(position,view));
                x.image().bind(holder.right_photo,Url.USER_IMG+userimg,imageOptions);
                holder.right_seconds.setText(Math.round(msgList.get(position).getLength())+"\"");
                ViewGroup.LayoutParams lp=holder.right_length.getLayoutParams();
                lp.width=(int)(mMinItemWidth+(mMaxItemWidth/60f*msgList.get(position).getLength()));
            }else{
                holder.left.setVisibility(View.VISIBLE);
                holder.right.setVisibility(View.GONE);
                holder.left_anim.setOnClickListener(view->onPlayAnim(position,view));
                x.image().bind(holder.left_photo,Url.USER_IMG+userimg,imageOptions);
                holder.left_seconds.setText(Math.round(msgList.get(position).getLength())+"\"");
                ViewGroup.LayoutParams lp=holder.left_length.getLayoutParams();
                lp.width=(int)(mMinItemWidth+(mMaxItemWidth/60f*msgList.get(position).getLength()));
            }
        }
    }
    public int getItemViewType(int position) {
        int type=msgList.get(position).getType();
        if(type==Message.TYPE_TEXT){
            return TYPE_TEXT;
        }else if(type==Message.TYPE_VOICE){
            return TYPE_VOICE;
        }else if(type==Message.TYPE_IMAGE){
            return TYPE_IMAGE;
        }else if(type==Message.TYPE_VIDEO){
            return TYPE_VIDEO;
        }
        return TYPE_TEXT;
    }
    public int getItemCount() {
        return msgList.size();
    }
    public void addData(ArrayList<Message> msgList,boolean isFooter){
        Collections.reverse(msgList);
        if(isFooter){
            int index=this.msgList.size();
            this.msgList.addAll(msgList);
            notifyItemRangeInserted(index,msgList.size());
        }else {
            this.msgList.addAll( 0,msgList);
            notifyItemRangeInserted(0, msgList.size()-1);
        }
    }
    public void addOneData(Message m){
        msgList.add(m);
        notifyItemInserted(msgList.size()-1);
    }
    private void onPlayAnim(int position,View view){
        if(mAnimView!=null){
            mAnimView.setBackgroundResource(R.drawable.adj);
            mAnimView=null;
        }
        //播放动画
        mAnimView = view;
        mAnimView.setBackgroundResource(R.drawable.play_anim);
        AnimationDrawable anim= (AnimationDrawable) mAnimView.getBackground();
        anim.start();
        //播放音频
        MediaManager.playSound(msgList.get(position).getContent(), mediaPlayer -> mAnimView.setBackgroundResource(R.drawable.adj));
    }
    class TextViewHolder extends RecyclerView.ViewHolder{
        LinearLayout left,right;
        TextView left_text,right_text;
        ImageView left_photo,right_photo;
        TextViewHolder(@NonNull View itemView) {
            super(itemView);
            left=itemView.findViewById(R.id.chat_items_text_left);
            right=itemView.findViewById(R.id.chat_items_text_right);
            left_photo = itemView.findViewById(R.id.chat_items_text_left_photo);
            right_photo = itemView.findViewById(R.id.chat_items_text_right_photo);
            left_text = itemView.findViewById(R.id.chat_items_text_left_text);
            right_text = itemView.findViewById(R.id.chat_items_text_right_text);
        }
    }
    class VoiceViewHolder extends RecyclerView.ViewHolder{
        LinearLayout left,right;
        ImageView left_photo,right_photo;
        TextView left_seconds,right_seconds;
        View left_length,right_length;
        View left_anim,right_anim;
        public VoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            left=itemView.findViewById(R.id.chat_items_voice_left);
            right=itemView.findViewById(R.id.chat_items_voice_right);
            left_photo=itemView.findViewById(R.id.chat_items_voice_left_photo);
            right_photo=itemView.findViewById(R.id.chat_items_voice_right_photo);
            left_seconds=itemView.findViewById(R.id.chat_items_voice_left_time);
            right_seconds=itemView.findViewById(R.id.chat_items_voice_right_time);
            left_length=itemView.findViewById(R.id.chat_items_voice_left_length);
            right_length=itemView.findViewById(R.id.chat_items_voice_right_length);
            left_anim=itemView.findViewById(R.id.chat_items_voice_left_anim);
            right_anim=itemView.findViewById(R.id.chat_items_voice_right_anim);
        }
    }
}
