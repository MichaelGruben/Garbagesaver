package de.it_garten.micha.garbagesaver;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Overview extends AppCompatActivity {
    final int MAX_ERASE=26;
    final int MAX_SAVE=8;
    final double ERASE_COST=2.2;

    int saved_val=0;
    int erased_val=0;

    String[] dates = {
            "08.01.2016",
            "21.01.2016",
            "04.02.2016",
            "18.02.2016",
            "03.03.2016",
            "17.03.2016",
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

    String next_date="unbekannt";

    Date[] garbageDates;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

    SharedPreferences sharedPref;

    int lastDateIndex=0;
    int lastFreeIndex=0;
    int lastEraseIndex=0;

    String lastGarbageMethod="undefined";

    String[] garbageDateSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        sharedPref=this.getPreferences(Context.MODE_PRIVATE);
        //Set garbage Dates in array and initialize garbageDateSetting
        garbageDates= new Date[dates.length];
        garbageDateSetting= new String[dates.length];
        sharedPref.getAll();
        SharedPreferences.Editor prefEditor=sharedPref.edit();
        lastFreeIndex=sharedPref.getInt("lastFreeIndex",0);
        for (int i=0; i<dates.length; i++) {
            try{
                garbageDates[i]=sdf.parse(dates[i]);
            } catch (java.text.ParseException e){
                System.out.println("kann nicht parsen");
            }
            garbageDateSetting[i]=sharedPref.getString(sdf.format(garbageDates[i]),"undefined");
            if(!garbageDateSetting[i].equals("undefined") && lastFreeIndex==0){
                    lastFreeIndex = i;
            }
        }

        prefEditor.putInt("lastFreeIndex",lastFreeIndex);
        prefEditor.apply();

        get_next_garbage_date();

        set_text_to_field((TextView) findViewById(R.id.next_collect_date),String.valueOf(next_date));
        set_text_to_field((TextView) findViewById(R.id.max_erase),String.valueOf(MAX_ERASE));
        set_text_to_field((TextView) findViewById(R.id.max_garbage_sum), String.valueOf(MAX_ERASE));
        set_text_to_field((TextView) findViewById(R.id.max_save), String.valueOf(MAX_SAVE));
        erased_val = sharedPref.getInt("garbage_erased", 0);
        set_text_to_field((TextView) findViewById(R.id.garbage_erased), String.valueOf(erased_val));
        saved_val = sharedPref.getInt("garbage_saved", 0);
        set_text_to_field((TextView) findViewById(R.id.garbage_saved), String.valueOf(saved_val));


        update_sum();
        set_setting_for_date(lastFreeIndex);
        set_button_state();
        if(lastFreeIndex>0) {
            set_revert_action_for_index(
                    sharedPref.getString(
                            sdf.format(garbageDates[lastFreeIndex-1]),
                            "undefined"
                    ),
                    lastFreeIndex-1
            );
        } else {
            set_revert_action_for_index("undefined",lastFreeIndex);
        }
    }

    private void set_text_to_field(TextView object, String text){
        object.setText(text);
    }

    //set the date for which a plus-action is going to
    private void set_setting_for_date(int index){
        TextView setting_for_text = (TextView) findViewById(R.id.setting_for);
        if(index>=0 && index<MAX_ERASE) {
            set_text_to_field(setting_for_text,sdf.format(garbageDates[index]));
        } else {
            set_text_to_field(setting_for_text, "Das Ende des Jahres erreicht");
        }
    }

    //get the next garbage collection date by realtime
    private void get_next_garbage_date(){
        long today=new Date().getTime();
        for (int i=0; i < garbageDates.length; i++){
            if (today<garbageDates[i].getTime()){
                next_date= sdf.format(garbageDates[i]);
                lastEraseIndex=i;
                break;
            }
        }
    }

    //set the revert button state
    private void set_revert_action_for_index(String action, int index){
        TextView revert_for_text = (TextView) findViewById(R.id.revert_for);
        Button revert_action_button = (Button) findViewById(R.id.counter_minus);
        if(index<=0 && action.equals("undefined")){
            set_text_to_field(revert_for_text,"Anfang des Jahres");
            set_text_to_field(revert_action_button," rückgängig machen nicht möglich ");
            revert_action_button.setClickable(false);
            revert_action_button.setBackgroundColor(Color.rgb(200,100,100));
        } else {
            if(index<0) index=0;
            set_text_to_field(revert_for_text,sdf.format(garbageDates[index]));
            switch (action) {
                case "erased":
                    revert_action_button.setText("\"geleert\" rückgängig machen ");
                    revert_action_button.setClickable(true);
                    revert_action_button.setBackgroundColor(Color.rgb(100, 200, 100));
                    break;
                case "saved":
                    revert_action_button.setText("\"gespart\" rückgängig machen ");
                    revert_action_button.setClickable(true);
                    revert_action_button.setBackgroundColor(Color.rgb(100, 200, 100));
                    break;
                default:
                    revert_action_button.setText(" rückgängig machen nicht möglich ");
                    revert_action_button.setClickable(false);
                    revert_action_button.setBackgroundColor(Color.rgb(200, 100, 100));
                    break;
            }
        }
    }

    //update the garbage_collection sum
    private void update_sum(){
        TextView sum_counter = (TextView) findViewById(R.id.garbage_sum);
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        erased_val=Integer.parseInt(erased_counter.getText().toString());

        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        saved_val=Integer.parseInt(saved_counter.getText().toString());

        sum_counter.setText(String.valueOf(saved_val+erased_val));

        update_saved_money(saved_val);
    }

    //set the state of the plus-buttons
    private void set_button_state(){
        Button erase_plus_btn = (Button) findViewById(R.id.erase_plus);
        Button save_plus_btn = (Button) findViewById(R.id.save_plus);
        lastFreeIndex=sharedPref.getInt("lastFreeIndex",0);
        boolean clickable=lastEraseIndex>lastFreeIndex;
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        erased_val=Integer.parseInt(erased_counter.getText().toString());
        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        saved_val=Integer.parseInt(saved_counter.getText().toString());
        if(clickable && erased_val<MAX_ERASE){//sharedPref.getString(garbageDateSetting[lastDateIndex],"").equals("") &&
            erase_plus_btn.setBackgroundColor(Color.rgb(100,200,100));
        }
        if(clickable && saved_val<MAX_SAVE) {
            save_plus_btn.setBackgroundColor(Color.rgb(100, 200, 100));
        }
        if(!clickable || erased_val>=MAX_ERASE) {
            erase_plus_btn.setBackgroundColor(Color.rgb(200, 100, 100));
        }
        if(!clickable || saved_val>=MAX_SAVE){
            save_plus_btn.setBackgroundColor(Color.rgb(200,100,100));
        }

        erase_plus_btn.setClickable(clickable);
        save_plus_btn.setClickable(clickable);
    }

    //update the saved money counter
    private void update_saved_money(int saved_val){
        TextView saved_money=(TextView) findViewById(R.id.saved_money);
        double saved_sum=saved_val*ERASE_COST;
        saved_money.setText(String.format("%2.2f", saved_sum));
    };

//Grundgebühr 47,52€ und je Leerung 2,20€
    public void change_counter_erased_plus(View view){
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        erased_val=Integer.parseInt(erased_counter.getText().toString())+1;
        if(erased_val<=MAX_ERASE && erased_val>=0) {
            erased_counter.setText(String.valueOf(erased_val));
            lastFreeIndex=sharedPref.getInt("lastFreeIndex",0);
            SharedPreferences.Editor prefEditor=sharedPref.edit();
            prefEditor.putInt("garbage_erased",erased_val);
            prefEditor.putString(sdf.format(garbageDates[lastFreeIndex]),"erased");
            lastFreeIndex++;
            prefEditor.putInt("lastFreeIndex",lastFreeIndex);
            prefEditor.apply();
            set_revert_action_for_index("erased",lastFreeIndex-1);
            update_sum();
            lastDateIndex++;
            set_button_state();
            set_setting_for_date(lastFreeIndex);
        }
    }

    public void change_counter_saved_plus(View view){
        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        saved_val=Integer.parseInt(saved_counter.getText().toString())+1;
        if(saved_val<=MAX_SAVE && saved_val>=0) {
            saved_counter.setText(String.valueOf(saved_val));
            lastFreeIndex=sharedPref.getInt("lastFreeIndex",0);
            SharedPreferences.Editor prefEditor=sharedPref.edit();
            prefEditor.putInt("garbage_saved",saved_val);
            prefEditor.putString(sdf.format(garbageDates[lastFreeIndex]),"saved");
            lastFreeIndex++;
            prefEditor.putInt("lastFreeIndex",lastFreeIndex);
            prefEditor.apply();
            set_revert_action_for_index("saved",lastFreeIndex-1);
            update_sum();
            set_button_state();
            lastDateIndex++;
            set_setting_for_date(lastFreeIndex);
        }
    }

    public void change_counter_minus(View view){
        TextView counter;
        int val=0;
        lastDateIndex--;
        lastFreeIndex=sharedPref.getInt("lastFreeIndex",0);
        if(lastFreeIndex>0) {
            lastFreeIndex--;
        }
        lastGarbageMethod= sharedPref.getString(sdf.format(garbageDates[lastFreeIndex]),"undefined");
        SharedPreferences.Editor prefEditor=sharedPref.edit();
        if(lastGarbageMethod.equals("erased")) {
            counter = (TextView) findViewById(R.id.garbage_erased);
            val=Integer.parseInt(counter.getText().toString())-1;
            if(val>MAX_ERASE || val<0) {
                val= Integer.parseInt(counter.getText().toString());
            }
            counter.setText(String.valueOf(val));
            prefEditor.putInt("garbage_erased",val);
        } else if (lastGarbageMethod.equals("saved")){
            counter = (TextView) findViewById(R.id.garbage_saved);
            val=Integer.parseInt(counter.getText().toString())-1;
            if(val>MAX_SAVE || val<0) {
                val= Integer.parseInt(counter.getText().toString());
            }
            counter.setText(String.valueOf(val));
            prefEditor.putInt("garbage_saved",val);
        }

        prefEditor.putString(sdf.format(garbageDates[lastFreeIndex]),"undefined");


        update_sum();
        prefEditor.putInt("lastFreeIndex",lastFreeIndex);
        prefEditor.apply();
        set_setting_for_date(lastFreeIndex);
        if(lastFreeIndex>0){
        lastFreeIndex--;}
        set_revert_action_for_index(sharedPref.getString(sdf.format(garbageDates[lastFreeIndex]), "undefined"),lastFreeIndex);
        set_button_state();
    }
}
