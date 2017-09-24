package com.example.bansal.fancymonkdemo;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity  {

    CardView r1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    GoogleApiClient mGoogleApiClient;
    CardView cardView;
    EditText editText1;
    TextInputLayout textInputLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        r1 = (CardView) findViewById(R.id.next);
        textInputLayout = (TextInputLayout) findViewById(R.id.emailLogin);
        cardView = (CardView) findViewById(R.id.signInButton);
        editText1 = (EditText) findViewById(R.id.editText);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }
    @Override
    protected void onResume() {
        super.onResume();
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = editText1.getText().toString().trim();
                if(!emailUser.matches("")  ) {
                    if(isValidEmail(emailUser)){
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("data",emailUser);
                    startActivity(intent);}
                    else {
//                       LayoutInflater inflater = getLayoutInflater();
                        LayoutInflater li = getLayoutInflater();
                        //Getting the View object as defined in the customtoast.xml file
                        View layout = li.inflate(R.layout.toast,
                                (ViewGroup) findViewById(R.id.toast_layout_root));


                        TextView text = (TextView) layout.findViewById(R.id.text);
                        text.setText("Invalid email");

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM, 0, 150);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }
                }
                else {
             //      LayoutInflater li = getLayoutInflater();
                    //Getting the View object as defined in the customtoast.xml file
                    LayoutInflater li = getLayoutInflater();
                    View layout = li.inflate(R.layout.toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));


                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Please enter email");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0, 150);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            if (result.isSuccess()) {
                //Getting google account
                GoogleSignInAccount acct = result.getSignInAccount();
                firebaseAuthWithGoogle(acct);


                //Displaying name and email


            } else {
                //If login fails
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this , User.class);
                            intent.putExtra("data",acct.getEmail());
                            intent.putExtra("data1",acct.getDisplayName());
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public final static boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
