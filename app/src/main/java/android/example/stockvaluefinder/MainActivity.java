package android.example.stockvaluefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void registerpage(View view)
    {
        Intent intent= new Intent(this,Register_page.class);
        startActivity(intent);
    }
    public void login_button(View view)
    {
        EditText t1=findViewById(R.id.editText);
        EditText t2=findViewById(R.id.editText2);
        String s1=t1.getText().toString().trim();
        String s2=t2.getText().toString().trim();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(s1,s2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this,"Log In!!", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(), homepage.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(MainActivity.this,"Wrong Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void reset(View view)
    {
        EditText t1=findViewById(R.id.editText);
        String rmail=t1.getText().toString().trim();
        if(rmail.isEmpty())
        {
            t1.setError("Enter An email to Reset");
            t1.requestFocus();
        }
        else
        {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(rmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this,"Email Sent",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        }

    }
}