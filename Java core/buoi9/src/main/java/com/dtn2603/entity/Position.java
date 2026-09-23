package com.dtn2603.entity;

import com.dtn2603.entity.enums.PositionName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private Integer positionId;
    private PositionName positionName;

    public Position(Integer positionId) {
        this.positionId = positionId;
    }
}
