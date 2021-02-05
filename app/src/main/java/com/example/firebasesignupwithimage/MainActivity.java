package com.example.firebasesignupwithimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference reference;
    EditText roll,name,course,contact;
    public Uri imageUri;
    private ImageView profilePic;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        signUp = findViewById( R.id.btn_signUp );
       profilePic = findViewById( R.id.profile_image );
        storage = FirebaseStorage.getInstance();

        profilePic.setOnClickListener( v -> {
            Intent intent = new Intent();
            intent.setType( "image/*" );
            intent.setAction( Intent.ACTION_GET_CONTENT );
            startActivityForResult( intent,1 );
            Log.e( "bsd", "onClick: " );
        } );


        signUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadToFireBase();
            }
        } );
    }

    private void upLoadToFireBase() {
        final ProgressDialog pd = new ProgressDialog( this );
        pd.setTitle( "image is uploading......." );
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = reference.child("images/"+randomKey);


        Log.e( "cxzNMcxznnzxcm", "onSuccess: "  );
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener< UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make( findViewById( android.R.id.content ),"image uploaded.",Snackbar.LENGTH_LONG ).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        Toast.makeText( MainActivity.this, "Failed to upload", Toast.LENGTH_SHORT ).show();
                    }
                })
                .addOnProgressListener( new OnProgressListener< UploadTask.TaskSnapshot >() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        Log.e( "bsdsdasfdasfdas", "Successs progressssss: " );
                        double progressPersent = (100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        pd.setMessage( "percentage "+(int)progressPersent+"%" );
                    }
                } );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(resultCode==RESULT_OK){
            Log.e( "bsdsdasfdasfdas", "onClick: "+requestCode );
           imageUri = data.getData();

            profilePic.setImageURI( imageUri );

        }
    }


}