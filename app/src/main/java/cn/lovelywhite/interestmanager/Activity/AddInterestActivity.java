package cn.lovelywhite.interestmanager.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import cn.lovelywhite.interestmanager.Data.DataUtil;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.R;

public class AddInterestActivity extends AppCompatActivity {
    EditText interestName;
    EditText interestDescription;
    Button interestOpenTime;
    Button interestCloseTime;
    EditText ticketPrice;
    EditText interestRemainingTicket;
    ProgressBar progressBar;
    Button interestButton;
    Handler handler;
    TimePicker timePicker;
    int x =0;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加景点");
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what) {
                    case StaticValues.OK:
                        Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                        StaticValues.InterestsData.add(StaticValues.interest);
                        StaticValues.interest=null;
                        setResult(0);
                        finish();
                        break;
                    case 3:progressBar.setVisibility(View.VISIBLE);break;
                    default:Toast.makeText(getApplicationContext(), "提交失败,请检查数据是否填写完整", Toast.LENGTH_SHORT).show();

                }
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        setContentView(R.layout.activity_add_interest);
        interestName = findViewById(R.id.add_interest_name);
        interestDescription = findViewById(R.id.add_interest_description);
        interestOpenTime = findViewById(R.id.add_interest_open_time);
        interestCloseTime = findViewById(R.id.add_interest_close_time);
        ticketPrice = findViewById(R.id.add_ticket_price);
        interestRemainingTicket = findViewById(R.id.add_interest_remain_ticket);
        progressBar = findViewById(R.id.add_interest_progress);
        interestButton = findViewById(R.id.add_interest_commit);
        class MyListener implements DialogInterface.OnClickListener
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DateFormat sdf = new SimpleDateFormat("hh:mm");
                Date date = null;
                try {
                     date = sdf.parse(timePicker.getHour() + ":" + timePicker.getMinute());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(which==-1)
                {
                    if(x==1)
                    {
                        interestOpenTime.setText(timePicker.getHour() + ":" + timePicker.getMinute());
                        StaticValues.interest.setOpenTime( new java.sql.Time(date.getTime()));
                    }
                    if (x==2)
                    {
                        interestCloseTime.setText(timePicker.getHour() + ":" + timePicker.getMinute());
                        StaticValues.interest.setCloseTime( new java.sql.Time(date.getTime()));
                    }
                }
            }
        }

        interestOpenTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        x= 1;
                        timePicker = new TimePicker(interestButton.getContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(interestButton.getContext());
                        builder.setTitle("请选择营业开始时间：")
                                .setView(timePicker)
                                .setPositiveButton("确认",new MyListener())
                                .setNegativeButton("取消",new MyListener())
                                .show();
                    }
                }

        );
        interestCloseTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        x= 2;
                        timePicker = new TimePicker(interestButton.getContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(interestButton.getContext());
                        builder.setTitle("请选择营业结束时间：")
                                .setView(timePicker)
                                .setPositiveButton("确认",new MyListener())
                                .setNegativeButton("取消",new MyListener())
                                .show();
                    }
                });
        interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              new Thread()
              {
                  @Override
                  public void run() {
                      super.run();
                      Message a = new Message();
                      a.what = 3;
                      handler.sendMessage(a);
                      Message m = new Message();

                      if(interestName.getText().toString().equals(""))
                          m.what = -1;
                      else if (interestOpenTime.getText().toString().equals(""))
                          m.what = -1;
                      else if (interestCloseTime.getText().toString().equals(""))
                          m.what = -1;
                      else if(ticketPrice.getText().toString().equals(""))
                          m.what = -1;
                      else if(interestRemainingTicket.getText().toString().equals(""))
                          m.what = -1;
                      else if(interestDescription.getText().toString().equals(""))
                          m.what = -1;
                      else if(StaticValues.interest.getCloseTime()==null||StaticValues.interest.getOpenTime()==null)
                          m.what = -1;
                      else
                          {

                              StaticValues.interest.setInterestId(UUID.randomUUID().toString());
                              StaticValues.interest.setInterestName(interestName.getText().toString());
                              StaticValues.interest.setInterestDescription(interestDescription.getText().toString());
                              StaticValues.interest.setTicketPrice(Float.parseFloat(ticketPrice.getText().toString()));
                              StaticValues.interest.setTicketRemaining(Integer.valueOf(interestRemainingTicket.getText().toString()));
                              m.what = DataUtil.addInterest(StaticValues.interest);
                          }
                      handler.sendMessage(m);

                  }
              }.start();
            }
        });
    }
}
