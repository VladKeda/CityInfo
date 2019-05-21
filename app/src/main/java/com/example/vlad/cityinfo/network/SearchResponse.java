package com.example.vlad.cityinfo.network;

import java.util.List;

public class SearchResponse {
    private List<CityInfo> geonames;

    public List<CityInfo> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<CityInfo> geonames) {
        this.geonames = geonames;
    }
}
