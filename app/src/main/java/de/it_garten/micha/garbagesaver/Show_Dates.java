package de.it_garten.micha.garbagesaver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Show_Dates extends AppCompatActivity {

    String[] datesResidual = {
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

    String[] datesOrganic = {
            "14.01.2016",
            "28.01.2016",
            "11.02.2016",
            "25.02.2016",
            "10.03.2016",
            "23.03.2016",
            "07.04.2016",
            "21.04.2016",
            "06.05.2016",
            "20.05.2016",
            "02.06.2016",
            "16.06.2016",
            "30.06.2016",
            "14.07.2016",
            "28.07.2016",
            "11.08.2016",
            "25.08.2016",
            "08.09.2016",
            "22.09.2016",
            "07.10.2016",
            "20.10.2016",
            "04.11.2016",
            "17.11.2016",
            "01.12.2016",
            "15.12.2016",
            "30.12.2016"
    };

    String[] datesPaper = {
            "21.01.2016",
            "16.02.2016",
            "15.03.2016",
            "12.04.2016",
            "10.05.2016",
            "07.06.2016",
            "05.07.2016",
            "02.08.2016",
            "30.08.2016",
            "27.09.2016",
            "25.10.2016",
            "22.11.2016",
            "20.12.2016"
    };

    String[] datesYellow = {
            "07.01.2016",
            "03.02.2016",
            "02.03.2016",
            "31.03.2016",
            "27.04.2016",
            "25.05.2016",
            "22.06.2016",
            "20.07.2016",
            "18.08.2016",
            "14.09.2016",
            "12.10.2016",
            "09.11.2016",
            "07.12.2016"
    };

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