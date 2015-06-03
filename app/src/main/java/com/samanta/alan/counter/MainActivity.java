package com.samanta.alan.counter;

//import android.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements CounterFragment.OnFragmentInteractionListener, CreateCounterDialog.CounterCreator {

    private int counterid;
	private List<CounterFragment> myCounters;

    public MainActivity(){
        counterid=0;
		myCounters=new LinkedList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newCounter();
        findViewById(R.id.addCounterButton).performClick();
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

    public void addCounter(View v){
        CreateCounterDialog createCounterDialog = new CreateCounterDialog();
        createCounterDialog.show(getSupportFragmentManager(), "addCounter");
    }

    @Override
    public void closeThis(CounterFragment toClose) {
        Log.d("MainActivity", "Closing CounterFragment");
        getSupportFragmentManager().beginTransaction().detach(toClose).commit();
		myCounters.remove(toClose);
    }

    public void newCounter(){
       Log.d("MainActivity", "newCounter method");
        newCounter(new CounterFragment());
    }

    public void newCounter(String title, int startVal){
        Log.d("MainActivity", "newCounter method [dialog version]");
        newCounter(CounterFragment.newInstance(startVal, title));
    }


    private void newCounter(CounterFragment cf){
        Log.d("MainActivity", "Adding New com.samanta.alan.counter.Counter");
        FragmentTransaction addCount = getSupportFragmentManager().beginTransaction();
        FrameLayout fl = new FrameLayout(this);
        fl.setId(counterid);
        addCount.add(fl.getId(),cf,
                "counter"+Integer.toString(counterid));
        ++counterid;
        addCount.commit();
        LinearLayout counterLayout = (LinearLayout) findViewById(R.id.counter_layout);
        counterLayout.addView(fl);
		myCounters.add(cf);
    }

	public void closeAll(MenuItem menuItem){
		while (!myCounters.isEmpty()){
			closeThis(myCounters.get(0));
		}
	}

}
