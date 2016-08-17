package com.technoway.bni;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PointF;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TooManyListenersException;

import mehdi.sakout.fancybuttons.FancyButton;

public class  ScannerFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener{
    //Button scan;

    ArrayList<String> nameList;
    ArrayList<String> catagoryList;
    ArrayList<String> chaspterList;
    ArrayList<String> telephoneList;
    CustomListAdapter listAdapter;
    LinearLayout linearLayout;
    ArrayAdapter<String> adapter_new;
  View view;
    TextView textView,scaneddata;
    DBAdapter adapter;
    private QRCodeReaderView mydecoderview;
    FancyButton scan;

    String startscaning="false";
    AppCompatImageView image;
    private Animation animslideup, animslidedwn;


    String isdata="false";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmentscan, container, false);
         textView= (TextView) view.findViewById(R.id.qrcontent);
        image= (AppCompatImageView) view.findViewById(R.id.image_new);
        scan= (FancyButton) view.findViewById(R.id.btn_scan);
        animslideup = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);
        scaneddata= (TextView) view.findViewById(R.id.codecontent);

        adapter = ((MainActivity) getActivity()).adapter;
        animslidedwn= AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);

        //scan= (Button)view. findViewById(R.id.scan);
        //view= (ListView) findViewById(R.id.list_barcode);
        //linearLayout= (LinearLayout) view.findViewById(R.id.tv_footer);


        mydecoderview = (QRCodeReaderView)view. findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





               if(startscaning.equals("false")){
                mydecoderview.setVisibility(View.VISIBLE);
                   scaneddata.setVisibility(View.GONE);
                   image.setVisibility(View.GONE);
                textView.setText("Place your QR code inside Rectangle....");
                scan.setText("Stop Scanning");

                   scaneddata.setText("");
                   mydecoderview.startAnimation(animslideup);
                startscaning="true";

                }else{



                    mydecoderview.setVisibility(View.GONE);

                    textView.setText("Tap start button to start scannig....");
                   mydecoderview.startAnimation(animslidedwn);
                    scaneddata.setText("");

                   animslidedwn.setAnimationListener(new Animation.AnimationListener() {
                       @Override
                       public void onAnimationStart(Animation animation) {

                       }

                       @Override
                       public void onAnimationEnd(Animation animation) {



                           image.setVisibility(View.VISIBLE);

                       }

                       @Override
                       public void onAnimationRepeat(Animation animation) {

                       }
                   });


                    scan.setText("Start Scanning");

                    startscaning="false";



                }


            }
        });








       image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.technowavegroup.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
       });



       // scan.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View v) {






       //     }
        //});




return  view;
    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {






        textView.setText("");
        scaneddata.setVisibility(View.VISIBLE);
        scaneddata.setText(text);
        mydecoderview.setVisibility(View.GONE);
        scan.setText("Start Scanning");
        startscaning="false";
        adapter.open();

        Cursor cursor=adapter.getallItemsData();

        if(cursor.moveToFirst()){

            do{


              if(cursor.getString(1).equals(text)){
                  isdata="true";
              }
               //Toast.makeText(getActivity(),cursor.getString(1),Toast.LENGTH_LONG).show();


            }
            while (cursor.moveToNext());


        }







        if(isdata.equals("false")) {


            long id = adapter.addItemData(text);

            Toast.makeText(getActivity(),"New Bacode Found",Toast.LENGTH_LONG).show();
        }
else{

          isdata="false";

        }



        adapter.close();


        //Toast.makeText(getActivity(),"Inserted",Toast.LENGTH_LONG).show();




        //String str = "";
       // String[] myList =text.split("\\|");

        //adapter.open();

        //long id=adapter.addItemData(myList[0], myList[1], myList[2], myList[3]);
       // adapter.close();




    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }
}
