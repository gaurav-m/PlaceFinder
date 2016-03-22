package com.example.gauravmittal.hsdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gauravmittal.hsdemo.places.Place;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class DB extends SQLiteOpenHelper {

    private static volatile SQLiteDatabase mDb;

    private static volatile DB _instance = null;

    private Context mContext;

    private DB(Context ctx) {
        super(ctx, DBConstants.FavDB, null, DBConstants.FavDbVersion);
        mDb = getWritableDatabase();
        mContext = ctx;
    }

    public static DB getInstance(Context context) {

        if (_instance == null) {
            synchronized (DB.class) {
                if (_instance == null)
                    _instance = new DB(context.getApplicationContext());
            }
        }
        return _instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db == null)
            db = mDb;


        String sql = "CREATE TABLE IF NOT EXISTS " + DBConstants.FAV_TABLE
                + " ( "
                + DBConstants.ID + " TEXT, "
                + DBConstants.ICON + " TEXT, "
                + DBConstants.NAME + " TEXT, "
                + DBConstants.PHOTO_METADATA + " TEXT, "
                + DBConstants.LAT + " TEXT, "
                + DBConstants.LONG + " TEXT "
                + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Place> getAllPlaces() {
        List<Place> list = new ArrayList<>();

        Cursor c = null;

        c = mDb.query(DBConstants.FAV_TABLE, null, null, null, null, null, null, null);

        int idIndex = c.getColumnIndex(DBConstants.ID);
        int nameIndex = c.getColumnIndex(DBConstants.NAME);
        int iconIndex = c.getColumnIndex(DBConstants.ICON);
        int latIndex = c.getColumnIndex(DBConstants.LAT);
        int longIndex = c.getColumnIndex(DBConstants.LONG);
        int photoIndex = c.getColumnIndex(DBConstants.PHOTO_METADATA);

        while (c.moveToNext()) {
            String id = c.getString(idIndex);
            String name = c.getString(nameIndex);
            String iconUrl = c.getString(iconIndex);
            double lat = Double.valueOf(c.getString(latIndex));
            double lng = Double.valueOf(c.getString(longIndex));
            String photoMetadata = c.getString(photoIndex);

            Place place = new Place(name, id, iconUrl, new Place.Geometry(new Place.ReverseGeocodeLocation(lat, lng)), photoMetadata);
            list.add(place);
        }

        return list;
    }

    public HashSet<String> getAllPlaceIds() {
        HashSet<String> hash = new HashSet<>();

        Cursor c = null;

        c = mDb.query(DBConstants.FAV_TABLE, new String[]{DBConstants.ID}, null, null, null, null, null, null);

        int idIndex = c.getColumnIndex(DBConstants.ID);

        while (c.moveToNext()) {
            String id = c.getString(idIndex);
            hash.add(id);
        }

        return hash;
    }

    public void addPlace(Place place) {
        ContentValues cv = new ContentValues();

        cv.put(DBConstants.ID, place.getId());
        cv.put(DBConstants.NAME, place.getName());
        cv.put(DBConstants.ICON, place.getIconUrl());
        cv.put(DBConstants.LAT, place.getGeometry().getLocation().getLat().toString());
        cv.put(DBConstants.LONG, place.getGeometry().getLocation().getLng().toString());
        cv.put(DBConstants.PHOTO_METADATA, place.getPhotoMetadata());

        mDb.insert(DBConstants.FAV_TABLE, null, cv);
    }

    public void removePlace(Place place) {
        mDb.delete(DBConstants.FAV_TABLE, DBConstants.ID + "=?", new String[]{place.getId()});
    }
}
