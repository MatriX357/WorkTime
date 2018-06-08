package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
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
    private BarChart barChart;
    private ListView lv;
    private SparseArray<String> task_map = new SparseArray<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_zadania);
        barChart = findViewById(R.id.charts_uz_id);
        lv = findViewById(R.id.listView_uz);
    }

    @Override
    protected void onResume() {
        super.onResume();
        task_map.clear();
        elements();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ManagerApplication.task_uuid = task_map.get(position);
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
        int position = 0;

        for(String i : ManagerApplication.tasks_list.keySet()){
            if(ManagerApplication.project_uuid.equals(ManagerApplication.tasks_list.get(i).get("project_id"))) {
                task_map.put(position, i);
                name = ManagerApplication.tasks_list.get(i).get("name");
                priority = ManagerApplication.tasks_list.get(i).get("priority");
                percent = String.valueOf((Integer.parseInt(ManagerApplication.tasks_list.get(i).get("used_time")) * 100) / Integer.parseInt(ManagerApplication.tasks_list.get(i).get("time").toString()));
                data_A.add(new Elements(Integer.parseInt(percent), name, percent + "%", priority));
                barEntries.add(new BarEntry(position - 1 + 0.0f, Integer.parseInt(percent)));
                position = position + 1;
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
}
