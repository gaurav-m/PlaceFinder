package com.example.gauravmittal.hsdemo.places;

/**
 * Created by gauravmittal on 20/03/16.
 */
public enum PlaceType {

    FOOD {
        @Override
        public String toString() {
            return "food";
        }
    },
    GYM {
        @Override
        public String toString() {
            return "gym";
        }
    },
    SPA {
        @Override
        public String toString() {
            return "spa";
        }
    },
    SCHOOL {
        @Override
        public String toString() {
            return "school";
        }
    },
    RESTAURANT {
        @Override
        public String toString() {
            return "restaurant";
        }
    },
    HOSPITAL {
        @Override
        public String toString() {
            return "hospital";
        }
    };

}
