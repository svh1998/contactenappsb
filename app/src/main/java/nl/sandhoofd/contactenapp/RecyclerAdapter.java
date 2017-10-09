package nl.sandhoofd.contactenapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by svanh on 2-10-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    ArrayList<Contact> arrayList = new ArrayList<>();
    Context context;

    public RecyclerAdapter(ArrayList<Contact> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Contact contact = arrayList.get(position);
        holder.id.setText(contact.getId());
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        holder.phone.setText(contact.getPhone());
        Log.d("Status", "going to add image.");

        Log.d("RecyclerAdapter ","url: "+contact.getImage());
        Uri.parse(contact.getImage());
        File f = new File(contact.getImage());
//        Picasso.with(context).load("https://api.learn2crack.com/android/images/donut.png").into(holder.img);
        //Picasso.with(context).load(contact.getImage()).into(holder.img);
        try {
            File file =new File(contact.getImage());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

//            ImageView img=(ImageView)context.findViewById(R.id.imgPicker);
//            img.setImageBitmap(b);
            holder.img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private void loadImageFromStorage(String path)
    {



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone, id;
        ImageView img;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.rowid);
            name = (TextView)itemView.findViewById(R.id.naam);
            email = (TextView) itemView.findViewById(R.id.emailadres);
            phone = (TextView) itemView.findViewById(R.id.telefoon);
            img = (ImageView) itemView.findViewById(R.id.afbeelding1);
        }
    }

    public void setFilter(ArrayList<Contact> newlist){
        arrayList = new ArrayList<>();
        arrayList.addAll(newlist);
        notifyDataSetChanged();
    }
}
