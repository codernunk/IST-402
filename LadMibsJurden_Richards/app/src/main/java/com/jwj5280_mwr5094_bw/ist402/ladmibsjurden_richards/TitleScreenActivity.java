package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class TitleScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        readStoryFile();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void readStoryFile(){
        try{
            Resources res = getResources();
            XmlResourceParser xrp = res.getXml(R.xml.stories);

            while (xrp.next() == XmlResourceParser.START_TAG) {
                if (xrp.getName().equals("title")) {
                    String title = xrp.nextText();
                    Log.d("XML stuff", title);
                }
                if (xrp.getName().equals("text")) {
                    String text = xrp.nextText();
                    Log.d("XML stuff", text);
                }
            }
        }catch (Exception e){
            Log.e("test",e.toString());
        }
    }
}
