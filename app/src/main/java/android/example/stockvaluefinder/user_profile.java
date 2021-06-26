package android.example.stockvaluefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class user_profile extends AppCompatActivity {
    DatabaseReference reference;
    String id;
    FirebaseAuth firebaseAuth;
    String s1,s2;
    double d1;
    int ii1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        fun();
        fun2();
    }
    public void fun()
    {
        TextView name=findViewById(R.id.nameprofile);
        TextView textView=findViewById(R.id.textView6);
        firebaseAuth=FirebaseAuth.getInstance();
        id= firebaseAuth.getCurrentUser().getUid();
        reference=FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                name.setText(""+snapshot.child("name").getValue(String.class));
                double dd=snapshot.child("points").getValue(double.class);
                dd=Double.parseDouble(new DecimalFormat("##.####").format(dd));
                textView.setText(""+dd);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    public void fun2()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        id= firebaseAuth.getCurrentUser().getUid();
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("Stocks").child(id);
       reference1.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NotNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot: snapshot.getChildren())
               {
                   s1=dataSnapshot.child("company").getValue(String.class);
                   s2=dataSnapshot.child("ticker").getValue(String.class);
                   ii1=dataSnapshot.child("count").getValue(int.class);
                   d1=dataSnapshot.child("buy_price").getValue(Double.class);
                   initData1();
                   initRecyclerView1();
               }


           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });

    }
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<stocks> user_stocks=new ArrayList<>();
    Adapter_for_user_profile adapter1;
    private void initData1() {
        user_stocks.add(new stocks(s1,d1,ii1,s2));
    }

    private void initRecyclerView1() {
        recyclerView=findViewById(R.id.recyclerView1);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter1=new Adapter_for_user_profile(user_stocks,this);
        recyclerView.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }
}