package de.it_garten.micha.garbagesaver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Show_Erased extends AppCompatActivity {

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

    Date[] garbageDates;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    String garbageDateSetting="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Activity parent = this.getParent();
        SharedPreferences sharedPref=getSharedPreferences("prefFile",Context.MODE_PRIVATE);
        sharedPref.getAll();
        super.onCreate(savedInstanceState);
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
            garbageDateSetting+=dates[i]+" | "+sharedPref.getString(sdf.format(garbageDates[i]),"unbelegt")+"\n";
        }
        list.setText(garbageDateSetting);
    }

}
