package android.example.stockvaluefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class buying_page extends AppCompatActivity {
    double d,money;
    FirebaseAuth firebaseAuth;
    String name;
    TextView textView;
    TextView textView1;
    TextView textView3;
    String id;
    String ticker;
    DatabaseReference reference;
    boolean b=true;
    List<String>companyname;
    List<Double>mo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_page);
        id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=getIntent();
        name=intent.getStringExtra("cname");
        ticker=intent.getStringExtra("ticker");
        d=intent.getDoubleExtra("price",00.00);
        d=Double.parseDouble(new DecimalFormat("##.####").format(d));
        textView1=findViewById(R.id.shareprice);
        textView=findViewById(R.id.comname);
        textView.setText("("+ticker+")"+name);
        textView1.setText("$"+d);
        companyname=new ArrayList<>();
        mo= new ArrayList<>();
        companyname.clear();
       preset();
    }
    public void preset()
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                double av=snapshot.child("points").getValue(double.class);
                av=Double.parseDouble(new DecimalFormat("##.####").format(av));
                textView3=findViewById(R.id.availpoint);
                textView3.setText(""+av);
                mo.add(av);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("Stocks").child(id);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                if(snapshot.hasChild(name))
                {
                    String te=snapshot.child(name).child("company").getValue(String.class);
                    companyname.add(te);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_for_selling,menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (item.getItemId() == R.id.signout1) {
            firebaseAuth.signOut();
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.profile1) {
            Intent intent = new Intent(this, user_profile.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void buying(View v)
    {
        EditText e=findViewById(R.id.quantityedittext);
        String s1=e.getText().toString();
        int i=Integer.parseInt(s1);
        if(mo.get(0)<(double)i*d)
        {
            AlertDialog.Builder alertDialog= new AlertDialog.Builder(buying_page.this);
            alertDialog.setTitle("LOW BALANCE");
            alertDialog.setMessage("You don't have enough Money. Please recharge!");
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            alertDialog.show();
        }
        else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(buying_page.this);
            alertDialog.setTitle("CONFIRM TRANSACTION");
            alertDialog.setMessage("Are sure to buy this? This will reduce " + (double) i * d + " dollar from you account");
            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    double price = (double) i * d * (double) -1;
                    DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("points");
                    r.setValue(ServerValue.increment(price));
                    DatabaseReference r2 = FirebaseDatabase.getInstance().getReference().child("Stocks").child(id);
                    if(companyname.size()>0)
                    {
                        r2.child(name).child("count").setValue(ServerValue.increment(i));
                        r2.child(name).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                double tempbprice=snapshot.child("buy_price").getValue(double.class);
                                d=(d+tempbprice)/(double)2;
                                r2.child(name).child("buy_price").setValue(d);
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                Toast.makeText(buying_page.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        stocks s=new stocks(name,d,i,ticker);
                        r2.child(name).setValue(s);
                        companyname.add(name);
                    }
                    Toast.makeText(buying_page.this,"BUY Successful",Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog a = alertDialog.create();
            a.show();
        }

    }


}