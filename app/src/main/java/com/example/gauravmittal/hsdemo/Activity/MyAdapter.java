package com.example.gauravmittal.hsdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gauravmittal.hsdemo.Image.ImageManager;
import com.example.gauravmittal.hsdemo.R;
import com.example.gauravmittal.hsdemo.db.DB;
import com.example.gauravmittal.hsdemo.places.Place;

import java.util.HashSet;
import java.util.List;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Place> places;
    HashSet<String> markedPlaces;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        public ImageView nav;
        public ImageView mark;
        public TextView title;
        public TextView imageSub;

        public ViewHolder(View parentView, ImageView image, TextView title, TextView imageSub, ImageView mark, ImageView nav) {
            super(parentView);
            this.image = image;
            this.title = title;
            this.imageSub = imageSub;
            this.nav = nav;
            this.mark = mark;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Place> myDataset, Context ctx) {
        places = myDataset;
        this.mContext = ctx;
        markedPlaces = DB.getInstance(ctx).getAllPlaceIds();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ImageView i = (ImageView) v.findViewById(R.id.image);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView imageSub = (TextView) v.findViewById(R.id.txt_image);
        ImageView nav = (ImageView) v.findViewById(R.id.btn_nav);
        nav.setOnClickListener(navClick);
        ImageView mark = (ImageView) v.findViewById(R.id.btn_mark);
        mark.setOnClickListener(markClick);


        ViewHolder vh = new ViewHolder(v, i, title, imageSub, mark, nav);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Place place = places.get(position);
        holder.title.setText(place.getName());
        holder.imageSub.setText(place.getName().substring(0, 1));
        ImageManager.getInstance(mContext).loadImage(place.getPhotoUrl(), holder.image, holder.imageSub);
        holder.mark.setSelected(markedPlaces.contains(place.getId()));

        holder.mark.setTag(place);
        holder.nav.setTag(place);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return places.size();
    }

    View.OnClickListener navClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Place place = (Place) view.getTag();

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr="
                            + place.getGeometry().getLocation().getLat().toString()
                            +","
                            +place.getGeometry().getLocation().getLng().toString()
            ));
            mContext.startActivity(intent);
        }
    };

    View.OnClickListener markClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Place place = (Place) view.getTag();

            if (markedPlaces.contains(place.getId()))
            {
                DB.getInstance(mContext).removePlace(place);
                markedPlaces.remove(place.getId());
            }
            else
            {
                DB.getInstance(mContext).addPlace(place);
                markedPlaces.add(place.getId());
            }
            notifyDataSetChanged();
        }
    };
}