package br.usjt.ciclodevidagpsemapas30;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    private RecyclerView weatherRecyclerView;
    private WeatherAdapter adapter;
    private List<Weather> previsoes;
    private RequestQueue requestQueue;
    public double lat = 0;
    public double lon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude", 0);
        lon = intent.getDoubleExtra("longitude", 0);

        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        previsoes = new ArrayList<>();
        adapter = new WeatherAdapter(previsoes, this);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherRecyclerView.setAdapter(adapter);

        //Toast.makeText(this, "Latitude " + lat + " Longitude " + lon, Toast.LENGTH_LONG).show();

        requestQueue = Volley.newRequestQueue(this);

        try{
            obtemPrevisoes(lat, lon);
        }
        catch(Exception e){
            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?", lat, lon));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void obtemPrevisoes (double lat, double lon){
        String url = getString(
                R.string.web_service_url,
                lat, lon,
                getString(R.string.api_key)
        );
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (response)->{
                    //aqui vamos tratar o json, cujo nome é response nesse caso
                    previsoes.clear();
                    try{
                        JSONArray list = response.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++){
                            JSONObject day = list.getJSONObject(i);
                            JSONObject main = day.getJSONObject("main");
                            JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                            previsoes.add (new Weather(day.getLong("dt"), main.getDouble("temp_min"),
                                    main.getDouble("temp_max"), main.getDouble ("humidity"),
                                    weather.getString("description"),weather.getString("icon")));
                        }
                        adapter.notifyDataSetChanged();
                        dismissKeyboard(weatherRecyclerView);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
                    }
                },
                (error)->{
                    Toast.makeText(WeatherActivity.this, getString(R.string.connect_error) + ": " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(req);
    }

    private void dismissKeyboard (View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
