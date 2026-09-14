package com.dtn2603.mapper;

import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.PositionName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionMapper {

    public static Position mapToPosition(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        return new Position(
            resultSet.getInt("position_id"),
                PositionName.valueOf(resultSet.getString("position_name"))
        );
    }
    
}
