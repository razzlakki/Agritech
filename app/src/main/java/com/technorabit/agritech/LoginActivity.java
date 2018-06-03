package com.technorabit.agritech;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dms.datalayerapi.network.Http;
import com.dms.datalayerapi.util.ParseUtils;
import com.technorabit.agritech.connection.RestClient;
import com.technorabit.agritech.constant.APIConstant;
import com.technorabit.agritech.db.SharedUtil;
import com.technorabit.agritech.fragment.dailog.BaseLoadingFragment;
import com.technorabit.agritech.model.LoginReq;
import com.technorabit.agritech.model.LoginRes;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {


    /**
     * A dummy authentication store containing known user names and passwords.
     */
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
            doLogin(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */


    private void doLogin(String email, String password) {
        LoginReq loginreq = new LoginReq();
        loginreq.uPwd = password;
        loginreq.uName = email;
        final BaseLoadingFragment baseLoadingFragment = BaseLoadingFragment.newInstance(getString(R.string.loading), getString(R.string.please_wait), true);
        baseLoadingFragment.show(getSupportFragmentManager(), BaseLoadingFragment.FRAGMENT_TAG);
        RestClient.get(this).new NetworkTask<Void, LoginRes>(LoginRes.class, Http.POST) {
            @Override
            protected void onPostExecute(LoginRes loginData) {
                super.onPostExecute(loginData);
                baseLoadingFragment.dismiss();
                if (loginData != null) {
                    if (loginData.Status.equalsIgnoreCase(APIConstant.SUCCESS_STATUS) && loginData.Content != null && loginData.Content.size() > 0) {
                        SharedUtil.get(LoginActivity.this).addToSet("userId", loginData.Content.get(0).userId).addToSet(SharedUtil.KEY_IS_LOGIN, true).commitSet();
                        startActivity(LoginActivity.this, DashboardActivity.class, true);
                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), loginData.Mssg, Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "invalid inputs", Snackbar.LENGTH_LONG).show();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, APIConstant.LOGIN, ParseUtils.getJsonString(loginreq));

    }


//    /**
//         * Represents an asynchronous login/registration task used to authenticate
//         * the user.
//         */
//        public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//            private final String mEmail;
//            private final String mPassword;
//
//            UserLoginTask(String email, String password) {
//                mEmail = email;
//                mPassword = password;
//            }
//
//            @Override
//            protected Boolean doInBackground(Void... params) {
//
//                LoginReq loginReq = new LoginReq();
//                loginReq.uName = mEmail;
//                loginReq.uPwd = mPassword;
//
//                ParseUtils.getParseObj(RestClient.get(LoginActivity.this)
//                        .diskCacheEnable(true).doPost(APIConstant.LOGIN, ParseUtils.getGson().toJson()), AttributeValuesComp.class);
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(final Boolean success) {
//                mAuthTask = null;
//                showProgress(false);
//                if (success) {
//                    startActivity(LoginActivity.this, DashboardActivity.class, true);
//                } else {
//                    mPasswordView.setError(getString(R.string.error_incorrect_password));
//                    mPasswordView.requestFocus();
//                }
//            }
//
//            @Override
//            protected void onCancelled() {
//                mAuthTask = null;
//                showProgress(false);
//            }
//    }
}

