package com.example.gauravmittal.hsdemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gauravmittal.hsdemo.R;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class LocationAddressView extends RelativeLayout {

    TextView searchTitle;

    TextView currentLocation;

    public LocationAddressView(Context context) {
        super(context);
        init();
    }

    public LocationAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LocationAddressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public LocationAddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_address_view, this, true);
        searchTitle = (TextView) view.findViewById(R.id.search_title);
        currentLocation = (TextView) view.findViewById(R.id.current_location);
    }

    public void setSearchTitle(String title) {
        searchTitle.setText(title);
    }

    public void setLocation(String title) {
        currentLocation.setText(title);
    }
}
