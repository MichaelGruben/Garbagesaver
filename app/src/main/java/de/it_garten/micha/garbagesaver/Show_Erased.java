package de.it_garten.micha.garbagesaver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Show_Erased extends AppCompatActivity {

    String[] dates = Garbage_Dates.residualDates;

    Date[] garbageDates;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    String garbageDateSetting="";
    String settingText="";
    CharSequence settingTextTemp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref=getSharedPreferences("prefFile",Context.MODE_PRIVATE);
        sharedPref.getAll();
        setContentView(R.layout.activity_show__erased);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (java.lang.NullPointerException e){
            System.out.println("kein Home");
        }

        garbageDates= new Date[dates.length];
        TextView list = (TextView) findViewById(R.id.show_erased_list);
        for (int i=0; i<dates.length; i++) {
            try{
                garbageDates[i]=sdf.parse(dates[i]);
            } catch (java.text.ParseException e){
                System.out.println("kann nicht parsen");
                break;
            }
            settingText="";
            settingTextTemp="";
            settingText=sharedPref.getString(sdf.format(garbageDates[i]),"unbelegt");
            System.out.println(settingText);
            switch (settingText) {
                case "erased":
                    garbageDateSetting += dates[i] + " | " + getString(R.string.erased) + "\n";
                    break;
                case "saved":
                    garbageDateSetting += dates[i] + " | " + getString(R.string.saved) + "\n";
                    break;
                default:
                    garbageDateSetting += dates[i] + " | " + getString(R.string.undefined) + "\n";
                    break;
            }
        }
        list.setText(garbageDateSetting);
    }

}
