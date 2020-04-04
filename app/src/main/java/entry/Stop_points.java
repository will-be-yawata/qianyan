package entry;

public class Stop_points {
    private int mid;//所在视频的id
    private int pid;
    private int start_time;
    private int end_time;
    private String sentence;
    private String role;



    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Stop_points{" +
                "mid=" + mid +
                ", pid=" + pid +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", sentence='" + sentence + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
}
