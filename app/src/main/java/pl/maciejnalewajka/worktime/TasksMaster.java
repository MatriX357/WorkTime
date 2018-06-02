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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import pl.maciejnalewajka.worktime.Elements.Elements;
import pl.maciejnalewajka.worktime.Elements.ProjectsElements;

public class TasksMaster extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ArrayList<String> task_list;
    static String id="";
    private ArrayList<String> users_list;
    private BarChart barChart;
    private ListView lv;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_zadania);
        barChart = findViewById(R.id.charts_mz_id);
        lv = findViewById(R.id.listView_mz);
        data = ManagerApplication.data;
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList();
        elements();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonMaster.idO = users_list.get(position);
        Intent intent_master_osoba = new Intent(this, PersonMaster.class);
        startActivity(intent_master_osoba);
    }

    public void back(View view) {
        finish();
    }           // Przycisk wstecz

    public void new_task(View view) {
        Intent intent_nowe_zadanie = new Intent(this, NewTask.class);
        startActivity(intent_nowe_zadanie);
    }               // Nowe zadanie

    private void elements(){
        String priority, name, percent, person = "";
        users_list = new ArrayList<>();
        ArrayList<Elements> data_S = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i< data.tasks_list.size(); i++){
            for(int j = 0; j< task_list.size(); j++){
                if(data.tasks_list.get(i).get("task_id").equals(task_list.get(j))){
                    name = data.tasks_list.get(i).get("task").toString();
                    priority = data.tasks_list.get(i).get("priority").toString();
                    for(int l = 0; l< data.users_list.size(); l++){
                        if(data.users_list.get(l).get("user_id").equals(data.tasks_list.get(i).get("user_id"))){
                            person = data.users_list.get(l).get("name").toString();
                            users_list.add(data.users_list.get(l).get("user_id").toString());
                        }
                    }
                    percent = String.valueOf((Integer.parseInt(data.tasks_list.get(i).get("used_time").toString())*100)/
                            Integer.parseInt(data.tasks_list.get(i).get("time").toString()));
                    data_S.add(new Elements(Integer.parseInt(percent), person +": " + name, percent + "%", priority));
                    barEntries.add(new BarEntry(i,Integer.parseInt(percent)));
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
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_S);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }                       // Zadania i wykres

    private void taskList(){
        task_list = new ArrayList<>();
        for(int i = 0; i< data.tasks_list.size(); i++){
            if(data.tasks_list.get(i).get("project_id").equals(id)){
                task_list.add(data.tasks_list.get(i).get("task_id").toString());
            }
        }
    }                   // Lista tasków do wyświetlenia
}
