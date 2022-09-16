package example.com.crud.controller;


import example.com.crud.service.LabelService;
import example.com.crud.model.Label;

import java.sql.SQLException;
import java.util.List;

public class LabelController {
    private final LabelService labelService;

    public LabelController() {
        this.labelService = new LabelService();
    }

    public List<Label> onShowAll() {
        return labelService.getAll();
    }

    public void onCreate(String nameLabel) {
        Label label = new Label(null, nameLabel);
        labelService.save(label);
    }

    public Label getById(Long id) throws SQLException {
        return labelService.getById(id);
    }

    public Label onUpdate(Long id, String nameLabel) {
        Label label = new Label(id, nameLabel);
        return labelService.update(label);
    }

    public boolean onDelete(Long id) {
        return labelService.delete(id);
    }

}
