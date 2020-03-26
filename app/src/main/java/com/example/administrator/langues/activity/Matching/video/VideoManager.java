package com.example.administrator.langues.activity.Matching.video;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

//import com.xt.gtlauncher.utils.L;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class VideoManager {

        private io.vov.vitamio.widget.VideoView mVideoView;

        private OnVideoProgressListener listener;

        private static final int H_PROGRESS  = 1001;

        private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
        switch (message.what) {
        case H_PROGRESS:
        if (listener != null){
        //获取当前时长
        long currentDuration = getCurrentPosition();
        listener.onProgress(currentDuration);
        mHandler.sendEmptyMessageDelayed(H_PROGRESS, 1000);
        }
        break;
        }
        return false;
       }
        });

        public VideoManager(VideoView mVideoView){
       this.mVideoView = mVideoView;
        }

        /**
      * 设置文件路径
      *
      * @param path
      */
        public void setVideoPath(String path) {
         mVideoView.setVideoPath(path);
        }

        /**
      * 设置网络路径
      *
      * @param uri
      */
        public void setVideoURI(Uri uri) {
        mVideoView.setVideoURI(uri);
        }

        /**
      * 设置倍数 [0.5 - 2.0]
      *
      * @param mediaPlayer
      * @param speed
      */
        public void setPlaybackSpeed(MediaPlayer mediaPlayer, float speed){
        mediaPlayer.setPlaybackSpeed(speed);
        }

        /**
      * 获取当前播放的位置
      *
      * @return
      */
        public long getCurrentPosition() {
        return mVideoView.getCurrentPosition();
        }

        /**
      * 获取当前的视频总长度
      *
      * @return
      */
        public long getDuration() {
        return mVideoView.getDuration();
        }

        /**
      * 是否播放
      *
      * @return
      */
        public boolean isPlaying() {
        return mVideoView.isPlaying();
        }

        /**
      * 播放
      */
        public void start() {
        mVideoView.start();
        //mVideoView.requestFocus();
        mHandler.sendEmptyMessage(H_PROGRESS);
        }

        /**
      * 暂停
      */
        public void pause() {
        mVideoView.pause();
        }

        /**
      * 重新播放
      */
        public void resume() {
        mVideoView.resume();
        }

        /**
      * 停止播放 释放资源
      */
        public void stopPlayback( ){
        mVideoView.stopPlayback();
        mHandler.removeMessages(H_PROGRESS);
        }

        /**
      * 从第几毫秒开始播放
      *
      * @param msec
      */
        public void seekTo(int msec) {
        mVideoView.seekTo(msec);
        }

        /**
      * 设置控制器
      *
      * @param controller
      */
        public void setMediaController(io.vov.vitamio.widget.MediaController controller){
        mVideoView.setMediaController(controller);
        }

        /**
      * 监听播放完毕
      *
      * @param l
      */
        public void setOnCompletionListener(io.vov.vitamio.MediaPlayer.OnCompletionListener l) {
        mVideoView.setOnCompletionListener(l);
        }

        /**
      * 监听发生错误
      *
      * @param l
      */
        public void setOnErrorListener(io.vov.vitamio.MediaPlayer.OnErrorListener l){
        mVideoView.setOnErrorListener(l);
        }

        /**
      * 监听视频装载完成
      *
      * @param l
      */
        public void setOnPreparedListener(io.vov.vitamio.MediaPlayer.OnPreparedListener l)
        { mVideoView.setOnPreparedListener(l);

        }


        /**
      * 监听进度
      *
      * @param listener
      */
        public void setOnVideoProgress(OnVideoProgressListener listener) {
            this.listener = listener;
        }

        public interface OnVideoProgressListener{
        void onProgress(long progress);
        }
        }