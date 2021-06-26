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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Selling_Page extends AppCompatActivity {
    String tickersell;
    String comsell;
    int count;
    String id;
    FirebaseAuth mfirebaseAuth;
    double price11;
    String url="https://cloud.iexapis.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_page);
        Intent intent=getIntent();
        tickersell=intent.getStringExtra("ticker");
        comsell=intent.getStringExtra("cname");
        count=intent.getIntExtra("count",0);
        mfirebaseAuth=FirebaseAuth.getInstance();
        id=mfirebaseAuth.getCurrentUser().getUid();
        TextView ttt=findViewById(R.id.comname1);
        ttt.setText(""+comsell);
        TextView textView1=findViewById(R.id.availqaunt);
        textView1.setText(""+count);

        funtion1(tickersell);
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
    public  void funtion1(String smb)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi api=retrofit.create(myApi.class);

        Call<model> call= api.getmodels(smb);

        call.enqueue(new Callback<model>() {
            @Override
            public void onResponse(Call<model> call, Response<model> response) {
                model data= response.body();
                price11=data.getLatestPrice();
                price11=Double.parseDouble(new DecimalFormat("##.####").format(price11));
                TextView textView=findViewById(R.id.shareprice1);
                textView.setText(""+price11);
            }

            @Override
            public void onFailure(Call<model> call, Throwable t) {


            }
        });

    }
    public void selling(View view)
    {
        EditText editText=findViewById(R.id.sellquantity);
        String s= editText.getText().toString().trim();
        int i= Integer.parseInt(s);
        editText.getText().clear();
        if(i>count)
        {
            AlertDialog.Builder alertDialog= new AlertDialog.Builder(Selling_Page.this);
            alertDialog.setTitle("LOW SHARE ");
            alertDialog.setMessage("You have Only "+count+" share in your account.");
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });
            AlertDialog a= alertDialog.create();
            a.show();
        }
        else if(i==count)
        {
            AlertDialog.Builder alertDialog= new AlertDialog.Builder(Selling_Page.this);
            alertDialog.setTitle("CONFIRM TRANSACTION");
            alertDialog.setMessage("Are sure to sell this? This will reduce "+i+" stocks from you account");
            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final double[] d = new double[1];
                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("Stocks").child(id).child(comsell);
                    reference1.removeValue();
                    DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                    reference2.child("points").setValue(ServerValue.increment((i*price11)));
                    Toast.makeText(Selling_Page.this,"Successful",Toast.LENGTH_SHORT).show();
                    TextView textView1=findViewById(R.id.availqaunt);
                    textView1.setText(""+0);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });
            AlertDialog a= alertDialog.create();
            a.show();

        }
        else
        {
            AlertDialog.Builder alertDialog= new AlertDialog.Builder(Selling_Page.this);
            alertDialog.setTitle("CONFIRM TRANSACTION");
            alertDialog.setMessage("Are sure to buy this? This will reduce "+i+" stocks from you account");
            alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("Stocks").child(id).child(comsell);
                    int r= count-i;
                    count=r;
                    reference1.child("count").setValue(r);
                    TextView textView1=findViewById(R.id.availqaunt);
                    textView1.setText(""+r);
                    DatabaseReference reference2=FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                    reference2.child("points").setValue(ServerValue.increment((i*price11)));
                    Toast.makeText(Selling_Page.this,"Successful",Toast.LENGTH_SHORT).show();

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }

            });
            AlertDialog a= alertDialog.create();
            a.show();

        }
    }
}