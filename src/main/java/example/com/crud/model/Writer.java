package example.com.crud.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Writer implements Model {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts = new ArrayList<>();

    public Writer() {
    }

    public Writer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Writer(Long id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "=======" + "\n" +
                "Writer: " + ", \n" +
                "=======" + "\n" +
                "id = " + id + ", \n" +
                "First Name = " + firstName + ", \n" +
                "Last Name = " + lastName + ", \n" +
                "POSTS: " + "\n" + listToString(posts) + "\n";
    }

    private String listToString(List<?> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id.equals(writer.id) && firstName.equals(writer.firstName) && lastName.equals(writer.lastName) && Objects.equals(posts, writer.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts);
    }
}
