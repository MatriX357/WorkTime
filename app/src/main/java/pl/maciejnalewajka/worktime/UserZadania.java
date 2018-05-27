package pl.maciejnalewajka.worktime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UserZadania extends AppCompatActivity implements AdapterView.OnItemClickListener{
    static ArrayList<String> task_lista = new ArrayList<String>();
    Dane dane;
    BarChart barChart;
    ListView lv;
    ArrayList<Elementy> data;
    ArrayAdapter<Elementy> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_zadania);
        barChart = (BarChart) findViewById(R.id.charts_uz_id);
        lv = (ListView) findViewById(R.id.listView_uz);
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
    }       // Przycisk wstecz

    public void elementy(){
        String priority, name, procent;
        data = new ArrayList<Elementy>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for(int i=0;i<Dane.tasks_list.size();i++){
            for(int j=0; j<task_lista.size();j++){
                if(Dane.tasks_list.get(i).get("task_id").equals(task_lista.get(j))){
                    name = Dane.tasks_list.get(i).get("name").toString();
                    priority = Dane.tasks_list.get(i).get("priority").toString();
                    procent = String.valueOf((Integer.parseInt(Dane.tasks_list.get(i).get("used_time").toString())*100)/
                            Integer.parseInt(Dane.tasks_list.get(i).get("time").toString()));
                    data.add(new Elementy(Integer.parseInt(procent), name, procent + "%", priority));
                    barEntries.add(new BarEntry(i+0.0f,Integer.parseInt(procent)));
                }
            }
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Zadania");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
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
