package pl.maciejnalewajka.worktime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UserZadania extends AppCompatActivity implements AdapterView.OnItemClickListener{
    BarChart barChart;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_zadania);
        barChart = (BarChart) findViewById(R.id.charts_uz_id);
        lv = (ListView) findViewById(R.id.listView_uz);

        charts();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void back(View view) {
        finish();
    }

    public void charts(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(12,12));
        barEntries.add(new BarEntry(2, 39));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Projekty");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        barChart.setDrawGridBackground(true);
        barChart.setData(barData);
    }
}
