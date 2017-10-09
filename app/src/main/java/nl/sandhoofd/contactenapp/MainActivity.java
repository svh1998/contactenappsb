package nl.sandhoofd.contactenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button b1, b2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);

    }
    public void downloadList(View v) {
        intent = new Intent(this, AddData.class);
        startActivity(intent);
    }

    public void displayList (View v) {
        intent = new Intent(this, DisplayList.class);
        startActivity(intent);
    }
}
