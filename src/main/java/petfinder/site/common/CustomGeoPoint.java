package petfinder.site.common;

public class CustomGeoPoint {
    Double lat, lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public CustomGeoPoint(Double lat, Double lon) {
        this.lat = new Double(lat);
        this.lon = new Double(lon);
    }
    public CustomGeoPoint (CustomGeoPoint other){
        this.lat = new Double(other.getLat());
        this.lon = new Double(other.getLon());
    }

    public CustomGeoPoint() {
        this.lat = new Double(0.0);
        this.lon = new Double(0.0);
    }
}
