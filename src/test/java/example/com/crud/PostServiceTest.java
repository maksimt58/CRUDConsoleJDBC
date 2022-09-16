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
    PostService postService;
    private Post post;

    @Before
    public void setUp() {
        postRepositoryMock = Mockito.mock(DBPostRepositoryImpl.class);
        postService = new PostService(postRepositoryMock);
        post = new Post(1L, "Java11", PostStatus.ACTIVE, new ArrayList<>(), new Writer(2L, "Nata", "Tata"));
    }

    @Test
    public void savePost_Should_Success() {

        when(postRepositoryMock.save(post)).thenReturn(post);

        Post savedPost = postRepositoryMock.save(post);

        assertNotNull(savedPost);
        assertSame(savedPost, post);
        assertEquals(savedPost.getContent(), post.getContent());
    }

    @Test(expected = SQLException.class)
    public void savePost_Should_Throw_Exception() {
        when(postRepositoryMock.save(any(Post.class))).thenThrow(SQLException.class);

        postRepositoryMock.save(new Post());
    }

    @Test
    public void getById_Should_Success() throws SQLException {

        when(postRepositoryMock.getById(anyLong())).thenReturn(post);

        Post getPost = postRepositoryMock.getById(2L);

        assertNotNull(getPost);

        assertSame(getPost, post);
    }

    @Test(expected = SQLException.class)
    public void getById_Should_Throw_Exception() throws SQLException {
        when(postRepositoryMock.getById(anyLong())).thenThrow(SQLException.class);

        postRepositoryMock.getById(2L);
    }

    @Test
    public void deleteById_Should_True() {

        when(postRepositoryMock.delete(anyLong())).thenReturn(true);

        boolean checkDeletedPost = postRepositoryMock.delete(3L);

        assertTrue(checkDeletedPost);
    }

    @Test
    public void deleteById_Should_False() {

        when(postRepositoryMock.delete(anyLong())).thenReturn(false);

        boolean checkDeletedPost = postRepositoryMock.delete(3L);

        assertFalse(checkDeletedPost);
    }

    @Test(expected = SQLException.class)
    public void deleteById_Should_Throw_Exception() {
        when(postRepositoryMock.delete(anyLong())).thenThrow(SQLException.class);

        postRepositoryMock.delete(2L);
    }

    @Test
    public void updatedPost_Should_Success() {

        when(postRepositoryMock.update(any(Post.class))).thenReturn(post);

        Post updatedPost = postRepositoryMock.update(new Post());

        assertNotNull(updatedPost);

        assertSame(post, updatedPost);
    }

    @Test(expected = SQLException.class)
    public void updatePost_Should_Throw_Exception() {
        when(postRepositoryMock.update(any(Post.class))).thenThrow(SQLException.class);

        postRepositoryMock.update(post);
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

        List<Post> getAllPosts = postRepositoryMock.getAll();

        assertNotNull(getAllPosts);

        assertSame(postsList, getAllPosts);

        assertArrayEquals(getAllPosts.stream().toArray(), getAllPosts.stream().toArray());

    }

    @Test(expected = SQLException.class)
    public void getAllPosts_Should_Throw_Exception() {
        when(postRepositoryMock.getAll()).thenThrow(SQLException.class);

        postRepositoryMock.getAll();
    }
}
