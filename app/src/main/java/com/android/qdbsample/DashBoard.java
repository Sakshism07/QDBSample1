package com.android.qdbsample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    String url = "http://api.smesconnect.com/api/web/index.php/v1/api/dashboard-data?language=en";
    ListView listView;
    String token, message;
    Boolean errorStatus;
    SharedPreferences sharedPreferences;
    ImageButton ivCloseNavigationDrawer;
    TextView textView, tvBelowSuitCase, tvBelowCalendar, tvInsideSuitCase, tvInsideCalendar, tvInsideDeals;
    String[] strArray_item_list = {"MY PROFILE", "BUSINESS PROFILE", "DEALS", "FAVORITES", "ABOUT", "FAQ", "LOGOUT"};
    ArrayList<String> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        init();
        new FetchAsyncData().execute();

        stringArrayList = new ArrayList<>();

        for (int i = 0; i < strArray_item_list.length; i++) {
            stringArrayList.add(strArray_item_list[i]);
        }

        NavigationDrawerListAdapter<String> adapter = new NavigationDrawerListAdapter<String>(stringArrayList);
        listView.setAdapter(adapter);

        ivCloseNavigationDrawer.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    Log.d("MyProfile", String.valueOf(position));
                    Intent myIntent = new Intent(DashBoard.this, MyProfile.class);
                    startActivity(myIntent);
                }

                if (position == 1) {

                    Log.d("Business Profile", String.valueOf(position));
                    Intent intent = new Intent(DashBoard.this, BusinessProfile.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.tvItemName);
        ivCloseNavigationDrawer = findViewById(R.id.ivCloseNavigationDrawer);
        tvBelowCalendar = findViewById(R.id.tvBelowCalendar);
        tvBelowSuitCase = findViewById(R.id.tvBelowSuitCase);
        tvInsideCalendar = findViewById(R.id.tvInsideCalendar);
        tvInsideSuitCase = findViewById(R.id.tvInsideSuitCase);
        tvInsideDeals = findViewById(R.id.tvInsideDeals);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menuRight) {

            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCloseNavigationDrawer) {
            drawerLayout.closeDrawer(Gravity.END);
        }
    }

    public class FetchAsyncData extends AsyncTask<Void, Void, String> {

        String membershipDaysLeft, businessCount, dealCount;

        ///ProgressDialogActivity objProgressDialogActivity;// = new ProgressDialogActivity();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //objProgressDialogActivity.showProgressDialog();

        }

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("Token", null);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("AUTH-TOKEN", token)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String mMessage = response.body().string();
                JSONObject jsonObject1 = new JSONObject(mMessage);

                errorStatus = jsonObject1.getBoolean("errorStatus");
                message = jsonObject1.getString("message");

                if (errorStatus == true) {

                    Log.d("TAG", message.toString());
                    Toast.makeText(DashBoard.this, "" + message, Toast.LENGTH_SHORT).show();
                } else {

                    JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                    membershipDaysLeft = jsonObject2.getString("membershipDaysLeft");
                    businessCount = jsonObject2.getString("businessCount");
                    dealCount = jsonObject2.getString("dealCount");

                    Log.d("TAG", message.toString());
                    Toast.makeText(DashBoard.this, "" + message, Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            //objProgressDialogActivity.dismissProgressDialog();
            tvInsideSuitCase.setText(businessCount);
            tvInsideCalendar.setText(membershipDaysLeft);
            tvInsideDeals.setText(dealCount);
            Log.d("TAG1", membershipDaysLeft);
            Log.d("TAG2", businessCount);
            Log.d("TAG", dealCount);
            Log.d("TOKEN", token);


        }
    }


}
