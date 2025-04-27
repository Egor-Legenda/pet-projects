package backend.academy.my_project;


public class User {
    String name;
    String level;
    String topic;

    @Override public String toString() {
        return "User{"
            + "name='" + name + '\''
            + ", level='" + level + '\''
            + ", topic='" + topic + '\''
            + '}';
    }

    public User(String name, String level, String topic) {
        this.name = name;
        this.level = level;
        this.topic = topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }
}
