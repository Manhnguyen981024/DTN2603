package com.dtn2603.service.impl;

import com.dtn2603.entity.Position;
import com.dtn2603.repository.IPositionRepository;
import com.dtn2603.service.IPositionService;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class PositionServiceImpl implements IPositionService {
    private final IPositionRepository positionRepository;

    @Override
    public List<Position> getPositions() {
        return positionRepository.findAll()
                .stream().sorted(Comparator.comparing(Position::getPositionId)).toList();
    }

    @Override
    public Position getPositionById(Integer id) {
        return positionRepository.findById(id);
    }
}
