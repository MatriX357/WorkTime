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

    private static Data data;

    static String idO = "";
    static String idP = "";
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
        data = ManagerApplication.data;
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
        for(int i = 0; i< data.tasks_list.size(); i++){
            if(data.tasks_list.get(i).get("user_id").equals(idO) && data.tasks_list.get(i).get("project_id").equals(idP)){
                name2 = data.tasks_list.get(i).get("task").toString();
                priority = data.tasks_list.get(i).get("priority").toString();
                percent = String.valueOf((Integer.parseInt(data.tasks_list.get(i).get("used_time").toString())*100)/
                        Integer.parseInt(data.tasks_list.get(i).get("time").toString()));
                data_A.add(new Elements(Integer.parseInt(percent), name2, percent + "%", priority));
                barEntries.add(new BarEntry(i,Integer.parseInt(percent)));
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

    private void user(){
        for(int i = 0; i< data.users_list.size(); i++){
            if(data.users_list.get(i).get("user_id").equals(idO)){
                name.setText(data.users_list.get(i).get("name").toString());
                email.setText(data.users_list.get(i).get("email").toString());
                phone.setText(data.users_list.get(i).get("phone").toString());
            }
        }
    }                           // Szuka i ustawia data_S Usera
}
