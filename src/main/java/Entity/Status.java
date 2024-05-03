package Entity;

public class Status {
    public int getId() {
        return id;
    }

    private int id;
    private String statusName;

    public String getOldName() {
        return oldName;
    }

    private String oldName;

    public int getLink() {
        return link;
    }

    private int link;

    public Status(int id, String statusName,String oldName) {
        this.id = id;
        this.statusName = statusName;
        this.oldName = oldName;
    }
    public Status(int id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setlinkId(int link) {
        this.link = link;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status() {

    }
}
