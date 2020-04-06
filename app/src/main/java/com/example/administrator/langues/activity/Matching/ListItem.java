package com.example.administrator.langues.activity.Matching;



public class ListItem {
    private String list_name;//标题
    private int listImageResId;//图像资源ID;
    private String  listNum;//在线人数


    public String getListNum() {
        return listNum;
    }

    public String getList_name() {
        return list_name;
    }

    public void setListNum(String listNum) {
        this.listNum = listNum;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public void setListImageResId(int listImageResId) {
        this.listImageResId = listImageResId;
    }

    public int getListImageResId() {
        return listImageResId;
    }
}
