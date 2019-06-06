package ir.ac.shirazu.softwareproject.server_api.Meal;

public class Self {
    private int selfId;
    private String selfName;

    public Self(int selfId, String selfName) {
        this.selfId = selfId;
        this.selfName = selfName;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public String getSelfName() {
        return selfName;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }
}