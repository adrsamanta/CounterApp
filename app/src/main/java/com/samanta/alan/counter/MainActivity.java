package com.samanta.alan.counter;

//import android.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity implements CounterFragment.OnFragmentInteractionListener {

    int counterid;

    public MainActivity(){
        counterid=0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newCounter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void closeThis(CounterFragment toClose) {
        Log.d("MainActivity", "Closing CounterFragment");
        getSupportFragmentManager().beginTransaction().detach(toClose).commit();
    }

    public void newCounter(View view){
       Log.d("MainActivity", "newCounter method");
        newCounter();
    }

    private void newCounter(){
        Log.d("MainActivity", "Adding New Counter");
        FragmentTransaction addCount = getSupportFragmentManager().beginTransaction();
        FrameLayout fl = new FrameLayout(this);
        fl.setId(counterid);
        addCount.add(fl.getId(), new CounterFragment(),
                "counter"+Integer.toString(counterid));
        ++counterid;
        addCount.commit();
        LinearLayout counterLayout = (LinearLayout) findViewById(R.id.counter_layout);
        counterLayout.addView(fl);
    }
}
