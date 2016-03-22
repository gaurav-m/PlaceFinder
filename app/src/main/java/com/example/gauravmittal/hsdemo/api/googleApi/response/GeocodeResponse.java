package com.example.gauravmittal.hsdemo.api.googleApi.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gauravmittal on 20/03/16.
 */
public class GeocodeResponse {

    @SerializedName("results")
    private List<Address> adresssList;

    public List<Address> getAdresssList() {
        return adresssList;
    }

    public class Address {
        @SerializedName("address_components")
        private List<AddressComponent> addressComponents;

        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }
    }

    public class AddressComponent {

        @SerializedName("long_name")
        private String componentAddress;

        public String getComponentAddress() {
            return componentAddress;
        }
    }
}
