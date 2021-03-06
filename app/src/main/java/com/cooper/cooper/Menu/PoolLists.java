package com.cooper.cooper.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cooper.cooper.R;
import com.cooper.cooper.Utils;
import com.cooper.cooper.http_requests.GetRequests;
import com.cooper.cooper.http_requests.HTTPRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PoolLists extends AppCompatActivity implements AdapterView.OnItemClickListener, HTTPRequestListener{
    @Override
    public void requestDone(Object object, int statusCode) {

    }

    private JSONArray pool_list;
    private ListView listview_pools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_list);


        this.pool_list = new JSONArray();

        try {
                GetRequests get_pool_list = new GetRequests(this);
                get_pool_list.execute(Utils.URL + "/profile/pools");

                JSONObject response = get_pool_list.get();
                this.pool_list = new JSONArray(response.getString("response"));
                /*for (int i = 0; i < pool_list.length() ; i++) {
                    JSONObject object = pool_list.getJSONObject(i);
                    this.makePool(object.getJSONObject("node"));
                    Log.d("Key", object.toString());
                }*/
                //JSONObject pool_list = new JSONObject(response.getString("response"));

                Log.d("response get pool list", pool_list.toString());
        } catch (Exception e) {
            Log.d("Get Pool List Error", e.toString());
        }


        this.listview_pools = (ListView) findViewById(R.id.list_pools);


        PoolListAdapter poolAdapter = new PoolListAdapter(pool_list, this);
        listview_pools.setAdapter(poolAdapter);
        listview_pools.setOnItemClickListener(this);
    }

    public void makePool(JSONObject node) throws Exception {
        String pool_name = node.getString("name");
        String pool_label = node.getString("label");
        String payment = node.getString("paymentMethod");
        boolean isPrivate = node.getBoolean("private");
        String currency = node.getString("currency");
        String invitation_code = node.getString("invite");
        String ends = node.getString("ends");
        int id = node.getInt("_id");
        double total = node.getDouble("total");

        Pool new_pool = new Pool();
            new_pool.setName(pool_name);
            new_pool.setPayment_method(payment);
            new_pool.setIs_private(isPrivate);
            new_pool.setId(id);
            new_pool.setCurrency(currency);
            new_pool.setInvitation_code(invitation_code);
            new_pool.setEnd_date(ends);
            new_pool.setTotal(total);
        //this.pool_list.add(new_pool);
    }
    public void createPool(View v){
        Intent intent = new Intent(this, CreateCoops_Act.class);
        this.startActivity(intent);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("ID pool", l+"");
        Intent intent = new Intent(this, Coop_Detail_Act.class);
        intent.putExtra("pool", l);
        this.startActivity(intent);
    }
}
