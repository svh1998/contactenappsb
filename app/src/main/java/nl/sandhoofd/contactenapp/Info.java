package nl.sandhoofd.contactenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Info extends AppCompatActivity {

    private RequestQueue requestQueue ;
    private Quote quote;
    private ArrayList<Quote> quotes = new ArrayList<Quote>();

    private XmlPullParserFactory factory;
    private XmlPullParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Volley.newRequestQueue(this);
        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest("https://www.w3schools.com/xml/note.xml", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parser.setInput(new StringReader(response));
                    int event = parser.getEventType();

                    String tag = ""; String value="";

                    while (event != XmlPullParser.END_DOCUMENT) {
                        tag = parser.getName();


                        switch (event) {
                            case XmlPullParser.START_TAG:
                                if(tag.equals("quote")) {
                                    quote = new Quote();
                                    quotes.add(quote);
                                }
                                break;
                            case XmlPullParser.TEXT:
                                value = parser.getText();
                                break;
                            case XmlPullParser.END_TAG:

                                switch (tag) {
                                    case "quote":
                                        quote.setQuote(value);
                                }
                                break;
                        }
                        event = parser.next();
                    }

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i<quotes.size(); i++){
                   quotes.get(i).print_quote();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

        }
    }

