package pl.maciejnalewajka.worktime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Master extends AppCompatActivity{
    ListView lv;
    LinkedHashMap<Integer, String> listdatas ;
    LayoutInflater inflater ;
    AddjavaListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        listdatas  = new LinkedHashMap<Integer, String>() ;
        inflater = LayoutInflater.from(this);
        adapter = new AddjavaListAdapter();
        lv.setAdapter(adapter) ;
    }

    @Override
    public void onBackPressed() {
    }



    public void new_project(View view) {
        Intent intent_new_project = new Intent(this, NowyProjekt.class);
        startActivity(intent_new_project);
    }

    public void profil(View view) {
        Intent intent_profil = new Intent(this, Profil.class);
        startActivity(intent_profil);
    }

    class Kontenery{
        ProgressBar bar;
        TextView name, procent, priority;
    }

    class AddjavaListAdapter extends BaseAdapter {



        @Override
        public int getCount() {
            return listdatas.keySet().size();
        }

        @Override
        public Object getItem(int position) {
            Object[] d = listdatas.keySet().toArray() ;
            int c = (Integer)d[position] ;
            return listdatas.get(c) ;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            Kontenery holder ;
            if (v==null) {
                v = inflater.inflate(R.layout.list_projects, null) ;
                holder = new Kontenery() ;

                holder.name = (TextView) v.findViewById(R.id.l_nazwa) ;
                holder.bar = (ProgressBar) v.findViewById(R.id.l_progressBar);
                holder.priority = (Button) (TextView) v.findViewById(R.id.l_priorytet) ;
                holder.procent = (TextView) v.findViewById(R.id.l_procent) ;

                v.setTag(holder) ;

            }else{
                holder = (Kontenery) v.getTag();
            }

            holder.name.setText("Maciek") ;
            holder.bar.setProgress(20) ;

            return v;
        }
    }
}
