package backend.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Position;
import entity.enums.PositionName;

public class PositionMapper {

    public static Position mapperToPosition(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        return new Position(resultSet.getInt("position_id"), PositionName.valueOf(resultSet.getString("position_name")));
    }
    
}
