package com.flhs.utils;

import android.app.AlertDialog;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConnectionErrorFragment extends DialogFragment {

	private AlertDialogListener mListener;

	public interface AlertDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}




	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Connection Error");
		builder.setMessage("We couldn't connect to the internet. Do you want us to keep trying?");
		builder.setNegativeButton("Give up", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mListener.onDialogNegativeClick(ConnectionErrorFragment.this);
			}
		});
		builder.setPositiveButton("Try again", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mListener.onDialogPositiveClick(ConnectionErrorFragment.this);

			}
		});
		return builder.create();
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (AlertDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement AlertDialogListener");
		}
	}
}
