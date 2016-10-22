package de.it_garten.micha.garbagesaver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Show_Dates extends AppCompatActivity {
    String[] datesResidual = Garbage_Dates.residualDates;
    String[] datesOrganic = Garbage_Dates.organicDates;
    String[] datesPaper = Garbage_Dates.paperDates;
    String[] datesYellow = Garbage_Dates.yellowDates;

    Date[] garbageDates;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    String garbageDate="";
    String dateOrganic="";
    String datePaper="";
    String dateYellow="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__dates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (java.lang.NullPointerException e){
            System.out.println("kein Home");
        }

        garbageDate=getString(R.string.residualWaste)+"\t\t|"+getString(R.string.organicWaste)+"\t\t\t|"+getString(R.string.paperWaste)+"\t|"+getString(R.string.yellowWaste)+"\n";
        garbageDates= new Date[datesResidual.length];
        TextView list = (TextView) findViewById(R.id.show_dates_list);
        for (int i=0; i<datesResidual.length; i++) {
            try{
                garbageDates[i]=sdf.parse(datesResidual[i]);
            } catch (java.text.ParseException e){
                System.out.println("kann nicht parsen");
                break;
            }

            try{
                dateOrganic=datesOrganic[i];
            } catch (java.lang.ArrayIndexOutOfBoundsException e){
                dateOrganic="kein Termin";
            }
            try{
                datePaper=datesPaper[i];
            } catch (java.lang.ArrayIndexOutOfBoundsException e){
                datePaper="kein Termin";
            }
            try{
                dateYellow=datesYellow[i];
            } catch (java.lang.ArrayIndexOutOfBoundsException e){
                dateYellow="kein Termin";
            }
            garbageDate+=datesResidual[i]+"\t|"+dateOrganic+"\t|"+datePaper+"\t|"+dateYellow+"\n";
        }
        list.setText(garbageDate);
    }

}