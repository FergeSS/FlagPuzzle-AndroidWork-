package com.company.poklimesev;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.os.Vibrator;

public class Settings {

    public static void action(Context context) {
        if (context.getSharedPreferences("settings", MODE_PRIVATE).getBoolean("vibro_enabled", true)) {
            Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(100);
            }
        }

//        if (context.getSharedPreferences("settings", MODE_PRIVATE).getBoolean("sound_enabled", true)) {
//            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.click);
//            mediaPlayer.setVolume(0.4f, 0.4f);
//            mediaPlayer.start();
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    mp.release();
//                }
//            });
//        }
    }
}