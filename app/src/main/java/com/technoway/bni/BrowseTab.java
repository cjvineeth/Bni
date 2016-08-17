package com.technoway.bni;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ansa on 7/13/2016.
 */
public class BrowseTab extends Fragment {

    private View view;
    private List<Movie> movieList;
    private RecyclerView recyclerView;
    private BrowserAdapter mAdapter;
    private DBAdapter adapter;
    AlertDialog.Builder bottomDialog;
    FloatingActionButton actionButton;
    Movie movie;
    private LayoutInflater mInflater;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.browsetab, container, false);



        adapter = ((MainActivity) getActivity()).adapter;
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        actionButton= (FloatingActionButton) view.findViewById(R.id.fab);



        movieList= new ArrayList<>();




        adapter.open();

        Cursor cursor=adapter.getallItemsData();

        if(cursor.moveToFirst()){

            do{


                movie=new Movie();
                movie.setTitle(cursor.getString(1));
                movie.setGenre("");
                movie.setYear("");
                movieList.add(movie);
               // Toast.makeText(getActivity(),cursor.getString(1),Toast.LENGTH_LONG).show();


            }
            while (cursor.moveToNext());


        }




        adapter.close();


        mAdapter = new BrowserAdapter(movieList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                
                View dialogview;
                Dialog dialog;
                //Toast.makeText(getActivity(),"hello",Toast.LENGTH_LONG).show();

                bottomDialog=new AlertDialog.Builder(getActivity());

                bottomDialog.setTitle("Send Details");
                //bottomDialog.setContent("Daya");
                mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialogview=mInflater.inflate(R.layout.emaill,null,false);
                final EditText input= (EditText) dialogview.findViewById(R.id.emilText);
                bottomDialog.setView(dialogview);


                bottomDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArrayList<String> list=new ArrayList<String>();



                        StringBuffer stringBuffer=new StringBuffer();


                        adapter.open();



                        Cursor cursor=adapter.getallItemsData();

                        if (cursor.moveToFirst()){


                            do{





                                //stringBuffer.append(" \n "+" "+cursor.getString(1));

                                list.add(cursor.getString(1));









                            }
                            while (cursor.moveToNext());

                        }













                        adapter.close();









                       /* BackgroundMail.newBuilder(getActivity())
                                .withUsername("cjvineeth@gmail.com")
                                .withPassword("9496722732")
                                .withMailto(input.getText().toString())
                                .withSubject("Contacts")
                                .withBody(stringBuffer.toString())
                                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(getActivity(),"mail send",Toast.LENGTH_LONG).show();




                                    }
                                })
                                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                    @Override
                                    public void onFail() {
                                        Toast.makeText(getActivity(), "mail send failed", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .send();*/







                        if(!input.getText().toString().equals("")&& list.isEmpty()==false) {

                            if(isValidEmail(input.getText().toString())==true) {

                                new SendMail(list, input.getText().toString()).execute();
                            }

                            else{
                                input.setError("Not a valid mail adress");


                                Toast.makeText(getActivity(),"Not a valid mail adress",Toast.LENGTH_LONG).show();
                            }
                        }

                        else{

                        }


                    }
                });


                bottomDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                dialog=bottomDialog.create();


                dialog.show();




                
            }
        });





        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = movieList.get(position);
                Toast.makeText(getActivity(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //prepareMovieData();







        return  view;
    }



    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }





  class SendMail extends AsyncTask<Void,Void,Void>{


      ProgressDialog progressDialog;

      String string;
      private JSONObject object;
      ArrayList<String> list;

      String tomail;


      SendMail(ArrayList<String> list,String tomail){


          this.list=list;
          this.tomail=tomail;

      }


      @Override
      protected void onPreExecute() {
          super.onPreExecute();


          progressDialog =new ProgressDialog(getActivity());

          progressDialog.setMessage("Sending Mail......");

          progressDialog.show();
      }

      @Override
      protected Void doInBackground(Void... params) {


          try {

          ServiceHandler serviceHandler=new ServiceHandler();

        /*  ArrayList<String> list = new ArrayList<String>();
          list.add("john");
          list.add("mat");
          list.add("jason");
          list.add("matthew");*/


      object=new JSONObject();



              object.put("Messages",new JSONArray(list));
              object.put("MailID",tomail);



              string=serviceHandler.PostBody("http://54.251.37.65/aidcapi/api/data/transfer",object);




          } catch (JSONException e) {
              e.printStackTrace();
          }












          return null;




      }


      @Override
      protected void onPostExecute(Void aVoid) {
          super.onPostExecute(aVoid);



          //AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
          //builder.setMessage(string);
         // Dialog dialog=builder.create();
          //dialog.show();

          try {
              JSONObject result_obj=new JSONObject(string);




              if (result_obj.getString("ReturnValue").equals("true")){




                  Toast.makeText(getActivity(), "mail send", Toast.LENGTH_LONG).show();
                  adapter.open();


                  adapter.deleteAlltype();


                  adapter.close();

                  movieList.clear();
                  mAdapter.notifyDataSetChanged();

                  progressDialog.dismiss();


              }


              else{
                  progressDialog.dismiss();

                  Toast.makeText(getActivity(),result_obj.getString("ErrorMessage"), Toast.LENGTH_LONG).show();
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }


      }
  }


}
