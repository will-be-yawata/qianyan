package entry;

import java.util.ArrayList;

public class Scence {
    private int mid;
    private ArrayList<Stop_points> stop_points;
    private String media_name;
    private String media_path;
    private String media_kind;


    @Override
    public String toString() {
        return "Scence{" +
                "mid=" + mid +
                ", stop_points=" + stop_points +
                ", media_name='" + media_name + '\'' +
                ", media_path='" + media_path + '\'' +
                ", media_kind='" + media_kind + '\'' +
                ", media_img='" + media_img + '\'' +
                '}';
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public ArrayList<Stop_points> getStop_points() {
        return stop_points;
    }

    public void setStop_points(ArrayList<Stop_points> stop_points) {
        this.stop_points = stop_points;
    }

    public String getMedia_name() {
        return media_name;
    }

    public void setMedia_name(String media_name) {
        this.media_name = media_name;
    }

    public String getMedia_path() {
        return media_path;
    }

    public void setMedia_path(String media_path) {
        this.media_path = media_path;
    }

    public String getMedia_kind() {
        return media_kind;
    }

    public void setMedia_kind(String media_kind) {
        this.media_kind = media_kind;
    }

    public String getMedia_img() {
        return media_img;
    }

    public void setMedia_img(String media_img) {
        this.media_img = media_img;
    }

    private String media_img;


}
