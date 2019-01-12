
package cn.lovelywhite.interestmanager.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import cn.lovelywhite.interestmanager.Data.DataUtil;
import cn.lovelywhite.interestmanager.Data.ExcelUtils;
import cn.lovelywhite.interestmanager.Data.PriceSum;
import cn.lovelywhite.interestmanager.R;

public class RevenueStatistics extends AppCompatActivity {


    Vector<PriceSum> priceSum;
    private BarChart chart;
    private YAxis leftAxis;
    private XAxis xAxis;
    private Legend legend;
    private ConstraintLayout ticketOutput;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:Toast.makeText(getApplicationContext(),"已保存在根目录下的excel文件夹下",Toast.LENGTH_LONG).show();break;
                case -1:Toast.makeText(getApplicationContext(),"保存失败，请检查权限",Toast.LENGTH_LONG).show();break;
            }
            return false;
        }
    });
    private void initBarChart(BarChart barChart) {
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawBorders(true);
        barChart.animateY(1000, Easing.Linear);
        barChart.animateX(1000, Easing.Linear);
        barChart.setNoDataText("正在加载数据");
        barChart.setNoDataTextColor(Color.GRAY);
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return priceSum.get((int)value).getInterestName();
            }
        });
        Description d= new Description();
        d.setText("");
        barChart.setDescription(d);
        leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(11f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }
    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        barDataSet.setDrawValues(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_information);
        setTitle("营收数据");
        chart =  findViewById(R.id.tickets_chart);
        ticketOutput = findViewById(R.id.tickets_output);
        ticketOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
               Message m = new Message();
                if( ExcelUtils.writeExcel(Environment.getExternalStorageDirectory().getAbsolutePath() +"/excel/营收表-"+ df.format(new Date()) + ".xls", priceSum))
               {
                   m.what = 0;
               }
               else
                {
                    m.what = -1;
                }
                handler.sendMessage(m);
            }
        });
        initBarChart(chart);
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                priceSum = DataUtil.getPriceSum();
                List<BarEntry> entries = new ArrayList<>();
                for(int i = 0; i< (priceSum != null ? priceSum.size() : 0); i++)
                {
                    entries.add(new BarEntry(i,priceSum.get(i).getInterestPriceSum().floatValue()));
                }
                BarDataSet barDataSet = new BarDataSet(entries,"各景区营收柱状图");
                initBarDataSet(barDataSet,Color.GRAY);
                BarData barData = new BarData(barDataSet);
                barData.setBarWidth(0.3f);
                chart.setData(barData);
                chart.invalidate();
            }
        }.start();
    }
}

