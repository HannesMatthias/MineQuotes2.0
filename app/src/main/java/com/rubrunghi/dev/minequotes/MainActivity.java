package com.rubrunghi.dev.minequotes;


import android.net.Uri;
import android.os.Bundle;


import android.support.v4.app.FragmentManager;

import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import ban.BanList;
import chat.ChatActivity;
import login.LoginActivity;
import login.LoginProfiles;

import player.playerlist.PlayerListActivity;
import rank.RankActivity;
import rank.RankHandler;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static RankHandler rankHandler;
    LoginProfiles profile;
    public static String uniquePlayerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        profile = LoginActivity.loggedInUser;
        uniquePlayerID = profile.getPlayerID();

        View hView = navigationView.getHeaderView(0);
        hView.findViewById(R.id.profilename_menu);
        TextView nav_user = (TextView) hView.findViewById(R.id.profilename_menu);
        nav_user.setText(profile.getUsername());
        ImageView image = hView.findViewById(R.id.profile_img);
        Picasso.with(this).load(Uri.parse(profile.getHead(10))).error(R.mipmap.ic_launcher).into(image);
        TextView nav_rank = (TextView) hView.findViewById(R.id.profile_rank);
        nav_rank.setText("Rang: " + profile.getRankname());

        rankHandler = new RankHandler();
        rankHandler.getRanks("ranktop10");

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_player) {
            Log.e("Open", "PlayerList");
            FragmentManager fm = getSupportFragmentManager();

            fm.beginTransaction().addToBackStack("").replace(R.id.main, new PlayerListActivity()).commit();
        } else if (id == R.id.nav_ranklist) {
            if (rankHandler.getRanks().size() > 0) {
                Log.e("Open", "RankList");
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().addToBackStack("").replace(R.id.main, new RankActivity()).commit();
            } else {
                Toast.makeText(this, "Rangliste wird noch geladen...", Toast.LENGTH_SHORT).show();
            }


        } else if (id == R.id.chat) {
            Log.e("Open", "Chat");
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().addToBackStack("").replace(R.id.main, new ChatActivity()).commit();
        } else if (id == R.id.nav_ban) {
            Log.e("Open", "Ban");
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().addToBackStack("").replace(R.id.main, new BanList()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
