package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Master extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView lv;
    public ArrayList<Elementy> data;
    private ArrayAdapter<Elementy> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        lv = (ListView) findViewById(R.id.listview_m);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        data.get(position).setProgres(data.get(position).getProgres()+2);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
    }

    public void new_project(View view) {
        Intent intent_new_project = new Intent(this, NowyProjekt.class);
        startActivity(intent_new_project);
    }

    public void profil(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }

    private void elementy(){

        data = new ArrayList<Elementy>();
        data.add(new Elementy(20, "Maciek", "20%", "Aktywne"));
        data.add(new Elementy(59, "Kamil", "59%", "Aktywne"));
        adapter = new ElementyMaster(this, data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }







}
