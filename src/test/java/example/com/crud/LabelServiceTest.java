package example.com.crud;


import example.com.crud.service.LabelService;
import org.junit.*;
import org.mockito.Mockito;

import example.com.crud.model.Label;
import example.com.crud.repository.LabelRepository;
import example.com.crud.repository.impl.DBLabelRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LabelServiceTest {

    private LabelRepository labelRepositoryMock;
    LabelService labelService;
    private Label label;

    @Before
    public void setUp() {
        labelRepositoryMock = Mockito.mock(DBLabelRepositoryImpl.class);
        labelService = new LabelService(labelRepositoryMock);
        label = new Label(1L, "Life");
    }

    @Test
    public void savedLabel_Should_Success() {
        when(labelRepositoryMock.save(label)).thenReturn(label);

        Label savedLabel = labelRepositoryMock.save(label);
        assertNotNull(savedLabel.getNameLabel());
    }

    @Test(expected = SQLException.class)
    public void savedLabel_Should_Throw_Exception() {

        when(labelRepositoryMock.save(any(Label.class))).thenThrow(SQLException.class);

        labelRepositoryMock.save(new Label());

    }

    @Test
    public void getById_Should_Success() throws SQLException {
        when(labelRepositoryMock.getById(anyLong())).thenReturn(label);
        Label getLabel = labelRepositoryMock.getById(3L);

        assertEquals(label.getNameLabel(), getLabel.getNameLabel());
    }

    @Test(expected = SQLException.class)
    public void getById_Should_Throw_Exception() throws SQLException {
        when(labelRepositoryMock.getById(anyLong())).thenThrow(SQLException.class);
        labelRepositoryMock.getById(56L);

    }

    @Test
    public void deleteById_Should_True() {
        when(labelRepositoryMock.delete(anyLong())).thenReturn(true);

        boolean checkDeletedLabel = labelRepositoryMock.delete(2L);

        assertTrue(checkDeletedLabel);
    }

    @Test
    public void deleteById_Should_False() {
        when(labelRepositoryMock.delete(anyLong())).thenReturn(false);

        boolean checkDeletedLabel = labelRepositoryMock.delete(2L);

        assertFalse(checkDeletedLabel);
    }

    @Test(expected = SQLException.class)
    public void deleteById_Should_Throw_Exception() {
        when(labelRepositoryMock.delete(anyLong())).thenThrow(SQLException.class);

        labelRepositoryMock.delete(5L);
    }

    @Test
    public void updatedLabel_Should_Success() {
        when(labelRepositoryMock.update(any(Label.class))).thenReturn(label);

        Label updatedLabel = labelRepositoryMock.update(label);

        assertNotNull(updatedLabel);

        assertSame(label, updatedLabel);
    }

    @Test(expected = SQLException.class)
    public void updatedLabel_Should_Throw_Exception() {

        when(labelRepositoryMock.update(any(Label.class))).thenThrow(SQLException.class);

        labelRepositoryMock.update(label);
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

        List<Label> getAllLabels = labelRepositoryMock.getAll();

        assertNotNull(getAllLabels);

        assertSame(labelsList, getAllLabels);

        assertEquals(getAllLabels.get(2), new Label(3L, "Relax"));

        assertArrayEquals(labelsList.stream().toArray(), getAllLabels.stream().toArray());

    }

    @Test(expected = SQLException.class)
    public void getAllLabels_Should_Throw_Exception() {

        when(labelRepositoryMock.getAll()).thenThrow(SQLException.class);

        labelRepositoryMock.getAll();
    }
}
