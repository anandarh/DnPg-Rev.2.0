package com.anandarherdianto.dinas;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.anandarherdianto.dinas.helper.DocumentationAdapter;
import com.anandarherdianto.dinas.model.DocumentationAlbumModel;
import com.anandarherdianto.dinas.util.CommunicationController;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.anandarherdianto.dinas.util.AppConfig.URL_ALBUM_DOCUMENTATION;
import static com.anandarherdianto.dinas.util.AppConfig.URL_LIST_DOCUMENTATION;

public class ListDocumentationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DocumentationAlbumModel> listDoc;
    private DocumentationAdapter adapter;
    private ProgressDialog pDialog;
    private String village;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_documentation);

        recyclerView = (RecyclerView) findViewById(R.id.rv_Documentation);

        village = this.getIntent().getStringExtra("village");

        getSupportActionBar().setTitle(village);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        listDoc = new ArrayList<>();

        adapter = new DocumentationAdapter(this, listDoc);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareDocumetationData(village);

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

    private void prepareDocumetationData(final String village) {
        String tag_string_req = "req_documentation2";

        pDialog.setMessage("Loading...");
        showDialog();

        StringRequest documentationReq = new StringRequest(Request.Method.POST,
                URL_LIST_DOCUMENTATION, new Response.Listener<String>() {
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
                                    docModel.setTitle(obj.getString("title"));
                                    docModel.setThumbnail(obj.getString("img_path"));
                                    docModel.setDescription(obj.getString("description"));
                                    docModel.setDate(obj.getString("post_date"));
                                    listDoc.add(docModel);

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
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to server
                Map<String, String> params = new HashMap<>();
                params.put("village", village);
                return params;
            }
        };
                ;

        // Adding request to request queue
        CommunicationController.getInstance().addToRequestQueue(documentationReq, tag_string_req);
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
