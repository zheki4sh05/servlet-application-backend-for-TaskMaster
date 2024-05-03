package DTO;

public class SubTaskDTO {
    public int getId() {
        return id;
    }

    private int id;
    private final String name;
    private final int isDone;
    public SubTaskDTO(int id,String name, int isDone) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
    }

    public String getName() {
        return name;
    }

    public int isDone() {
        return isDone;
    }
}
