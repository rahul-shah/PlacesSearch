package nearbysearch.rahulshah.com.nearbyplaces.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlacesSearchResults
{
    @SerializedName("geometry")
    @Expose
    private PlaceGeometry geometry;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("place_id")
    @Expose
    private String place_id;

    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    public PlaceGeometry getPlaceGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getVicinity() {
        return vicinity;
    }
}
