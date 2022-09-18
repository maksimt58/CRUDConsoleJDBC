package example.com.crud;

import example.com.crud.model.Writer;
import example.com.crud.service.WriterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import example.com.crud.repository.WriterRepository;
import example.com.crud.repository.impl.DBWriterRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WriterServiceTest {
    private WriterRepository writerRepositoryMock;
    private WriterService writerService;


    private Writer getDefaultWriter() {
        return new Writer(1L, "Maks", "Tata");
    }

    @Before
    public void setUp() {
        writerRepositoryMock = Mockito.mock(DBWriterRepositoryImpl.class);
        writerService = new WriterService(writerRepositoryMock);
    }

    @Test
    public void saveWriter_Should_Success() {

        when(writerRepositoryMock.save(any(Writer.class))).thenReturn(getDefaultWriter());

        Writer savedWriter = writerService.save(getDefaultWriter());

        assertNotNull(savedWriter);
        assertEquals(savedWriter, getDefaultWriter());
        assertEquals("Maks", getDefaultWriter().getFirstName());
    }

    @Test(expected = SQLException.class)
    public void saveWriter_Should_Throw_Exception() {
        when(writerRepositoryMock.save(any(Writer.class))).thenThrow(SQLException.class);

        writerService.save(getDefaultWriter());
    }

    @Test
    public void getById_Should_Success() throws SQLException {
        when(writerRepositoryMock.getById(anyLong())).thenReturn(getDefaultWriter());

        Writer getWriter = writerService.getById(1L);

        assertNotNull(getWriter);

        assertEquals("Tata", getWriter.getLastName());
    }

    @Test(expected = SQLException.class)
    public void getById_Should_Throw_Exception() throws SQLException {
        when(writerRepositoryMock.getById(anyLong())).thenThrow(SQLException.class);

        writerService.getById(2L);
    }

    @Test
    public void deleteById_Should_True() {
        when(writerRepositoryMock.delete(anyLong())).thenReturn(true);

        boolean checkDeletedWriter = writerService.delete(3L);

        assertTrue(checkDeletedWriter);
    }

    @Test
    public void deleteById_Should_False() {
        when(writerRepositoryMock.delete(anyLong())).thenReturn(false);

        boolean checkDeletedWriter = writerService.delete(3L);

        assertFalse(checkDeletedWriter);
    }

    @Test(expected = SQLException.class)
    public void deleteById_Should_Throw_Exception() {
        when(writerRepositoryMock.delete(anyLong())).thenThrow(SQLException.class);

        writerService.delete(2L);
    }

    @Test
    public void updatedWriter_Should_Success() {
        when(writerRepositoryMock.update(any(Writer.class))).thenReturn(getDefaultWriter());

        Writer writerUpdated = writerService.update(getDefaultWriter());

        assertNotNull(writerUpdated);
    }

    @Test(expected = SQLException.class)
    public void updateWriter_Should_Throw_Exception() {
        when(writerRepositoryMock.update(any(Writer.class))).thenThrow(SQLException.class);

        writerService.update(getDefaultWriter());
    }

    @Test
    public void getAllWriters_Should_Success() {
        List<Writer> writersList = List.of(
                new Writer(1L, "Maks", "Tata"),
                new Writer(2L, "Nata", "Tata"),
                new Writer(3L, "Eugene", "Suleimanov"),
                new Writer(4L, "John", "Dou"),
                new Writer(5L, "Foo", "Bar")
        );

        when(writerRepositoryMock.getAll()).thenReturn(writersList);

        List<Writer> getAllWriters = writerService.getAll();

        assertNotNull(getAllWriters);

        assertSame(writersList, getAllWriters);

        assertEquals(getAllWriters.get(2), new Writer(3L, "Eugene", "Suleimanov"));

        assertArrayEquals(writersList.stream().toArray(), getAllWriters.stream().toArray());

    }

    @Test(expected = SQLException.class)
    public void getAllWriters_Should_Throw_Exception() {
        when(writerRepositoryMock.getAll()).thenThrow(SQLException.class);

        writerService.getAll();
    }
}
