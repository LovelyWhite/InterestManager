package cn.lovelywhite.interestmanager.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Types;

import cn.lovelywhite.interestmanager.Data.DataUtil;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.Data.User;
import cn.lovelywhite.interestmanager.Data.Utils;
import cn.lovelywhite.interestmanager.R;

public class UserDetailsActivity extends AppCompatActivity {
    ImageView usersHeadPic;
    TextView usersName;
    TextView usersEmail;
    TextView usersCreateTime;
    TextView usersType;
    ConstraintLayout changeUsersName;
    ConstraintLayout changeUsersPassword;
    ConstraintLayout changeUsersEmail;
    ConstraintLayout changeUsersType;
    EditText editText;
    ProgressBar progressBar;
    Handler handler;
    int select = 0;

    class MyListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1) {//确定之后
                new Thread() {
                    Message m = new Message();

                    @Override
                    public void run() {
                        super.run();
                        if (select == 1) {
                            if (editText.getText().toString().equals("")) {
                                m.what = 1;
                            } else {
                                StaticValues.UsersData.get(StaticValues.UserPosition).setUserName(editText.getText().toString());
                                if (DataUtil.changeUsersName(StaticValues.UsersData.get(StaticValues.UserPosition).getUserName()))
                                    m.what = 2;
                                else
                                    m.what = 3;
                            }
                        } else if (select == 2) {
                            if (editText.getText().toString().equals("")) {
                                m.what = 1;
                            } else {
                                StaticValues.UsersData.get(StaticValues.UserPosition).setUserPassword(editText.getText().toString());
                                if (DataUtil.changeUsersPassword(StaticValues.UsersData.get(StaticValues.UserPosition).getUserPassword()))
                                    m.what = 2;
                                else
                                    m.what = 3;
                            }
                        } else if (select == 3) {
                            if (editText.getText().toString().equals("")) {
                                m.what = 1;
                            } else {

                                String x = StaticValues.UsersData.get(StaticValues.UserPosition).getUserEmail();
                                StaticValues.UsersData.get(StaticValues.UserPosition).setUserEmail(editText.getText().toString());
                                if (DataUtil.changeUsersEmail(StaticValues.UsersData.get(StaticValues.UserPosition).getUserEmail(), x))
                                    m.what = 2;
                                else
                                    m.what = 4;
                            }
                        }
                        handler.sendMessage(m);
                    }
                }.start();
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final String backUpEmail =  StaticValues.UsersData.get(StaticValues.UserPosition).getUserEmail();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        usersHeadPic = findViewById(R.id.change_users_head_pic);
        usersName = findViewById(R.id.change_users_name);
        usersEmail = findViewById(R.id.change_users_email);
        usersCreateTime = findViewById(R.id.change_users_create_time);
        usersType = findViewById(R.id.change_users_type);
        changeUsersName = findViewById(R.id.change_users_name_t);
        changeUsersPassword = findViewById(R.id.change_users_password_t);
        changeUsersEmail = findViewById(R.id.change_users_email_t);
        changeUsersType = findViewById(R.id.change_users_type_t);
        progressBar = findViewById(R.id.change_s_progress_bar);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(getApplicationContext(), "未输入任何值", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        if (select == 1)
                        {
                            usersName.setText("用户名：" + StaticValues.UsersData.get(StaticValues.UserPosition).getUserName());
                            setResult(1);
                        }
                        else if (select == 3)
                        {
                            usersEmail.setText("用户邮箱" + StaticValues.UsersData.get(StaticValues.UserPosition).getUserEmail());
                            setResult(2);
                        }
                        else if (select == 4)
                        {
                            usersType.setText("用户类型：" + Utils.getType(StaticValues.UsersData.get(StaticValues.UserPosition).getUserType()));
                            setResult(3);
                        }
                        if(backUpEmail.equals(StaticValues.user.getUserEmail()))
                        {
                            StaticValues.user = StaticValues.UsersData.get(StaticValues.UserPosition);
                        }
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Email已注册", Toast.LENGTH_SHORT).show();break;
                }
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        if (StaticValues.UsersData.get(StaticValues.UserPosition).getUserName() != null)
            usersName.setText("用户名：" + StaticValues.UsersData.get(StaticValues.UserPosition).getUserName());
        usersEmail.setText("用户邮箱：" + StaticValues.UsersData.get(StaticValues.UserPosition).getUserEmail());
        usersCreateTime.setText("创建时间：" + StaticValues.UsersData.get(StaticValues.UserPosition).getUserCreateTime().toString());
        if (StaticValues.UsersData.get(StaticValues.UserPosition).getUserHeadImage() != null)
            usersHeadPic.setImageBitmap(Utils.stringToBitmap(StaticValues.UsersData.get(StaticValues.UserPosition).getUserHeadImage()));
        usersType.setText("用户类型：" + Utils.getType(StaticValues.UsersData.get(StaticValues.UserPosition).getUserType()));
        usersHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        changeUsersName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                select = 1;
                editText = new EditText(UserDetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailsActivity.this);
                builder.setTitle("请输入姓名：")
                        .setView(editText)
                        .setPositiveButton("确认", new MyListener())
                        .setNegativeButton("取消", new MyListener())
                        .show();
            }
        });
        changeUsersPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                select = 2;
                editText = new EditText(UserDetailsActivity.this);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailsActivity.this);
                builder.setTitle("请输入密码：")
                        .setView(editText)
                        .setPositiveButton("确认", new MyListener())
                        .setNegativeButton("取消", new MyListener())
                        .show();
            }
        });
        changeUsersEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                select = 3;
                editText = new EditText(UserDetailsActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailsActivity.this);
                builder.setTitle("请输入Email：")
                        .setView(editText)
                        .setPositiveButton("确认", new MyListener())
                        .setNegativeButton("取消", new MyListener())
                        .show();
            }
        });
        changeUsersType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                select = 4;
                editText = new EditText(UserDetailsActivity.this);
                final String[] x = {"普通管理员", "超级管理员"};
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailsActivity.this);
                builder.setTitle("请选择用户类别：")
                        .setSingleChoiceItems(x, StaticValues.UsersData.get(StaticValues.UserPosition).getUserType(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                              new Thread()
                              {
                                  @Override
                                  public void run() {
                                      super.run();
                                      Message m = new Message();
                                      if (which == 0)
                                          if (DataUtil.changeUsersType(0)) {
                                              StaticValues.UsersData.get(StaticValues.UserPosition).setUserType(0);
                                              m.what = 2;
                                          } else
                                              m.what = 3;
                                      else {
                                          if (DataUtil.changeUsersType(1)) {
                                              StaticValues.UsersData.get(StaticValues.UserPosition).setUserType(1);
                                              m.what = 2;
                                          } else
                                              m.what = 3;
                                      }
                                      handler.sendMessage(m);
                                  }
                              }.start();
                              dialog.dismiss();
                            }
                        }).show();
            }
        });
    }
}
