package com.example.brayan.parcial.Views;

import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.brayan.parcial.R;
import com.example.brayan.parcial.Views.fragment.EventFragment;
import com.example.brayan.parcial.Views.fragment.MapsFragment;
import com.example.brayan.parcial.Views.fragment.PushNotificationsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.maps);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.event:
                        EventFragment eventFragment = new EventFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, eventFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

                        break;
                    case R.id.maps:
                        MapsFragment mapsFragment = new MapsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapsFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                        break;
                    case R.id.notifications:
                        PushNotificationsFragment pushNotificationsFragment = new PushNotificationsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, pushNotificationsFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                        break;
                }
            }
        });
    }
}
