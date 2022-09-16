package example.com.crud.controller;

import example.com.crud.service.WriterService;
import example.com.crud.model.Writer;

import java.sql.SQLException;
import java.util.List;

public class WriterController{
    private final WriterService writerService;

    public WriterController(){
        this.writerService = new WriterService();
    }

    public List<Writer> onShowAll() {
        return writerService.getAll();
    }

    public void onCreate(String firstName, String lastName) {
        Writer writer = new Writer(null, firstName, lastName);
        writerService.save(writer);
    }

    public Writer getById(Long id) throws SQLException {
        return writerService.getById(id);
    }

    public Writer onUpdate(Long id, String firstName, String lastName) {
        Writer writer = new Writer(id, firstName, lastName);
        return writerService.update(writer);
    }

    public boolean onDelete(Long id) {
        return writerService.delete(id);
    }

}
