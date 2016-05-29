package de.it_garten.micha.garbagesaver;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        String[] dates = {
                "08.01.2016",
                "21.01.2016",
                "04.02.2016",
                "18.02.2016",
                "03.03.2016",
                "17.01.2016",
                "01.04.2016",
                "14.04.2016",
                "28.04.2016",
                "12.05.2016",
                "27.05.2016",
                "09.06.2016",
                "23.06.2016",
                "07.07.2016",
                "21.07.2016",
                "04.08.2016",
                "19.08.2016",
                "01.09.2016",
                "15.09.2016",
                "29.09.2016",
                "13.10.2016",
                "27.10.2016",
                "10.11.2016",
                "24.11.2016",
                "08.12.2016",
                "22.12.2016"
        };
        Date []garbageDates= new Date[dates.length];
        for (int i=0; i<dates.length; i++) {
            try{
                garbageDates[i]=sdf.parse(dates[i]);
            } catch (java.text.ParseException e){
                System.out.println("kann nicht parsen");
            }
        }

        long today=new Date().getTime();
        String next_date="01.01.2016";
        for (Date garbageDate: garbageDates){
            if (today<garbageDate.getTime()){
                next_date= sdf.format(garbageDate);
                break;
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        TextView next_collect = (TextView) findViewById(R.id.next_collect);
       //System.out.println(next_collect.getText());
        next_collect.setText(String.valueOf(next_date));

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
