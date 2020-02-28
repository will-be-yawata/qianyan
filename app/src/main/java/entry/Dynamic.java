package entry;

import java.util.Arrays;

public class Dynamic {
    private String id;
    private String name;
    private String[] img;
    private String text;
    private String agree;
    private String user_img;
    private String like;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getUser_img() {
        return user_img;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }
    public String toString() {
        return "Dynamic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img=" + Arrays.toString(img) +
                ", text='" + text + '\'' +
                ", agree='" + agree + '\'' +
                ", user_img='" + user_img + '\'' +
                ", like='" + like + '\'' +
                '}';
    }
}
