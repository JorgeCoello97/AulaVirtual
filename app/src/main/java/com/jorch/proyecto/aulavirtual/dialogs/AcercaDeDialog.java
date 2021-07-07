package com.jorch.proyecto.aulavirtual.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jorch.proyecto.aulavirtual.R;

/**
 * Created by JORCH on 22/01/2017.
 */

public class AcercaDeDialog extends DialogFragment {
    public static AcercaDeDialog newInstance() {
        
        Bundle args = new Bundle();
        
        AcercaDeDialog fragment = new AcercaDeDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialog_acerca_de,null);
        builder.setView(view);
        return builder.create();
    }
}
