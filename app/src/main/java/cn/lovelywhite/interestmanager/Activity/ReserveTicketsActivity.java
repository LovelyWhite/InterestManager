package cn.lovelywhite.interestmanager.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.lovelywhite.interestmanager.Data.DataUtil;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.R;

public class ReserveTicketsActivity extends AppCompatActivity {

    EditText userEmail;
    EditText reserveNum;
    TextView remainNum;
    Button submit;
    Handler handler;
    int remain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_tickets);
        setTitle("订票");
        userEmail = findViewById(R.id.reserve_email);
        reserveNum = findViewById(R.id.reserve_num);
        submit = findViewById(R.id.reserve_submit);
        remainNum = findViewById(R.id.remain_amount);
        remain =StaticValues.InterestsData.get(StaticValues.InterestPosition).getTicketRemaining();
        remainNum.setText("剩余票量："+remain);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
               switch (msg.what)
               {
                   case 0:

                       StaticValues.InterestsData.get(StaticValues.InterestPosition).setTicketRemaining(remain-Integer.parseInt(reserveNum.getText().toString()));
                       remain =StaticValues.InterestsData.get(StaticValues.InterestPosition).getTicketRemaining();
                       remainNum.setText("剩余票量："+remain);
                       Toast.makeText(getApplicationContext(),"购票成功",Toast.LENGTH_SHORT).show();
                   break;
                   case -1: Toast.makeText(getApplicationContext(),"无此用户",Toast.LENGTH_SHORT).show();break;
                   case -2:Toast.makeText(getApplicationContext(),"购票失败，请检查网络连接",Toast.LENGTH_SHORT).show();break;
                   case -3:Toast.makeText(getApplicationContext(),"剩余票数不足",Toast.LENGTH_SHORT).show();break;
               }
                return false;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Message m = new Message();
                if(remain>=Integer.parseInt(reserveNum.getText().toString()))
                {
                    new Thread()
                    {
                        @Override
                        public void run() {
                            super.run();

                            int result =  DataUtil.reserve(userEmail.getText().toString(), StaticValues.InterestsData.get(StaticValues.InterestPosition).getInterestId(), Integer.parseInt(reserveNum.getText().toString()));
                            if (result == StaticValues.OK) {
                                m.what = 0;
                            }
                            else if(result == StaticValues.NOTHISUSER)
                            {
                                m.what = -1;
                            }
                            else
                            {
                                m.what = -2;
                            }
                            handler.sendMessage(m);
                        }
                    }.start();
                }
                else
                {
                    m.what = -3;
                    handler.sendMessage(m);
                }
            }
        });
    }
}
