package cn.lovelywhite.interestmanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import cn.lovelywhite.interestmanager.Activity.InterestDetailsActivity;
import cn.lovelywhite.interestmanager.Activity.MainActivity;
import cn.lovelywhite.interestmanager.Activity.ReserveTicketsActivity;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.Data.Utils;
import cn.lovelywhite.interestmanager.R;

public class HomeListAdapter extends BaseAdapter  {
    private LayoutInflater layoutInflater;
    private Fragment f;
    public HomeListAdapter(Context context, Fragment f)
    {
        layoutInflater  = LayoutInflater.from(context);
        this.f=f;
    }
    @Override
    public int getCount() {
        if(StaticValues.InterestsData!=null)
            return StaticValues.InterestsData.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
       return StaticValues.InterestsData.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder
    {
        public ImageView interestImage,interestDetails;
        public TextView interestTitle,interestDescription,reserve;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView ==null)
        {
            convertView = layoutInflater.inflate(R.layout.interest_list,null);
            holder = new ViewHolder();
            holder.interestImage = convertView.findViewById(R.id.interest_image);
            holder.interestDetails = convertView.findViewById(R.id.interest_details);
            holder.interestTitle = convertView.findViewById(R.id.interest_title);
            holder.interestDescription = convertView.findViewById(R.id.interest_description);
            holder.reserve = convertView.findViewById(R.id.reserve);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();

        }
        if (StaticValues.InterestsData.get(position).getInterestPic()!=null&&!StaticValues.InterestsData.get(position).getInterestPic().isEmpty())
            holder.interestImage.setImageBitmap(Utils.stringToBitmap(StaticValues.InterestsData.get(position).getInterestPic()));
        holder.interestTitle.setText("景点名称："+StaticValues.InterestsData.get(position).getInterestName());
        holder.interestDescription.setText("景点描述："+StaticValues.InterestsData.get(position).getInterestDescription());
        final View finalConvertView = convertView;
        holder.interestDetails.setOnClickListener(new View.OnClickListener()
                                                  {

                                                      @Override
                                                      public void onClick(View v) {
                                                          Intent intent =new Intent(finalConvertView.getContext(),InterestDetailsActivity.class);
                                                          StaticValues.InterestPosition = position;
                                                          Objects.requireNonNull(f.getActivity()).startActivity(intent);
                                                      }
                                                  }
        );
        holder.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticValues.InterestPosition = position;
                Intent intent = new Intent(finalConvertView.getContext(),ReserveTicketsActivity.class);
                Objects.requireNonNull(f.getActivity()).startActivity(intent);
                Toast.makeText(finalConvertView.getContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

}