package nl.sandhoofd.contactenapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.Random;

public class AddData extends AppCompatActivity {
    final int REQUEST_CODE_GALLERY = 999;
    EditText id,e_name,e_email,e_phone;
    String name, email, phone;
    Button btn_choose;
    ImageView img;
    String idnummer;

    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private boolean hasImageChanged = false;
    Bitmap thumbnail;
    String imageurl, afbeelding;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        imageView = (ImageView) findViewById(R.id.imageView);
        e_name = (EditText) findViewById(R.id.name);
        e_email = (EditText) findViewById(R.id.email);
        e_phone = (EditText) findViewById(R.id.phone);
        btn_choose = (Button) findViewById(R.id.btn_choose);

    }

    public void OnImageChoose(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
//            textTargetUri.setText(targetUri.toString());
            //Toast.makeText(getApplicationContext(), targetUri.toString(), Toast.LENGTH_LONG).show();
            Bitmap bitmap;
            //imageurl = targetUri.toString();

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                imageView.setImageBitmap(bitmap);
                imageurl = saveToInternalStorage(bitmap).toString().trim();
                Toast.makeText(getApplicationContext(),"Image is saved in:" + saveToInternalStorage(bitmap).toString().trim(), Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
private String database() {
        ContactDbHandler contactDbHandler = new ContactDbHandler(this);
        SQLiteDatabase sqLiteDatabase = contactDbHandler.getReadableDatabase();

        Cursor cursor = contactDbHandler.getInformation(sqLiteDatabase);

        if(cursor != null && cursor.moveToFirst()){
            do {
                idnummer = cursor.getString(0);
            } while (cursor.moveToNext());
            contactDbHandler.close();
    }
    return idnummer;
}
    private String saveToInternalStorage(Bitmap bitmapImage){
        //database();
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        Random r = new Random();
        int a = r.nextInt((999999999-123456789)+1)+123456789;
        idnummer = (Integer.parseInt(database()) + 1) + "-" + a ;

        File mypath=new File(directory,""+idnummer+".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
//                Toast.makeText(getApplicationContext(), directory.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "ID: " + idnummer, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ""+mypath;
    }

    public void AddData(View view) {
        name = e_name.getText().toString();
        email = e_email.getText().toString();
        phone = e_phone.getText().toString();
        if(imageurl != null) {
            afbeelding = imageurl;
        } else {
            afbeelding = "";
        }


        BackgroundTask backGroundTask = new BackgroundTask(this);
        backGroundTask.execute("add_info", name, email, phone, afbeelding );
        final Intent intent = new Intent(this, DisplayList.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
            }
        }, 1200); // 3000 milliseconds delay


    }




}
