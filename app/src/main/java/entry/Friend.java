package entry;

/**
 * Created by up on 2019/11/10.
 */

public class Friend {
    private String phone;
    private String name;
    private String img;
    private int msgNum=0;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", msgNum=" + msgNum +
                '}';
    }
}
