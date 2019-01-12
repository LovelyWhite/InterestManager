package cn.lovelywhite.interestmanager.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.lovelywhite.interestmanager.Adapter.UserListAdapter;
import cn.lovelywhite.interestmanager.Data.DataUtil;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.Data.Utils;
import cn.lovelywhite.interestmanager.R;

public class UserManagerActivity extends AppCompatActivity {


    ListView manageUserList;
    UserListAdapter userListAdapter;
    Handler handler;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        setTitle("用户管理");
        manageUserList = findViewById(R.id.manage_user_list);
        progressBar = findViewById(R.id.manage_user_loading_progress);
        userListAdapter =new UserListAdapter(getApplicationContext(),this);
        manageUserList .setAdapter(userListAdapter);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what)
                {
                    case StaticValues.OK: Toast.makeText(getApplicationContext(),"获取成功",Toast.LENGTH_LONG).show();
                    userListAdapter.notifyDataSetChanged();
                    break;
                    case StaticValues.DELETEFAILED: Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_LONG).show();
                        break;
                    case StaticValues.DELETEOK: Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
                        userListAdapter.notifyDataSetChanged();
                        break;
                }
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        new Thread()
        {
            @Override
            public void run() {
                Message m = new Message();
                super.run();
                int count = 0;
                int countNow = 0;
                if(null != StaticValues.UsersData)
                    count = StaticValues.UsersData.size();
                StaticValues.UsersData = DataUtil.getUsers();//获取信息
                if(null != StaticValues.UsersData)
                    countNow = StaticValues.UsersData.size();
                if(count!=countNow)
                    m.what=StaticValues.OK;
                handler.sendMessage(m);
            }
        }.start();
        manageUserList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(manageUserList.getContext());
                builder.setTitle("删除")
                        .setMessage("确定删除?将清空此用户所有信息")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == -1) {
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            super.run();
                                            final Message m = new Message();
                                            if (StaticValues.OK == DataUtil.removeUser(StaticValues.UsersData.get(position).getUserEmail())) {
                                                StaticValues.UsersData.remove(position);
                                                m.what = StaticValues.DELETEOK;
                                            } else {
                                                m.what = StaticValues.DELETEFAILED;
                                            }
                                            handler.sendMessage(m);
                                        }
                                    }.start();
                                }
                            }
                        }).show();
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1)
        {
            if(resultCode == 1)
            {
                UserListAdapter.ViewHolder viewHolder = (UserListAdapter.ViewHolder)(manageUserList.getChildAt(StaticValues.UserPosition).getTag());
                viewHolder.manageUserName.setText(StaticValues.UsersData.get(StaticValues.UserPosition).getUserName());
            }
            else if(resultCode==2)
            {

                UserListAdapter.ViewHolder viewHolder = (UserListAdapter.ViewHolder)(manageUserList.getChildAt(StaticValues.UserPosition).getTag());
                viewHolder.manageUserEmail.setText(StaticValues.UsersData.get(StaticValues.UserPosition).getUserEmail());
            }
            else if(resultCode==3)
            {
                UserListAdapter.ViewHolder viewHolder = (UserListAdapter.ViewHolder)(manageUserList.getChildAt(StaticValues.UserPosition).getTag());
                viewHolder.manageUserType.setText(Utils.getType(StaticValues.UsersData.get(StaticValues.UserPosition).getUserType()));
            }
        }
    }
}
