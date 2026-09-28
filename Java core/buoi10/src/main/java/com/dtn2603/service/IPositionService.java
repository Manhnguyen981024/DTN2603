package com.dtn2603.service;

import com.dtn2603.entity.Position;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IPositionService extends ICsvImportService<Position> {
    List<Position> getPositions();
    Position getPositionById(Integer id);
    Set<Integer> findExistingPositionIds(List<Integer> positionIds);

    default Map<String, Integer> importPositionFromCSV(String filename) {
        return importCSV(filename);
    }
}