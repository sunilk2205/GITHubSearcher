package com.search.github.githubsearcher.Main;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.search.github.githubsearcher.R;
import com.search.github.githubsearcher.adapter.CustomListAdapter;
import com.search.github.githubsearcher.app.AppController;
import com.search.github.githubsearcher.model.GitHub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends Activity {
    public static final int RequestPermissionCode = 1;

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String url = "https://api.github.com/search/repositories?q=";
    private ProgressDialog pDialog;
    private List<GitHub> RepoList = new ArrayList<GitHub>();
    private ListView listView;
    private CustomListAdapter adapter;

    private void proceedAfterPermission()
    {
        EditText editName  = (EditText) findViewById(R.id.search_text);
        String SearchQuery = editName.getText().toString();
        if((SearchQuery != null) && (SearchQuery.length() > 0))
        {
            SearchQuery = SearchQuery.replace(" ", "%20");
            AppController.getInstance();
            listView = (ListView) findViewById(R.id.list);
            adapter = new CustomListAdapter(this, RepoList);
            listView.setAdapter(adapter);
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading GitHub...");
            pDialog.show();
            JsonObjectRequest movieReq = new JsonObjectRequest(url+SearchQuery,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            Log.e(TAG, response.toString());
                            hidePDialog();
                            for (int i = 0; i < 1; i++)
                            {
                                try
                                {
                                    JSONArray srcArry = response.getJSONArray("items");
                                    for (int j = 0; j < srcArry.length(); j++)
                                    {
                                        GitHub repo = new GitHub();
                                        Log.e("Number of Sources", String.valueOf(j));
                                        JSONObject outer_object =  srcArry.getJSONObject(j);
                                        JSONObject nested_object = outer_object.getJSONObject("owner");

                                        repo.setName(outer_object.getString("full_name"));
                                        repo.setAvatar(nested_object.getString("avatar_url"));
                                        repo.setDescription(outer_object.getString("description"));
                                        repo.setNumberofstars(Integer.toString(outer_object.getInt("stargazers_count")));
                                        repo.setStargazers(outer_object.getInt("stargazers_count"));
                                        RepoList.add(repo);
                                    }
                                    Collections.sort(RepoList, new Comparator() {
                                        @Override
                                        public int compare(Object o1, Object o2) {
                                            GitHub p1 = (GitHub) o1;
                                            GitHub p2 = (GitHub) o2;
                                            return Integer.valueOf(p2.getStargazers()).compareTo(p1.getStargazers());
                                        }
                                    });
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();
                }
            });
            AppController.getInstance().addToRequestQueue(movieReq);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        PermissionUtil.checkPermission(MainActivity.this, Manifest.permission.INTERNET,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.INTERNET},
                                RequestPermissionCode
                        );
                    }
                    @Override
                    public void onPermissionPreviouslyDenied() {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Definition Found");
                        builder1.setMessage("Application needs to connect to internet in order to fetch GitHub Data");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                    @Override
                    public void onPermissionDisabled() {
                        Toast.makeText(MainActivity.this, "Permission Disabled.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(MainActivity.this, "Permission Granted.", Toast.LENGTH_SHORT).show();
                    }
                });
        Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedAfterPermission();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}