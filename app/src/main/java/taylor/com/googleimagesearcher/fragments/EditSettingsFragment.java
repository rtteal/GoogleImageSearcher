package taylor.com.googleimagesearcher.fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import taylor.com.googleimagesearcher.R;
import taylor.com.googleimagesearcher.activities.SearchActivity;
import taylor.com.googleimagesearcher.models.Settings;

/**
 * Created by rtteal on 2/12/2015.
 */
public class EditSettingsFragment extends DialogFragment {
    private Spinner sizeSpinner, typeSpinner, colorSpinner;
    private EditText etSite;
    private Settings settings;

    public interface EditSettingsFragmentListener{
        void onFinishEditDialog(Settings settings);
    }

    public static EditSettingsFragment newInstance(Settings settings){
        EditSettingsFragment frag = new EditSettingsFragment();
        Bundle args = new Bundle();
        args.putString("title", "Advanced Settings");
        args.putSerializable("settings", settings);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_settings, container);
        String title = getArguments().getString("title", "Advanced Filters");
        getDialog().setTitle(title);
        settings = (Settings) getArguments().getSerializable("settings");

        sizeSpinner = (Spinner) view.findViewById(R.id.spSize);
        Button btSave = (Button) view.findViewById(R.id.btSave);
        typeSpinner = (Spinner) view.findViewById(R.id.spType);
        colorSpinner = (Spinner) view.findViewById(R.id.spColor);
        etSite = (EditText) view.findViewById(R.id.etSite);
        if (null != settings){
            // TODO figure out how to set the selection
        }
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = sizeSpinner.getSelectedItem().toString();
                String type = typeSpinner.getSelectedItem().toString();
                String color = colorSpinner.getSelectedItem().toString();
                String site = etSite.getText().toString();
                Settings settings = new Settings(size, color, type, site);
                Log.d("DEBUG", settings.toString());
                EditSettingsFragmentListener s = (EditSettingsFragmentListener) getActivity();
                s.onFinishEditDialog(settings);
                dismiss();
            }
        });
        Button btCancel = (Button) view.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
