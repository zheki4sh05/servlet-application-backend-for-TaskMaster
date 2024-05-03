package DTO;

public class StatusDTO {
    private String name;

    public int getId() {
        return id;
    }

    private int id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
