package Structures;

public class StructPerson {
    public int id;
    public String name;
    public String mobile;
    public String address;
    public boolean male;
    public boolean friend;
    public float score;
    public boolean stored;

    public void load(StructPerson other) {
        id = other.id;
        name = other.name;
        mobile = other.mobile;
        address = other.address;
        male = other.male;
        friend = other.friend;
        score = other.score;
        stored = other.stored;
    }
}
