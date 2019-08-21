package com.antonklintsevich.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchParameters  implements Serializable{
    @JsonProperty
private List<SortData> searchData=new ArrayList<>();
    @JsonProperty
private List<FilterData> filterData=new ArrayList<>();
public List<SortData> getSearchData() {
    return searchData;
}

public void setSearchData(List<SortData> searchData) {
    this.searchData = searchData;
}

public List<FilterData> getFilterData() {
    return filterData;
}

public void setFilterData(List<FilterData> filterData) {
    this.filterData = filterData;
}

}
