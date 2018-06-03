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

public class UserTasks extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ArrayList<String> task_list;
    static String id = "";
    private String myID;
    private BarChart barChart;
    private ListView lv;
    private Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_zadania);
        barChart = findViewById(R.id.charts_uz_id);
        lv = findViewById(R.id.listView_uz);
        data = ManagerApplication.data;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Data data = new Data();
        myID = data.getMy_hash().get("user_id").toString();
        task();
        elements();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserTask.task_id = task_list.get(position);
        Intent intent_user_zadanie = new Intent(this, UserTask.class);
        startActivity(intent_user_zadanie);
    }

    public void back(View view) {
        finish();
    }       // Przycisk wstecz

    private void elements(){
        String priority, name, percent;
        ArrayList<Elements> data_A = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for(int i = 0; i< data.tasks_list.size(); i++){
            for(int j = 0; j< task_list.size(); j++){
                if(data.tasks_list.get(i).get("task_id").equals(task_list.get(j))){
                    name = data.tasks_list.get(i).get("name").toString();
                    priority = data.tasks_list.get(i).get("priority").toString();
                    percent = String.valueOf((Integer.parseInt(data.tasks_list.get(i).get("used_time").toString())*100)/
                            Integer.parseInt(data.tasks_list.get(i).get("time").toString()));
                    data_A.add(new Elements(Integer.parseInt(percent), name, percent + "%", priority));
                    barEntries.add(new BarEntry(i+0.0f,Integer.parseInt(percent)));
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
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_A);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }                       // Zadania i wykres

    private void task(){
        task_list = new ArrayList<>();
        for(int i = 0; i< data.tasks_list.size(); i++){
            if(data.tasks_list.get(i).get("project_id").equals(id) && data.tasks_list.get(i).get("user_id").equals(myID)){
                task_list.add(data.tasks_list.get(i).get("task_id").toString());
            }
        }
    }                       // Tworzy liste tasków
}
