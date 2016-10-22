package de.it_garten.micha.garbagesaver;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Show_Dates extends AppCompatActivity {
    String[] datesResidual = Garbage_Dates.residualDates;
    String[] datesOrganic = Garbage_Dates.organicDates;
    String[] datesPaper = Garbage_Dates.paperDates;
    String[] datesYellow = Garbage_Dates.yellowDates;

    String garbageDate="";
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    SimpleDateFormat month = new SimpleDateFormat("MMMM", Locale.GERMANY);
    SimpleDateFormat day = new SimpleDateFormat("d. (E)", Locale.GERMANY);

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

        String[] today = new String[1];
        String now = sdf.format(new Date());
        today[0]=now;
        Map<String, String[]> dates = new ArrayMap<>();
        dates.put(getString(R.string.residualWaste), datesResidual);
        dates.put(getString(R.string.organicWaste), datesOrganic);
        dates.put(getString(R.string.paperWaste),datesPaper);
        dates.put(getString(R.string.yellowWaste), datesYellow);
        dates.put(getString(R.string.today), today);

        Map<Date,String> datesList=new ArrayMap<Date, String>();
        int i=0;
        for(String key : dates.keySet()){
            String[] values = dates.get(key);
            for(String val: values){
                Date valDate;
                try{
                    valDate=sdf.parse(val);
                } catch (java.text.ParseException e){
                    valDate= new Date(0);
                }
                if(key.equals(getString(R.string.today))){
                    key="<b style='color:green'>*****<i>"+key+"</i>*****</b>";
                } else {
                    i++;
                }
                if(datesList.containsKey(valDate)){
                    datesList.put(valDate, key+"<br />"+datesList.get(valDate));
                } else {
                    datesList.put(valDate, key);
                }
            }
        }

        ValueComparator bvc = new ValueComparator(datesList);
        TreeMap<Date, String> sorted_map = new TreeMap<Date, String>(bvc);

        sorted_map.putAll(datesList);
        garbageDate="<table style='width:100%; border-collapse:collapse'>";
        String lastMonth="";
        for(Date entry : sorted_map.keySet()){
            if(!lastMonth.equals(month.format(entry))){
                lastMonth=month.format(entry);
                garbageDate+="<tr><td colspan=2 style='height:10px'></td></tr><tr><td colspan=2><b style='font-family:serif'>"+lastMonth+"</b></td></tr>";
            }
            garbageDate+="<tr style='border-bottom:2px dashed lightgrey'><td>"+day.format(entry)+"</td><td>"+datesList.get(entry)+"</td></tr>";
        }
        garbageDate+="</table>";
        ScrollView scrollView = (ScrollView) findViewById(R.id.show_dates_scroll);
        WebView list = (WebView) findViewById(R.id.show_dates_list);
        list.loadData("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+garbageDate,"text/html; charset='UTF-8'",null);
        scrollView.scrollTo(0,i);
    }
}

class ValueComparator implements Comparator<Date> {
    Map<Date, String> base;

    public ValueComparator(Map<Date, String> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(Date a, Date b) {
        if (a.getTime() <= b.getTime()) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}