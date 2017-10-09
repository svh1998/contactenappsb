package nl.sandhoofd.contactenapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * Created by svanh on 2-10-2017.
 */

public class BackgroundTask extends AsyncTask<String , Void, String> {
    ProgressDialog progressDialog;
    Context context;



    public BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Adding data to database...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String name = params[1];
        String email = params[2];
        String phone = params[3];
        String image = params[4];
        ContactDbHandler contactDBHandler = new ContactDbHandler(context);
        SQLiteDatabase db = contactDBHandler.getWritableDatabase();
        contactDBHandler.putInformation(name,email,phone, image, db);
        contactDBHandler.close();


        return "cool";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 1000); // 3000 milliseconds delay
    }
}
