package com.matius.indodess;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AllCryptosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.matius.indodess.AllCryptoAdapter adapter;
    private List<Currency> currencyList;

    ArrayList<String> names = new ArrayList<>();
    String BASE_URL = "https://min-api.cryptocompare.com";
    String IMAGE_URL= "https://www.cryptocompare.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_crypto);
        recyclerView = findViewById(R.id.recycler_view);

        currencyList = new ArrayList<>();
        adapter = new com.matius.indodess.AllCryptoAdapter(this, currencyList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        String url = BASE_URL + "/data/top/totalvolfull?limit=20&tsym=USD";
        prepareCurrencies(url);

    }

    private void prepareCurrencies(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray js = response.getJSONArray("Data");
                    for (int i=0; i < js.length(); i++) {
                        JSONObject display = js.getJSONObject(i).getJSONObject("DISPLAY").getJSONObject("USD");
                        String image = IMAGE_URL + display.getString("IMAGEURL");
                        JSONObject coinInfo = js.getJSONObject(i).getJSONObject("CoinInfo");
                        names.add(coinInfo.getString("FullName"));

                        Currency c = new Currency(coinInfo.getString("FullName"), coinInfo.getString("Name"), display.getString("PRICE"),
                                display.getString("LOWDAY"), display.getString("HIGHDAY"),
                                display.getString("OPENDAY"), image, coinInfo.getString("Algorithm"));
                        currencyList.add(c);

                        Log.i("nnnn: ", ""+i );
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("******", "Error");
            }
        });

        queue.add(jsObjRequest);

    }

}
