package com.samanta.alan.counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by alan on 6/1/2015.
 */
public class CreateCounterDialog extends DialogFragment {

    /*
        has the newCounter method that will be called when this dialog
        is ready to create a new counter
     */
    public interface CounterCreator{
        public void newCounter(String title, int startVal);
    }

    //the private instance of the CounterCreator interface for this dialog
    private CounterCreator myCC;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            myCC = (CounterCreator) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement CounterCreator");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View myView = getActivity().getLayoutInflater().inflate(
                R.layout.create_counter, null);
        builder.setView(myView);

		final NumberPicker sv= (NumberPicker) myView.findViewById(R.id.start_Val_Picker);
		sv.setMinValue(0);
		sv.setMaxValue(10000);

        builder.setPositiveButton(R.string.create_counter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText titleBox = (EditText) myView.findViewById(R.id.title_Text);
						CheckBox checkBox = (CheckBox) myView.findViewById(R.id.negCheckBox);

						int val= checkBox.isChecked() ? -1*sv.getValue() : sv.getValue();

                        myCC.newCounter(titleBox.getText().toString(), val);
                    }
                });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

	/**
	 * Sets up the given number picker to have the correct values
	 * currently max/min set at 10000 arbitrarily
	 * @param np number picker to be set up
	 */
	//No longer used!
	private void setUpNumberPicker(NumberPicker np){
		final int minVal=-10000;
		final int maxVal=10000;

		np.setMinValue(0);
		np.setMaxValue(maxVal-minVal);
		np.setValue(10000);

		np.setFormatter(new NumberPicker.Formatter() {
			@Override
			public String format(int value) {
				return Integer.toString(value+minVal);
			}
		});
	}
}
