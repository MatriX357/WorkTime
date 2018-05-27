package pl.maciejnalewajka.worktime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ElementyProjektow extends ArrayAdapter<Elementy> {

    private Context context;
    private List<Elementy> data;

    public ElementyProjektow(Context context, ArrayList<Elementy> data) {
        super(context, R.layout.list_projects);
        this.data = data;
        this.context = context;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ElementyHolder holder = null;
        if (row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_projects, null);
            holder = new ElementyHolder();
            holder.progres = (ProgressBar) row.findViewById(R.id.l_progressBar);
            holder.name = (TextView) row.findViewById(R.id.l_nazwa);
            holder.procent = (TextView) row.findViewById(R.id.l_procent);
            holder.active = (TextView) row.findViewById(R.id.l_active);
            row.setTag(holder);
        }
        else{
            holder = (ElementyHolder) row.getTag();
        }
        holder.progres.setProgress(data.get(position).getProgres());
        holder.progres.setProgressTintList(ColorStateList.valueOf(Color.rgb(189, 228, 244)));
        holder.name.setText(data.get(position).getName());
        holder.procent.setText(data.get(position).getProcent());
        holder.active.setText(data.get(position).getActive());
        return row;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    static class ElementyHolder{
        ProgressBar progres;
        TextView name;
        TextView procent;
        TextView active;
    }
}