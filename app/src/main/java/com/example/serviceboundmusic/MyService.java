package com.example.serviceboundmusic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private MyPlayer myPlayer;
    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Đã gọi onCreate()");
        binder = new MyBinder(); // do MyBinder được extends Binder

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer = new MyPlayer(this, (Music) intent.getSerializableExtra("music"));
        myPlayer.play();
        return binder;

    }
    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    // Xây dựng các phương thức thực hiện nhiệm vụ,
    // ở đây mình demo phương thức tua bài hát
    public void fastForward(){

        myPlayer.fastForward(60000); // tua đến giây thứ 120
    }
    public void fastStart(){

        myPlayer.fastStart();
    }

    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public MyService getService() {

            return MyService.this;
        }
    }

}

class MyPlayer {

    private MediaPlayer mediaPlayer;

    public MyPlayer(Context context,Music music) {
        // Nạp bài nhạc vào mediaPlayer
        mediaPlayer = MediaPlayer.create(
                context, music.getId());
        // Đặt chế độ phát lặp lại liên tục
        mediaPlayer.setLooping(true);
    }

    public void fastForward(int pos){
        //mediaPlayer.seekTo(pos);
        mediaPlayer.pause();

    }
    public void fastStart(){
        mediaPlayer.start();
    }

    // phát nhạc
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // dừng phát nhạc
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
