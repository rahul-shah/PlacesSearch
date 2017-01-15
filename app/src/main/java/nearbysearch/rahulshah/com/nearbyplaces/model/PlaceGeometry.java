package nearbysearch.rahulshah.com.nearbyplaces.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceGeometry
{
    @SerializedName("location")
    @Expose
    private PlaceLocation location;

    public PlaceLocation getLocation() {
        return location;
    }
}
