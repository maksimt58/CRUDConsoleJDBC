package example.com.crud.service;

import example.com.crud.model.Writer;
import example.com.crud.repository.WriterRepository;
import example.com.crud.repository.impl.DBWriterRepositoryImpl;
import java.sql.SQLException;
import java.util.List;


public class WriterService {
    private final WriterRepository writerRepository;

    public WriterService() {
        this.writerRepository = new DBWriterRepositoryImpl();
    }

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer getById(Long id) throws SQLException {
        return writerRepository.getById(id);
    }

    public boolean delete(Long id) {
        return writerRepository.delete(id);
    }

    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }
}
