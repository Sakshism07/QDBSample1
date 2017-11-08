package com.android.qdbsample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Android on 03-Nov-17.
 */

public class MyProfile extends Activity {

    String[] strArray_item_list = {"Industry", "No. of Employees", "Area of Specializations", "Phone Number", "Language Spoken", "Website", "Address", "Password"};
    ArrayList<String> stringArrayList;
    ListView listViewMyProfile;
    SharedPreferences sharedPreferences;
    String message, token;
    String urlMyProfile = "http://api.smesconnect.com/api/web/index.php/v1/user/my-business?language=en";
    NavigationDrawerListAdapter navigationDrawerListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchMyProfile().execute();
        setContentView(R.layout.activity_my_profile);

        listViewMyProfile = findViewById(R.id.listViewMyProfile);
        stringArrayList = new ArrayList<>();

        for (int i = 0; i < strArray_item_list.length; i++) {

            stringArrayList.add(strArray_item_list[i]);
        }
        navigationDrawerListAdapter = new NavigationDrawerListAdapter(stringArrayList);
        listViewMyProfile.setAdapter(navigationDrawerListAdapter);

    }

    public class FetchMyProfile extends AsyncTask<Void, Void, String> {

        Boolean errorStatus;

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("Token", null);

            Request request = new Request.Builder()
                    .url(urlMyProfile)
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
                    Toast.makeText(MyProfile.this, "" + message, Toast.LENGTH_SHORT).show();
                } else {

                    JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("businessData");

                    Log.d("No. of Employees", jsonObject3.getString("no_of_employees"));
                    Log.d("Industry Type", jsonObject3.getString("industry_name"));
                    Log.d("Message", message.toString());
                    //Toast.makeText(MyProfile.this, "" + message, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
