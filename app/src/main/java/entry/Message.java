package entry;

public class Message {
    public static final int TYPE_TEXT=0;    //文本类型
    public static final int TYPE_IMAGE=1;   //图片类型
    public static final int TYPE_VOICE=2;   //语音类型
    public static final int TYPE_VIDEO=3;   //视频类型

    private String id;
    private String from;
    private String to;
    private int type;
    private String content;
    private float length;
    private String thumbPath;
    private boolean original=false;
    private String time;

    public Message(){}
    public Message(String from,int type){
        this.from=from;
        this.type=type;
    }

    public String getId(){return id;}

    public void setId(String id){this.id=id;}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", length=" + length +
                ", thumbPath='" + thumbPath + '\'' +
                ", original=" + original +
                ", time='" + time + '\'' +
                '}';
    }
}
