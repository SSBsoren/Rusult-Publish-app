package com.soren.sagen.driemsfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultsActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON

    private static String url = "https://script.googleusercontent.com/macros/echo?user_content_key=M7MUAJA1z6PVW6RhBj3LhsuCacGo6m_nH7gfa7GWaMXcQam2eB9oFeJgV-vYM3utwxXCYd1gY1qMw_HwTBGik27Csb_VzWEtOJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1GhPSVukpSQTydEwAEXFXgt_wltjJcH3XHUaaPC1fv5o9XyvOto09QuWI89K6KjOu0SP2F-BdwUl299jdiMAbA4gERO78DM6s8EERF2TtEmK7E2bd4yIDL30LB7uVjxodSRa6fx-yJm5y7FLqOV0Tk27B8Rh4QJTQ&lib=MnrE7b2I2PjfH799VodkCPiQjIVyBAxva";


    ArrayList<HashMap<String, String>> marksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        marksList = new ArrayList<>();

        lv =  findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ResultsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Sheet1");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);


                        String name = c.getString("Name");
                        String regedNo = c.getString("RegdNo");
                        String am = c.getString("AppliedMath");
                        String coa = c.getString("COA");
                        String daa = c.getString("DAA");
                        String ds = c.getString("DS");
                        String da = c.getString("DA");
                        String total = c.getString("Total_SGPA");

                        /*// Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("name", name);
                        contact.put("regedNo", regedNo);
                        contact.put("am", am);
                        contact.put("coa", coa);
                        contact.put("daa", daa);
                        contact.put("ds", ds);
                        contact.put("da", da);
                        contact.put("Total_SGPA", total);



                        // adding contact to contact list
                        marksList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ResultsActivity.this, marksList,
                    R.layout.layout_marks, new String[]{"name", "regedNo", "am","coa","daa","ds","da","Total_SGPA"},
                    new int[]{R.id.student_nameTv, R.id.regedNo, R.id.mark_am, R.id.mark_coa, R.id.mark_daa, R.id.mark_ds, R.id.mark_da, R.id.mark_total});

            lv.setAdapter(adapter);
        }

    }
}
