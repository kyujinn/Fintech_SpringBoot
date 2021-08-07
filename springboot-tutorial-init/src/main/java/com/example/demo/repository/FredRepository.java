package com.example.demo.repository;

import com.example.demo.entity.FredData;
import com.example.demo.entity.id.FredId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface FredRepository extends CrudRepository<FredData, FredId> {
    public List<FredData> findAllBySeriesIdAndObservationDateAfterAndObservationDateBefore(String seriesId, String from, String to);
}
