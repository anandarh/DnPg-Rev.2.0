package com.anandarherdianto.dinas.model;

import android.support.annotation.NonNull;

/**
 * Created by Ananda R. Herdianto on 24/04/2017.
 */

public class LHistoryModel implements Comparable<LHistoryModel>{

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

    //Sort by history_id
    @Override
    public int compareTo(LHistoryModel lHistoryModel) {
        int compareId = ((LHistoryModel)lHistoryModel).getHistory_id();

        //Asc
        //return this.history_id - compareId ;

        //Desc
        return compareId - this.history_id;
    }

}
