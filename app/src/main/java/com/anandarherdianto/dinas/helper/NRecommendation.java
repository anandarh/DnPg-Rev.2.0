package com.anandarherdianto.dinas.helper;

import java.text.DecimalFormat;

/**
 * Created by Ananda R. Herdianto on 15/04/2017.
 */

public class NRecommendation {

    private int level1, level2, level3, level4, level5,
            level6, level7, level8, level9, level10;
    private int jmlTargetProduksi;
    private String avgLevel;
    private int nRecommend;

    public NRecommendation() {

    }

    public void process(){
        DecimalFormat oneDigit = new DecimalFormat("#.0");
        avgLevel = oneDigit.format((level1 + level2 + level3 +
                level4 + level5 + level6 + level7 + level8 + level9 + level10)/10.0);

        Double nAvg = Double.parseDouble(avgLevel.replace(",", "."));

        if(nAvg <= 3.0){
            if(jmlTargetProduksi == 5){
                nRecommend = 75;
            }else
            if(jmlTargetProduksi == 6){
                nRecommend = 100;
            }else
            if(jmlTargetProduksi == 7){
                nRecommend = 125;
            }else
            if(jmlTargetProduksi == 8){
                nRecommend = 150;
            }else {
                nRecommend = 75;
            }
        }else
        if(nAvg > 3.0 && nAvg <= 4.0){
            if(jmlTargetProduksi == 5){
                nRecommend = 50;
            }else
            if(jmlTargetProduksi == 6){
                nRecommend = 75;
            }else
            if(jmlTargetProduksi == 7){
                nRecommend = 100;
            }else
            if(jmlTargetProduksi == 8){
                nRecommend = 125;
            }else {
                nRecommend = 50;
            }
        }else
        if(nAvg > 4.0){
            if(jmlTargetProduksi == 5){
                nRecommend = 0;
            }else {
                nRecommend = 50;
            }
        }
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
    }

    public void setLevel5(int level5) {
        this.level5 = level5;
    }

    public void setLevel6(int level6) {
        this.level6 = level6;
    }

    public void setLevel7(int level7) {
        this.level7 = level7;
    }

    public void setLevel8(int level8) {
        this.level8 = level8;
    }

    public void setLevel9(int level9) {
        this.level9 = level9;
    }

    public void setLevel10(int level10) {
        this.level10 = level10;
    }

    public void setJmlTargetProduksi(int jmlTargetProduksi) {
        this.jmlTargetProduksi = jmlTargetProduksi;
    }

    public String getAvgLevel() {
        return avgLevel;
    }

    public int getnRecommend() {
        return nRecommend;
    }

}
