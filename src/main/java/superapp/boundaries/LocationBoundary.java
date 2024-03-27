package superapp.boundaries;

public class LocationBoundary {
    private Float lat;
    private Float lng;

    public LocationBoundary() {
    }

    public LocationBoundary(Float lat, Float lng) {
        super();
        this.lat = lat;
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LocationBoundary{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
