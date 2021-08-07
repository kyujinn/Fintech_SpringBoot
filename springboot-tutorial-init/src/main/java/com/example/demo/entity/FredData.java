package com.example.demo.entity;

import com.example.demo.entity.id.FredId;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(FredId.class)
@AllArgsConstructor
public class FredData {
    @Id
    String seriesId;
    @Id
    String observationDate;

    Double value;
}
