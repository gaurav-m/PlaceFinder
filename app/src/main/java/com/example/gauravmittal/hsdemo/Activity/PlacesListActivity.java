package com.example.gauravmittal.hsdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gauravmittal.hsdemo.R;
import com.example.gauravmittal.hsdemo.api.googleApi.GoogleRestClient;
import com.example.gauravmittal.hsdemo.api.googleApi.response.PlacesResponse;
import com.example.gauravmittal.hsdemo.db.DB;
import com.example.gauravmittal.hsdemo.location.DeviceLocation;
import com.example.gauravmittal.hsdemo.places.Place;
import com.example.gauravmittal.hsdemo.places.PlaceType;
import com.example.gauravmittal.hsdemo.places.PlacesRequirement;

import java.util.List;
import java.util.logging.Logger;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class PlacesListActivity extends AppCompatActivity {

    private View emptyView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.placeList);
        emptyView = findViewById(R.id.empty_view);
        setupList();
        fetchData();

    }

    private void fetchData()
    {
        Intent i = getIntent();
        if (i.hasExtra("bookmarks"))
        {
            setupAdapter(DB.getInstance(this).getAllPlaces());
        }
        else {
            DeviceLocation location = i.getParcelableExtra("location");
            int radius = i.getIntExtra("radius", 5000);
            PlaceType pt = PlaceType.valueOf(i.getStringExtra("type"));

            PlacesRequirement body = new PlacesRequirement(pt, radius, location);
            GoogleRestClient client = GoogleRestClient.with(this);
            client.getPlacesFromLocation(body, new Callback<PlacesResponse>() {
                @Override
                public void success(PlacesResponse placesResponse, Response response) {
                    if (placesResponse.getStatus().equals("OK")) {
                        setupAdapter(placesResponse.getPlacesList());
                    } else {
                        errorNRetry();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    errorNRetry();
                }
            });
        }
    }

    private void setupList()
    {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setEmptyView(true);

    }

    private void setupAdapter(List<Place> myDataset)
    {
        mAdapter = new MyAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView(myDataset.isEmpty());
    }

    private void setEmptyView(boolean set)
    {
        if (set)
        {
            emptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else
        {
            emptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void errorNRetry()
    {
        Toast.makeText(PlacesListActivity.this,"Something went wrong. Retrying...",Toast.LENGTH_SHORT).show();
        if (isDestroyed() || isFinishing())
        {
            return;
        }
        fetchData();
    }


}
