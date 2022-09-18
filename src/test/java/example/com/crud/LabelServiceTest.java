package example.com.crud;

import example.com.crud.service.LabelService;
import org.junit.*;
import org.mockito.Mockito;

import example.com.crud.model.Label;
import example.com.crud.repository.LabelRepository;
import example.com.crud.repository.impl.DBLabelRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LabelServiceTest {

    private LabelRepository labelRepositoryMock;
    private LabelService labelService;

    private Label getDefaultLabel() {
        return new Label(1L, "Life");
    }

    @Before
    public void setUp() {
        labelRepositoryMock = Mockito.mock(DBLabelRepositoryImpl.class);
        labelService = new LabelService(labelRepositoryMock);
    }

    @Test
    public void savedLabel_Should_Success() {
        when(labelRepositoryMock.save(any(Label.class))).thenReturn(getDefaultLabel());

        Label savedLabel = labelService.save(getDefaultLabel());

        assertNotNull(savedLabel);
    }

    @Test(expected = SQLException.class)
    public void savedLabel_Should_Throw_Exception() {

        when(labelRepositoryMock.save(any(Label.class))).thenThrow(SQLException.class);

        labelService.save(getDefaultLabel());

    }

    @Test
    public void getById_Should_Success() throws SQLException {
        when(labelRepositoryMock.getById(anyLong())).thenReturn(getDefaultLabel());
        Label getLabel = labelService.getById(3L);

        assertNotNull(getLabel);

        assertEquals(getLabel, getDefaultLabel());
    }

    @Test(expected = SQLException.class)
    public void getById_Should_Throw_Exception() throws SQLException {
        when(labelRepositoryMock.getById(anyLong())).thenThrow(SQLException.class);

        labelService.getById(5L);
    }

    @Test
    public void deleteById_Should_True() {
        when(labelRepositoryMock.delete(anyLong())).thenReturn(true);

        boolean checkDeletedLabel = labelService.delete(2L);

        assertTrue(checkDeletedLabel);
    }

    @Test
    public void deleteById_Should_False() {
        when(labelRepositoryMock.delete(anyLong())).thenReturn(false);

        boolean checkDeletedLabel = labelService.delete(2L);

        assertFalse(checkDeletedLabel);
    }

    @Test(expected = SQLException.class)
    public void deleteById_Should_Throw_Exception() {
        when(labelRepositoryMock.delete(anyLong())).thenThrow(SQLException.class);

        labelService.delete(5L);
    }

    @Test
    public void updatedLabel_Should_Success() {
        when(labelRepositoryMock.update(any(Label.class))).thenReturn(getDefaultLabel());

        Label updatedLabel = labelService.update(getDefaultLabel());

        assertNotNull(updatedLabel);

        assertEquals(updatedLabel, getDefaultLabel());
    }

    @Test(expected = SQLException.class)
    public void updatedLabel_Should_Throw_Exception() {

        when(labelRepositoryMock.update(any(Label.class))).thenThrow(SQLException.class);

        labelService.update(getDefaultLabel());
    }

    @Test
    public void getAllLabels_Should_Success() {
        List<Label> labelsList = List.of(
                new Label(1L, "Life"),
                new Label(2L, "Work"),
                new Label(3L, "Relax"),
                new Label(4L, "Travel"),
                new Label(5L, "Hobby")
        );

        when(labelRepositoryMock.getAll()).thenReturn(labelsList);

        List<Label> getAllLabels = labelService.getAll();

        assertNotNull(getAllLabels);

        assertSame(labelsList, getAllLabels);

        assertEquals(getAllLabels.get(2), new Label(3L, "Relax"));

        assertArrayEquals(labelsList.stream().toArray(), getAllLabels.stream().toArray());

    }

    @Test(expected = SQLException.class)
    public void getAllLabels_Should_Throw_Exception() {

        when(labelRepositoryMock.getAll()).thenThrow(SQLException.class);

        labelService.getAll();
    }
}
