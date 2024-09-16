package com.company.poklimesev;

import android.os.Bundle;
import android.view.View;

import com.company.poklimesev.databinding.ActivityPolicyBinding;

public class PolicyActivity extends Property {
    private ActivityPolicyBinding binding;
    public static boolean active = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.policy.setBackgroundColor(0x0000000);
        binding.policy.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        binding.policy.loadUrl("file:///android_asset/policy.html");

        active = true;
    }

    public void home(View v) {
        Settings.action(PolicyActivity.this);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }
}
