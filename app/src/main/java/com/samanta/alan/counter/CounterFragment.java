package com.samanta.alan.counter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


/**
 * fragment that contains a simple counter
 * implements OnClickListener to accept clicks on it's component buttons
 */
public class CounterFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    private static String TITLE="TITLE";
    private static String STARTVAL="START_VAL";

    //the counter backing this fragment
    private Counter myCount;
    //title of this counter
    private String title;


    //container of this fragment
    private OnFragmentInteractionListener mListener;

    /**
     * Factory method to create new CounterFragment
     * @return
     */
    public static CounterFragment newInstance(int start_val, String title) {
        CounterFragment fragment = new CounterFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(STARTVAL, ""+start_val);
        fragment.setArguments(args);
        return fragment;
    }

    //creates a simple counter fragment with counter initiallized to 0
    public CounterFragment() {
        myCount=new Counter();
        title="";
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("CounterFrag", "Creating CounterFrag");
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        this.title=(String) args.get(TITLE);
        this.myCount=new Counter(Integer.parseInt((String) args.get(STARTVAL)));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_counter, container, false);

        //find textfield, set to counter value
        EditText field= (EditText) v.findViewById(R.id.counter_Field);
        field.setText(Integer.toString(myCount.getCount()));

        //attach text changed listener to field
        field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                Log.d("CounterFrag", "Listener fired, unknown event");
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("CounterFrag", "Correct ime event");
                    try{
                        int newCount=Integer.parseInt(v.getText().toString());
                        myCount.setCount(newCount);
                    }
                    catch (NumberFormatException e) {
                        Log.e("CounterFrag", "NumberFormatException");
                        //Eek....
                        //?
                    }

                    handled=true;
                }
                else{
                    Log.e("CounterFrag", "Incorrect ime event");
                    Log.i("CounterFrag", "IME event: "+actionId);
                    if (event==null){
                        Log.e("CounterFrag", "event not triggered by an enter key");
                    }
                }
                return handled;
            }
        });


        //add this as listener to all buttons
        v.findViewById(R.id.decrement_Btn).setOnClickListener(this);
        v.findViewById(R.id.increment_Btn).setOnClickListener(this);
        v.findViewById(R.id.close_btn).setOnClickListener(this);

        TextView titleView = (TextView) v.findViewById(R.id.title_text_view);
        titleView.setText(title);

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            Log.e("CounterFrag", "Couldn't case main activity to OnFragmentInteractionListener");
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.decrement_Btn: decrement(); break;
            case R.id.increment_Btn: increment(); break;
            case R.id.close_btn: close(); break;
            default: Log.e("CounterFrag", "Unknown item clicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * decrements the counter
     */
    public void decrement(){
        Log.d("CounterFrag", "Decremented Count");
        myCount.decrement();
        updateFieldFromCount();
    }

    /**
     * increments the counter
     */
    public void increment(){
        Log.d("CounterFrag", "Incremented Count");
        myCount.increment();
        updateFieldFromCount();
    }

    /**
     * updates the textfield from the current value of the counter
     */
    private void updateFieldFromCount(){
        EditText field= (EditText) getView().findViewById(R.id.counter_Field);
        field.setText(Integer.toString(myCount.getCount()));
    }

    /**
     * closes this fragment by calling the close method on mListener
     */
    public void close(){
        mListener.closeThis(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void closeThis(CounterFragment toClose);
    }

}
