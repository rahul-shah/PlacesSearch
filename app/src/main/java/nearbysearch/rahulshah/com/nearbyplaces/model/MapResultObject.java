package nearbysearch.rahulshah.com.nearbyplaces.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MapResultObject
{
    @SerializedName("results")
    @Expose
    private ArrayList<PlacesSearchResults> results;

    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<PlacesSearchResults> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
