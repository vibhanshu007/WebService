package vibsbro.com.webservice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://192.168.1.105:8080/examples/default.json";
    private static final String TAG = MainActivity.class.getSimpleName();
    Button button;
    HttpRequest httpRequest;
    ArrayList<HashMap<String, String>>  list;
    private ListView lv;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.go);
        list = new ArrayList();
        lv = (ListView)findViewById(R.id.list);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpRequest = new HttpRequest();
                httpRequest.execute(URL);
            }
        });
        /*httpRequest = new HttpRequest();
        httpRequest.execute(URL);*/

    }

    private class HttpRequest extends AsyncTask<String,Void,Void>{
        String first_name,email, gender, ip_address, id,last_name;
        @Override
        protected Void doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String url = params[0];
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if(jsonStr != null){
                try {
                    /*JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.has(AppConstant.KEY_FIRST_NAME))
                        first_name = jsonObject.getString(AppConstant.KEY_FIRST_NAME);
                        Log.e(TAG, "Result "+first_name);
                        last_name = jsonObject.getString("last_name");
                        email = jsonObject.getString("email");
                        ip = jsonObject.getString("ip");
                        gender = jsonObject.getString("gender");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("ip", ip);
                        contact.put("last_name", last_name);
                        contact.put("first_name", first_name);
                        contact.put("email", email);
                        contact.put("gender", gender);

                        // adding contact to contact list
                        list.add(contact);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Android JSON Parse Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, list,
                    R.layout.list_item, new String[]{ "first_name","last_name","gender","ip"},
                    new int[]{R.id.first_name, R.id.last_name,R.id.gender,R.id.ip});
            lv.setAdapter(adapter);
            mProgressDialog.dismiss();
        }*/
                    // looping through All Contacts
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i <=  jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        String ip = c.getString("ip_address");
                        String first_name = c.getString("first_name");
                       /* String last_name = c.getString("last_name");
                        String email = c.getString("email");
                        String gender = c.getString("gender");*/

                      /*  // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("ip_address", ip_address);
                        contact.put("first_name", first_name);
                       /* contact.put("last_name", last_name);
                        contact.put("email", email);
                        contact.put("gender", gender);
*/
                        // adding contact to contact list
                        list.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
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
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,list ,
                    R.layout.list_item, new String[]{ "email","mobile"},
                    new int[]{R.id.ip_address, R.id.first_name});
            lv.setAdapter(adapter);
        }
    }

}
