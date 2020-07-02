package com.reivai.finalmoviecatalogue.feature.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.reivai.finalmoviecatalogue.R;
import com.reivai.finalmoviecatalogue.SharedPreference;

public class ActivityReminder extends AppCompatActivity implements View.OnClickListener {
    private SharedPreference sharedPreference;
    private ReceiverReminder receiverReminder;
    private Switch switchDaily, switchNewRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        setTitle(R.string.title_reminder);

        sharedPreference = new SharedPreference(this);
        receiverReminder = new ReceiverReminder();

        switchDaily = findViewById(R.id.switch_daily);
        switchNewRelease = findViewById(R.id.switch_new_release);

        switchDaily.setOnClickListener(this);
        switchNewRelease.setOnClickListener(this);

        checkRemindStat();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_daily:
                if (switchDaily.isChecked()) {
                    sharedPreference.booleanSave(SharedPreference.DAILY_REMINDER, true);
                    receiverReminder.setDailyRemind(this, "07:00", "Go Check Movie Catalogue App Today");
                    Toast.makeText(this, getString(R.string.enable_daily), Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreference.booleanSave(SharedPreference.DAILY_REMINDER, false);
                    receiverReminder.cancelDailyRemind(this);
                    Toast.makeText(this, getString(R.string.disable_daily), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.switch_new_release:
                if (switchNewRelease.isChecked()) {
                    sharedPreference.booleanSave(SharedPreference.NEW_RELEASE_REMINDER, true);
                    receiverReminder.setNewReleaseRemind(this, "08:00", ReceiverReminder.EXTRA_MESSAGE);
                    Toast.makeText(this, getString(R.string.enable_new_release), Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreference.booleanSave(SharedPreference.NEW_RELEASE_REMINDER, false);
                    receiverReminder.cancelNewReleaseRemind(this);
                    Toast.makeText(this, getString(R.string.disable_new_release), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkRemindStat() {
        if (sharedPreference.getDailyReminder()) {
            switchDaily.setChecked(true);
        } else {
            switchDaily.setChecked(false);
        }

        if (sharedPreference.getNewReleaseReminder()) {
            switchNewRelease.setChecked(true);
        } else {
            switchNewRelease.setChecked(false);
        }
    }
}
