package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MasterZadania extends AppCompatActivity implements AdapterView.OnItemClickListener{
    static ArrayList<String> task_lista = new ArrayList<String>();
    Dane dane;
    BarChart barChart;
    ListView lv;
    ArrayList<Elementy> data;
    ArrayAdapter<Elementy> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_zadania);
        barChart = (BarChart) findViewById(R.id.charts_mz_id);
        lv = (ListView) findViewById(R.id.listView_mz);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dane = new Dane();
        elementy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void back(View view) {
        finish();
    }           // Przycisk wstecz

    public void new_task(View view) {
        Intent intent_nowe_zadanie = new Intent(this, NoweZadanie.class);
        startActivity(intent_nowe_zadanie);
    }               // Nowe zadanie

    public void elementy(){
        String priority, name, procent, osoba = "";
        data = new ArrayList<Elementy>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i=0;i<Dane.tasks_list.size();i++){
            for(int j=0; j<task_lista.size();j++){
                if(Dane.tasks_list.get(i).get("task_id").equals(task_lista.get(j))){
                    name = Dane.tasks_list.get(i).get("name").toString();
                    priority = Dane.tasks_list.get(i).get("priority").toString();
                    for(int l=0;l<Dane.users_list.size();l++){
                        if(Dane.users_list.get(l).get("user_id").equals(Dane.tasks_list.get(i).get("user_id"))){
                            osoba = Dane.users_list.get(l).get("name").toString();
                        }
                    }
                    procent = String.valueOf((Integer.parseInt(Dane.tasks_list.get(i).get("used_time").toString())*100)/
                            Integer.parseInt(Dane.tasks_list.get(i).get("time").toString()));
                    data.add(new Elementy(Integer.parseInt(procent), osoba +": " + name, procent + "%", priority));
                    barEntries.add(new BarEntry(i,Integer.parseInt(procent)));
                }
            }
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Zadania");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        barData.setValueTextColor(Color.BLACK);
        barData.setValueTextSize(9f);
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setGridBackgroundColor(Color.WHITE);
        barChart.setDrawGridBackground(true);
        barChart.getDescription().setEnabled(false);
        barChart.setNoDataText("Brak danych!");
        barChart.setNoDataTextColor(Color.WHITE);
        barChart.setData(barData);
        barChart.animateXY(100, 1000);
        barChart.invalidate();
        adapter = new ElementyProjektow(this, data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }                       // Zadania i wykres
}
