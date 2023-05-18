package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.clevertap.android.sdk.CleverTapAPI;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import com.clevertap.android.sdk.CTInboxActivity;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.CleverTapInstanceConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InAppNotificationButtonListener;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.interfaces.OnInitCleverTapIDListener;

import android.util.Log;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CTInboxListener, DisplayUnitListener, InAppNotificationButtonListener {
    public Button btn;
    public Button btn2;
    public Button btn3;
    public Button btn4;
    public Button btn5;
    public Button btn6;
    public Button btn7;
    public String ctid;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        CleverTapAPI.createNotificationChannel(getApplicationContext(),"P01","First Push","This is my first push channel try",NotificationManager.IMPORTANCE_MAX,true);
        Bundle extras = null;
        CleverTapAPI.getDefaultInstance(this).pushNotificationViewedEvent(null);

        clevertapDefaultInstance.setInAppNotificationButtonListener(this);

        if (clevertapDefaultInstance != null) {
            //Set the Notification Inbox Listener
            clevertapDefaultInstance.setCTNotificationInboxListener(this);
            //Initialize the inbox and wait for callbacks on overridden methods
            clevertapDefaultInstance.initializeInbox();
        }
        clevertapDefaultInstance.getCleverTapID(new OnInitCleverTapIDListener() {
            @Override
            public void onInitCleverTapID(final String cleverTapID) {
                // Callback on main thread
                Adjust.addSessionPartnerParameter("clevertap_id", cleverTapID);
                ctid = cleverTapID;
                Log.d("Clevertap", "Biswa:" + ctid);
            }
        });
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);     //Set Log level to VERBOSE
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);
        CleverTapAPI.getDefaultInstance(this).pushEvent("Native Display pc");
//        clevertapDefaultInstance.pushEvent("OnePlus Event");
        clevertapDefaultInstance.deleteInboxMessage("1683633788_1683638204");

        btn=findViewById(R.id.p);
        btn2=findViewById(R.id.p2);
        btn3=findViewById(R.id.p3);
        btn4=findViewById(R.id.p4);
        btn5=findViewById(R.id.p5);
        btn6=findViewById(R.id.nextView);
        btn7=findViewById(R.id.p7);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // each of the below mentioned fields are optional
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", "Krishna");    // String
                profileUpdate.put("Identity", 1231221);      // String or number
                profileUpdate.put("Email", "sssssaaaa@clevertap.com"); // Email address of the user
                profileUpdate.put("Gender", "M");             // Can be either M or F
                // optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", true);        // Disable email notifications
                profileUpdate.put("MSG-push", true);          // Enable push notifications
                profileUpdate.put("MSG-sms", true);          // Disable SMS notifications
                profileUpdate.put("MSG-whatsapp", true);      // Enable WhatsApp notifications
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("Tote Bag");
                stuff.add("Jordans");
                profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings
                String[] otherStuff = {"Denims","Body Mist"};
                profileUpdate.put("MyStuff", otherStuff);                   //String Array

                clevertapDefaultInstance.onUserLogin(profileUpdate);
            }

        });


        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdjustEvent adjustEvent = new AdjustEvent("38ku53");
                adjustEvent.addPartnerParameter("clevertap_id", ctid);
                adjustEvent.addCallbackParameter("key", ctid);

                Log.d("clevertap", "onClick: inside adjust" + ctid);
                Adjust.trackEvent(adjustEvent);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Casio Chronograph Watch");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
                prodViewedAction.put("Date", new Date());

                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Nike Airforce");
                prodViewedAction.put("Category", "Mens Shoes");
                prodViewedAction.put("Price", 199.99);
                prodViewedAction.put("Date", new Date());
                clevertapDefaultInstance.getDefaultInstance(getApplicationContext()).incrementValue("score",10);
                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                chargeDetails.put("Amount", 300);
                chargeDetails.put("Payment Mode", "Credit card");
                chargeDetails.put("Charged ID", 24052013);

                HashMap<String, Object> item1 = new HashMap<String, Object>();
                item1.put("Product category", "books");
                item1.put("Book name", "The Millionaire next door");
                item1.put("Quantity", 1);

                HashMap<String, Object> item2 = new HashMap<String, Object>();
                item2.put("Product category", "books");
                item2.put("Book name", "Achieving inner zen");
                item2.put("Quantity", 1);

                HashMap<String, Object> item3 = new HashMap<String, Object>();
                item3.put("Product category", "books");
                item3.put("Book name", "Chuck it, let's do it");
                item3.put("Quantity", 5);

                ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                items.add(item1);
                items.add(item2);
                items.add(item3);

                CleverTapAPI cleverTap;
                clevertapDefaultInstance.pushChargedEvent(chargeDetails, items);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertapDefaultInstance.showAppInbox();//Opens Activity with default style configs
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onInAppButtonClick(HashMap<String, String> hashMap) {
        if(hashMap != null && hashMap.containsKey("phone")){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hashMap.get("phone")));
            startActivity(intent);
        }
    }
    @Override
    public void inboxDidInitialize() {

    }

    @Override
    public void inboxMessagesDidUpdate() {

    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        for (int i = 0; i <units.size() ; i++) {
            CleverTapDisplayUnit unit = units.get(i);
            prepareDisplayView(unit);
            Log.d("Clevertap", "Native display" + units);
        }
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {
        //TODO: code for UI
    }
}