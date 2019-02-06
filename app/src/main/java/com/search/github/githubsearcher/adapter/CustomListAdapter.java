package com.search.github.githubsearcher.adapter;

import com.search.github.githubsearcher.app.AppController;
import com.search.github.githubsearcher.R;
import com.search.github.githubsearcher.model.GitHub;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<GitHub> gitHubs;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<GitHub> gitHubs) {
        this.activity = activity;
        this.gitHubs = gitHubs;
    }

    @Override
    public int getCount() {
        return gitHubs.size();
    }

    @Override
    public Object getItem(int location) {
        return gitHubs.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        GitHub m = gitHubs.get(position);
        thumbNail.setImageUrl(m.getAvatar(), imageLoader);
        title.setText(m.getName());
        rating.setText("Star Gazer \u2605: " + String.valueOf(m.getNumberofstars()));
        desc.setText(m.getDescription());
        return convertView;
    }
}
