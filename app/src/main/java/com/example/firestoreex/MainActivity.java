package com.example.firestoreex;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_email;
    Button btn_save, btn_read, btn_update, btn_delete;

    TextView showdata;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference friendsRef = db.collection("Users")
            .document("ListDAta");
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.name);
        et_email = findViewById(R.id.email);

        btn_save = findViewById(R.id.savedata);
        btn_read = findViewById(R.id.getdata);
        btn_update = findViewById(R.id.update);
        btn_delete = findViewById(R.id.delete);
        showdata = findViewById(R.id.datacheck);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getALLData();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAll();
            }
        });


    }

    private void SaveData() {
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();

        UserModel userModel = new UserModel(name, email);

        collectionReference.add(userModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });
    }

    private void getALLData() {

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                String data = "";

                for (QueryDocumentSnapshot q : queryDocumentSnapshots) {

                    UserModel userModel = q.toObject(UserModel.class);

                    data += "Name: " + userModel.getName() + "Email: " + userModel.getEmail() + "\n";

                }

                showdata.setText(data);


            }
        });
    }


    private void UpdateData() {
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();

        friendsRef.update("name", name);
        friendsRef.update("email", email);
    }

    private void DeleteAll() {
        friendsRef.delete();
    }
}