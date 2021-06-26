package android.example.stockvaluefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userinfo extends AppCompatActivity {

    String userid;
    EditText name,phn,bank;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        Intent intent=getIntent();
        userid =intent.getStringExtra("mail");
    }

    public void add_info(View view)
    {
        name=findViewById(R.id.editTextTextPersonName);
        phn=findViewById(R.id.editTextTextPersonName2);
        bank=findViewById(R.id.editTextTextPersonName3);
        String names,phns,banks;
        names=name.getText().toString().trim();
        phns=phn.getText().toString();
        banks=bank.getText().toString().trim();
        name.getText().clear();
        phn.getText().clear();
        bank.getText().clear();
        info Info= new info(names,phns,banks,10000);
        reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).setValue(Info);
        Toast.makeText(userinfo.this,"Information Updated!",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,homepage.class);
        startActivity(intent);
    }

}