package nl.sandhoofd.contactenapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayList extends AppCompatActivity implements SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Contact> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ContactDbHandler contactDbHandler = new ContactDbHandler(this);
        SQLiteDatabase sqLiteDatabase = contactDbHandler.getReadableDatabase();

        Cursor cursor = contactDbHandler.getInformation(sqLiteDatabase);
        if(cursor != null && cursor.moveToFirst()){
            do {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                arrayList.add(contact);
            } while (cursor.moveToNext());
            contactDbHandler.close();
            adapter = new RecyclerAdapter(arrayList, this);
            recyclerView.setAdapter(adapter);
        }
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        newText =  newText.toLowerCase();
        ArrayList<Contact> newlist = new ArrayList<>();
        for(Contact contact : arrayList) {
            String name = contact.getName();
            String email = contact.getEmail();
            if(name.contains(newText) || email.contains(newText)){
                newlist.add(contact);
            }
        }
        adapter.setFilter(newlist);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItem contact = menu.findItem(R.id.contact);
        contact.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), AddData.class);
                startActivity(intent);
                return false;
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    public void Adddata(View v) {
        Intent intent = new Intent(this, AddData.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}
