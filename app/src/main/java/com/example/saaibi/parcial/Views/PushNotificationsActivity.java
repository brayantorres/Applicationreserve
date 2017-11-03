package com.example.saaibi.parcial.Views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.saaibi.parcial.Firebase.CoursePushNotification;
import com.example.saaibi.parcial.R;
import com.example.saaibi.parcial.Views.Adapter.PushNotificationsAdapter;

public class PushNotificationsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayout mNoMessagesView;
    private PushNotificationsAdapter mNotificationsAdapter;

    public static final String ACTION_NOTIFY_NEW_PROMO = "NOTIFY_NEW_PROMO";
    private BroadcastReceiver mNotificationsReceiver;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notifications);

        mNotificationsAdapter = new PushNotificationsAdapter();
        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_notifications_list);
        mNoMessagesView = (LinearLayout) this.findViewById(R.id.noMessages);
        mRecyclerView.setAdapter(mNotificationsAdapter);

        mNotificationsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String title = intent.getStringExtra("title");
                String description = intent.getStringExtra("description");
                String discount = intent.getStringExtra("discount");
                savePushMessage(title, description, discount);
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mNotificationsReceiver, new IntentFilter(ACTION_NOTIFY_NEW_PROMO));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNotificationsReceiver);
    }

    public void savePushMessage(String title, String description, String discount){
        CoursePushNotification coursePushNotification = new CoursePushNotification();
        coursePushNotification.setTitle(title);
        coursePushNotification.setDescription(description);
        coursePushNotification.setDiscount(TextUtils.isEmpty(discount) ? 0 : Float.parseFloat(discount));

        mNotificationsAdapter.addItem(coursePushNotification);
        showEmptyState(false);
    }

    public void showEmptyState(boolean empty) {
        mRecyclerView.setVisibility(empty ? View.GONE : View.VISIBLE);
        mNoMessagesView.setVisibility(empty ? View.VISIBLE : View.GONE);
    }
}
