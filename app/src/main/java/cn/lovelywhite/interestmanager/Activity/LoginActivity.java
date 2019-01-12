package cn.lovelywhite.interestmanager.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;


import java.util.LinkedList;
import java.util.List;

import cn.lovelywhite.interestmanager.Data.DataUtil;
import cn.lovelywhite.interestmanager.Data.LocalDataUtil;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.Data.User;
import cn.lovelywhite.interestmanager.R;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    private UserSignTask mSignTask = null;

    //UI 控件
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    SQLiteDatabase db;//本地SQLite
    Cursor c;
    List<String> emails;

    //启动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        LocalDataUtil localDataUtil = new LocalDataUtil(this,"emails.db",null,2);
        db = localDataUtil.getWritableDatabase();
        c = db.query("email",null,null,null,null,null,null);
        boolean success = c.moveToFirst();
        emails = new LinkedList<>();
        if(success)
        {
            do{
                emails.add( c.getString(0));
            }while (c.moveToNext());

        }
        mEmailView =findViewById(R.id.email);
        addEmailsToAutoComplete(emails);
        mPasswordView = findViewById(R.id.password);
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

        // 设置按键和监听
        Button mEmailLoginButton = (Button) findViewById(R.id.email_login);
        Button mEmailSignButton = (Button) findViewById(R.id.email_sign);
        mEmailLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mEmailSignButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSign();
            }
        });


        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(password))
        {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if ( !isPasswordValid(password)) {
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
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });
    }


    private void attemptSign() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if(!isPasswordValid(password))
        {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

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
            showProgress(true);
            mSignTask = new UserSignTask(email, password);
            mSignTask.execute((Void) null);
        }
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mEmail;
        private final String mPassword;


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            StaticValues.user = DataUtil.getUser(mEmail);//设置静态登陆数据
            if(StaticValues.user==null)
            {
                return  StaticValues.LINKDBFAILED;
            }
            else if (StaticValues.user.getUserEmail() == null)
            {
                return  StaticValues.USEREMAILEMPTY;
            }
            else if(StaticValues.user.getUserPassword().equals(mPassword))
            {
                return StaticValues.OK;
            }
            else
            {
                return  StaticValues.FAILED;
            }
        }

        @Override
        protected void onPostExecute(final Integer re) {
            mAuthTask = null;
            showProgress(false);
            boolean result = true;
            if (re == StaticValues.OK) {//成功
                for(String i:emails)
                {
                    if (i.equals(mEmail))
                    {
                        result = false;
                        break;
                    }
                }
                if (result ==true)
                {
                    db.execSQL("insert into email values('"+mEmail+"');");
                }
                c.close();
                db.close();
                finish();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity (intent);
            } else if(re == StaticValues.LINKDBFAILED) {//数据库连接失败
                StaticValues.user = null;//设置静态登陆数据
                mPasswordView.setError(getString(R.string.check_internet));
            }
            else if(re == StaticValues.FAILED){//失败
                StaticValues.user = null;//设置静态登陆数据
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
            else if(re == StaticValues.USEREMAILEMPTY){
                StaticValues.user = null;//设置静态登陆数据
                mEmailView.setError(getString(R.string.error_not_exist_email));
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class UserSignTask extends AsyncTask<Void, Void, Integer> {

        private final String mEmail;
        private final String mPassword;

        UserSignTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            StaticValues.user = new User();
            return DataUtil.sign(mEmail,mPassword);
        }

        @Override
        protected void onPostExecute(final Integer re) {
            mSignTask = null;
            showProgress(false);
            if (re == StaticValues.ORDINARY||re ==StaticValues.ADMIN) {//成功
                if (re == StaticValues.ORDINARY)
                    StaticValues.user.setUserType(StaticValues.ORDINARY);
                else
                    StaticValues.user.setUserType(StaticValues.ADMIN);
                db.execSQL("insert into email values('"+mEmail+"');");
                c.close();
                db.close();
                finish();
                StaticValues.user.setUserEmail(mEmail);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            } else if(re == StaticValues.FAILED) {
                mEmailView.setError(getString(R.string.user_email_exist));
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mSignTask = null;
            showProgress(false);
        }
    }

}

