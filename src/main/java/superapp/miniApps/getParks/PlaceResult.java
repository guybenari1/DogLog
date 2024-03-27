package superapp.miniApps.getParks;

import com.fasterxml.jackson.annotation.JsonProperty;

import superapp.boundaries.LocationBoundary;

public class PlaceResult {
    @JsonProperty("formatted_address")
    private String formattedAddress;
    private double rating;
    private Geometry geometry;
    private String name;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    //	public Geometry getGeometry() {
//		return geometry;
//	}
//
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public LocationBoundary getLocation() {
		return geometry.getLocation();
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PlaceResult{" +
                "formattedAddress=" + formattedAddress +
                ", geometry=" + geometry +
                ", name='" + name + '\'' +

                '}';
    }
}