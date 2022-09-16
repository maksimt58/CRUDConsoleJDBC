package example.com.crud.service;

import example.com.crud.model.Post;
import example.com.crud.repository.PostRepository;
import example.com.crud.repository.impl.DBPostRepositoryImpl;
import java.sql.SQLException;
import java.util.List;


public class PostService {
    private final PostRepository postRepository;

    public PostService() {
        this.postRepository = new DBPostRepositoryImpl();
    }

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getById(Long id) throws SQLException {
        return postRepository.getById(id);
    }

    public boolean delete(Long id) {
        return postRepository.delete(id);
    }

    public Post update(Post post) {
        return postRepository.update(post);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }
}
