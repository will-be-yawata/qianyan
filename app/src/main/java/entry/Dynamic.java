package entry;

import java.util.Arrays;

public class Dynamic {
    String id;
    String name;
    String[] img;
    String text;
    String agree;

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

    @Override
    public String toString() {
        return "Dynamic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img=" + Arrays.toString(img) +
                ", text='" + text + '\'' +
                ", agree='" + agree + '\'' +
                '}';
    }
}
