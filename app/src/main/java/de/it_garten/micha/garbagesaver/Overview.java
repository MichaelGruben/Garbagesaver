package de.it_garten.micha.garbagesaver;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date[] garbageDate = {
                new Date(sdf.parse("08.01.2016")),
                new Date(sdf.parse("21.01.2016")),
                new Date(sdf.parse("04.02.2016")),
                new Date(sdf.parse("18.02.2016")),
                new Date(sdf.parse("03.03.2016")),
                new Date(sdf.parse("17.01.2016")),
                new Date(sdf.parse("01.04.2016")),
                new Date(sdf.parse("14.04.2016")),
                new Date(sdf.parse("28.04.2016")),
                new Date(sdf.parse("12.05.2016")),
                new Date(sdf.parse("27.05.2016")),
                new Date(sdf.parse("09.06.2016")),
                new Date(sdf.parse("23.06.2016")),
                new Date(sdf.parse("07.07.2016")),
                new Date(sdf.parse("21.07.2016")),
                new Date(sdf.parse("04.08.2016")),
                new Date(sdf.parse("19.08.2016")),
                new Date(sdf.parse("01.09.2016")),
                new Date(sdf.parse("15.09.2016")),
                new Date(sdf.parse("29.09.2016")),
                new Date(sdf.parse("13.10.2016")),
                new Date(sdf.parse("27.10.2016")),
                new Date(sdf.parse("10.11.2016")),
                new Date(sdf.parse("24.11.2016")),
                new Date(sdf.parse("08.12.2016")),
                new Date(sdf.parse("22.12.2016"))
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        int val_erased = sharedPref.getInt("garbage_erased",0);
        erased_counter.setText(String.valueOf(val_erased));

        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        int val_saved=sharedPref.getInt("garbage_saved",0);
        saved_counter.setText(String.valueOf(val_saved));

        TextView sum_counter = (TextView) findViewById(R.id.garbage_sum);
        sum_counter.setText(String.valueOf(val_saved+val_erased));
    }
//Grundgebühr 47,52€ und je Leerung 2,20€
    public void change_counter_erased_plus(View view){
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        Integer val=Integer.parseInt(erased_counter.getText().toString())+1;
        erased_counter.setText(String.valueOf(val));
        SharedPreferences sharedPref=getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putInt("garbage_erased",val);
        editor.commit();
    }

    public void change_counter_saved_plus(View view){
        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        Integer val=Integer.parseInt(saved_counter.getText().toString())+1;
        saved_counter.setText(String.valueOf(val));
        SharedPreferences sharedPref=getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putInt("garbage_saved",val);
        editor.commit();
    }

    public void change_counter_minus(View view){
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        Integer val=Integer.parseInt(erased_counter.getText().toString())-1;
        erased_counter.setText(String.valueOf(val));
        SharedPreferences sharedPref=getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putInt("garbage_erased",val);
        editor.commit();
    }
}
