package com.example.ssiam.employee;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

 private EditText NameEditText,TypeEditText,DateEditText,IdEditText,AddressEditText,PasswordEditText;
 private Button AdButton,DisButton,UpdateButton,DeleteEmployeeButton,SignUpButton;
    Mysql mysql;
    Userdetails userdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mysql= new Mysql(this);
        userdetails = new Userdetails();

        SQLiteDatabase sqLiteDatabase =mysql.getWritableDatabase();

        NameEditText=(EditText)findViewById(R.id.EmployeeName);
        TypeEditText=(EditText)findViewById(R.id.EmployeeType);
        IdEditText=(EditText)findViewById(R.id.Id);
        DateEditText=(EditText)findViewById(R.id.DateofJoining);
        AddressEditText=(EditText)findViewById(R.id.Address);

        AdButton=(Button)findViewById(R.id.AddEmployee);
        DisButton=(Button)findViewById(R.id.DisEmployee);
        UpdateButton=(Button)findViewById(R.id.UpdateEmployee);
        DeleteEmployeeButton=(Button)findViewById(R.id.DeleteEmployee);
        SignUpButton=(Button)findViewById(R.id.SignUp);


        AdButton.setOnClickListener(this);
        DisButton.setOnClickListener(this);
        UpdateButton.setOnClickListener(this);
        DeleteEmployeeButton.setOnClickListener(this);
        SignUpButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){


       // String username = usernameEditText.getText().toString();
        //String password = passwordEditText.getText().toString();






        String name=NameEditText.getText().toString();//username
        String id=IdEditText.getText().toString();//password
        String type=TypeEditText.getText().toString();
        String date=DateEditText.getText().toString();
        String address=AddressEditText.getText().toString();
        //String password=PasswordEditText.getText().toString();

        userdetails.setName(name);
        userdetails.settype(type);
        userdetails.setid(id);
        userdetails.setaddress(address);
        userdetails.setdate(date);



        if (view.getId() == R.id.AddEmployee) {


           long rowid = mysql.insertData(name,type,id,address,date);

           if(rowid==-1){
               Toast.makeText(getApplicationContext(), "unsuccessfull! ", Toast.LENGTH_LONG).show();

           }
           else{

               Toast.makeText(getApplicationContext(), "Row "+rowid+" is successfully inserted ! ", Toast.LENGTH_LONG).show();
           }


        }
        if(view.getId()==R.id.DisEmployee)
        {
            Cursor cursor= mysql.displayAllData();
            if(cursor.getCount()==0)
            {

                showData("Error","No data found");
                //there is no data to show

               return;
            }


            StringBuffer stringbBuffer =new StringBuffer();
            while(cursor.moveToNext())
            {
                stringbBuffer.append("Name: "+cursor.getString(0)+"\n");
                stringbBuffer.append("Type :"+cursor.getString(1)+"\n");
                stringbBuffer.append("E. ID :"+cursor.getString(2)+"\n");
                stringbBuffer.append("Address: "+cursor.getString(3)+"\n");
                stringbBuffer.append("Date of Joining: "+cursor.getString(4)+"\n\n");

            }
            showData("ResultSet",stringbBuffer.toString());

        }

        if(view.getId()==R.id.UpdateEmployee)
        {
             Boolean isupdated =  mysql.updatedata(name,type,id,address,date);

               if(isupdated==true)
               {
                   Toast.makeText(getApplicationContext(),"Updated Successfully!",Toast.LENGTH_LONG).show();
               }else{
                   Toast.makeText(getApplicationContext(),"unsuccessfull",Toast.LENGTH_LONG).show();
               }



        }


        if(view.getId()==R.id.DeleteEmployee)
        {
            int isdeleted =  mysql.deletedata(id);

            if(isdeleted>0)
            {
                Toast.makeText(getApplicationContext(),"deleted Successfully!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"unsuccessfull deletion",Toast.LENGTH_LONG).show();
            }



        }

        if(view.getId()==R.id.SignUp)
        {
            //Boolean result=true;
            Boolean result=mysql.findPassword(name,id);

            if(result==true)
            {

                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name",NameEditText.getText().toString());
                startActivity(intent);



            }else{

                Toast.makeText(getApplicationContext(),"username or password did not match",Toast.LENGTH_LONG).show();
            }




        }









    }

    public void showData(String title,String message)
    {

        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }




}
