package backend.repository;

import java.util.List;

import entity.Position;

public interface IQLPosition {
    List<Position> findAll();
}
