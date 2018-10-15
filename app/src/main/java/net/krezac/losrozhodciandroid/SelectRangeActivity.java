package net.krezac.losrozhodciandroid;

import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.krezac.losrozhodciandroid.data_adapters.RangeListAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import io.swagger.client.ApiClient;
import io.swagger.client.Configuration;
import io.swagger.client.api.RangeApi;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.Range;

public class SelectRangeActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_range);

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strBasePath = SP.getString("base_url", "NA");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView errorView = (TextView)findViewById(R.id.errorView);
        errorView.setText("a");

       ApiClient defaultClient = Configuration.getDefaultApiClient();
       defaultClient.setBasePath(strBasePath); // http://localhost:18079/v1
        errorView.setText("b");

        // Configure OAuth2 access token for authorization: los_auth
        OAuth los_auth = (OAuth) defaultClient.getAuthentication("los_auth");
        los_auth.setAccessToken(SP.getString("api_token", "NA"));
        //los_auth.setAccessToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb3MuZGV2IiwianRpIjoidXNlcjFAZGV2LmxvcyIsInJvbGVzIjpbImFkbWluIl19.Ex-ctChRmjb9vw_NRRH8c2_nT9LiTARclPCZRtC_h41i-Uw2eBI5SkHTyviBMklrp5ewDE60PtYwYLN4v7oEZ08W6nGLkC6d7T_9YRUb18fnE298MhXgdxmr-YOBjPdMSuMF4TAEl82TGWVbf1xpkqK85oh8p5WnBDvLLOEkwKg-IA8a2WNFbzno8qSNsOysoy9DRPVCWJllZ_Su5OLKzArsLL50YxWzw9SFjEKREblYkOyuPifqp4b81odjK8CHJZoVWzaLByU_Tym9Tdq-T9Ouej12jRpTTnAGSEhfrsPX_20j9h8dsg3A0Gn8-NywDMk7P3kSWWUUc2ULsJ23iw");

        RangeApi apiInstance = new RangeApi();
        try {
            List<Range> result = apiInstance.getRanges(true);
            System.out.println(result);
            errorView.setText(result.toString());
            spinner = (Spinner) findViewById(R.id.rangeSpinner);

            RangeListAdapter mAdapter = new RangeListAdapter(this, result);
            spinner.setAdapter(mAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Range clickedItem = (Range) parent.getItemAtPosition(position);
                    String clickedCountryName = clickedItem.getName();
                    Toast.makeText(SelectRangeActivity.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            System.err.println("Exception when calling RangeApi#getRangeById");
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            errorView.setText(e.toString() + " **** " + sStackTrace);
        }
    }

    public void getSelectedUser(View v) {
        Range range = (Range) spinner.getSelectedItem();
        displayUserData(range);
    }

    private void displayUserData(Range range) {
        String name = range.getName();
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
    }
}
