package de.it_garten.micha.garbagesaver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/*
Activity that shows an info-screen
 */
public class Show_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (java.lang.NullPointerException e){
            System.out.println("kein Home");
        }

        final Button button = (Button) findViewById(R.id.downloadbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://apps.it-garten.de/apk/muell-app.apk"));
                startActivity(browserIntent);
            }
        });

    }
}
