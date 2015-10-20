package sidelance.com.stormclouds.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import sidelance.com.stormclouds.R;

/**
 *
 */
public class AlertDialogFragment extends DialogFragment {

    /**
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(getString(R.string.ERROR_MSG_HEADER))
                .setMessage(R.string.ERROR_MSG_BODY)
                .setPositiveButton(getString(R.string.ERROR_MSG_OK_BUTTON), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                });


        return builder.create();
    }
}
