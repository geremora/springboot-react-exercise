package com.cayuse.coding.geremora.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class ResponseGElevationAPI {

    private Double elevation;

    private String status;

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("results")
    private void unpackNestedCoord(List<Map<String,Object>> results) {
        try {
            this.elevation = (Double) results.get(0).get("elevation");
        }catch (Exception e){

        }
    }

}
