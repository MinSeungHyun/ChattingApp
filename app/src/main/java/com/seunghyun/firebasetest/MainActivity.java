package com.seunghyun.firebasetest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList<Item> items;
    private int messageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        items = new ArrayList<>();
        setUpRecyclerView(items);



        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        button.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (text.length() > 0) {
                reference.child("message").child("asdf").push().setValue(text);
            }
            editText.setText("");
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                items.add(new Item(value.toString()));
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(items.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        editText = findViewById(R.id.edit_text);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void setUpRecyclerView(ArrayList<Item> items) {
        adapter = new CustomAdapter(items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
