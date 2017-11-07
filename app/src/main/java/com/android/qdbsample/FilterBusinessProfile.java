package com.android.qdbsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Android on 06-Nov-17.
 */

public class FilterBusinessProfile extends Activity implements View.OnClickListener {

    String urlFilterList = "http://api.smesconnect.com/api/web/index.php/v1/business/industry-list?language=ar";
    ArrayList<ModelFilterList> arrayList;
    RecyclerView listviewFilter;
    Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterlist);
        init();
        bindOnClick();
        new FetchFilterList().execute();
    }

    private void bindOnClick() {
        btnSubmit.setOnClickListener(this);
    }

    private void init() {
        listviewFilter = findViewById(R.id.listviewFilter);
        btnSubmit = findViewById(R.id.btnSubmit);
        arrayList = new ArrayList<>();

        listviewFilter.setLayoutManager(new LinearLayoutManager(FilterBusinessProfile.this));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {

        }
    }

    public class FetchFilterList extends AsyncTask<Void, Void, String> {

        Boolean errorStatus = null;
        String message, token, name;
        SharedPreferences sharedPreferences;
        ProgressDialog progressDialog = new ProgressDialog(FilterBusinessProfile.this);

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Loading...");
            progressDialog.show();
            //obj.showProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("Token", null);

            Request request = new Request.Builder()
                    .url(urlFilterList)
                    .addHeader("AUTH-TOKEN", token)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String mMessage = response.body().string();
                JSONObject jsonObject1 = new JSONObject(mMessage);

                errorStatus = jsonObject1.getBoolean("errorStatus");
                message = jsonObject1.getString("message");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                JSONArray jsonArray = jsonObject2.getJSONArray("industryList");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    name = jsonObject3.getString("name_en");
                    ModelFilterList objModelFilterList = new ModelFilterList(name, false);
                    arrayList.add(objModelFilterList);

                    Log.d("Industry List", name);
                }

                if (errorStatus == true) {

                    Log.d("TAG", message.toString());
                    Toast.makeText(FilterBusinessProfile.this, "" + message, Toast.LENGTH_SHORT).show();
                } else {

                    Log.d("TAG", message.toString());
                    Toast.makeText(FilterBusinessProfile.this, "" + message, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            //obj.dismissProgressDialog();
            FilterListAdapter filterListAdapter = new FilterListAdapter(arrayList);
            listviewFilter.setAdapter(filterListAdapter);
            super.onPostExecute(s);
        }
    }
}
