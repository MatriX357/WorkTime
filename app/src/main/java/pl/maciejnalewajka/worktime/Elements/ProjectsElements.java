package pl.maciejnalewajka.worktime.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.maciejnalewajka.worktime.R;


public class ProjectsElements extends ArrayAdapter<Elements> {

    private final Context context;
    private final List<Elements> data;

    public ProjectsElements(Context context, ArrayList<Elements> data) {
        super(context, R.layout.list_projects);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ElementsHolder holder;
        if (row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_projects, null);
            holder = new ElementsHolder();
            holder.progress = row.findViewById(R.id.l_progressBar);
            holder.name = row.findViewById(R.id.l_nazwa);
            holder.percent = row.findViewById(R.id.l_percent);
            holder.active = row.findViewById(R.id.l_active);
            row.setTag(holder);
        }
        else{
            holder = (ElementsHolder) row.getTag();
        }
        holder.progress.setProgress(data.get(position).getProgress());
        holder.progress.setProgressTintList(ColorStateList.valueOf(Color.rgb(189, 228, 244)));
        holder.name.setText(data.get(position).getName());
        holder.percent.setText(data.get(position).getPercent());
        holder.active.setText(data.get(position).getActive());
        return row;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    static class ElementsHolder {
        ProgressBar progress;
        TextView name;
        TextView percent;
        TextView active;
    }
}