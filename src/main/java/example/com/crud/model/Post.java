package example.com.crud.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Post implements Model {
    private Long id;
    private String content;
    private Timestamp created;
    private Timestamp updated;
    private List<Label> labels = new ArrayList<>();
    private PostStatus postStatus;
    private Long writerId;
    private Writer writer;

    public Post() {
    }

    public Post(Long id, String content, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.postStatus = postStatus;
    }

    public Post(String content, List<Label> labels, PostStatus postStatus) {
        this.content = content;
        this.labels = labels;
        this.postStatus = postStatus;
    }

    public Post(String content, List<Label> labels, PostStatus postStatus, Long writerId) {
        this.content = content;
        this.labels = labels;
        this.postStatus = postStatus;
        this.writerId = writerId;
    }
    public Post(Long id, String content, PostStatus postStatus, Long writerId) {
        this.id = id;
        this.content = content;
        this.postStatus = postStatus;
        this.writerId = writerId;
    }

    public Post(Long id, String content, PostStatus postStatus, List<Label> labels, Long writerId) {
        this.id = id;
        this.content = content;
        this.labels = labels;
        this.postStatus = postStatus;
        this.writerId = writerId;
    }

    public Post(Long id, String content, PostStatus postStatus, List<Label> labels, Writer writer) {
        this.id = id;
        this.content = content;
        this.labels = labels;
        this.postStatus = postStatus;
        this.writer = writer;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id) && content.equals(post.content) && created.equals(post.created) && Objects.equals(updated, post.updated) && Objects.equals(labels, post.labels) && postStatus == post.postStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, updated, labels, postStatus);
    }


    public String toStringAllData() {
        return "=====" + "\n" +
                "Post: " + " \n" +
                "=====" + "\n" +
                "id = " + id + ", \n" +
                "Post Name = " + content  + ", \n" +
                "Writer = " + writer.getFirstName()+" "+ writer.getLastName() + ", \n" +
                "Created = " + created + ", \n" +
                "Updated = " + updated + ", \n" +
                "TAGS: " + "\n" + listToString(labels) + "\n" +
                "Post Status = " + postStatus + "\n" +
                "----------------------------------------------------------------------" + "\n";
    }

    private String listToString(List<?> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i);
        }
        return result;
    }

    @Override
    public String toString() {
        return "=====" + "\n" +
                "Post: " + " \n" +
                "=====" + "\n" +
                "id = " + id + ", \n" +
                "Post Name = " + content  + ", \n" +
                "Post Status = " + postStatus + "\n" +
                "----------------------------------------------------------------------" + "\n";
    }
}
