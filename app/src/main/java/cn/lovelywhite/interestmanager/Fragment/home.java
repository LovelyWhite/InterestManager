package cn.lovelywhite.interestmanager.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.Objects;

import cn.lovelywhite.interestmanager.Activity.AddInterestActivity;
import cn.lovelywhite.interestmanager.Data.DataUtil;
import cn.lovelywhite.interestmanager.Adapter.HomeListAdapter;
import cn.lovelywhite.interestmanager.Data.Interest;
import cn.lovelywhite.interestmanager.Data.Location;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.R;

import static cn.lovelywhite.interestmanager.R.menu.bar;

public class Home extends Fragment {
    private MapView mMapView = null;
    View view ;
    BaiduMap baiduMap = null;
    Location location;
    private ListView homeList;
    HomeListAdapter homeListAdapter;
    static Handler handler;
    Menu m;
    public class LocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            LatLng nowLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.place);
            OverlayOptions option = new MarkerOptions()
                    .position(nowLatLng)
                    .icon(bitmap);
            baiduMap.addOverlay(option);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(nowLatLng).zoom(18).build()));
            Toast.makeText(view.getContext(), "定位成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        mMapView = view.findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        homeList = view.findViewById(R.id.home_list_view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==0)
        {
            Manage f = (Manage)Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("manage");
            if (f != null) {
                f.setNum(StaticValues.InterestsData.size());
            }
            homeListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeListAdapter =  new HomeListAdapter(view.getContext(),this);
        homeList.setAdapter(homeListAdapter);
        location= new Location(Objects.requireNonNull(getActivity()).getApplicationContext(),this);//传入自己，为了声明这里面的class
        location.start();
        baiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                StaticValues.interest =new Interest();
                StaticValues.interest.setInterestLongitude(latLng.longitude);
                StaticValues.interest.setInterestDimension(latLng.latitude);
                Intent intent = new Intent(view.getContext(),AddInterestActivity.class);
                startActivityForResult(intent,1);
            }

        });
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                baiduMap.clear();
                    LatLng point = new LatLng(StaticValues.InterestsData.get(position).getInterestDimension(),StaticValues.InterestsData.get(position).getInterestLongitude());
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.place);
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
                    baiduMap.addOverlay(option);
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(point).zoom(18).build()));
                }
        });
        homeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("删除")
                        .setMessage("确定删除?\n删除此景点它的所有门票数据将会被清空！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==-1)
                                {
                                    new Thread()
                                    {
                                        @Override
                                        public void run() {
                                            super.run();
                                            final Message m  = new Message();
                                            if(StaticValues.OK==DataUtil.removeInterest(StaticValues.InterestsData.get(position).getInterestId()))
                                            {      StaticValues.InterestsData.remove(position);
                                                m.what = StaticValues.DELETEOK;
                                            }
                                            else
                                            {
                                                m.what = StaticValues.DELETEFAILED;
                                            }
                                            handler.sendMessage(m);
                                        }
                                    }.start();
                                }
                            }
                        }).show();
                return true ;
            }
        });
        handler =  new Handler(
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Manage f = (Manage) getActivity().getSupportFragmentManager().findFragmentByTag("manage");
                        switch (msg.what) {

                            case StaticValues.OK:
                                if (f != null) {
                                    f.setNum(StaticValues.InterestsData.size());
                                }
                                Toast.makeText(view.getContext(), "列表更新成功", Toast.LENGTH_SHORT).show();
                                homeListAdapter.notifyDataSetChanged();
                                break;
                            case StaticValues.DELETEOK:
                                Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                homeListAdapter.notifyDataSetChanged();
                                if (f != null) {
                                    f.setNum(StaticValues.InterestsData.size());
                                }
                                break;
                            case StaticValues.DELETEFAILED:
                                Toast.makeText(view.getContext(), "删除失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                }
        );
        //这个地方得异步加载
        new Thread()
        {
            @Override
            public void run() {
            Message m = new Message();
            super.run();
            int count = 0;
            int countNow = 0;
            if(null != StaticValues.InterestsData)
                count = StaticValues.InterestsData.size();
            StaticValues.InterestsData = DataUtil.getInterests();//获取信息
            if(null != StaticValues.InterestsData)
                countNow = StaticValues.InterestsData.size();
            if(StaticValues.InterestsData==null)
            {
                m.what = StaticValues.LINKDBFAILED;
            }
            else if(count!=countNow)
            {
                m.what=StaticValues.OK;
            }
            handler.sendMessage(m);
        }
        }.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.bar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId())
      {
          case R.id.refresh:
              new Thread()
              {
                  @Override
                  public void run() {
                      Message m = new Message();
                      super.run();
                      int count = 0;
                      int countNow = 0;
                      if(null != StaticValues.InterestsData)
                          count = StaticValues.InterestsData.size();
                      StaticValues.InterestsData = DataUtil.getInterests();
                      if(null != StaticValues.InterestsData)
                          countNow = StaticValues.InterestsData.size();
                      if(StaticValues.InterestsData==null)
                      {
                          m.what = StaticValues.LINKDBFAILED;
                      }
                      else if(count!=countNow)
                      {
                          m.what=StaticValues.OK;
                      }
                      handler.sendMessage(m);
                  }
              }.start();
              break;
      }
        return super.onOptionsItemSelected(item);
    }
}
