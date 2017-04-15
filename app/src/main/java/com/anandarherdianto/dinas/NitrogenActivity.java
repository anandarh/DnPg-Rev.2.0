package com.anandarherdianto.dinas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.helper.NRecommendation;
import com.anandarherdianto.dinas.util.CompressImage;
import com.anandarherdianto.dinas.util.MyAlgorithm;
import com.anandarherdianto.dinas.util.NitrogenImageDialogBox;

import java.io.File;

public class NitrogenActivity extends AppCompatActivity implements NitrogenImageDialogBox.MyInterface {

    private ImageView imgNitrogen1, imgNitrogen2, imgNitrogen3,
            imgNitrogen4, imgNitrogen5, imgNitrogen6;

    private TextView lblLevelImg1, lblLevelImg2, lblLevelImg3,
            lblLevelImg4, lblLevelImg5, lblLevelImg6, lblLevelRata;

    private Bitmap bmpNitrogen1, bmpNitrogen2, bmpNitrogen3,
            bmpNitrogen4, bmpNitrogen5, bmpNitrogen6;

    private Uri file_uri1, file_uri2, file_uri3,
            file_uri4, file_uri5, file_uri6;

    private boolean imgStatus1, imgStatus2, imgStatus3,
            imgStatus4, imgStatus5, imgStatus6 = false;

    private int level1, level2, level3,
            level4, level5, level6;

    //From Camera
    static final int REQUEST_IMG_NITROGEN1 = 101;
    static final int REQUEST_IMG_NITROGEN2 = 102;
    static final int REQUEST_IMG_NITROGEN3 = 103;
    static final int REQUEST_IMG_NITROGEN4 = 104;
    static final int REQUEST_IMG_NITROGEN5 = 105;
    static final int REQUEST_IMG_NITROGEN6 = 106;

    //From Gallery
    static final int REQUEST_IMG_NITROGEN7 = 107;
    static final int REQUEST_IMG_NITROGEN8 = 108;
    static final int REQUEST_IMG_NITROGEN9 = 109;
    static final int REQUEST_IMG_NITROGEN10 = 110;
    static final int REQUEST_IMG_NITROGEN11 = 111;
    static final int REQUEST_IMG_NITROGEN12 = 112;

    private View frmResult;

    private NitrogenImageDialogBox dialog;

    private MyAlgorithm my;

    private CompressImage ci;

    private NRecommendation nr;

    private ScrollView sc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitrogen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgNitrogen1 = (ImageView) findViewById(R.id.imgNitrogen1);
        imgNitrogen2 = (ImageView) findViewById(R.id.imgNitrogen2);
        imgNitrogen3 = (ImageView) findViewById(R.id.imgNitrogen3);
        imgNitrogen4 = (ImageView) findViewById(R.id.imgNitrogen4);
        imgNitrogen5 = (ImageView) findViewById(R.id.imgNitrogen5);
        imgNitrogen6 = (ImageView) findViewById(R.id.imgNitrogen6);

        lblLevelImg1 = (TextView) findViewById(R.id.lblLevelImg1);
        lblLevelImg2 = (TextView) findViewById(R.id.lblLevelImg2);
        lblLevelImg3 = (TextView) findViewById(R.id.lblLevelImg3);
        lblLevelImg4 = (TextView) findViewById(R.id.lblLevelImg4);
        lblLevelImg5 = (TextView) findViewById(R.id.lblLevelImg5);
        lblLevelImg6 = (TextView) findViewById(R.id.lblLevelImg6);
        lblLevelRata = (TextView) findViewById(R.id.lblLevelRata);

        frmResult = findViewById(R.id.frmResult);

        sc = (ScrollView) findViewById(R.id.scrollNitrogen);

        Button btnProses = (Button) findViewById(R.id.btnProses);

        frmResult.setVisibility(View.GONE);

        dialog = new NitrogenImageDialogBox();

        my = new MyAlgorithm();

        ci = new CompressImage();

        nr = new NRecommendation();

        imgNitrogen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get1");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get2");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get3");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get4");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get5");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get6");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (imgStatus1 //&& imgStatus2 && imgStatus3 && imgStatus4 && imgStatus5
                       // && imgStatus6) {

                    nr.process();
                    lblLevelRata.setText(nr.getAvgLevel());

                    frmResult.setVisibility(View.VISIBLE);
                    frmResult.setAlpha(0.0f);
                    frmResult.setScaleY(0.0f);

                    frmResult.animate().scaleY(1.0f).alpha(1.0f).setDuration(1000);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        sc.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 300);



               // } else {
                    Toast.makeText(getApplicationContext(),
                            "Ambil gambar sampel terlebih dahulu!", Toast.LENGTH_SHORT)
                            .show();
               // }
            }
        });

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Maaf! Perangkat anda tidak mensuport kamera",
                    Toast.LENGTH_LONG).show();
            finish();
        }

    }


    public void camera1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri1);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN1);

    }

    public void camera2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri2);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN2);
    }

    public void camera3() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri3);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN3);
    }

    public void camera4() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri4);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN4);
    }

    public void camera5() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri5);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN5);
    }

    public void camera6() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri6);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN6);
    }

    public void gallery1(){
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN7);
    }

    public void gallery2(){
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN8);
    }

    public void gallery3(){
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN9);
    }

    public void gallery4(){
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN10);
    }

    public void gallery5(){
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN11);
    }

    public void gallery6(){
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN12);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("file_uri1", file_uri1);
        outState.putParcelable("file_uri2", file_uri2);
        outState.putParcelable("file_uri3", file_uri3);
        outState.putParcelable("file_uri4", file_uri4);
        outState.putParcelable("file_uri5", file_uri5);
        outState.putParcelable("file_uri6", file_uri6);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        file_uri1 = savedInstanceState.getParcelable("file_uri1");
        file_uri2 = savedInstanceState.getParcelable("file_uri2");
        file_uri3 = savedInstanceState.getParcelable("file_uri3");
        file_uri4 = savedInstanceState.getParcelable("file_uri4");
        file_uri5 = savedInstanceState.getParcelable("file_uri5");
        file_uri6 = savedInstanceState.getParcelable("file_uri6");
    }

    private boolean isDeviceSupportCamera() {
        // this device has a camera
        return getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    private void getFileUri() {
        String image_name1 = "imgNitrogen1.jpg";
        String image_name2 = "imgNitrogen2.jpg";
        String image_name3 = "imgNitrogen3.jpg";
        String image_name4 = "imgNitrogen4.jpg";
        String image_name5 = "imgNitrogen5.jpg";
        String image_name6 = "imgNitrogen6.jpg";

        String extr = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File mFolder = new File(extr + "/Dinas");
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }

        File file1 = new File(mFolder + File.separator + image_name1);
        file_uri1 = Uri.fromFile(file1);

        File file2 = new File(mFolder + File.separator + image_name2);
        file_uri2 = Uri.fromFile(file2);

        File file3 = new File(mFolder + File.separator + image_name3);
        file_uri3 = Uri.fromFile(file3);

        File file4 = new File(mFolder + File.separator + image_name4);
        file_uri4 = Uri.fromFile(file4);

        File file5 = new File(mFolder + File.separator + image_name5);
        file_uri5 = Uri.fromFile(file5);

        File file6 = new File(mFolder + File.separator + image_name6);
        file_uri6 = Uri.fromFile(file6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMG_NITROGEN1) {
                previewImageNitrogen1();
            }

            if (requestCode == REQUEST_IMG_NITROGEN2) {
                previewImageNitrogen2();
            }

            if (requestCode == REQUEST_IMG_NITROGEN3) {
                previewImageNitrogen3();
            }

            if (requestCode == REQUEST_IMG_NITROGEN4) {
                previewImageNitrogen4();
            }

            if (requestCode == REQUEST_IMG_NITROGEN5) {
                previewImageNitrogen5();
            }

            if (requestCode == REQUEST_IMG_NITROGEN6) {
                previewImageNitrogen6();
            }

            if (requestCode == REQUEST_IMG_NITROGEN7) {
                previewImageNitrogen7(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN8) {
                previewImageNitrogen8(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN9) {
                previewImageNitrogen9(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN10) {
                previewImageNitrogen10(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN11) {
                previewImageNitrogen11(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN12) {
                previewImageNitrogen12(data);
            }

        } else if (resultCode == RESULT_CANCELED) {
            //cancel by user
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void previewImageNitrogen1() {
        try {

            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri1.toString()));

            bmpNitrogen1 = cropImage(bf);

            imgNitrogen1.setImageBitmap(bmpNitrogen1);

            my.proses(getRGB(bmpNitrogen1));

            lblLevelImg1.setText(my.getResultLevel());

            nr.setLevel1(Integer.parseInt(my.getResultLevel()));

            imgStatus1 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen2() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri2.toString()));

            bmpNitrogen2 = cropImage(bf);

            imgNitrogen2.setImageBitmap(bmpNitrogen2);

            my.proses(getRGB(bmpNitrogen2));

            lblLevelImg2.setText(my.getResultLevel());

            nr.setLevel2(Integer.parseInt(my.getResultLevel()));

            imgStatus2 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen3() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri3.toString()));

            bmpNitrogen3 = cropImage(bf);

            imgNitrogen3.setImageBitmap(bmpNitrogen3);

            my.proses(getRGB(bmpNitrogen3));

            lblLevelImg3.setText(my.getResultLevel());

            nr.setLevel3(Integer.parseInt(my.getResultLevel()));

            imgStatus3 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen4() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri4.toString()));

            bmpNitrogen4 = cropImage(bf);

            imgNitrogen4.setImageBitmap(bmpNitrogen4);

            my.proses(getRGB(bmpNitrogen4));

            lblLevelImg4.setText(my.getResultLevel());

            nr.setLevel4(Integer.parseInt(my.getResultLevel()));

            imgStatus4 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen5() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri5.toString()));

            bmpNitrogen5 = cropImage(bf);

            imgNitrogen5.setImageBitmap(bmpNitrogen5);

            my.proses(getRGB(bmpNitrogen5));

            lblLevelImg5.setText(my.getResultLevel());

            nr.setLevel5(Integer.parseInt(my.getResultLevel()));

            imgStatus5 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen6() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri6.toString()));

            bmpNitrogen6 = cropImage(bf);

            imgNitrogen6.setImageBitmap(bmpNitrogen6);

            my.proses(getRGB(bmpNitrogen6));

            lblLevelImg6.setText(my.getResultLevel());

            nr.setLevel6(Integer.parseInt(my.getResultLevel()));

            imgStatus6 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen7(Intent data){
        Uri imgUri = data.getData();

        try{
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString()));

            bmpNitrogen1 = cropImage(bf);

            imgNitrogen1.setImageBitmap(bmpNitrogen1);

            my.proses(getRGB(bmpNitrogen1));

            lblLevelImg1.setText(my.getResultLevel());

            nr.setLevel1(Integer.parseInt(my.getResultLevel()));

            imgStatus1 = true;
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen8(Intent data){
        Uri imgUri = data.getData();

        try{
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString()));

            bmpNitrogen2 = cropImage(bf);

            imgNitrogen2.setImageBitmap(bmpNitrogen2);

            my.proses(getRGB(bmpNitrogen2));

            lblLevelImg2.setText(my.getResultLevel());

            nr.setLevel2(Integer.parseInt(my.getResultLevel()));

            imgStatus2 = true;
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen9(Intent data){
        Uri imgUri = data.getData();

        try{
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString()));

            bmpNitrogen3 = cropImage(bf);

            imgNitrogen3.setImageBitmap(bmpNitrogen3);

            my.proses(getRGB(bmpNitrogen3));

            lblLevelImg3.setText(my.getResultLevel());

            nr.setLevel3(Integer.parseInt(my.getResultLevel()));

            imgStatus3 = true;
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen10(Intent data){
        Uri imgUri = data.getData();

        try{
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString()));

            bmpNitrogen4 = cropImage(bf);

            imgNitrogen4.setImageBitmap(bmpNitrogen4);

            my.proses(getRGB(bmpNitrogen4));

            lblLevelImg4.setText(my.getResultLevel());

            nr.setLevel4(Integer.parseInt(my.getResultLevel()));

            imgStatus4 = true;
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen11(Intent data){
        Uri imgUri = data.getData();

        try{
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString()));

            bmpNitrogen5 = cropImage(bf);

            imgNitrogen5.setImageBitmap(bmpNitrogen5);

            my.proses(getRGB(bmpNitrogen5));

            lblLevelImg5.setText(my.getResultLevel());

            nr.setLevel5(Integer.parseInt(my.getResultLevel()));

            imgStatus5 = true;
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen12(Intent data){
        Uri imgUri = data.getData();

        try{
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString()));

            bmpNitrogen6 = cropImage(bf);

            imgNitrogen6.setImageBitmap(bmpNitrogen6);

            my.proses(getRGB(bmpNitrogen6));

            lblLevelImg6.setText(my.getResultLevel());

            nr.setLevel6(Integer.parseInt(my.getResultLevel()));

            imgStatus6 = true;
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap cropImage(Bitmap srcBmp) {

        Bitmap imgSrc;

        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

            imgSrc = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        } else {

            imgSrc = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return imgSrc;
    }

    private int[] getRGB(Bitmap img) {
        long redColors = 0;
        long greenColors = 0;
        long blueColors = 0;
        long pixelCount = 0;

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int c = img.getPixel(x, y);
                pixelCount++;
                redColors += Color.red(c);
                greenColors += Color.green(c);
                blueColors += Color.blue(c);
            }
        }
        // calculate average of bitmap r,g,b values
        long red = (redColors / pixelCount);
        long green = (greenColors / pixelCount);
        long blue = (blueColors / pixelCount);

        String rgbHex = String.format("%02x%02x%02x", red, green, blue).toUpperCase();

        return new int[]{(int) red, (int) green, (int) blue};

        //Toast.makeText(this, "R = " + red + "; G = " + green + "; B = " + blue, Toast.LENGTH_LONG).show();
        // Toast.makeText(this, rgbHex, Toast.LENGTH_LONG).show();
    }



}
