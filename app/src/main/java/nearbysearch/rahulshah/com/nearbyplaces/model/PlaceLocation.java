package nearbysearch.rahulshah.com.nearbyplaces.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceLocation
{
    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    public Double getLat() {
        return Double.valueOf(lat);
    }

    public Double getLng() {
        return Double.valueOf(lng);
    }
}
