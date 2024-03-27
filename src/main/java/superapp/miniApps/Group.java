package superapp.miniApps;

public class Group {  //need to add attributes: user
    private String name;

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group [name=" + name + "]";
    }
}
