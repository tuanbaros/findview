package com.simple.findview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindView.bind(this);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleAdapter(list));
        mRecyclerView.setHasFixedSize(true);
    }
}
