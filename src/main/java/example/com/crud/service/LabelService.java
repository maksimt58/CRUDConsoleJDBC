package example.com.crud.service;

import example.com.crud.model.Label;
import example.com.crud.repository.LabelRepository;
import example.com.crud.repository.impl.DBLabelRepositoryImpl;
import java.sql.SQLException;
import java.util.List;


public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService() {
        this.labelRepository = new DBLabelRepositoryImpl();
    }

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label getById(Long id) throws SQLException {
        return labelRepository.getById(id);
    }

    public boolean delete(Long id) {
        return labelRepository.delete(id);
    }

    public Label update(Label label) {
        return labelRepository.update(label);
    }

    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    public Label save(Label label) {
        return labelRepository.save(label);
    }
}
