package com.happinessfoot.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean checkTableProducts = false;
        ArrayList<Products> products = new ArrayList<>();
        Log.d("MyTag","Тестируем штуку\n");
        Products product = new Products();
        try {
            XmlPullParser parser = getResources().getXml(R.xml.test);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String name;
                String tmp = "";
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if(name.equalsIgnoreCase("table") && parser.getAttributeValue(0).equalsIgnoreCase("products"))
                        {
                            product = new Products();
                            checkTableProducts = true;
                        }
                        if(name.equalsIgnoreCase("column") && parser.getAttributeValue(0).equalsIgnoreCase("id"))
                        {
                            product.id = Integer.parseInt(parser.nextText());
                        }
                        break;
                    case  XmlPullParser.TEXT:
                        if(checkTableProducts)
                        {

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name=parser.getName();
                        if(name.equalsIgnoreCase("table"))
                        {
                            if(checkTableProducts)
                            {
                                products.add(product);
                                checkTableProducts=false;
                            }
                        }
                        break;
                }

                eventType=parser.next();
            }
        } catch (Throwable t) {
            Toast.makeText(this,
                    "Ошибка при загрузке XML-документа: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
        final String TAG = "ЛогКот";

        for (Products p : products) {
            Log.d("MyTag","ID: "+p.name);
        }
    }
}
