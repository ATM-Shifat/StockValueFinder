package android.example.stockvaluefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class homepage extends AppCompatActivity {

    static  public List<Modelclassfor_recycle> userlist=new ArrayList<>();

    //.............//
    // There is a problem in homepage. While Pushing the content in userlist from retrofit, it become
    // null after the scope. As a result, When I set some default ticker for homepage, Its order gets changed
    // and after clicking some buy button from homepage, It crashes. If anyone is using this, Hope you can fix it!
    // Best Of luck dear!
    String ab;
    static String ticker;
    static double changePercent,price1;
    static String name1,id;
    Context context;
    String url="https://cloud.iexapis.com/";
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        fun();


        //funtion2("AAPL");

        //funtion2("F");



    }
    public void fun()
    {
        funtion1("AAPL",false);
        funtion1("MSFT",false);
        funtion1("WFC",false);
        funtion1("FB",false);
        funtion1("F",false);
        funtion1("TSLA",false);
        funtion1("MCD",false);

    }
//    public  void funtion2(String smb)
//    {
//        ticker=smb;
//        Retrofit retrofit2 = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        myApi api=retrofit2.create(myApi.class);
//
//        Call<model> call1= api.getmodels(smb);
//
//        call1.enqueue(new Callback<model>() {
//            @Override
//            public void onResponse(Call<model> call1, Response<model> response1) {
//                model data= response1.body();
//                name1=data.getCompanyName();
//                price1=data.getLatestPrice();
//                price1=Double.parseDouble(new DecimalFormat("##.####").format(price1));
//                //TextView textView =findViewById(R.id.testhome);
//                changePercent= data.getChangePercent() *100;
//                changePercent=Double.parseDouble(new DecimalFormat("##.####").format(changePercent));
//                String t=data.getSymbol();
//                //initData(price1,name1);
//                if(name1.isEmpty())
//                {
//                    //nodata();
//                }
//                else {
//                    initData();
//                    initRecyclerView();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<model> call, Throwable t) {
//
//
//            }
//        });
//
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        firebaseAuth=FirebaseAuth.getInstance();
        if(item.getItemId()==R.id.signout)
        {
            firebaseAuth.signOut();
            finish();
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.profile)
        {
            Intent intent=new Intent(this,user_profile.class);
            startActivity(intent);



        }
        else if (item.getItemId()==R.id.search)
        {
            AlertDialog.Builder alertDialog= new AlertDialog.Builder(homepage.this);
            alertDialog.setTitle("GET THE CURRENT STOCK PRICE!");
            alertDialog.setMessage("ENTER TICKER/SYMBOL OF STOCK :");
            final EditText alart= new EditText(homepage.this);
            alart.setInputType(InputType.TYPE_CLASS_TEXT);
            alertDialog.setView(alart);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ab=alart.getText().toString().trim();
                    funtion1(ab, true);
                }
            });
            alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
            return true;
        }
        else if(item.getItemId()==R.id.favourites)
        {
            Intent intent=new Intent(homepage.this, favourites.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
    public  void funtion1(String smb, boolean ch)
    {
        ticker=smb;
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
                name1=data.getCompanyName();
                price1=data.getLatestPrice();
                price1=Double.parseDouble(new DecimalFormat("##.####").format(price1));
                changePercent= data.getChangePercent() *100;
                ticker=data.getSymbol();
                changePercent=Double.parseDouble(new DecimalFormat("##.####").format(changePercent));
                if(name1.isEmpty())
                {
                    Toast.makeText(homepage.this,"WRONG TICKER",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(ch==true){
                        searchal();

                    }
                    else{

                        initData();
                        initRecyclerView();

                    }
                }
            }

            @Override
            public void onFailure(Call<model> call, Throwable t) {


            }
        });

    }
    public void searchal()
    {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(homepage.this);
        View v= getLayoutInflater().inflate(R.layout.alert,null);
        TextView t1,t2,t3;
        t1=v.findViewById(R.id.dname);
        t2=v.findViewById(R.id.dprice);
        t3=v.findViewById(R.id.dchange);
        t1.setText(""+name1);
        t2.setText("$"+price1);
        t3.setText(""+changePercent+"%");

        alertDialog.setPositiveButton("ADD TO FAVOURITES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("FAV");
                Modelclassfor_recycle m= new Modelclassfor_recycle(price1,name1,changePercent,ticker);
                FirebaseAuth f= FirebaseAuth.getInstance();
                String id= f.getCurrentUser().getUid();
                databaseReference.child(id).child(name1).setValue(m);
                Toast.makeText(homepage.this,"ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setView(v);
        alertDialog.show();

    }


    private void initData() {

                userlist.add(new Modelclassfor_recycle(price1,name1,changePercent,ticker));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;
        Adapter adapter;
        recyclerView=findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(homepage.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new Adapter(userlist,homepage.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

}