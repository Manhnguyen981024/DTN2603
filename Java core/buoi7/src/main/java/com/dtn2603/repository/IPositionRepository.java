package com.dtn2603.repository;

import com.dtn2603.entity.Position;

import java.util.List;

public interface IPositionRepository {
    List<Position> findAll();
}
