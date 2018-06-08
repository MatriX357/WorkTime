package pl.maciejnalewajka.worktime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import pl.maciejnalewajka.worktime.Elements.Elements;
import pl.maciejnalewajka.worktime.Elements.ProjectsElements;

public class PersonMaster extends AppCompatActivity{
    private BarChart barChart;
    private TextView name;
    private TextView email;
    private TextView phone;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_osoba);
        name = findViewById(R.id.textView_mo_name);
        email = findViewById(R.id.textView_mo_email);
        phone = findViewById(R.id.textView_mo_phone);
        barChart = findViewById(R.id.charts_mo_id);
        lv = findViewById(R.id.list_view_mo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        user();
        elementy();

    }

    public void back(View view) {
        finish();
    }           // Przycisk wstecz

    private void elementy(){
        String priority, name2, percent;
        ArrayList<Elements> data_A = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        int position = 1;
        for(String i : ManagerApplication.tasks_list.keySet()){
            if(ManagerApplication.tasks_list.get(i).get("user_id").equals(ManagerApplication.select_user_uuid) && ManagerApplication.tasks_list.get(i).get("project_id").equals(ManagerApplication.project_uuid)){
                name2 = ManagerApplication.tasks_list.get(i).get("task");
                priority = ManagerApplication.tasks_list.get(i).get("priority");
                percent = String.valueOf((Integer.parseInt(ManagerApplication.tasks_list.get(i).get("used_time"))*100)/
                        Integer.parseInt(ManagerApplication.tasks_list.get(i).get("time")));
                data_A.add(new Elements(Integer.parseInt(percent), name2, percent + "%", priority));
                barEntries.add(new BarEntry(position,Integer.parseInt(percent)));
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
        ArrayAdapter<Elements> adapter = new ProjectsElements(this, data_A);
        lv.setAdapter(adapter);
    }                       // Zadania i wykres

    private void user() {
        name.setText(ManagerApplication.users_list.get(ManagerApplication.select_user_uuid).get("name"));
        email.setText(ManagerApplication.users_list.get(ManagerApplication.select_user_uuid).get("email"));
        phone.setText(ManagerApplication.users_list.get(ManagerApplication.select_user_uuid).get("phone"));
    }
}
