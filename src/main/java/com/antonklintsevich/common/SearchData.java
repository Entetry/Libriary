package com.antonklintsevich.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchData  implements Serializable{
    @JsonProperty
public String name;
    @JsonProperty
public String sortOrder;
}
