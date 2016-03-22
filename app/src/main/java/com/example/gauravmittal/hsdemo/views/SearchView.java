package com.example.gauravmittal.hsdemo.views;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.gauravmittal.hsdemo.R;

/**
 * Created by gauravmittal on 03/10/15.
 */
public class SearchView extends RelativeLayout {

    ImageView searchIcon;

    ImageView deleteIcon;

    EditText searchBox;


    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_search_view, this, true);

        deleteIcon = (ImageView) view.findViewById(R.id.delete_text_icon);
        ImageView searchIcon = (ImageView) view.findViewById(R.id.searchview_icon);
        searchBox = (EditText) view.findViewById(R.id.et_search);

        deleteIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBox.setText("");
            }
        });
    }

    public void setTextChangeListener(TextWatcher textWatcher) {
        searchBox.addTextChangedListener(textWatcher);
    }

    public void setDeleteIconVisibility(boolean visibility) {
        if (visibility) {
            deleteIcon.setVisibility(VISIBLE);
        } else {
            deleteIcon.setVisibility(GONE);
        }
    }
}
