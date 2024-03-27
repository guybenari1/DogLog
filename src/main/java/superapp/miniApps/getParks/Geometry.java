package superapp.miniApps.getParks;

import superapp.boundaries.LocationBoundary;

public class Geometry {
    private LocationBoundary location;

    public LocationBoundary getLocation() {
        return location;
    }

    public void setLocation(LocationBoundary location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Geometry [location=" + location + "]";
    }
}