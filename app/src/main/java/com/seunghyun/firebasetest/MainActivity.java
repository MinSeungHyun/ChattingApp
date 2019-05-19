package com.seunghyun.firebasetest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView sendButton;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList<Item> items;

    private String id;
    private int chatCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        items = new ArrayList<>();
        setUpRecyclerView(items);


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("chat").child("id-count").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                    chatCount = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendButton.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (text.length() > 100) {
                Toast.makeText(MainActivity.this, getString(R.string.excess_text), Toast.LENGTH_LONG).show();
            } else if (text.length() > 0) {
                reference.child("chat").child("chatting").child(id + "-" + chatCount).setValue(text);
                reference.child("chat").child("id-count").child(id).setValue(chatCount + 1);
                editText.setText("");
            }
        });

        reference.child("chat").child("chatting").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String[] splitKey = Objects.requireNonNull(key).split("-");
                String name = splitKey[0];
                String value = dataSnapshot.getValue(String.class);
                items.add(new Item(name + " : ", value));
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(items.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, R.string.deleted_by_admin, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        editText = findViewById(R.id.edit_text);
        sendButton = findViewById(R.id.send_button);
        recyclerView = findViewById(R.id.recycler_view);
        id = getIntent().getStringExtra("id");
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
