package com.dtn2603.controller;

import com.dtn2603.entity.Position;
import com.dtn2603.exception.DataAccessException;
import com.dtn2603.service.IPositionService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PositionController {
    private final IPositionService positionService;

    public List<Position> getAllPosition() {
        try {
            return positionService.getPositions();
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
        return List.of();
    }
}
