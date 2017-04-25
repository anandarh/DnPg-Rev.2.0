package com.anandarherdianto.dinas;

import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anandarherdianto.dinas.helper.NRecommendation;
import com.anandarherdianto.dinas.model.HistoryModel;
import com.anandarherdianto.dinas.util.CompressImage;
import com.anandarherdianto.dinas.util.DatabaseHandler;
import com.anandarherdianto.dinas.util.MyAlgorithm;
import com.anandarherdianto.dinas.util.NitrogenImageDialogBox;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NitrogenActivity extends AppCompatActivity implements NitrogenImageDialogBox.MyInterface {

    private ImageView imgNitrogen1, imgNitrogen2, imgNitrogen3, imgNitrogen4, imgNitrogen5,
            imgNitrogen6, imgNitrogen7, imgNitrogen8, imgNitrogen9, imgNitrogen10;

    private TextView lblLevelImg1, lblLevelImg2, lblLevelImg3, lblLevelImg4, lblLevelImg5,
            lblLevelImg6, lblLevelImg7, lblLevelImg8, lblLevelImg9, lblLevelImg10,
            lblLevelRata, lblRekUrea;

    private Bitmap bmpNitrogen1, bmpNitrogen2, bmpNitrogen3, bmpNitrogen4, bmpNitrogen5,
            bmpNitrogen6, bmpNitrogen7, bmpNitrogen8, bmpNitrogen9, bmpNitrogen10;

    private Uri file_uri1, file_uri2, file_uri3, file_uri4, file_uri5,
            file_uri6, file_uri7, file_uri8, file_uri9, file_uri10;

    private boolean imgStatus1, imgStatus2, imgStatus3, imgStatus4, imgStatus5,
            imgStatus6, imgStatus7, imgStatus8, imgStatus9, imgStatus10 = false;

    private int target;


    //From Camera
    static final int REQUEST_IMG_NITROGEN1 = 101;
    static final int REQUEST_IMG_NITROGEN2 = 102;
    static final int REQUEST_IMG_NITROGEN3 = 103;
    static final int REQUEST_IMG_NITROGEN4 = 104;
    static final int REQUEST_IMG_NITROGEN5 = 105;
    static final int REQUEST_IMG_NITROGEN6 = 106;
    static final int REQUEST_IMG_NITROGEN13 = 113;
    static final int REQUEST_IMG_NITROGEN14 = 114;
    static final int REQUEST_IMG_NITROGEN15 = 115;
    static final int REQUEST_IMG_NITROGEN16 = 116;

    //From Gallery
    static final int REQUEST_IMG_NITROGEN7 = 107;
    static final int REQUEST_IMG_NITROGEN8 = 108;
    static final int REQUEST_IMG_NITROGEN9 = 109;
    static final int REQUEST_IMG_NITROGEN10 = 110;
    static final int REQUEST_IMG_NITROGEN11 = 111;
    static final int REQUEST_IMG_NITROGEN12 = 112;
    static final int REQUEST_IMG_NITROGEN17 = 117;
    static final int REQUEST_IMG_NITROGEN18 = 118;
    static final int REQUEST_IMG_NITROGEN19 = 119;
    static final int REQUEST_IMG_NITROGEN20 = 120;

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
        imgNitrogen7 = (ImageView) findViewById(R.id.imgNitrogen7);
        imgNitrogen8 = (ImageView) findViewById(R.id.imgNitrogen8);
        imgNitrogen9 = (ImageView) findViewById(R.id.imgNitrogen9);
        imgNitrogen10 = (ImageView) findViewById(R.id.imgNitrogen10);

        lblLevelImg1 = (TextView) findViewById(R.id.lblLevelImg1);
        lblLevelImg2 = (TextView) findViewById(R.id.lblLevelImg2);
        lblLevelImg3 = (TextView) findViewById(R.id.lblLevelImg3);
        lblLevelImg4 = (TextView) findViewById(R.id.lblLevelImg4);
        lblLevelImg5 = (TextView) findViewById(R.id.lblLevelImg5);
        lblLevelImg6 = (TextView) findViewById(R.id.lblLevelImg6);
        lblLevelImg7 = (TextView) findViewById(R.id.lblLevelImg7);
        lblLevelImg8 = (TextView) findViewById(R.id.lblLevelImg8);
        lblLevelImg9 = (TextView) findViewById(R.id.lblLevelImg9);
        lblLevelImg10 = (TextView) findViewById(R.id.lblLevelImg10);
        lblLevelRata = (TextView) findViewById(R.id.lblLevelRata);
        lblRekUrea = (TextView) findViewById(R.id.lblRekUrea);

        frmResult = findViewById(R.id.frmResult);

        sc = (ScrollView) findViewById(R.id.scrollNitrogen);

        final Button btnProses = (Button) findViewById(R.id.btnProses);

        final Button btnHistory = (Button) findViewById(R.id.btnHistory);
        btnHistory.setVisibility(View.GONE);

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

        imgNitrogen7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get7");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get8");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get9");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        imgNitrogen10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCode("get10");
                dialog.show(getSupportFragmentManager(), "DialogImage");
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgStatus1 && imgStatus2 && imgStatus3 && imgStatus4 && imgStatus5
                        && imgStatus6 && imgStatus7 && imgStatus8 && imgStatus9 && imgStatus10) {

                    nr.process();
                    lblLevelRata.setText(nr.getAvgLevel());

                    lblRekUrea.setText(String.valueOf(nr.getnRecommend()));

                    btnProses.setVisibility(View.GONE);
                    btnHistory.setVisibility(View.VISIBLE);

                    imgNitrogen1.setEnabled(false);
                    imgNitrogen2.setEnabled(false);
                    imgNitrogen3.setEnabled(false);
                    imgNitrogen4.setEnabled(false);
                    imgNitrogen5.setEnabled(false);
                    imgNitrogen6.setEnabled(false);
                    imgNitrogen7.setEnabled(false);
                    imgNitrogen8.setEnabled(false);
                    imgNitrogen9.setEnabled(false);
                    imgNitrogen10.setEnabled(false);


                    frmResult.setVisibility(View.VISIBLE);
                    frmResult.setAlpha(0.0f);
                    frmResult.setScaleY(0.0f);

                    frmResult.animate().scaleY(1.0f).alpha(1.0f).setDuration(1000);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sc.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    }, 300);


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Ambil gambar sampel terlebih dahulu!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(NitrogenActivity.this);
                alert.setTitle("Simpan ke Histori?");
                alert.setMessage("\nCatatan : ");

                final EditText input = new EditText(NitrogenActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                input.setSingleLine(false);
                input.setLines(3);
                input.setMaxLines(5);
                input.setVerticalScrollBarEnabled(true);
                input.setMovementMethod(ScrollingMovementMethod.getInstance());
                input.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

                alert.setView(input);
                alert.setIcon(R.drawable.ic_save_black_24dp);

                alert.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String str = input.getEditableText().toString();
                        toHistory(str);
                    }
                });
                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
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

    public void camera7() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri7);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN13);
    }

    public void camera8() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri8);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN14);
    }

    public void camera9() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri9);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN15);
    }

    public void camera10() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri10);
        startActivityForResult(intent, REQUEST_IMG_NITROGEN16);
    }

    public void gallery1() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN7);
    }

    public void gallery2() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN8);
    }

    public void gallery3() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN9);
    }

    public void gallery4() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN10);
    }

    public void gallery5() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN11);
    }

    public void gallery6() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN12);
    }

    public void gallery7() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN17);
    }

    public void gallery8() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN18);
    }

    public void gallery9() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN19);
    }

    public void gallery10() {
        Intent iPicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iPicker, REQUEST_IMG_NITROGEN20);
    }

    public void pilihTargetProduksiClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.limaTon:
                if (checked) {
                    nr.setJmlTargetProduksi(5);
                    target = 5;
                }
                break;
            case R.id.enamTon:
                if (checked) {
                    nr.setJmlTargetProduksi(6);
                    target = 6;
                }
                break;
            case R.id.tujuhTon:
                if (checked) {
                    nr.setJmlTargetProduksi(7);
                    target = 7;
                }
                break;
            case R.id.delapanTon:
                if (checked) {
                    nr.setJmlTargetProduksi(8);
                    target = 8;
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        outState.putParcelable("file_uri7", file_uri7);
        outState.putParcelable("file_uri8", file_uri8);
        outState.putParcelable("file_uri9", file_uri9);
        outState.putParcelable("file_uri10", file_uri10);
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
        file_uri7 = savedInstanceState.getParcelable("file_uri7");
        file_uri8 = savedInstanceState.getParcelable("file_uri8");
        file_uri9 = savedInstanceState.getParcelable("file_uri9");
        file_uri10 = savedInstanceState.getParcelable("file_uri10");
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
        String image_name7 = "imgNitrogen7.jpg";
        String image_name8 = "imgNitrogen8.jpg";
        String image_name9 = "imgNitrogen9.jpg";
        String image_name10 = "imgNitrogen10.jpg";

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

        File file7 = new File(mFolder + File.separator + image_name7);
        file_uri7 = Uri.fromFile(file7);

        File file8 = new File(mFolder + File.separator + image_name8);
        file_uri8 = Uri.fromFile(file8);

        File file9 = new File(mFolder + File.separator + image_name9);
        file_uri9 = Uri.fromFile(file9);

        File file10 = new File(mFolder + File.separator + image_name10);
        file_uri10 = Uri.fromFile(file10);
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

            if (requestCode == REQUEST_IMG_NITROGEN13) {
                previewImageNitrogen13();
            }

            if (requestCode == REQUEST_IMG_NITROGEN14) {
                previewImageNitrogen14();
            }

            if (requestCode == REQUEST_IMG_NITROGEN15) {
                previewImageNitrogen15();
            }

            if (requestCode == REQUEST_IMG_NITROGEN16) {
                previewImageNitrogen16();
            }

            if (requestCode == REQUEST_IMG_NITROGEN17) {
                previewImageNitrogen17(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN18) {
                previewImageNitrogen18(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN19) {
                previewImageNitrogen19(data);
            }

            if (requestCode == REQUEST_IMG_NITROGEN20) {
                previewImageNitrogen20(data);
            }

        } else if (resultCode == RESULT_CANCELED) {
            //cancel by user
            Log.d("TAG", "Canceled by user");
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void previewImageNitrogen1() {
        try {

            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri1.toString(), "imgNitrogen1"));

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
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri2.toString(), "imgNitrogen2"));

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
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri3.toString(), "imgNitrogen3"));

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
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri4.toString(), "imgNitrogen4"));

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
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri5.toString(), "imgNitrogen5"));

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
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri6.toString(), "imgNitrogen6"));

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

    private void previewImageNitrogen13() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri7.toString(), "imgNitrogen7"));

            bmpNitrogen7 = cropImage(bf);

            imgNitrogen7.setImageBitmap(bmpNitrogen7);

            my.proses(getRGB(bmpNitrogen7));

            lblLevelImg7.setText(my.getResultLevel());

            nr.setLevel7(Integer.parseInt(my.getResultLevel()));

            imgStatus7 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen14() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri8.toString(), "imgNitrogen8"));

            bmpNitrogen8 = cropImage(bf);

            imgNitrogen8.setImageBitmap(bmpNitrogen8);

            my.proses(getRGB(bmpNitrogen8));

            lblLevelImg8.setText(my.getResultLevel());

            nr.setLevel8(Integer.parseInt(my.getResultLevel()));

            imgStatus8 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen15() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri9.toString(), "imgNitrogen9"));

            bmpNitrogen9 = cropImage(bf);

            imgNitrogen9.setImageBitmap(bmpNitrogen9);

            my.proses(getRGB(bmpNitrogen9));

            lblLevelImg9.setText(my.getResultLevel());

            nr.setLevel9(Integer.parseInt(my.getResultLevel()));

            imgStatus9 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen16() {
        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, file_uri10.toString(), "imgNitrogen10"));

            bmpNitrogen10 = cropImage(bf);

            imgNitrogen10.setImageBitmap(bmpNitrogen10);

            my.proses(getRGB(bmpNitrogen10));

            lblLevelImg10.setText(my.getResultLevel());

            nr.setLevel10(Integer.parseInt(my.getResultLevel()));

            imgStatus10 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewImageNitrogen7(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen1"));

            bmpNitrogen1 = cropImage(bf);

            imgNitrogen1.setImageBitmap(bmpNitrogen1);

            my.proses(getRGB(bmpNitrogen1));

            lblLevelImg1.setText(my.getResultLevel());

            nr.setLevel1(Integer.parseInt(my.getResultLevel()));

            imgStatus1 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen8(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen2"));

            bmpNitrogen2 = cropImage(bf);

            imgNitrogen2.setImageBitmap(bmpNitrogen2);

            my.proses(getRGB(bmpNitrogen2));

            lblLevelImg2.setText(my.getResultLevel());

            nr.setLevel2(Integer.parseInt(my.getResultLevel()));

            imgStatus2 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen9(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen3"));

            bmpNitrogen3 = cropImage(bf);

            imgNitrogen3.setImageBitmap(bmpNitrogen3);

            my.proses(getRGB(bmpNitrogen3));

            lblLevelImg3.setText(my.getResultLevel());

            nr.setLevel3(Integer.parseInt(my.getResultLevel()));

            imgStatus3 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen10(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen4"));

            bmpNitrogen4 = cropImage(bf);

            imgNitrogen4.setImageBitmap(bmpNitrogen4);

            my.proses(getRGB(bmpNitrogen4));

            lblLevelImg4.setText(my.getResultLevel());

            nr.setLevel4(Integer.parseInt(my.getResultLevel()));

            imgStatus4 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen11(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen5"));

            bmpNitrogen5 = cropImage(bf);

            imgNitrogen5.setImageBitmap(bmpNitrogen5);

            my.proses(getRGB(bmpNitrogen5));

            lblLevelImg5.setText(my.getResultLevel());

            nr.setLevel5(Integer.parseInt(my.getResultLevel()));

            imgStatus5 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen12(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen6"));

            bmpNitrogen6 = cropImage(bf);

            imgNitrogen6.setImageBitmap(bmpNitrogen6);

            my.proses(getRGB(bmpNitrogen6));

            lblLevelImg6.setText(my.getResultLevel());

            nr.setLevel6(Integer.parseInt(my.getResultLevel()));

            imgStatus6 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen17(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen7"));

            bmpNitrogen7 = cropImage(bf);

            imgNitrogen7.setImageBitmap(bmpNitrogen7);

            my.proses(getRGB(bmpNitrogen7));

            lblLevelImg7.setText(my.getResultLevel());

            nr.setLevel7(Integer.parseInt(my.getResultLevel()));

            imgStatus7 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen18(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen8"));

            bmpNitrogen8 = cropImage(bf);

            imgNitrogen8.setImageBitmap(bmpNitrogen8);

            my.proses(getRGB(bmpNitrogen8));

            lblLevelImg8.setText(my.getResultLevel());

            nr.setLevel8(Integer.parseInt(my.getResultLevel()));

            imgStatus8 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen19(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen9"));

            bmpNitrogen9 = cropImage(bf);

            imgNitrogen9.setImageBitmap(bmpNitrogen9);

            my.proses(getRGB(bmpNitrogen9));

            lblLevelImg9.setText(my.getResultLevel());

            nr.setLevel9(Integer.parseInt(my.getResultLevel()));

            imgStatus9 = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat mengambil gambar!", Toast.LENGTH_LONG).show();
        }
    }

    private void previewImageNitrogen20(Intent data) {
        Uri imgUri = data.getData();

        try {
            Bitmap bf = BitmapFactory.decodeFile(ci.compressImage(this, imgUri.toString(), "imgNitrogen10"));

            bmpNitrogen10 = cropImage(bf);

            imgNitrogen10.setImageBitmap(bmpNitrogen10);

            my.proses(getRGB(bmpNitrogen10));

            lblLevelImg10.setText(my.getResultLevel());

            nr.setLevel10(Integer.parseInt(my.getResultLevel()));

            imgStatus10 = true;
        } catch (NullPointerException e) {
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

        Log.d("TAG_RGB", rgbHex);

        return new int[]{(int) red, (int) green, (int) blue};

        //Toast.makeText(this, "R = " + red + "; G = " + green + "; B = " + blue, Toast.LENGTH_LONG).show();
        // Toast.makeText(this, rgbHex, Toast.LENGTH_LONG).show();
    }

    private void toHistory(String note) {

        DatabaseHandler db = new DatabaseHandler(this);

        Integer lvl1 = Integer.parseInt(lblLevelImg1.getText().toString());
        Integer lvl2 = Integer.parseInt(lblLevelImg2.getText().toString());
        Integer lvl3 = Integer.parseInt(lblLevelImg3.getText().toString());
        Integer lvl4 = Integer.parseInt(lblLevelImg4.getText().toString());
        Integer lvl5 = Integer.parseInt(lblLevelImg5.getText().toString());
        Integer lvl6 = Integer.parseInt(lblLevelImg6.getText().toString());
        Integer lvl7 = Integer.parseInt(lblLevelImg7.getText().toString());
        Integer lvl8 = Integer.parseInt(lblLevelImg8.getText().toString());
        Integer lvl9 = Integer.parseInt(lblLevelImg9.getText().toString());
        Integer lvl10 = Integer.parseInt(lblLevelImg10.getText().toString());

        String avgLevel = lblLevelRata.getText().toString();
        String nDosage = lblRekUrea.getText().toString();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = df.format(Calendar.getInstance().getTime());

        // Inserting history
        Log.d("Insert: ", "Inserting ..");
        db.addHistory(new HistoryModel(lvl1, lvl2, lvl3, lvl4, lvl5, lvl6, lvl7, lvl8, lvl9, lvl10, target, avgLevel, nDosage, note, date));

        Intent intent = new Intent(getApplicationContext(), History.class);
        startActivity(intent);
        finish();
    }
}
