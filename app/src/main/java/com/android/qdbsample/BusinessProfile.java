package com.android.qdbsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.http.GET;

/**
 * Created by Android on 04-Nov-17.
 */

public class BusinessProfile extends Activity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    ArrayList<GetSet> arrayList;
    ArrayList<GetSet> arrayListCheckedFilter;
    String token, message;
    Button btnFilter;
    Boolean errorStatus;
    int index = 0;
    int getALHint;
    RecyclerView recyclerView;
    String urlBusinessProfile = "http://api.smesconnect.com/api/web/index.php/v1/business/list?language=en&offset=0&industry_ids=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Business Profile");
        setContentView(R.layout.activity_businessprofile);

        init();
        bindOnClicktoComponents();
        recyclerView.setLayoutManager(new GridLayoutManager(BusinessProfile.this, 2));
        recyclerView.setHasFixedSize(true);


        new FetchBusinessProfile().execute();





    }

    private void bindOnClicktoComponents() {
        btnFilter.setOnClickListener(this);
    }

    private void init() {
        recyclerView = findViewById(R.id.card_recycler_view);
        btnFilter = findViewById(R.id.btnFilter);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnFilter) {

            Intent intent = new Intent(BusinessProfile.this, FilterBusinessProfile.class);
            startActivity(intent);
        }
    }

    public class FetchBusinessProfile extends AsyncTask<Void, Void, String> {

        String name;
        String imgurl, industryid;
        ProgressDialog progressDialog = new ProgressDialog(BusinessProfile.this);

        ///ProgressDialogActivity obj;// = new ProgressDialogActivity();

        @Override
        protected void onPreExecute() {
            //obj.showProgressDialog();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            arrayList = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();
            sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("Token", null);

            Request request = new Request.Builder()
                    .url(urlBusinessProfile)
                    .addHeader("AUTH-TOKEN", token)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String mMessage = response.body().string();
                JSONObject jsonObject1 = new JSONObject(mMessage);

                errorStatus = jsonObject1.getBoolean("errorStatus");
                message = jsonObject1.getString("message");

                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                JSONArray jsonArray = jsonObject2.getJSONArray("businessList");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                    name = jsonObject3.getString("name");
                    imgurl = jsonObject3.getString("logo_image");
                    industryid = jsonObject3.getString("industry_id");
                    Log.d("ccccc7",industryid);

                    GetSet getSet = new GetSet(name, imgurl,industryid);
                    Log.d("NAME", name);
                    arrayList.add(getSet);
                    index++;

                    Log.d("INDEX", String.valueOf(index));
                }

                if (errorStatus == true) {

                    Log.d("TAG", message.toString());
                    Toast.makeText(BusinessProfile.this, "" + message, Toast.LENGTH_SHORT).show();
                } else {

                    Log.d("TAG", message.toString());
                    Toast.makeText(BusinessProfile.this, "" + message, Toast.LENGTH_SHORT).show();
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

            getALHint = getIntent().getIntExtra("Checked",0);
            Log.d("ccccc3",arrayList.size()+"");
            if(getALHint == 1){
                for(int i=0 ; i<arrayList.size() ; i++){

                    arrayListCheckedFilter = new ArrayList<>();

                    for(int j=0 ; j< FilterBusinessProfile.arrayListFilter.size(); j++){

                        GetSet getSet = arrayList.get(i);
                        ModelFilterList modelFilterList = FilterBusinessProfile.arrayListFilter.get(j);

                        String Indid1 = getSet.getIndustryid()+"";
                        String Indid2 = modelFilterList.getIndustryid()+"";
                        Log.d("ccccc2",Indid2);
                        Log.d("ccccc",Indid1);



                        if(Indid1.equalsIgnoreCase(Indid2)){
                            arrayListCheckedFilter.add(getSet);
                            DataAdapterRecyclerView adapterRecyclerView = new DataAdapterRecyclerView(arrayListCheckedFilter);

                            recyclerView.setAdapter(adapterRecyclerView);
                        }

                    }
                }
            }else {
                DataAdapterRecyclerView adapterRecyclerView = new DataAdapterRecyclerView(arrayList);

                recyclerView.setAdapter(adapterRecyclerView);
            }

            //ArrayList<GetSet> showItemList = new ArrayList<>();
            // int endIndex = index + 6;
//            for (int i = 0; i < arrayList.size(); i++) {
//
//                GetSet objGetset = arrayList.get(i);
//                tvMyBusinessName.setText(objGetset.getName());
//                ivMyBusinessLogo.setImageURI(Uri.parse(objGetset.getIngurl()));
//            }

            super.onPostExecute(s);
        }
    }
}
