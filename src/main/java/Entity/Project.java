package Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private String color;

    public int getLink() {
        return link;
    }

    private int link;

    public Project() {
    }

    public Project(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public void setlinkId(int link) {
        this.link = link;
    }
}
