package pl.files.model;

public class DayName {

    private int id;
    private String name;
    private int displayOrder;

    @Override
    public String toString() {
        return "DayName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public DayName() {
    }

    public DayName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DayName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
