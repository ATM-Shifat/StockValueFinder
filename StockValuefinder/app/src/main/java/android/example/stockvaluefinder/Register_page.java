package android.example.stockvaluefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Register_page extends AppCompatActivity {

   // public static final String extra="android.intent.extra.email";
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    String email, pass, conpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public void login(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void register(View view)
    {
        EditText editText, editText2,editText3;
        editText=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        email=editText.getText().toString().trim();
        pass=editText2.getText().toString().trim();
        conpass=editText3.getText().toString().trim();
        editText.getText().clear();
        editText2.getText().clear();
        editText3.getText().clear();
        if(pass.equals(conpass))
        {
            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        fun();
                    }
                    else
                    {
                        FirebaseAuthException e = (FirebaseAuthException )task.getException();
                        Toast.makeText(Register_page.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(Register_page.this,"Password Don't match", Toast.LENGTH_SHORT).show();
        }
    }
    public void fun()
    {
        Toast.makeText(Register_page.this,"Registration Successful",Toast.LENGTH_SHORT).show();
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=new Intent(this,userinfo.class);
        intent.putExtra("mail",userid);
        startActivity(intent);
    }
}