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
    final int max_erase=26;
    final int max_save=8;
    int saved_val=0;
    int erased_val=0;
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
    int thisDateNum=0;
    String next_date="unbekannt";
    Date[] garbageDates;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    SharedPreferences sharedPref;
    SharedPreferences.Editor prefEditor;
    int lastDateIndex=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        garbageDates= new Date[dates.length];
        for (int i=0; i<dates.length; i++) {
            try{
                garbageDates[i]=sdf.parse(dates[i]);
            } catch (java.text.ParseException e){
                System.out.println("kann nicht parsen");
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        sharedPref=this.getPreferences(Context.MODE_PRIVATE);
        prefEditor=sharedPref.edit();

        get_next_garbage_date();

        TextView next_collect_date = (TextView) findViewById(R.id.next_collect_date);
        next_collect_date.setText(String.valueOf(next_date));

        TextView max_erase_text = (TextView) findViewById(R.id.max_erase);
        max_erase_text.setText(String.valueOf(max_erase));

        TextView max_garbage_sum_text = (TextView) findViewById(R.id.max_garbage_sum);
        max_garbage_sum_text.setText(String.valueOf(max_erase));

        TextView max_save_text = (TextView) findViewById(R.id.max_save);
        max_save_text.setText(String.valueOf(max_save));

        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        erased_val = sharedPref.getInt("garbage_erased",0);
        erased_counter.setText(String.valueOf(erased_val));

        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        saved_val=sharedPref.getInt("garbage_saved",0);
        saved_counter.setText(String.valueOf(saved_val));

        update_sum();
    }

    private void get_next_garbage_date(){
        long today=new Date().getTime();
        for (int i=0; i < garbageDates.length; i++){
            if (today<garbageDates[i].getTime()){
                next_date= sdf.format(garbageDates[i]);
                lastDateIndex=i-1;
                break;
            }
        }
    }

    private void update_sum(){
        TextView sum_counter = (TextView) findViewById(R.id.garbage_sum);
        sum_counter.setText(String.valueOf(saved_val+erased_val));
    }
//Grundgebühr 47,52€ und je Leerung 2,20€
    public void change_counter_erased_plus(View view){
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        erased_val=Integer.parseInt(erased_counter.getText().toString())+1;
        if(erased_val>max_erase || erased_val<0) {
            erased_val= Integer.parseInt(erased_counter.getText().toString());
        }
            erased_counter.setText(String.valueOf(erased_val));

        prefEditor.putInt("garbage_erased",erased_val);
        prefEditor.commit();

        update_sum();
    }

    public void change_counter_saved_plus(View view){
        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        saved_val=Integer.parseInt(saved_counter.getText().toString())+1;
        if(saved_val>max_save || saved_val<0) {
            saved_val= Integer.parseInt(saved_counter.getText().toString());
        }
        saved_counter.setText(String.valueOf(saved_val));
        prefEditor.putInt("garbage_saved",saved_val);
        prefEditor.commit();

        update_sum();
    }

    public void change_counter_minus(View view){
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        erased_val=Integer.parseInt(erased_counter.getText().toString())-1;
        if(erased_val>max_erase || erased_val<0) {
            erased_val= Integer.parseInt(erased_counter.getText().toString());
        }
        erased_counter.setText(String.valueOf(erased_val));
        prefEditor.putInt("garbage_erased",erased_val);
        prefEditor.commit();

        update_sum();
    }
}
