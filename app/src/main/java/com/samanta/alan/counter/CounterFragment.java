package com.samanta.alan.counter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


/**
 * fragment that contains a simple counter
 * implements OnClickListener to accept clicks on it's component buttons
 */
public class CounterFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{
    // TODO: Rename parameter arguments, choose names that match

    private static final String TITLE="TITLE";
    private static final String STARTVAL="START_VAL";
    private static final String classTag = "CounterFrag";


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
        Log.d(classTag, "Creating CounterFrag");
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        if (args!=null) {
            this.title = (String) args.get(TITLE);
            this.myCount = new Counter(Integer.parseInt((String) args.get(STARTVAL)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_counter, container, false);

        //find textfield, set to counter value
        EditText field= (EditText) v.findViewById(R.id.counter_Field);
        field.setText(Integer.toString(myCount.getCount()));
        field.setOnFocusChangeListener(this);
        //attach text changed listener to field
        field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                boolean handled = false;
                Log.d(classTag, "Listener fired, unknown event");
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(classTag, "Correct ime event");
                    if (!setCount(tv.getText().toString())){
                        Log.e(classTag, "Failed to properly set count");
                    }
                    handled=true;
                }
                else{
                    Log.e(classTag, "Incorrect ime event");
                    Log.i(classTag, "IME event: "+actionId);
                    if (event==null){
                        Log.e(classTag, "event not triggered by an enter key");
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

    private boolean setCount(String newVal){
        try{
            int newCount=Integer.parseInt(newVal);
            myCount.setCount(newCount);
            return true;
        }
        catch (NumberFormatException e) {
            Log.e(classTag, "NumberFormatException");
            //Eek....
            //?
            return false;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            Log.e(classTag, "Couldn't case main activity to OnFragmentInteractionListener");
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
            default: Log.e(classTag, "Unknown item clicked");
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
        Log.d(classTag, "Decremented Count");
        myCount.decrement();
        updateFieldFromCount();
    }

    /**
     * increments the counter
     */
    public void increment(){
        Log.d(classTag, "Incremented Count");
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

    //designed to detect if TextView
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(classTag, "FocusChanged");
        if(!hasFocus){
            EditText editText = (EditText) v;
            setCount(editText.getText().toString());
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if(hasFocus){
            Log.d(classTag, "Gained Focus");
        }
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
