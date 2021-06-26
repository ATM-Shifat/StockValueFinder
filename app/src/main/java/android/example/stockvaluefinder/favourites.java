package android.example.stockvaluefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class favourites extends AppCompatActivity {
    String ticker;
    double changePercent,price1;
    String name1;
    FirebaseAuth firebaseAuth;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        fun2();
    }
    public void fun2()
    {
        firebaseAuth= FirebaseAuth.getInstance();
        id= firebaseAuth.getCurrentUser().getUid();
        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference().child("FAV").child(id);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    name1=dataSnapshot.child("company_name").getValue(String.class);
                    ticker=dataSnapshot.child("ticker").getValue(String.class);
                    changePercent=dataSnapshot.child("changePercent").getValue(Double.class);
                    price1=dataSnapshot.child("price").getValue(Double.class);
                    initData();
                    initRecyclerView();
                }
            }

            @Override
            public void onCancelled( @NotNull DatabaseError error) {

            }
        });

    }

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<Modelclassfor_recycle> userlist1=new ArrayList<>();
    Adapter adapter;

    private void initData() {
        userlist1.add(new Modelclassfor_recycle(price1,name1,changePercent,ticker));

    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recyclerView1);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new Adapter(userlist1,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}