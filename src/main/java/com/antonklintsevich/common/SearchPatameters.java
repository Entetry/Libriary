package com.antonklintsevich.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchPatameters  implements Serializable{
    @JsonProperty
private List<SearchData> searchData=new ArrayList<>();

public List<SearchData> getSearchData() {
    return searchData;
}

public void setSearchData(List<SearchData> searchData) {
    this.searchData = searchData;
}

}
