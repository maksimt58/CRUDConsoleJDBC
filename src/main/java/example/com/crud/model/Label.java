package example.com.crud.model;

public class Label implements Model {
    private Long id;
    private String nameLabel;

    public Label() {
    }

    public Label(Long id, String nameLabel) {
        this.id = id;
        this.nameLabel = nameLabel;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(String nameLabel) {
        this.nameLabel = nameLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id.equals(label.id) && nameLabel.equals(label.nameLabel);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31*result + (int)(id - (id >>> 32));
        result = 31 * result + (nameLabel == null ? 0 : nameLabel.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return "\n======" +
                "\nLabel: " +
                "\n======" +
                "\nid = " + id +
                "\nName Label = " + nameLabel+ "\n" +
                "----------------------------------------------------------------------";
    }
}
