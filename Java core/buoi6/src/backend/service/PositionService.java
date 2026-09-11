package backend.service;

import java.util.Comparator;
import java.util.List;

import backend.repository.IQLPosition;
import entity.Position;

public class PositionService {
    private final IQLPosition qlPosition;

    public PositionService(IQLPosition qlPosition){
        this.qlPosition = qlPosition;
    }

    public List<Position> findAll(){
        return qlPosition.findAll()
                .stream()
                .sorted(Comparator.comparing(Position::getPositionId))
                .toList();
    }

    public Position createPosition(Position position) {
        validatePosition(position);
        return qlPosition.save(position);
    }

    public Position updatePosition(Position position) {
        if (position == null || position.getPositionId() == null || position.getPositionId() <= 0) {
            throw new IllegalArgumentException("Position id must be greater than 0");
        }
        validatePosition(position);
        return qlPosition.update(position);
    }

    public void deletePosition(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Position id must be greater than 0");
        }
        qlPosition.deleteById(id);
    }

    private void validatePosition(Position position) {
        if (position == null || position.getPositionName() == null) {
            throw new IllegalArgumentException("Position name cannot be null");
        }
    }
}
