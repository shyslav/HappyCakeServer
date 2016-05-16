package com.shyslav.models;

import java.util.Date;

/**
 * Created by shyshkin_vlad on 22.04.16.
 */
public class hotPrice {
    private int id;
    private int dishId;
    private int percent;
    private String description;
    private Date dateStart;
    private Date dateEnd;

    public hotPrice(int id, int dishId, int percent, String description, Date dateStart, Date dateEnd) {
        this.id = id;
        this.dishId = dishId;
        this.percent = percent;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
