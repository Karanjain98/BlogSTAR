package com.code.blogstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by karan jain on 19-08-2017.
 */

public class PostActivity extends AppCompatActivity {
    ImageButton im;
    EditText etTitle,etDescription;
    DatabaseReference databaseReference;
    Button addBtn;

    Uri uri;
    StorageReference storageReference;
    ProgressDialog progressBar;
    public static final int GALLERY_REQUEST=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_post);
        super.onCreate(savedInstanceState);

        im= (ImageButton) findViewById(R.id.imageSelect);
        etDescription= (EditText) findViewById(R.id.desciptionField);
        etTitle= (EditText) findViewById(R.id.titleField);
        addBtn= (Button) findViewById(R.id.btnAdd);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("BLOG");
        progressBar=new ProgressDialog((this));


        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_REQUEST);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

    }
    private void startPosting()
    {   progressBar.setMessage("POSTING TO BLOG..");
        progressBar.show();
        final String title_val=etTitle.getText().toString();
        final String description_val=etDescription.getText().toString();

        if(!TextUtils.isEmpty(title_val)&&!TextUtils.isEmpty((description_val)))
        {
            StorageReference filepath=storageReference.child("BLOG_IMAGES").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                        Uri downloadurl=taskSnapshot.getDownloadUrl();

                        DatabaseReference newPost=databaseReference.push();
                        newPost.child("Title").setValue(title_val);
                        newPost.child("Description").setValue(description_val);
                        newPost.child("ImageUrl").setValue(downloadurl.toString());

                        progressBar.dismiss();
                        startActivity(new Intent(PostActivity.this,MainActivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK)
        {
            uri=data.getData();
            im.setImageURI(uri);
        }
    }
}
