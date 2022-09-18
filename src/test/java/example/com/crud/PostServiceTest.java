package example.com.crud;


import example.com.crud.model.Writer;
import example.com.crud.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import example.com.crud.model.Post;
import example.com.crud.model.PostStatus;
import example.com.crud.repository.PostRepository;
import example.com.crud.repository.impl.DBPostRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {
    private PostRepository postRepositoryMock;
    private PostService postService;

    private Post getDefaultPost() {
        return new Post(1L, "Java11", PostStatus.ACTIVE, new ArrayList<>(), new Writer(2L, "Nata", "Tata"));
    }

    @Before
    public void setUp() {
        postRepositoryMock = Mockito.mock(DBPostRepositoryImpl.class);
        postService = new PostService(postRepositoryMock);
    }

    @Test
    public void savePost_Should_Success() {

        when(postRepositoryMock.save(any(Post.class))).thenReturn(getDefaultPost());

        Post savedPost = postService.save(getDefaultPost());

        assertNotNull(savedPost);
        assertEquals(savedPost, getDefaultPost());
        assertEquals("Java11", savedPost.getContent());
    }

    @Test(expected = SQLException.class)
    public void savePost_Should_Throw_Exception() {
        when(postRepositoryMock.save(any(Post.class))).thenThrow(SQLException.class);

        postService.save(getDefaultPost());
    }

    @Test
    public void getById_Should_Success() throws SQLException {

        when(postRepositoryMock.getById(anyLong())).thenReturn(getDefaultPost());

        Post getPost = postService.getById(2L);

        assertNotNull(getPost);

        assertEquals(getPost, getDefaultPost());
    }

    @Test(expected = SQLException.class)
    public void getById_Should_Throw_Exception() throws SQLException {
        when(postRepositoryMock.getById(anyLong())).thenThrow(SQLException.class);

        postService.getById(2L);
    }

    @Test
    public void deleteById_Should_True() {

        when(postRepositoryMock.delete(anyLong())).thenReturn(true);

        boolean checkDeletedPost = postService.delete(3L);

        assertTrue(checkDeletedPost);
    }

    @Test
    public void deleteById_Should_False() {

        when(postRepositoryMock.delete(anyLong())).thenReturn(false);

        boolean checkDeletedPost = postService.delete(3L);

        assertFalse(checkDeletedPost);
    }

    @Test(expected = SQLException.class)
    public void deleteById_Should_Throw_Exception() {
        when(postRepositoryMock.delete(anyLong())).thenThrow(SQLException.class);

        postService.delete(2L);
    }

    @Test
    public void updatedPost_Should_Success() {

        when(postRepositoryMock.update(any(Post.class))).thenReturn(getDefaultPost());

        Post updatedPost = postService.update(new Post());

        assertNotNull(updatedPost);

        assertEquals(updatedPost, getDefaultPost());
    }

    @Test(expected = SQLException.class)
    public void updatePost_Should_Throw_Exception() {
        when(postRepositoryMock.update(any(Post.class))).thenThrow(SQLException.class);

        postService.update(getDefaultPost());
    }

    @Test
    public void getAllPosts_Should_Success() {
        List<Post> postsList = new ArrayList<>();

        postsList.add(new Post(1L, "Java best practies", PostStatus.ACTIVE));
        postsList.add(new Post(2L, "JUNit best practies", PostStatus.DELETED));
        postsList.add(new Post(3L, "JDBC best practies", PostStatus.UNDER_REVIEW));
        postsList.add(new Post(4L, "Spring best practies", PostStatus.ACTIVE));
        postsList.add(new Post(5L, "Kafka best practies", PostStatus.UNDER_REVIEW));

        when(postRepositoryMock.getAll()).thenReturn(postsList);

        List<Post> getAllPosts = postService.getAll();

        assertNotNull(getAllPosts);

        assertSame(postsList, getAllPosts);

        assertArrayEquals(getAllPosts.stream().toArray(), getAllPosts.stream().toArray());

    }

    @Test(expected = SQLException.class)
    public void getAllPosts_Should_Throw_Exception() {
        when(postRepositoryMock.getAll()).thenThrow(SQLException.class);

        postService.getAll();
    }
}
