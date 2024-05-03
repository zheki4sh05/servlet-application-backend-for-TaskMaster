package Entity;

public class SubTask {
    public int getId() {
        return id;
    }

    public SubTask() {
    }

    private  int id;
    private  String name;
    private  int isDone;

    public int getLink() {
        return link;
    }

    private int link;
    public SubTask(int id, String name, int isDone) {
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

    public void setlinkId(int link) {
        this.link = link;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDone(int checked) {
        this.isDone  = checked;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDone=" + isDone +
                ", link=" + link +
                '}';
    }
}
