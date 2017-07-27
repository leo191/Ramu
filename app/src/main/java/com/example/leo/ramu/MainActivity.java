package com.example.leo.ramu;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;

    Button visitors,residents,check;
    EditText id_car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        visitors = (Button)findViewById(R.id.visitors_btn);
        residents = (Button)findViewById(R.id.resident_btn);
        check = (Button)findViewById(R.id.check_btn);
        id_car = (EditText)findViewById(R.id.carNo_id);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        visitors.setOnClickListener(this);
        residents.setOnClickListener(this);
        check.setOnClickListener(this);


    }

    boolean togman;
    boolean toggle;
    boolean up=true;

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.visitors_btn:
                togman=false;
                id_car.setEnabled(true);
                id_car.setHint("Enter Car No");
                check.setVisibility(View.VISIBLE);
                residents.setVisibility(View.INVISIBLE);
                visitors.setVisibility(View.INVISIBLE);
                break;
            case R.id.resident_btn:
                togman=true;
                id_car.setEnabled(true);
                id_car.setHint("Enter ID No");
                check.setVisibility(View.VISIBLE);
                residents.setVisibility(View.INVISIBLE);
                visitors.setVisibility(View.INVISIBLE);
                break;
            case R.id.check_btn:
                if(togman)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    if(!id_car.getText().toString().trim().equals(""))
                    {
                        toggle=true;
                        final String id = id_car.getText().toString();
                        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Residents");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (final DataSnapshot dp : dataSnapshot.getChildren()
                                            ) {
                                        progressBar.setVisibility(View.INVISIBLE);

                                        if (dp.child("id").getValue().equals(id)) {
                                            toggle = false;

                                            final Residents usr = dp.getValue(Residents.class);
                                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                                            View content = inflater.inflate(R.layout.few_det,null);
                                            TextView name =(TextView)content.findViewById(R.id.name_few);
                                            TextView phone =(TextView)content.findViewById(R.id.phone_few);
                                            TextView id =(TextView)content.findViewById(R.id.id_few);
                                            TextView intime =(TextView)content.findViewById(R.id.intime_few);
                                            name.setText(usr.name);
                                            id.setText(usr.id);
                                            phone.setText(usr.phoneNo);
                                            intime.setText(usr.inTime);


                                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setView(content);
                                            builder.setMessage("Do you want checkout recident?");
                                            builder.setTitle("Exists");
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    DataService.CheckOut(true,dp.getKey(),usr.inMilis);
                                                    DataService.fetchAllResibyid(usr.id, new GetCallbacks() {
                                                        @Override
                                                        public void getOneById(String field) {

                                                        }

                                                        @Override
                                                        public void getVisitById(Visiters vis) {

                                                        }

                                                        @Override
                                                        public void getResiById(Residents res) {
                                                            if(res!=null)
                                                            {

                                                                showwalker(usr);
                                                            }
                                                        }


                                                    });


                                                }
                                            });
                                            builder.setNegativeButton("No", null);
                                            AlertDialog alert = builder.create();
                                            alert.show();

                                            break;
                                        }


                                    }
                                    if(toggle){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Do you want to add?");
                                        builder.setTitle("Nothing Here");
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                addwalker(id);
                                                //addbtn.setBackgroundColor(Color.BLUE);
                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }

                                    databaseReference.removeEventListener(this);




                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        //progressBar.setVisibility(View.VISIBLE);

                        //Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();







                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter ID",Toast.LENGTH_SHORT).show();}

                }
                else
                {

                    progressBar.setVisibility(View.VISIBLE);
                    if(!id_car.getText().toString().trim().equals(""))
                    {
                        toggle=true;
                        final String id = id_car.getText().toString();
                        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Visitors");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if(toggle==true) {
//                                    toggle = false;
                                    for (final DataSnapshot dp : dataSnapshot.getChildren()
                                            ) {
                                        progressBar.setVisibility(View.INVISIBLE);

                                        if (dp.child("carNo").getValue().equals(id)) {
                                            toggle=false;
                                            final Visiters usr = dp.getValue(Visiters.class);
                                            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                                            View content = inflater.inflate(R.layout.few_det_visi,null);
                                            TextView name =(TextView)content.findViewById(R.id.name_few);
                                            TextView phone =(TextView)content.findViewById(R.id.phone_few);
                                            TextView carNo =(TextView)content.findViewById(R.id.carNo_few);
                                            TextView intime =(TextView)content.findViewById(R.id.intime_few);
                                            name.setText(usr.name);
                                            carNo.setText(usr.carNo);
                                            phone.setText(usr.phoneNo);
                                            intime.setText(usr.inTime);


                                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setView(content);
                                            builder.setMessage("Do you want checkout Visitor?");
                                            builder.setTitle("Exists");
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    DataService.CheckOut(false,dp.getKey(),usr.inMilis);


                                                    DataService.fetchAllVisibyid(usr.carNo, new GetCallbacks() {
                                                        @Override
                                                        public void getOneById(String field) {

                                                        }

                                                        @Override
                                                        public void getVisitById(Visiters vis) {
                                                            if(vis!=null)
                                                            {
                                                                showVisitor(vis);
                                                            }
                                                        }

                                                        @Override
                                                        public void getResiById(Residents res) {

                                                        }


                                                    });


                                                }
                                            });
                                            builder.setNegativeButton("No", null);
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                            break;
                                        }


                                    }
                                    if(toggle){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Do you want to add?");
                                        builder.setTitle("Nothing Here");
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                addVisitor(id);
                                                //addbtn.setBackgroundColor(Color.BLUE);
                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }





                                //}
                                databaseReference.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        //progressBar.setVisibility(View.VISIBLE);

                        //Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();







                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter Car No",Toast.LENGTH_SHORT).show();}

                }






        }
    }







    public void showwalker(Residents usr)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View content = inflater.inflate(R.layout.details_resident,null);
        final TextView name = (TextView) content.findViewById(R.id.name_tv);
        final TextView id = (TextView)content.findViewById(R.id.id_tv);

        final TextView addr = (TextView)content.findViewById(R.id.add_tv);
        final TextView phone = (TextView)content.findViewById(R.id.phone_tv);
        final TextView intime = (TextView)content.findViewById(R.id.inTime_tv);
        final TextView outtime = (TextView)content.findViewById(R.id.outTime_tv);

        final TextView duration = (TextView)content.findViewById(R.id.duration_tv);

        name.setText(usr.name);
        id.setText(usr.id);
        phone.setText(usr.phoneNo);
        addr.setText(usr.address);
        intime.setText(usr.inTime);
        outtime.setText(usr.OutTime);
        duration.setText(usr.duration);


        alert.setTitle("Details of Resident");
        alert.setView(content);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                residents.setVisibility(View.VISIBLE);
                visitors.setVisibility(View.VISIBLE);
            }
        });
        alert.setNegativeButton("Cancel",null);
        AlertDialog dialog = alert.create();
        dialog.show();

    }
    public void showVisitor(Visiters usr)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View content = inflater.inflate(R.layout.details_visitors,null);
        final TextView name = (TextView) content.findViewById(R.id.name_tv);
        final TextView id = (TextView)content.findViewById(R.id.carNo_tv);

        final TextView addr = (TextView)content.findViewById(R.id.add_tv);
        final TextView phone = (TextView)content.findViewById(R.id.phone_tv);
        final TextView intime = (TextView)content.findViewById(R.id.inTime_tv);
        final TextView outtime = (TextView)content.findViewById(R.id.outTime_tv);

        final TextView duration = (TextView)content.findViewById(R.id.duration_tv);

        name.setText(usr.name);
        id.setText(usr.carNo);
        phone.setText(usr.phoneNo);
        addr.setText(usr.address);
        intime.setText(usr.inTime);
        outtime.setText(usr.OutTime);
        duration.setText(usr.duration);


        alert.setTitle("Details of Visitor");
        alert.setView(content);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                residents.setVisibility(View.VISIBLE);
                visitors.setVisibility(View.VISIBLE);

            }
        });
        alert.setNegativeButton("Cancel",null);
        AlertDialog dialog = alert.create();
        dialog.show();

    }


    public void addwalker(final String id)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View content = inflater.inflate(R.layout.dialog_add,null);
        final EditText nameed = (EditText) content.findViewById(R.id.nameed);
        final EditText ided = (EditText)content.findViewById(R.id.ided);
        ided.setText(id);
        final EditText addred = (EditText)content.findViewById(R.id.addred);
        final EditText phoneed = (EditText)content.findViewById(R.id.phoned);
        final EditText intimeed = (EditText)content.findViewById(R.id.intimeed);
        GregorianCalendar gc = new GregorianCalendar();
        int am_pm = gc.get(Calendar.AM_PM);

        intimeed.setText(gc.get(Calendar.HOUR)+"H:"+gc.get(Calendar.MINUTE)+"M:"+gc.get(Calendar.HOUR)+"S:"+(am_pm==0?"AM":"PM"));
        alert.setTitle("Details of walker");
        alert.setView(content);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DataService.entryResi(new Residents(nameed.getText().toString(),
                        id,
                        addred.getText().toString(),
                        phoneed.getText().toString(),
                        intimeed.getText().toString()));
                residents.setVisibility(View.VISIBLE);
                visitors.setVisibility(View.VISIBLE);
            }
        });
        alert.setNegativeButton("Cancel",null);
        AlertDialog dialog = alert.create();
        dialog.show();

    }


    public void addVisitor(final String carNo)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View content = inflater.inflate(R.layout.dialog_add_vis_car,null);
        final EditText nameed = (EditText) content.findViewById(R.id.nameed);
        final EditText carNoed = (EditText)content.findViewById(R.id.carNoed);
        carNoed.setText(carNo);
        final EditText addred = (EditText)content.findViewById(R.id.addred);
        final EditText phoneed = (EditText)content.findViewById(R.id.phoned);
        final EditText intimeed = (EditText)content.findViewById(R.id.intimeed);
        GregorianCalendar gc = new GregorianCalendar();
        int am_pm = gc.get(Calendar.AM_PM);

        intimeed.setText(gc.get(Calendar.HOUR)+"H:"+gc.get(Calendar.MINUTE)+"M:"+gc.get(Calendar.HOUR)+"S:"+(am_pm==0?"AM":"PM"));
        alert.setTitle("Details of walker");
        alert.setView(content);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DataService.entryVisi(new Visiters(nameed.getText().toString(),
                        carNo,
                        addred.getText().toString(),
                        phoneed.getText().toString(),
                        intimeed.getText().toString()));
                residents.setVisibility(View.VISIBLE);
                visitors.setVisibility(View.VISIBLE);
            }
        });
        alert.setNegativeButton("Cancel",null);
        AlertDialog dialog = alert.create();
        dialog.show();

    }
}

