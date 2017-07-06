package com.simple.findview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.ViewById;
import com.simple.findviewbyid.FindView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.text_view)
    TextView mTextView;

    @ViewById(R.id.button)
    Button mButton;

    @ViewById(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @com.example.Test
    @ViewById(R.id.text_number)
    TextView mTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindView.bind(this);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }

        Dialog dialog = new Dialog(this);
        View view = View.inflate(getBaseContext(), R.layout.item, null);
        dialog.setContentView(view);
        FindView.bind2(this, view);
        dialog.show();

//        mTextNumber = (TextView) view.findViewById(R.id.text_number);
        mTextNumber.setText("test");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleAdapter(list));
        mRecyclerView.setHasFixedSize(true);
    }
}
