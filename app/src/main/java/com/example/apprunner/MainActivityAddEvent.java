package com.example.apprunner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityAddEvent extends AppCompatActivity  {

    TextView date_start,date_end;
    Button pickDate_start,pickDate_end,buttonsubmit,buttonAdd,addpicture;
    ImageView imageView;
    DatePickerDialog.OnDateSetListener setListener;
    LinearLayout layoutlist;
    String first_name,last_name,type;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 1;

    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;

    List<String> distanceList = new ArrayList<>();
    ArrayList<Cricketer> cricketersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_event);

//        first_name = getIntent().getExtras().getString("first_name");
//        last_name = getIntent().getExtras().getString("last_name");
//        type = getIntent().getExtras().getString("type");

        MainActivity mainActivity = new MainActivity();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        date_start = findViewById(R.id.tv_date_start);
        pickDate_start = findViewById(R.id.btn_pickDate1);

        Calendar calendar_start = Calendar.getInstance();
        final int year1 = calendar_start.get(Calendar.YEAR);
        final int month1 = calendar_start.get(Calendar.MONTH);
        final int day1 = calendar_start.get(Calendar.DAY_OF_MONTH);

        pickDate_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivityAddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                        month1 = month1+1;
                        String date = day1 + "/" + month1 + "/" + year1;
                        date_start.setText(date);
                    }
                },year1,month1,day1);
                datePickerDialog.show();
            }
        });

        date_end = findViewById(R.id.tv_date_end);
        pickDate_end = findViewById(R.id.btn_pickDate2);

        Calendar calendar_end = Calendar.getInstance();
        final int year2 = calendar_end.get(Calendar.YEAR);
        final int month2 = calendar_end.get(Calendar.MONTH);
        final int day2 = calendar_end.get(Calendar.DAY_OF_MONTH);

        pickDate_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivityAddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year2, int month2, int day2) {
                        month2 = month2+1;
                        String date = day2 + "/" + month2 + "/" + year2;
                        date_end.setText(date);
                    }
                },year2,month2,day2);
                datePickerDialog.show();
            }
        });

        //setButtonSubmit
        buttonsubmit = findViewById(R.id.btnRegEvent);
        layoutlist = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);

        distanceList.add("Choose KG");
        distanceList.add("4 KG");
        distanceList.add("11 KG");
        distanceList.add("21 KG");
        distanceList.add("42 KG");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){

                    case R.id.button_add:

                        addView();

                        break;

                    case R.id.btnRegEvent:

                        if(checkIfValidAndRead()){

                            Intent intent = new Intent(MainActivityAddEvent.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("list",cricketersList);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                }
            }
        });

        final EditText editorganizer = findViewById(R.id.userEDT);
        final EditText editevent = findViewById(R.id.eventEDT);
        final EditText editproducer = findViewById(R.id.producerEDT);
        final TextView textdatestart = findViewById(R.id.tv_date_start);
        final TextView textdateend = findViewById(R.id.tv_date_end);
        //        final EditText editprice1 = findViewById(R.id.priceEDT1);
        //        final EditText editprice2 = findViewById(R.id.priceEDT2);
        //        final EditText editprice3 = findViewById(R.id.priceEDT3);
        //        final EditText editprice4 = findViewById(R.id.priceEDT4);
        final EditText editobjective = findViewById(R.id.objectiveEDT);
        final EditText editdetail = findViewById(R.id.detailsEDT);

        buttonsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String organizer = editorganizer.getText().toString();
                String event = editevent.getText().toString();
                String producer = editproducer.getText().toString();
                String date_start = textdatestart.getText().toString();
                String date_end = textdateend.getText().toString();
                String objective = editobjective.getText().toString();
                String detail = editdetail.getText().toString();
                if (validateAddevent(organizer, event, producer, date_start, date_end, objective, detail)) {
                    doAddevent(editorganizer, editevent, editproducer, textdatestart, textdateend, editobjective, editdetail);
                }
            }

            private boolean validateAddevent(String organizer, String event, String producer, String date_start, String date_end, String objective, String detail) {
                if(organizer == null || organizer.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this,"Organizer is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(event == null || event.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this, "Event is required", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(producer == null || producer.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this,"Producer is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(date_start.equals("dd / mm / yyyy")){
                    Toast.makeText(MainActivityAddEvent.this, "DateStart is required", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(date_end.equals("dd / mm / yyyy")){
                    Toast.makeText(MainActivityAddEvent.this,"DateEnd is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(objective == null || objective.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this, "Objective is required", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(detail == null || detail.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this,"Detail is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            private void doAddevent(EditText editorganizer, EditText editevent, EditText editproducer, TextView textdatestart, TextView textdateend, EditText editobjective, EditText editdetail) {
                OrganizerAPI service = retrofit.create(OrganizerAPI.class);
                Call<ResultQuery> call = service.insertEvent(new EventFormDB(editorganizer.getText().toString(),
                        editevent.getText().toString(),
                        editproducer.getText().toString(),
                        textdatestart.getText().toString(),
                        textdateend.getText().toString(),
                        editobjective.getText().toString(),
                        editdetail.getText().toString(),
                        "adasdsadsa"));

                call.enqueue(new Callback<ResultQuery>() {
                    @Override
                    public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                        Toast.makeText(MainActivityAddEvent.this,"ลงทะเบียนเสร็จสิ้นโปรดรอแอดมินอนุมัติ",Toast.LENGTH_LONG).show();
                        uploadImage();
                        openMainActiviry();
                    }

                    @Override
                    public void onFailure(Call<ResultQuery> call, Throwable t) {
                        Toast.makeText(MainActivityAddEvent.this,"ข้อมูลของท่านยังกรอกไม่ครบกรุณาลองใหม่อีกครั้ง",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Firebase Init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Int view
        addpicture = findViewById(R.id.btn_addPicture);
        imageView = findViewById(R.id.imgView);

        addpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading....");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                            progressDialog.dismiss();
                            Toast.makeText(MainActivityAddEvent.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivityAddEvent.this, "Failed " +e.getMessage() ,Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded" +(int)progress+"%");
                        }
                    });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    // endOnCreate
    private boolean checkIfValidAndRead() {

        cricketersList.clear();
        boolean result = true;

        for(int i=0;i<layoutlist.getChildCount();i++){

            View cricketerView = layoutlist.getChildAt(i);

            EditText editTextName = (EditText)cricketerView.findViewById(R.id.row_event);
            AppCompatSpinner spinnerTeam = (AppCompatSpinner)cricketerView.findViewById(R.id.spinner_row);

            Cricketer cricketer = new Cricketer();

            if(!editTextName.getText().toString().equals("")){
                cricketer.setPrice(editTextName.getText().toString());
            }else {
                result = false;
                break;
            }

            if(spinnerTeam.getSelectedItemPosition()!=0){
                cricketer.setDistance(distanceList.get(spinnerTeam.getSelectedItemPosition()));
            }else {
                result = false;
                break;
            }

            cricketersList.add(cricketer);

        }

        if(cricketersList.size()==0){
            result = false;
            Toast.makeText(this, "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    private void addView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_event,null,false);

        EditText editText = (EditText)cricketerView.findViewById(R.id.row_event);
        AppCompatSpinner spinnerTeam = (AppCompatSpinner)cricketerView.findViewById(R.id.spinner_row);
        ImageView imageClose = (ImageView)cricketerView.findViewById(R.id.image_remove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,distanceList);
        spinnerTeam.setAdapter(arrayAdapter);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(cricketerView);
            }
        });

        layoutlist.addView(cricketerView);

    }

    private void removeView(View view){

        layoutlist.removeView(view);

    }

    //intent
    public void openMainActiviry(){
        Intent intent = new Intent(MainActivityAddEvent.this, MainActivity.class);
        intent.putExtra("first_name", getIntent().getExtras().getString("first_name"));
        intent.putExtra("last_name", getIntent().getExtras().getString("last_name"));
        intent.putExtra("type", getIntent().getExtras().getString("type"));
        startActivity(intent);
    }

}
