package hanu.a2_1801040189.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hanu.a2_1801040189.Activity.ProductListActivity;
import hanu.a2_1801040189.R;

public class LoginFragment extends Fragment {


    EditText edt_username, edt_password;
    Button btn_login, btn_GetSignUp;
    FirebaseAuth auth;
    CheckBox remember;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        auth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        Log.d("checkbox::", checkbox);
        if (checkbox.equals("false")) {
            auth.signOut();
        }

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(v.getContext(), ProductListActivity.class));
            Log.d("auth in framenr", "" + auth.getCurrentUser().getEmail().toString());
        } else {
            Log.d("auth infragment", "null");
        }

        edt_username = v.findViewById(R.id.edtUsername);
        edt_password = v.findViewById(R.id.edtPassword);
        btn_login = v.findViewById(R.id.btnLogin);
        remember = v.findViewById(R.id.cb_remember);
        btn_GetSignUp = v.findViewById(R.id.btn_getSignUp);


        btn_GetSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                Fragment fragment = new SignUpFragment();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in,R.anim.slide_in)
                        .replace(R.id.container, fragment)
                        .addToBackStack("back")
                        .commit();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(v.getContext(), ProductListActivity.class));
                        } else {
                            Log.d("incorrect account", "incorrect");
                        }
                    }
                });
            }
        });


        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit().putString("remember", "true");
                    editor.apply();
                    Log.d("","pont checkbox : truwe");
                } else {
                    SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit().putString("remember", "false");
                    editor.apply();
                    Log.d("","pont checkbox : false");
                }
            }
        });

        return v;
    }


}