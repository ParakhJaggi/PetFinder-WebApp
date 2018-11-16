package petfinder.site.common;

public class CustomGeoPoint {
    double lat, lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public CustomGeoPoint(Double lat, Double lon) {
        this.lat =lat;
        this.lon = lon;
    }
    public CustomGeoPoint (CustomGeoPoint other){
        this.lat = other.getLat();
        this.lon = other.getLon();
    }

    public CustomGeoPoint() {
        this.lat = 0.0;
        this.lon = 0.0;
    }
}
