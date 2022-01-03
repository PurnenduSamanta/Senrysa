package com.purnendu.senrysa.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.purnendu.senrysa.Model.JobModel;
import com.purnendu.senrysa.Model.JobSeekerDetails;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "DATABASE";
    private final Context context;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;


    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table RecruiterDetails " +
                        "(id integer primary key autoincrement, name text not null,password text not null,email text not null unique)");


        db.execSQL(
                "create table jobPost " +
                        "(post_id integer primary key autoincrement," +
                        "email text not null," +
                        "job_title text not null," +
                        "job_description text not null," +
                        "skill_set_1 text not null," +
                        "skill_set_2 text not null," +
                        "department  TEXT NOT NULL," +
                        "experience  INTEGER NOT NULL," +
                        "recruiter_id INTEGER NOT NULL," +
                        "FOREIGN KEY (recruiter_id) REFERENCES RecruiterDetails(id))");

        db.execSQL(
                "create table JobSeekerDetails "+
                        "(id integer primary key autoincrement, name text not null,password text not null,email text not null unique,cvImage Blob not null )");


        db.execSQL(
                "create table JobApplied "+
                        "(jobID INTEGER NOT NULL,"+
                        "jobSeekerID INTEGER NOT NULL,"+
                        "FOREIGN KEY(jobID) REFERENCES jobPost(post_id),"+
                        "FOREIGN KEY(jobSeekerID) REFERENCES JobSeekerDetails(id))");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS RecruiterDetails");
        db.execSQL("DROP TABLE IF EXISTS jobPost");
        db.execSQL("DROP TABLE IF EXISTS JobSeekerDetails");
        db.execSQL("DROP TABLE IF EXISTS JobApplied");
        onCreate(db);
    }


    public boolean insertRecruiterDetails(String recruiterName, String password, String email) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", recruiterName);
            contentValues.put("password", password);
            contentValues.put("email", email);
            long rowInserted = db.insert("RecruiterDetails", null, contentValues);
            return rowInserted != -1;
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean insertJobSeekerDetails(String  jobSeekerName,String password,String email,byte[] cvImage)
    {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", jobSeekerName);
            contentValues.put("password", password);
            contentValues.put("email", email);
            contentValues.put("cvImage", cvImage);

            long rowInserted=db.insert("JobSeekerDetails", null, contentValues);
            return rowInserted != -1;

        }
        catch (SQLiteException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateRecruiter(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String Query = "Select * from RecruiterDetails where email = '" + email + "' and password = '" + password + "'";
            Cursor cursor = db.rawQuery(Query, null);

            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }


    }


    public boolean validateJobSeeker(String email,String password)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            String Query = "Select * from JobSeekerDetails where email = '" + email + "' and password = '"+password+"'";
            Cursor cursor = db.rawQuery(Query, null);

            if(cursor.getCount() ==1){
                cursor.close();
                return true;
            }
            else
            {
                cursor.close();
                return false;
            }
        }
        catch (SQLiteException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    public boolean insertJobDetails(String email, String jobTitle, String jobDesc, String skillSet1, String skillSet2, String department, int experience,int recruiterId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("job_title", jobTitle);
            contentValues.put("job_description", jobDesc);
            contentValues.put("skill_set_1", skillSet1);
            contentValues.put("skill_set_2", skillSet2);
            contentValues.put("department", department);
            contentValues.put("experience", experience);
            contentValues.put("recruiter_id", recruiterId);
            long rowInserted = db.insert("jobPost", null, contentValues);
            return rowInserted != -1;
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<JobModel> getAllJobPost(String recruiterEmail) {

        String mail = recruiterEmail.replace("@gmail.com", "");
        ArrayList<JobModel> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " +
                "jobPost" + " where " + "email" + " like '%" + mail + "%'", null);
        if (res.moveToFirst()) {
            do {
                int jobId=res.getInt(0);
                String email = res.getString(1);
                String title = res.getString(2);
                String desc = res.getString(3);
                String ss1 = res.getString(4);
                String ss2 = res.getString(5);
                String department = res.getString(6);
                int experience = res.getInt(7);
                array_list.add(new JobModel(jobId,email, title, desc, ss1, ss2, department, experience));
            } while (res.moveToNext());
        }
        res.close();
        return array_list;
    }

    public ArrayList<JobModel> getAllJobPostFromUser() {

        ArrayList<JobModel> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " +
                "jobPost", null);
        if (res.moveToFirst()) {
            do {
                int jobId=res.getInt(0);
                String email = res.getString(1);
                String title = res.getString(2);
                String desc = res.getString(3);
                String ss1 = res.getString(4);
                String ss2 = res.getString(5);
                String department = res.getString(6);
                int experience = res.getInt(7);
                array_list.add(new JobModel(jobId,email, title, desc, ss1, ss2, department, experience));
            } while (res.moveToNext());
        }
        res.close();
        return array_list;
    }

    public ArrayList<JobModel> getAllJobPostBySearch(String finder) {

        ArrayList<JobModel> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String query="SELECT * FROM jobPost where skill_set_1 like"+ " '%"+finder+"%'"+" or skill_set_2 like"+"'%"+finder+"%'";
        Cursor res = db.rawQuery(query,null);


        if (res.moveToFirst()) {
            do {
                int jobId=res.getInt(0);
                String email = res.getString(1);
                String title = res.getString(2);
                String desc = res.getString(3);
                String ss1 = res.getString(4);
                String ss2 = res.getString(5);
                String department = res.getString(6);
                int experience = res.getInt(7);
                array_list.add(new JobModel(jobId,email, title, desc, ss1, ss2, department, experience));
            } while (res.moveToNext());
        }
        res.close();
        return array_list;
    }


    public boolean isRecruiter(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String Query = "Select * from RecruiterDetails where email = '" + email + "'";
            Cursor cursor = db.rawQuery(Query, null);

            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public boolean isJobSeeker(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String Query = "Select * from JobSeekerDetails where email = '" + email + "'";
            Cursor cursor = db.rawQuery(Query, null);

            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public  int getRecruiterIdFromEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String Query = "Select id from RecruiterDetails where email = '" + email + "'";
            Cursor cursor = db.rawQuery(Query, null);

            if( cursor != null && cursor.moveToFirst() ){
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                cursor.close();
                return  id;
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
           return  -1;
        }
        return  -1;
    }

    public  int getJobSeekerIdFromEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String Query = "Select id from JobSeekerDetails where email = '" + email + "'";
            Cursor cursor = db.rawQuery(Query, null);

            if( cursor != null && cursor.moveToFirst() ){
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                cursor.close();
                return  id;
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return  -1;
        }
        return  -1;
    }


    public  String getJobSeekerNameFromEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String Query = "Select name from JobSeekerDetails where email = '" + email + "'";
            Cursor cursor = db.rawQuery(Query, null);

            if( cursor != null && cursor.moveToFirst() ){
               @SuppressLint("Range") String name= cursor.getString(cursor.getColumnIndex("name"));
                cursor.close();
                return  name;
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return  null;
        }
        return  null;
    }

    public  String getRecruiterNameFromEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String Query = "Select name from RecruiterDetails where email = '" + email + "'";
            Cursor cursor = db.rawQuery(Query, null);

            if( cursor != null && cursor.moveToFirst() ){
                @SuppressLint("Range") String name= cursor.getString(cursor.getColumnIndex("name"));
                cursor.close();
                return  name;
            }
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return  null;
        }
        return  null;
    }


    public  boolean applyForJob(int jobId,int jobSeekerId)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("jobID", jobId);
            contentValues.put("jobSeekerID", jobSeekerId);
            long rowInserted = db.insert("JobApplied", null, contentValues);
            return rowInserted != -1;
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isAlreadyApplied(int jobId,int jobSeekerId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select jobSeekerID from JobApplied where jobID =" + jobId+" and jobSeekerID ="+jobSeekerId;
        try {
            Cursor cursor = db.rawQuery(Query, null);
            if(cursor.getCount() <= 0){
                cursor.close();
                return false;
            }
        }
        catch (SQLiteException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return  false;
        }
        return  true;
    }


    public ArrayList<JobSeekerDetails>getAppliedJobSeeker(int jobId)
    {
        ArrayList<JobSeekerDetails> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor res = db.rawQuery("SELECT * FROM " +
                    "JobSeekerDetails where id in ( SELECT jobSeekerID from JobApplied where jobID ="+jobId+")", null);
            if (res.moveToFirst()) {
                do {
                    int jobSeekerId=res.getInt(0);
                    String jobSeekerName = res.getString(1);
                    String jobSeekerEmail = res.getString(3);
                    byte[] cv = res.getBlob(4);
                    array_list.add(new JobSeekerDetails(jobSeekerId,jobSeekerName,jobSeekerEmail,cv));
                } while (res.moveToNext());
            }
            res.close();
            return  array_list;
        }
        catch (SQLiteException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return  null;
        }
    }





}
