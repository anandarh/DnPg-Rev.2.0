package com.anandarherdianto.dinas.model;

/**
 * Created by Ananda R. Herdianto on 24/04/2017.
 */

public class LHistoryModel {

    private int history_id;
    private String avgLevel, note, date;

    public LHistoryModel() {
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public String getAvgLevel() {
        return avgLevel;
    }

    public void setAvgLevel(String avgLevel) {
        this.avgLevel = avgLevel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
