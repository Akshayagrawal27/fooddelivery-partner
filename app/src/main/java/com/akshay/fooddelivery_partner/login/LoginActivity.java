package com.akshay.fooddelivery_partner.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.akshay.fooddelivery_partner.MainActivity;
import com.akshay.fooddelivery_partner.Model.RestaurantInfo;
import com.akshay.fooddelivery_partner.Model.User;
import com.akshay.fooddelivery_partner.R;
import com.akshay.fooddelivery_partner.Util.Constants;
import com.akshay.fooddelivery_partner.Util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final String PROGRESS_SIGN_WITH_NUMBER = "Sending Otp";
    private static final String PROGRESS_VERIFY_OTP = "Verifying Otp";
    private static final String PROGRESS_VERIFY = "Verifying...";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog mAuthProgressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mRestaurantInfoReference;

    private LinearLayout layoutToSignInWithNumber;
    private LinearLayout layoutToVerifyOtp;
    private LinearLayout layoutToEnterInfo;
    private EditText etPhoneNumber, etEnteredOtp;
    private EditText etRestaurantName, etRestaurantOwnerName, etRestaurantContactNumber, etRestaurantAddress;

    private static Boolean calledAlready = false;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (!calledAlready) {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                calledAlready = true;
            }
        } catch (Exception e) {
            Log.wtf(TAG, e.toString());
        }
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        Log.wtf(TAG, "LoginActivity: onCreate() method Called");
        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        mAuth = FirebaseAuth.getInstance();
        mRestaurantInfoReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_RESTAURANT_INFO);
        mAuthProgressDialog = new ProgressDialog(this);

        layoutToSignInWithNumber = (LinearLayout) findViewById(R.id.layout_signIn);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);

        layoutToVerifyOtp = (LinearLayout) findViewById(R.id.layout_otp);
        etEnteredOtp = (EditText) findViewById(R.id.et_getOtp);

        layoutToEnterInfo = (LinearLayout)findViewById(R.id.layout_restaurant_info);
        etRestaurantName = (EditText) findViewById(R.id.et_restaurant_name);
        etRestaurantOwnerName = (EditText) findViewById(R.id.et_restaurant_owner_name);
        etRestaurantContactNumber = (EditText) findViewById(R.id.et_restaurant_contact_number);
        etRestaurantAddress = (EditText) findViewById(R.id.et_restaurant_address);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.wtf(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                mVerificationInProgress = false;

                if (phoneAuthCredential != null) {
                    if (phoneAuthCredential.getSmsCode() != null) {
                        showProgressDialog(PROGRESS_VERIFY_OTP);
                        setViewsGone(layoutToSignInWithNumber);
                        setViewsVisible(layoutToVerifyOtp);
                        etEnteredOtp.setText(phoneAuthCredential.getSmsCode());

                    } else {
                        mAuthProgressDialog.setMessage(PROGRESS_VERIFY);
                    }
                }
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.wtf(TAG, "onVerificationFailed", e);
                mAuthProgressDialog.dismiss();
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    etPhoneNumber.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    etPhoneNumber.setError("SMS quota has exausted");
                }else if(e instanceof FirebaseNetworkException){
                    etPhoneNumber.setError("Check Your Internet Connection");
                }

            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.wtf(TAG, "onCodeSent: " + verificationId + " \n" + forceResendingToken.toString());

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;

                Utils.hideKeyboard(LoginActivity.this);
                mAuthProgressDialog.dismiss();
                setViewsGone(layoutToSignInWithNumber);
                setViewsVisible(layoutToVerifyOtp);
                //etEnteredOtp.setFocusable(true);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Log.wtf(TAG, "LoginActivity: onStart() method Called");
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (mVerificationInProgress) {
                signInWithPhoneNumber(null);
            }

        } catch (Exception e) {
            Log.wtf(TAG, "Start: " + e.toString() + e.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    public void signInWithPhoneNumber(View view) {

        String phoneNumber = etPhoneNumber.getText().toString();
        if(!Utils.isPhoneNumberValid(phoneNumber)){
            etPhoneNumber.setError("Invalid PhoneNumber");
            return;
        }

        mPhoneNumber = Utils.formattedPhoneNumber(phoneNumber);
        showProgressDialog(PROGRESS_SIGN_WITH_NUMBER);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        mVerificationInProgress = true;
    }

    private void setViewsVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    private void setViewsGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Log.wtf(TAG, "signInWithCredential:success " + phoneAuthCredential.getSmsCode());

                            mRestaurantInfoReference.child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue() != null){
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }else{
                                        setViewsGone(layoutToSignInWithNumber, layoutToVerifyOtp);
                                        setViewsVisible(layoutToEnterInfo);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                etEnteredOtp.setError("Invalid code.");
                            }
                        }
                    }
                });
    }

    public void verifyOtp(View view) {
        showProgressDialog(PROGRESS_VERIFY_OTP);
        String otp = etEnteredOtp.getText().toString().trim();
        verifyPhoneNumberWithCode(mVerificationId, otp);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        Log.wtf(TAG, "VerifyPhoneNumberWithCode: " + verificationId + " \n" + "Code: " + code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);
    }

    private void showProgressDialog(String message){
        mAuthProgressDialog.setMessage(message);
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
    }

    public void resendOtp(View view) {
        resendVerificationCode(mPhoneNumber, mResendToken);
    }

    private void resendVerificationCode(String phoneNumber,
                                         PhoneAuthProvider.ForceResendingToken token) {

        mAuthProgressDialog.show();
        Log.wtf(TAG, "resendVerificationCode");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    public void submitInformation(View view) {

        saveInfoToFirebase();
    }

    private void saveInfoToFirebase() {
        final String restaurantName = etRestaurantName.getText().toString();
        final String ownerName = etRestaurantOwnerName.getText().toString();
        final String contactNumber = etRestaurantContactNumber.getText().toString();
        final String address = etRestaurantAddress.getText().toString();
        final String uid = mAuth.getUid();

        if (restaurantName.isEmpty()  || ownerName.isEmpty()){
            //etShopName.setError("Cannot be empty");
            return;
        }

        mRestaurantInfoReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {

                    RestaurantInfo restaurantInfo = new RestaurantInfo(uid, restaurantName, ownerName, contactNumber, address);
                    mRestaurantInfoReference.child(uid)
                            .setValue(restaurantInfo)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.wtf(TAG, "Successfully Added To Database" + restaurantName + ownerName + mPhoneNumber);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}