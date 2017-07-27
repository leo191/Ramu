package com.example.leo.ramu;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;





public class DataService {




    public static void entryResi(Residents usr)
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Residents");
        String key = databaseReference.push().getKey();
        usr.inMilis = System.currentTimeMillis();
        databaseReference.child(key).setValue(usr);


    }

    public static void entryVisi(Visiters usr)
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Visitors");
        String key = databaseReference.push().getKey();
        usr.inMilis = System.currentTimeMillis();
        databaseReference.child(key).setValue(usr);


    }





    public static void fetchAllResibyid(final String id, final GetCallbacks callback) {


            new getWalker(id,callback).execute();


    }
    public static void fetchAllVisibyid(final String carNo, final GetCallbacks callback) {


        new getVisi(carNo,callback).execute();


    }




    public static void CheckOut(boolean togman, String id_car,long start) {
        String path;
        if(togman) {
           path = "Residents/" + id_car;
        }else{
            path = "Visitors/" + id_car;

        }
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(path);
        long duration = System.currentTimeMillis()-start;

        int seconds = (int) (duration / 1000) % 60 ;
        int minutes = (int) ((duration / (1000*60)) % 60);
        int hours   = (int) ((duration / (1000*60*60)) % 24);


        databaseReference.child("duration").setValue(String.valueOf(hours)+"H:"
        +String.valueOf(minutes)+"M:"+String.valueOf(seconds)+"S");
        GregorianCalendar gc = new GregorianCalendar();
        int am_pm = gc.get(Calendar.AM_PM);
        databaseReference.child("OutTime").setValue(gc.get(Calendar.HOUR)+"H:"+gc.get(Calendar.MINUTE)+"M:"+ gc.get(Calendar.SECOND)+"S"+(am_pm==1?"PM":"AM"));


    }






    public static  class getWalker extends AsyncTask<Void,Void,Void>
    {
            String id;
            GetCallbacks callback;
            static Residents usr;
            public getWalker(String id,GetCallbacks callback)
            {
                this.id=id;
                this.callback = callback;
            }



        @Override
        protected Void doInBackground(Void... voids) {

            final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Residents");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dp: dataSnapshot.getChildren()
                         ) {
                            usr = dp.getValue(Residents.class);
                            if(usr.id.equals(id))
                            {

                                callback.getResiById(usr);
                                databaseReference.removeEventListener(this);
                                break;
                            }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            return null;
        }



    }




    public static  class getVisi extends AsyncTask<Void,Void,Void>
    {
        String carNo;
        GetCallbacks callback;
        static Visiters usr;
        public getVisi(String id,GetCallbacks callback)
        {
            this.carNo=id;
            this.callback = callback;
        }



        @Override
        protected Void doInBackground(Void... voids) {

            final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Visitors");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dp: dataSnapshot.getChildren()
                            ) {
                        usr = dp.getValue(Visiters.class);
                        if(usr.carNo.equals(carNo))
                        {
                            callback.getVisitById(usr);
                            databaseReference.removeEventListener(this);

                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            return null;
        }



    }

}
