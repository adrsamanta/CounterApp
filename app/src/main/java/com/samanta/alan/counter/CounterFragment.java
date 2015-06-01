package com.samanta.alan.counter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CounterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CounterFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.decrement_Btn: decrement(); break;
            case R.id.increment_Btn: increment(); break;
            case R.id.close_btn: close(); break;
            default: Log.e("CounterFrag", "Unknown item clicked");
        }
    }
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private int count;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CounterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CounterFragment newInstance(String param1, String param2) {
        CounterFragment fragment = new CounterFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public CounterFragment() {
        count=0;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("CounterFrag", "Creating CounterFrag");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_counter, container, false);

        //find textfield, set to counter value
        EditText field= (EditText) v.findViewById(R.id.counter_Field);
        field.setText(Integer.toString(count));
        //attach text changed listener to field
        field.addTextChangedListener(new countChangedListener());

        //add this as listener to all buttons
        v.findViewById(R.id.decrement_Btn).setOnClickListener(this);
        v.findViewById(R.id.increment_Btn).setOnClickListener(this);
        v.findViewById(R.id.close_btn).setOnClickListener(this);

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void decrement(){
        Log.d("CounterFrag", "Decremented Count");
        --count;
        updateFieldFromCount();
    }

    public void increment(){
        Log.d("CounterFrag", "Incremented Count");
        ++count;
        updateFieldFromCount();
    }

    private class countChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("CounterFrag", "Count Changed Listener Fired");
            try{
                int newCount=Integer.parseInt(s.toString());
                count=newCount;
            }
            catch (NumberFormatException e){
                Log.e("CounterFrag", "NumberFormatException");
                //Eek....
                //?
            }
        }
    }

    private void updateFieldFromCount(){
        EditText field= (EditText) getView().findViewById(R.id.counter_Field);
        field.setText(Integer.toString(count));
    }

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
        // TODO: Update argument type and name
        public void closeThis(CounterFragment toClose);
    }

}
