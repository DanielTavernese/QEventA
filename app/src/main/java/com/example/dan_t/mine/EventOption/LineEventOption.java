package com.example.dan_t.mine.EventOption;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dan_t.mine.Event;
import com.example.dan_t.mine.EventOptionActivity;
import com.example.dan_t.mine.EventType;
import com.example.dan_t.mine.MainActivity;
import com.example.dan_t.mine.R;
import com.example.dan_t.mine.RetrieveFeedTask;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class LineEventOption extends EventOption {

    private int counter;

    private int position = -1;

    private Set<String> names = new HashSet<>();
    public LineEventOption(Activity activity, Event event) {
        super(activity, EventOptionType.LINE, event, "Line Queue", "https://i.imgur.com/9XZw9cD.jpg");
    }

    @Override
    public void fire() {

        if(names.contains(MainActivity.getAccountName())) {
            Toast.makeText(getActivity(), "You are already in the line.", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "You have been added to the line!", Toast.LENGTH_SHORT).show();

                //URL url = new URL(urlString);
                //HttpURLConnection connection

                counter++;
                position = counter;
                EventOptionActivity.initRecyclerView(getActivity(), (RecyclerView) getActivity().findViewById(R.id.event_view));
                names.add(MainActivity.getAccountName());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public String getLabel(){
        return super.getLabel()+"\n"+"People in Line: "+ counter + "\n" + "Position in line: " + (position == -1 ? "NOT IN" : position);
    }
}
