package com.dfrobot.angelo.blunobasicdemo;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class datashare_consent extends DialogFragment {

    private CheckBox cbConsent;
    private Button btnCancel, btnSave;
    private OnConsentListener listener;
    private boolean currentConsent;
    public static datashare_consent newInstance() {
        return new datashare_consent();
    }
    public interface OnConsentListener {
        void onConsentChanged(boolean consentGiven);
    }
  //  @Override
   // public void onAttach(@NonNull Context context) {
     //   super.onAttach(context);
       // try {
         //   listener = (OnConsentListener) context;
        //} catch (ClassCastException e) {
          //  throw new ClassCastException(context.toString() + " must implement OnConsentListener");
        //}
    //}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datashare_consent_dialogfragment, container, false);

        cbConsent = view.findViewById(R.id.cb_consent);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);

        // Load current consent status
        //SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //boolean currentConsent = prefs.getBoolean(KEY_CONSENT_GIVEN, false);
        cbConsent.setChecked(currentConsent);
        btnSave.setOnClickListener(v -> {
            // Optional: notify listener if needed
            if (listener != null) {
                listener.onConsentChanged(cbConsent.isChecked());
            }

            // Close the dialog
            dismiss();
        });
        btnCancel.setOnClickListener(v -> dismiss());
       // setupClickListeners();

        return view;
    }
}

