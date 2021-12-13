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

import java.util.ArrayList;

public class RecruiterRegistrationDb extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "RECRUITER_DB";
    private final Context context;


    public  RecruiterRegistrationDb(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
        this.context=context;


    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table RecruiterDetails "+
                        "(id integer primary key autoincrement, name text not null,password text not null,email text not null unique,type integer not null)");


        db.execSQL(
                "create table jobPost "+
                        "(post_id integer primary key autoincrement," +
                        "email text not null,"+
                        " job_title text not null," +
                        "job_description text not null," +
                        "skill_set_1 text not null," +
                        "skill_set_2 text not null,"+
                        "department  TEXT NOT NULL,"+
                        "experience  INTEGER NOT NULL"+
                        ")");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS RecruiterDetails");
        db.execSQL("DROP TABLE IF EXISTS jobPost");
        onCreate(db);
    }


    public boolean insertRecruiterDetails(String  recruiterName,String password,String email,int type)
    {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", recruiterName);
            contentValues.put("password", password);
            contentValues.put("email", email);
            contentValues.put("type", type);
            long rowInserted=db.insert("RecruiterDetails", null, contentValues);
            return rowInserted != -1;
        }
        catch (SQLiteException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateEmailAddress(String email,String password)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        try{
            String Query = "Select * from " + "RecruiterDetails" + " where " + "email" + " = " + "'" + email + "'"+"and password="+password;
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

    public boolean insertJobDetails(String email,String jobTitle,String jobDesc,String skillSet1,String skillSet2,String department,int experience)
    {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", email);
            contentValues.put("job_title", jobTitle);
            contentValues.put("job_description", jobDesc);
            contentValues.put("skill_set_1", skillSet1);
            contentValues.put("skill_set_2", skillSet2);
            contentValues.put("department", department);
            contentValues.put("experience", experience);
            long rowInserted=db.insert("jobPost", null, contentValues);
            return rowInserted != -1;
        }
        catch (SQLiteException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<JobModel>getAllJobPost(String recruiterEmail)
    {

        String mail=recruiterEmail.replace("@gmail.com","");
        ArrayList<JobModel> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " +
                "jobPost" + " where " +"email"+ " like '%"+mail+"%'" , null);
        if (res.moveToFirst())
        {
            do {
                String email=res.getString(1);
                String title=res.getString(2);
                String desc=res.getString(3);
                String ss1=res.getString(4);
                String ss2=res.getString(5);
                String department=res.getString(6);
                int experience=res.getInt(7);
                array_list.add(new JobModel(email,title,desc,ss1,ss2,department,experience));
            } while (res.moveToNext());
        }
        res.close();
        return array_list;
    }

    public ArrayList<JobModel>getAllJobPostFromUser()
    {

        ArrayList<JobModel> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " +
                "jobPost", null);
        if (res.moveToFirst())
        {
            do {
                String email=res.getString(1);
                String title=res.getString(2);
                String desc=res.getString(3);
                String ss1=res.getString(4);
                String ss2=res.getString(5);
                String department=res.getString(6);
                int experience=res.getInt(7);
                array_list.add(new JobModel(email,title,desc,ss1,ss2,department,experience));
            } while (res.moveToNext());
        }
        res.close();
        return array_list;
    }

    public ArrayList<JobModel>getAllJobPostBySearch(String finder)
    {

        ArrayList<JobModel> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " +
                "jobPost" + " where " +"skill_set_1"+ " like '% "+finder+" %'"+" or "+"skill_set_2"+ " like '% "+finder+" %'" , null);


        if (res.moveToFirst())
        {
            do {
                String email=res.getString(1);
                String title=res.getString(2);
                String desc=res.getString(3);
                String ss1=res.getString(4);
                String ss2=res.getString(5);
                String department=res.getString(6);
                int experience=res.getInt(7);
                array_list.add(new JobModel(email,title,desc,ss1,ss2,department,experience));
            } while (res.moveToNext());
        }
        res.close();
        return array_list;
    }

    public int isRecruiter(String email)
    {
        String mail=email.replace("@gmail","");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT "+"type " +" from "+
            "RecruiterDetails" + " where " +"email"+ " like '%"+mail+"%'" , null);
        if(res.getCount() == 1){
            res.moveToFirst();
            @SuppressLint("Range") int isRecruiter = res.getInt(res.getColumnIndex("4"));
            res.close();
            return isRecruiter;
        }
        res.close();
        return 0;
    }








}
