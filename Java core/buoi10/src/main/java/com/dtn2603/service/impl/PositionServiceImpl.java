package com.dtn2603.service.impl;

import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.PositionName;
import com.dtn2603.repository.IPositionRepository;
import com.dtn2603.service.IPositionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

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

    @Override
    public Set<Integer> findExistingPositionIds(List<Integer> positionIds) {
        return positionRepository.findExistingIds(positionIds);
    }

    @Override
    public int getRequiredFieldCount() {
        return 1;
    }

    @Override
    public String getErrorLogFileName() {
        return "insert_position_error_log.csv";
    }

    @Override
    public Position parseFields(List<String> fields) {
        String nameValue = fields.get(0).trim();
        try {
            return new Position(PositionName.valueOf(nameValue));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Position name must be one of "
                    + Arrays.toString(PositionName.values()) + ": " + fields.get(1));
        }
    }

    @Override
    public String validateFields(Position item) {
        if (item.getPositionName() == null) {
            return "Position name must not be empty";
        }
        return null;
    }

    @Override
    public Map<String, Set<String>> loadContext(List<Position> items) {
        List<Position> all = positionRepository.findAll();
        Map<String, Set<String>> context = new HashMap<>();
        context.put("positionName", all.stream()
                .map(Position::getPositionName)
                .map(name -> name.name())
                .collect(Collectors.toSet()));
        return context;
    }

    @Override
    public Map<String, String> extractKeys(Position item) {
        Map<String, String> keys = new HashMap<>();
        keys.put("positionName", item.getPositionName().name());
        return keys;
    }

    @Override
    public String validateUniqueKeys(Position item, Map<String, Set<String>> context,
                                     Map<String, Set<String>> seenKeys) {
        return null;
    }

    @Override
    public int saveAll(List<Position> items) {
        return positionRepository.saveAll(items);
    }
}