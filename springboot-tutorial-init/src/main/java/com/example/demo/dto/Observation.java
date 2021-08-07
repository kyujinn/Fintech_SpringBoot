package com.example.demo.dto;

import com.example.demo.entity.FredData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Observation {
    String date;
    Double value;

    public static Observation fromFredData(FredData fredData){
        return new Observation(fredData.getObservationDate(), fredData.getValue());
    }

//    public Observation(String date, Double value) {
//    }
}
