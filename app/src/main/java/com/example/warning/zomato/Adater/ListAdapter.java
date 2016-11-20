package com.example.warning.zomato.Adater;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.warning.zomato.Controller.Controller;
import com.example.warning.zomato.Model.GetData;
import com.example.warning.zomato.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    ArrayList<GetData>arrayList;

    final Handler handler = new Handler();

    ImageLoader imageLoader;
    public ListAdapter(Activity activity,ArrayList<GetData>arrayList)
    {
        this.activity=activity;
        this.arrayList=arrayList;
    }

    public int getCount()
    {
        return arrayList.size();
    }

    public Object getItem(int location)
    {
        return arrayList.get(location);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup viewGroup)
    {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.list, null);
        }
        if (imageLoader == null)
            imageLoader = Controller.getInstance().getImageLoader();
        NetworkImageView imageView=(NetworkImageView)view.findViewById(R.id.thumbnail);
        TextView title=(TextView)view.findViewById(R.id.title);
        TextView rating=(TextView)view.findViewById(R.id.rating);
        TextView cuisines=(TextView)view.findViewById(R.id.cuisines);

        GetData getData=arrayList.get(position);
        String image=getData.getImage();
       if (image!=null)
           imageView.setImageUrl(image,imageLoader);
        else
       imageView.setImageUrl("https://b.zmtcdn.com/data/pictures/8/18258748/24f429922030d78481e23bcbc061139e_featured_v2.jpg",imageLoader);
        title.setText(getData.getName());
        rating.setText(String.valueOf(getData.getRating()));
        cuisines.setText(getData.getCuisines());
        return view;

    }


}
