package com.anandarherdianto.dinas.model;

/**
 * Created by Ananda R. Herdianto on 23/04/2017.
 */

public class HistoryModel {

    private int history_id, level1, level2, level3, level4, level5,
                level6, level7, level8, level9, level10, target;

    private String avg_level, n_dosage, note, date_check;

    public HistoryModel() {
    }

    public HistoryModel(
            int level1, int level2, int level3, int level4, int level5,
            int level6, int level7, int level8, int level9, int level10,
            int target, String avg_level, String n_dosage, String note,
            String date_check) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.level4 = level4;
        this.level5 = level5;
        this.level6 = level6;
        this.level7 = level7;
        this.level8 = level8;
        this.level9 = level9;
        this.level10 = level10;
        this.target = target;
        this.avg_level = avg_level;
        this.n_dosage = n_dosage;
        this.note = note;
        this.date_check = date_check;
    }

    public HistoryModel(int history_id, int level1, int level2,
                        int level3, int level4, int level5, int level6,
                        int level7, int level8, int level9, int level10,
                        int target, String avg_level, String n_dosage, String note,
                        String date_check) {
        this.history_id = history_id;
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.level4 = level4;
        this.level5 = level5;
        this.level6 = level6;
        this.level7 = level7;
        this.level8 = level8;
        this.level9 = level9;
        this.level10 = level10;
        this.target = target;
        this.avg_level = avg_level;
        this.n_dosage = n_dosage;
        this.note = note;
        this.date_check = date_check;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
    }

    public int getLevel4() {
        return level4;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
    }

    public int getLevel5() {
        return level5;
    }

    public void setLevel5(int level5) {
        this.level5 = level5;
    }

    public int getLevel6() {
        return level6;
    }

    public void setLevel6(int level6) {
        this.level6 = level6;
    }

    public int getLevel7() {
        return level7;
    }

    public void setLevel7(int level7) {
        this.level7 = level7;
    }

    public int getLevel8() {
        return level8;
    }

    public void setLevel8(int level8) {
        this.level8 = level8;
    }

    public int getLevel9() {
        return level9;
    }

    public void setLevel9(int level9) {
        this.level9 = level9;
    }

    public int getLevel10() {
        return level10;
    }

    public void setLevel10(int level10) {
        this.level10 = level10;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getAvg_level() {
        return avg_level;
    }

    public void setAvg_level(String avg_level) {
        this.avg_level = avg_level;
    }

    public String getN_dosage() {
        return n_dosage;
    }

    public void setN_dosage(String n_dosage) {
        this.n_dosage = n_dosage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate_check() {
        return date_check;
    }

    public void setDate_check(String date_check) {
        this.date_check = date_check;
    }
}
