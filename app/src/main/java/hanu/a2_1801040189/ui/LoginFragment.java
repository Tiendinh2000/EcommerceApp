package hanu.a2_1801040189.ui;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hanu.a2_1801040189.ProductListActivity;
import hanu.a2_1801040189.R;
import hanu.a2_1801040189.models.Product;

public class LoginFragment extends Fragment {


    EditText edt_username, edt_password;
    Button btn_login, btn_GetSignUp;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        edt_username = v.findViewById(R.id.edtUsername);
        edt_password = v.findViewById(R.id.edtPassword);
        btn_login = v.findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        btn_GetSignUp = v.findViewById(R.id.btn_getSignUp);
        btn_GetSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                Fragment fragment = new SignUpFragment();
                manager.beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
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
        return v;
    }


}