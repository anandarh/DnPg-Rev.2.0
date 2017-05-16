package com.anandarherdianto.dinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.anandarherdianto.dinas.helper.DocumentationAlbumAdapter;
import com.anandarherdianto.dinas.helper.TouchListenerAlbum;
import com.anandarherdianto.dinas.model.DocumentationAlbumModel;
import com.anandarherdianto.dinas.util.CommunicationController;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.anandarherdianto.dinas.util.AppConfig.URL_ALBUM_DOCUMENTATION;

public class DocumentationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DocumentationAlbumAdapter adapter;
    private List<DocumentationAlbumModel> albumList;
    private ProgressDialog pDialog;
    private FloatingActionButton fab;
    private Uri file_uri;

    static final int REQUEST_IMG_CAPTURE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_documentation);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) findViewById(R.id.addDoc);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        albumList = new ArrayList<>();
        adapter = new DocumentationAlbumAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new TouchListenerAlbum(getApplicationContext(), recyclerView, new TouchListenerAlbum.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DocumentationAlbumModel album = albumList.get(position);
                //Toast.makeText(getApplicationContext(), album.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DocumentationActivity.this, ListDocumentationActivity.class);
                intent.putExtra("village",album.getTitle());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(intent, REQUEST_IMG_CAPTURE);


                //Intent i = new Intent(DocumentationActivity.this, AddDocumentationActivity.class);
                //startActivity(i);

            }
        });

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.bg_nav).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", file_uri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        file_uri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMG_CAPTURE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                launchUploadActivity();

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture


            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Maaf! Terjadi kesalahan saat mengambil gambar.", Toast.LENGTH_SHORT)
                        .show();
            }

        }

    }

    private void launchUploadActivity(){
        Intent i = new Intent(DocumentationActivity.this, AddDocumentationActivity.class);
        i.putExtra("filePath", file_uri.getPath());
        startActivity(i);
    }


    private void getFileUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String extr = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File mFolder = new File(extr + "/Dinas");
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }

        File file = new File(mFolder + File.separator + "IMG_" + timeStamp + ".jpg");
        file_uri = Uri.fromFile(file);
    }




    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_documentation);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_documentation);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.documentation_album));
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding albums data from server
     */
    private void prepareAlbums() {

        String tag_string_req = "req_documentation";

        pDialog.setMessage("Loading...");
        showDialog();

        StringRequest documentationReq = new StringRequest(URL_ALBUM_DOCUMENTATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Documentation : ", response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {
                                // Now store the user in SQLite
                                JSONArray jArr = jObj.getJSONArray("documentation");
                                for (int i = 0; i < jArr.length(); i++) {
                                    JSONObject obj = jArr.getJSONObject(i);
                                    DocumentationAlbumModel docModel = new DocumentationAlbumModel();
                                    docModel.setTitle(obj.getString("village"));
                                    docModel.setThumbnail(obj.getString("img_path"));
                                    docModel.setNumOfImages(obj.getInt("sum_image"));

                                    albumList.add(docModel);

                                }
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Error : Terjadi kesalahan!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat mengambil data!", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        CommunicationController.getInstance().addToRequestQueue(documentationReq, tag_string_req);

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
