package cn.lovelywhite.interestmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

import cn.lovelywhite.interestmanager.Activity.InterestDetailsActivity;
import cn.lovelywhite.interestmanager.Activity.UserDetailsActivity;
import cn.lovelywhite.interestmanager.Data.StaticValues;
import cn.lovelywhite.interestmanager.Data.Utils;
import cn.lovelywhite.interestmanager.R;

public class UserListAdapter extends BaseAdapter  {
    ViewHolder holder = null;
    private LayoutInflater layoutInflater;
    Activity a;
    public UserListAdapter(Context context,Activity a)
    {
        layoutInflater  = LayoutInflater.from(context);
        this.a= a;
    }
    @Override
    public int getCount() {
        if(StaticValues.UsersData!=null)
            return StaticValues.UsersData.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
       return StaticValues.UsersData.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView manageUserImage,manageUserDetails;
        public TextView manageUserName;
        public TextView manageUserEmail;
        public TextView manageUserType;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView ==null)
        {
            convertView = layoutInflater.inflate(R.layout.user_list,null);
            holder = new ViewHolder();
            holder.manageUserImage = convertView.findViewById(R.id.manage_user_image);
            holder.manageUserName = convertView.findViewById(R.id.manage_user_name);
            holder.manageUserEmail = convertView.findViewById(R.id.manage_user_email);
            holder.manageUserType = convertView.findViewById(R.id.manage_user_type);
            holder.manageUserDetails =convertView.findViewById(R.id.manage_user_details);
                    convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();

        }
        if(StaticValues.UsersData.get(position).getUserHeadImage()!=null) holder.manageUserImage.setImageBitmap(Utils.stringToBitmap(StaticValues.UsersData.get(position).getUserHeadImage()));
        holder.manageUserName.setText(StaticValues.UsersData.get(position).getUserName());
        holder.manageUserEmail.setText(StaticValues.UsersData.get(position).getUserEmail());
        holder.manageUserType.setText(Utils.getType(StaticValues.UsersData.get(position).getUserType()));
        final View finalConvertView = convertView;
        holder.manageUserDetails.setOnClickListener(new View.OnClickListener()
                                                    {

                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent =new Intent(finalConvertView.getContext(),UserDetailsActivity.class);
                                                            StaticValues.UserPosition = position;
                                                            a.startActivityForResult(intent,1);
                                                        }
                                                    }
        );
        return convertView;
    }
}