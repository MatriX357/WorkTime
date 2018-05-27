package pl.maciejnalewajka.worktime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MasterOsoba extends AppCompatActivity{
    static String idO = "";
    static String idP = "";
    Dane dane;
    BarChart barChart;
    TextView name, email, phone;
    ListView lv;
    ArrayList<Elementy> data;
    ArrayAdapter<Elementy> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_osoba);
        name = (TextView) findViewById(R.id.textView_mo_name);
        email = (TextView) findViewById(R.id.textView_mo_email);
        phone = (TextView) findViewById(R.id.textView_mo_phone);
        barChart = (BarChart) findViewById(R.id.charts_mo_id);
        lv = (ListView) findViewById(R.id.listview_mo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dane = new Dane();
        user();
        elementy();

    }

    public void back(View view) {
        finish();
    }           // Przycisk wstecz

    public void elementy(){
        String priority, name2, procent;
        data = new ArrayList<Elementy>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i=0;i<Dane.tasks_list.size();i++){
            if(Dane.tasks_list.get(i).get("user_id").equals(idO) && Dane.tasks_list.get(i).get("project_id").equals(idP)){
                name2 = Dane.tasks_list.get(i).get("task").toString();
                priority = Dane.tasks_list.get(i).get("priority").toString();
                procent = String.valueOf((Integer.parseInt(Dane.tasks_list.get(i).get("used_time").toString())*100)/
                        Integer.parseInt(Dane.tasks_list.get(i).get("time").toString()));
                data.add(new Elementy(Integer.parseInt(procent), name2, procent + "%", priority));
                barEntries.add(new BarEntry(i,Integer.parseInt(procent)));
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
    }                       // Zadania i wykres

    public void user(){
        for(int i=0; i<Dane.users_list.size();i++){
            if(Dane.users_list.get(i).get("user_id").equals(idO)){
                name.setText(Dane.users_list.get(i).get("name").toString());
                email.setText(Dane.users_list.get(i).get("email").toString());
                phone.setText(Dane.users_list.get(i).get("phone").toString());
            }
        }
    }                           // Szuka i ustawia dane Usera
}
