package com.purnendu.senrysa.Registration;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.purnendu.senrysa.DataBase.Database;
import com.purnendu.senrysa.MainActivity;
import com.purnendu.senrysa.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JobSeekerRegistrationPage extends AppCompatActivity {

    EditText jobSeekerName_editText,jobSeekerPassword_editText,jobSeekerEmail_EditText;
    Button jobSeekerRegistered_button;
    ImageButton attachPdf;
    Uri cvUri =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_registration_page);

        jobSeekerName_editText=findViewById(R.id.jobSeekerName_editText);
        jobSeekerPassword_editText=findViewById(R.id.jobSeekerPassword_editText);
        jobSeekerEmail_EditText=findViewById(R.id.jobSeekerEmail_EditText);
        jobSeekerRegistered_button=findViewById(R.id.jobSeekerRegistered_button);
        attachPdf=findViewById(R.id.attachPdf);


        Database database =new Database(JobSeekerRegistrationPage.this);

        //Doing Registration
        jobSeekerRegistered_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {


                String name=jobSeekerName_editText.getText().toString().trim();
                String password=jobSeekerPassword_editText.getText().toString().trim();
                String email=jobSeekerEmail_EditText.getText().toString().trim();


                if(name.isEmpty())
                {
                    jobSeekerName_editText.setError("Name is required");
                    return;
                }

                if(password.isEmpty())
                {
                    jobSeekerPassword_editText.setError("Password is required");
                    return;
                }

                if(email.isEmpty())
                {
                    jobSeekerEmail_EditText.setError("Email is required");
                    return;
                }

                if(cvUri ==null)
                {
                    Toast.makeText(JobSeekerRegistrationPage.this, "Attach your CV", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(database.isRecruiter(email))
                {
                    Toast.makeText(JobSeekerRegistrationPage.this, "You are a Recruiter", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(getFileSize(JobSeekerRegistrationPage.this,cvUri)>(0.5))
                {
                    Toast.makeText(JobSeekerRegistrationPage.this, "File size is too high", Toast.LENGTH_SHORT).show();
                    return;
                }


                ProgressDialog progressDialog=new ProgressDialog(JobSeekerRegistrationPage.this);
                progressDialog.setTitle("Processing...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                       try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(JobSeekerRegistrationPage.this.getContentResolver(), cvUri);
                            byte[] data = getBitmapAsByteArray(bitmap);
                            if(database.insertJobSeekerDetails(name,password,email,data))
                            {
                                Looper.prepare();
                                Toast.makeText(JobSeekerRegistrationPage.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(JobSeekerRegistrationPage.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(JobSeekerRegistrationPage.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            Looper.prepare();
                            Toast.makeText(JobSeekerRegistrationPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        //attaching pdf
        attachPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkStoragePermission();
            }
        });
    }

    private void checkStoragePermission() {

        Dexter.withContext(JobSeekerRegistrationPage.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                showSnackBar();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();


    }

    private void showSnackBar() {
        ConstraintLayout layout=findViewById(R.id.jobSeekerRegistrationPage);
        Snackbar snackbar
                = Snackbar.make(layout, "Storage access is needed to access pdf file", Snackbar.LENGTH_LONG)
                .setAction("Go to setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        snackbar.show();
    }

    //opening pdf
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data!=null)
                        {
                            Uri uri = data.getData();
                            if(uri!=null)
                            cvUri =uri;
                        }

                    }
                }
            });


    //Getting Bitmap Array from Bitmap
    private static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    //Getting file size
    private double getFileSize(Context context, Uri uri) {
        double fileSize=0;
        try (Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {

                // get file size
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (!cursor.isNull(sizeIndex)) {
                    fileSize = cursor.getDouble(sizeIndex);
                    fileSize=fileSize / (1024 * 1024);
                }
            }
        }
        return fileSize;
    }
}