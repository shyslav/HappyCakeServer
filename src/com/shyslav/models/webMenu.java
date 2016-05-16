package com.shyslav.models;

/**
 * Created by shyshkin_vlad on 22.04.16.
 */
public class webMenu {
    private int id;
    private String name;
    private String link;
    private int menuSort;

    public webMenu(int id, String name, String link, int menuSort) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.menuSort = menuSort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(int menuSort) {
        this.menuSort = menuSort;
    }
}
