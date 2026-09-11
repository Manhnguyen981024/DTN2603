package backend.repository;

import java.util.List;

import entity.Position;

public interface IQLPosition {
    List<Position> findAll();
    Position findById(Integer id);
    Position save(Position position);
    Position update(Position position);
    void deleteById(Integer id);
}
