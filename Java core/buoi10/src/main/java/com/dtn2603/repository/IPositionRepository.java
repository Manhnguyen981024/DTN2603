package com.dtn2603.repository;

import com.dtn2603.entity.Position;

import java.util.List;
import java.util.Set;

public interface IPositionRepository {
    List<Position> findAll();
    Position findById(Integer id);
    Set<Integer> findExistingIds(List<Integer> positionIds);
    int saveAll(List<Position> positions);
}
