package Entity;

import java.time.LocalDate;

public class Task {
    public int getId() {
        return id;
    }

    private int id;
    private String name;
    private String desc;
    private String status;
    private String dateto;

    public int getLink() {
        return link;
    }

    private int link;


    public void setId(int id) {
        this.id = id;
    }

    public String getDateto() {
        return dateto;
    }

    public void setDateto(String dateto) {
        this.dateto = dateto;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

    private String datefrom;
    public Task() {
    }

    public Task(String name, String desc, String status, LocalDate date) {
        this.name = name;
        this.desc = desc;
        this.status = status;

    }
    public Task(int id, String name, String desc, String status, String dateto) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.dateto = dateto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public void setlinkId(int link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", dateto='" + dateto + '\'' +
                ", link=" + link +
                ", datefrom='" + datefrom + '\'' +
                '}';
    }
}
