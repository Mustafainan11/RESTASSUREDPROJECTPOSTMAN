package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {
    private String placename;
    private String longitude;
    private String latitude;
    private String state;
    private String stateabbreviation;

    public String getPlacename() {
        return placename;
    }

    @JsonProperty("place name")
    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateabbreviation() {
        return stateabbreviation;
    }

    @JsonProperty("state abbreviation")
    public void setStateabbreviation(String stateabbreviation) {
        this.stateabbreviation = stateabbreviation;
    }

    @Override
    public String toString() {
        return "Place{" +
                "placename='" + placename + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", state='" + state + '\'' +
                ", stateabbreviation='" + stateabbreviation + '\'' +
                '}';
    }
}
