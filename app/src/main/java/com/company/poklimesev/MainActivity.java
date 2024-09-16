package com.company.poklimesev;

import static com.company.poklimesev.Settings.action;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.company.poklimesev.databinding.ActivityMainBinding;

public class MainActivity extends Property {
    private ActivityMainBinding binding;
    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkOpenActivities();
    }

    public void checkOpenActivities() {
        if (GameActivity.active) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
            return;
        }

        if (PolicyActivity.active) {
            Intent intent = new Intent(this, PolicyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
            return;
        }
    }

    public void settings(View v) {
        Settings.action(MainActivity.this);
        dialogSettings();
    }

    public void play(View v) {
        Settings.action(MainActivity.this);
        Intent intent = new Intent(this, GameActivity.class);
        if (GameActivity.active) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        startActivity(intent);
    }

    public void policy(View v) {
        Settings.action(MainActivity.this);
        Intent intent = new Intent(this, PolicyActivity.class);
        if (PolicyActivity.active) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        startActivity(intent);
    }

    public void close(View v) {
        Settings.action(MainActivity.this);
        finishAffinity();
    }

    public void dialogSettings() {
        dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        WindowManager.LayoutParams wlp = dialog.getWindow().getAttributes();
        wlp.dimAmount = 0.7f;
        dialog.getWindow().setAttributes(wlp);
        dialog.setContentView(R.layout.settings_dialog);
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ((Switch)(dialog.findViewById(R.id.soundSwitch))).setChecked(sharedPreferences.getBoolean("sound_enabled", true));

        ((Switch)(dialog.findViewById(R.id.soundSwitch))).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                action(MainActivity.this);
                editor.putBoolean("sound_enabled", isChecked);
                editor.apply();
            }
        });

        ((Switch)(dialog.findViewById(R.id.vibroSwitch))).setChecked(sharedPreferences.getBoolean("vibro_enabled", true));

        ((Switch)(dialog.findViewById(R.id.vibroSwitch))).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                action(MainActivity.this);
                editor.putBoolean("vibro_enabled", isChecked);
                editor.apply();
            }
        });

        dialog.findViewById(R.id.buttonExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.action(MainActivity.this);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}