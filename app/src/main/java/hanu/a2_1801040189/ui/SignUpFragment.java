package hanu.a2_1801040189.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hanu.a2_1801040189.R;


public class SignUpFragment extends Fragment {


    EditText edt_Xmail, edt_passWord, edt_confirm;
    Button btn_signIn;
    FirebaseAuth auth;
    TextView tv_warning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        edt_Xmail = v.findViewById(R.id.edtXmail_SignUp);
        edt_passWord = v.findViewById(R.id.edtPassword_SignUp);
        edt_confirm = v.findViewById(R.id.edtConfirm_SignUp);
        btn_signIn = v.findViewById(R.id.btnSignUp);
        tv_warning = v.findViewById(R.id.tv_warning);
        auth = FirebaseAuth.getInstance();
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edt_Xmail.getText().toString();
                String password = edt_passWord.getText().toString();
                String confirm = edt_confirm.getText().toString();

                Log.d("result123:", ": " + validateXmail(userName));
                if (validateXmail(userName) == null) {
                    if (validatePassword(password, confirm) == null) {
                        auth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                    getFragmentManager().popBackStack();
                                else
                                    Log.d("incorrect", "this xmail has been registed");
                                tv_warning.setText("this xmail has been registed");
                            }
                        });

                    } else {
                        tv_warning.setText(validatePassword(password, confirm));
                    }


                } else {
                    tv_warning.setText(validateXmail(userName));
                }
            }
        });
        return v;
    }


    private String validateXmail(String xmail) {


        String domain;
        String userName;
        if (xmail.length() > 11 || xmail == null) {
            domain = xmail.substring(xmail.length() - 10);
            userName = xmail.substring(0, xmail.length() - 10);
        } else {
            return "your email must follow the format";
        }
        Log.d("new:", "domain: " + domain);
        Log.d("new:", "uiser: " + userName);
        if (domain.equals("@xmail.com") == false) {
            return "xmail must finish with domain:\"@xmail.com\"";
        } else if (userName.length() < 4) {
            return "Name must contain at least 4 characters";
        } else if (containSpecialCharacter(userName) == true) {
            return "No special character in User Name";
        } else if (userName.matches(".*\\d.*") == false) {
            return "Name must contain at leat a number";
        } else {
            return null;
        }
    }

    ;

    private String validatePassword(String password, String confirm) {
        if (password.length() < 6)
            return "Password must contain at least 6 characters";
        else if (password.equals(confirm) == false)
            return "password and confirm password is not the same";
        else
            return null;
    }


    private boolean containSpecialCharacter(String input) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(input);
        boolean isStringContainsSpecialCharacter = matcher.find();
        if (isStringContainsSpecialCharacter)
            return true;
        else
            return false;
    }
}