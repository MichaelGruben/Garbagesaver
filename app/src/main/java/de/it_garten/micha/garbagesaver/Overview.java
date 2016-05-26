package de.it_garten.micha.garbagesaver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        int val_erased = sharedPref.getInt("garbage_erased",0);
        erased_counter.setText(String.valueOf(val_erased));

        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        int val_saved=sharedPref.getInt("garbage_saved",0);
        saved_counter.setText(String.valueOf(val_saved));
    }

    public void change_counter_erased_plus(View view){
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        Integer val=Integer.parseInt(erased_counter.getText().toString())+1;
        erased_counter.setText(String.valueOf(val));
        SharedPreferences sharedPref=getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putInt("garbage_erased",val);
        editor.commit();
    }

    public void change_counter_erased_minus(View view){
        TextView erased_counter = (TextView) findViewById(R.id.garbage_erased);
        Integer val=Integer.parseInt(erased_counter.getText().toString())-1;
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
    public void change_counter_saved_minus(View view){
        TextView saved_counter = (TextView) findViewById(R.id.garbage_saved);
        Integer val=Integer.parseInt(saved_counter.getText().toString())-1;
        saved_counter.setText(String.valueOf(val));
        SharedPreferences sharedPref=getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putInt("garbage_saved",val);
        editor.commit();
    }
}
