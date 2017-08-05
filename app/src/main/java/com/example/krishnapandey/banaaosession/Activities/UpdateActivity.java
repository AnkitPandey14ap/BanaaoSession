package com.example.krishnapandey.banaaosession.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Adapters.MyAdapter;
import com.example.krishnapandey.banaaosession.Adapters.MyCustomAdapter;
import com.example.krishnapandey.banaaosession.DataClasses.Nodes;
import com.example.krishnapandey.banaaosession.DataClasses.SessionInformation;
import com.example.krishnapandey.banaaosession.DataClasses.UserInformation;
import com.example.krishnapandey.banaaosession.PopUps.AttendancePopUp;
import com.example.krishnapandey.banaaosession.PopUps.ListSelectionPopUp;
import com.example.krishnapandey.banaaosession.PopUps.TopicPopUp;
import com.example.krishnapandey.banaaosession.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private TextView sessionNameEditText;
    private TextView locationEditText;
    private TextView topicEditText;
    private TextView timmingEditText;
    private TextView feedbackEditText;
    private CheckBox statusCheckBox;
    private Button sendButton;
    private Button attendanceButton;

    private ListView listView;


    public HashMap<String, String> names=new HashMap<>();
    private MyCustomAdapter adapter;

    String sessionName;

    private DatabaseReference databseRef;


    //forRecyclerView
    private static final int RESULT_LOAD_IMAGE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ArrayList<String> list;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //Recycler ended here

    //Storage Reference
    private StorageReference storageRef;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Log.i("Ankit","onCreate");

        final Intent intent = getIntent();
        sessionName = intent.getStringExtra("NAME");
        Log.i("Ankit",sessionName);

        //initialize all the widgets
        initialize();
//        Check permission
        verifyStoragePermissions(this);

//        fetch session data from server
        fetchData();

//        loadImage from gallery
        loadImage();
//        loadImageFromFirebase();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        topicEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UpdateActivity.this, TopicPopUp.class);
                intent1.putExtra("NAME", sessionName);
                startActivity(intent1);
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getText().equals("Completed")) {
                    status.setText("Not Complete");
                    status.setBackgroundColor(0xfff00000);
                    databseRef.child("completed").setValue(false);

                } else {
                    status.setText("Completed");
                    status.setBackgroundColor(0x00000000);
                    databseRef.child("completed").setValue(true);

                }
            }
        });

        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(UpdateActivity.this, AttendancePopUp.class);
                Log.i("Ankit","send name before "+sessionName);
                intent2.putExtra("NAME", sessionName);
                startActivityForResult(intent2,5);
            }
        });
    }

    private void loadImageFromFirebase() {

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageReference riversRef = storageRef.child("images/rivers.jpg");
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }

    private void loadImage() {
//        RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        /*Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });*/

    }

    private void fetchData() {

        databseRef=NewSessionBottomActivity.getDatabaseReference().child(Nodes.session).child(sessionName);
        databseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SessionInformation sessionInformation = dataSnapshot.getValue(SessionInformation.class);
                names = sessionInformation.trainer;
                sessionNameEditText.setText("Name: "+sessionInformation.name);
                locationEditText.setText("Location: "+sessionInformation.location);
                //topicEditText.setText(sessionInformation.topic);
                timmingEditText.setText("From : "+sessionInformation.timeTo+" - To : "+sessionInformation.timeTo);
                if (sessionInformation.completed) {
                    status.setText("Completed");
                    status.setBackgroundColor(0x00000000);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                list.add(picturePath);

                storageRef = FirebaseStorage.getInstance().getReference().child(sessionName);

                StorageReference riversRef = storageRef.child("photo").child(selectedImage.getLastPathSegment());
                riversRef.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UpdateActivity.this, "Suceesfully uploaded", Toast.LENGTH_SHORT).show();
                        Log.i("Ankit","Succesful");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                            }
                        });

            }
            /*mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter=new MyAdapter(list,MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
*/


        }catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new MyAdapter(list,UpdateActivity.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    void sendMail() {
/*
        final ProgressDialog progressDialog=ProgressDialog.show(this, "Waiting...", "Please wait...");
        final ArrayList<String> arrayList = new ArrayList<>();
        NewSessionBottomActivity.getDatabaseReference().child(Nodes.user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children ) {
                    UserInformation userInformation = child.getValue(UserInformation.class);
                    //arrayList.add(new UserInformation(userInformation.name, userInformation.timeFrom, userInformation.timeTo));
                    for (int i = 0; i < names.size(); i++) {
                        if (userInformation.name.equals(names.get(i))) {
                            arrayList.add(userInformation.email);
                            Log.i("Ankit", String.valueOf(arrayList.size()));
                            Log.i("Ankit", String.valueOf(arrayList));
                        }
                    }
                }
            progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.i("Ankit", String.valueOf(arrayList));
*/

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com","hamarapandey@gmail.com"});
//        i.putExtra(Intent.EXTRA_EMAIL  , arrayList);
        i.putExtra(Intent.EXTRA_SUBJECT, sessionNameEditText.getText()+" update");
        i.putExtra(Intent.EXTRA_TEXT   , feedbackEditText.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(UpdateActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    void initialize() {
        sessionNameEditText = (TextView) findViewById(R.id.sessionNameEditText);
        locationEditText = (TextView) findViewById(R.id.locationEditText);
        status = (TextView) findViewById(R.id.status);
        timmingEditText= (TextView) findViewById(R.id.timmingEditText);
        feedbackEditText= (TextView) findViewById(R.id.feedbackEditText);
        topicEditText = (TextView) findViewById(R.id.topicEditText);

        list = new ArrayList<String>();
//        statusCheckBox = (CheckBox) findViewById(R.id.statusCheckBox);

        sendButton = (Button) findViewById(R.id.sendButton);
        attendanceButton = (Button) findViewById(R.id.attendanceButton);
        listView = (ListView) findViewById(R.id.listView);
    }
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
