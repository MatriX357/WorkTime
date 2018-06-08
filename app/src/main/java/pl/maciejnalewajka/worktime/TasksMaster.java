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

public class TasksMaster extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private BarChart barChart;
    private ListView lv;
    private SparseArray<String> user_map = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_zadania);
        barChart = findViewById(R.id.charts_mz_id);
        lv = findViewById(R.id.listView_mz);
    }

    @Override
    protected void onResume() {
        super.onResume();
        elements();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ManagerApplication.select_user_uuid = user_map.get(position);
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
        String priority, name, percent, person, user_id;
        ArrayList<Elements> data_S = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        int position = 0;
        for(String i : ManagerApplication.tasks_list.keySet()){
            if(ManagerApplication.project_uuid.equals(ManagerApplication.tasks_list.get(i).get("project_id"))) {
                name = ManagerApplication.tasks_list.get(i).get("task");
                priority = ManagerApplication.tasks_list.get(i).get("priority");
                user_id = ManagerApplication.tasks_list.get(i).get("user_id");
                person = ManagerApplication.users_list.get(user_id).get("name");
                user_map.put(position, user_id);
                percent = String.valueOf((Integer.parseInt(ManagerApplication.tasks_list.get(i).get("used_time")) * 100) / Integer.parseInt(ManagerApplication.tasks_list.get(i).get("time").toString()));
                data_S.add(new Elements(Integer.parseInt(percent), person + ": " + name, percent + "%", priority));
                barEntries.add(new BarEntry(position - 1 + 0.0f, Integer.parseInt(percent)));
                position = position + 1;
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
}
