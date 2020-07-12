package com.example.androidshaper.book_sell.sampledata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidshaper.book_sell.R;

import java.util.List;
import java.util.Locale;

public class BuyActivityNew extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, LocationListener, View.OnClickListener {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    LocationManager locationManager;
    EditText editTextName,editTextPhone,editTextRegion,editTextCity,editTextArea,editTextAdress,editTextEmail;
    Button buttonSave;
    TextView textViewDelete;
    RadioGroup radioGroupPlace;
    RadioButton radioDelivaryPlace;
    Switch aSwitchShipping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy2);
        toolbar = findViewById(R.id.toolbarShipingAdress);
        //
        editTextName=findViewById(R.id.textSpName);
        editTextPhone=findViewById(R.id.textSpPhone);
        editTextRegion=findViewById(R.id.textSpRegion);
        editTextCity=findViewById(R.id.textSpCity);
        editTextArea=findViewById(R.id.textSpArea);
        editTextAdress=findViewById(R.id.textSpAdress);
        editTextEmail=findViewById(R.id.textSpEmail);
        textViewDelete=findViewById(R.id.textDeletShipingAdress);
        radioGroupPlace=findViewById(R.id.radioShipingGroup);
        aSwitchShipping=findViewById(R.id.switchShiping);




        buttonSave=findViewById(R.id.buttonSave);
        //

        buttonSave.setOnClickListener(this);
        textViewDelete.setOnClickListener(this);
        //

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SetSaveItem();


    }

    private void SetSaveItem() {
        SharedPreferences sharedPreferences=getSharedPreferences("ShipingAdress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        if(sharedPreferences.contains("namekey")&&sharedPreferences.contains("phonekey")&&sharedPreferences.contains("adresskey")&&sharedPreferences.contains("delivaryplacecode"))
        {
            editTextName.setText(sharedPreferences.getString("namekey",""));
            editTextPhone.setText(sharedPreferences.getString("phonekey",""));
            editTextEmail.setText(sharedPreferences.getString("emailkey",""));
            editTextRegion.setText(sharedPreferences.getString("region",""));
            editTextCity.setText(sharedPreferences.getString("city",""));
            editTextArea.setText(sharedPreferences.getString("area",""));
            editTextAdress.setText(sharedPreferences.getString("adresskey",""));
            int delivaryPlaceCode=sharedPreferences.getInt("delivaryplacecode",-1);
            String setDefult=sharedPreferences.getString("setdefult","");
            if (setDefult.equals("on"))
            {
                aSwitchShipping.setChecked(true);
            }
            //Toast.makeText(this,String.valueOf(delivaryPlaceCode),Toast.LENGTH_SHORT).show();

            if(delivaryPlaceCode==0)
            {
                radioDelivaryPlace=findViewById(R.id.radioOffice);
                radioDelivaryPlace.setChecked(true);

            }
            if(delivaryPlaceCode==1)
            {
                radioDelivaryPlace=findViewById(R.id.radioHome);
                radioDelivaryPlace.setChecked(true);

            }






        }
        if(sharedPreferences.contains("phonekey")||sharedPreferences.contains("emailkey"))
        {
            editTextPhone.setText(sharedPreferences.getString("phonekey",""));
            editTextEmail.setText(sharedPreferences.getString("emailkey",""));

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shiping_address_autometicbutton, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addressfinder) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loooding...");
            progressDialog.show();
            UsePermissions();


        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void UsePermissions() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {

            LocationFinding();
            progressDialog.dismiss();
        } else {
            EasyPermissions.requestPermissions(this, "Please Allow Permission Camera and Storage", 1, perms);
            progressDialog.dismiss();


        }
    }

    private void LocationFinding() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            UsePermissions();
            return;
        }
        Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (locationNetwork!=null) {

            onPermissionCheck(locationNetwork);
        }
        else if (locationGps!=null) {

            onPermissionCheck(locationGps);
        }
        else {
           Toast.makeText(this,"Network & Gps is Null",Toast.LENGTH_LONG).show();
        }


    }
    private void onPermissionCheck(Location location) {
        if (location == null) {
            Toast.makeText(this, "location not found", Toast.LENGTH_SHORT).show();

        } else {
            onLocationChanged(location);


        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        LocationFinding();

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms))
        {

            new AppSettingsDialog.Builder(this).build().show();;
        }

    }
    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        String latt = Double.toString(lat);
        String lang = Double.toString(lng);
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList=geocoder.getFromLocation(lat,lng,1);
            if(addressList!=null && addressList.size()>0)
            {
                String addressHolder = "";
                String addresslocality="";
                String addressPostal="";
                String addressFetureName="";
                String addressSubArea="";
                String addressAdarea="";
                //
                addressPostal=addressList.get(0).getPostalCode();
                //

                addresslocality=addressList.get(0).getAddressLine(0);

                //
                addressFetureName=addressList.get(0).getFeatureName().toString();
                addressSubArea=addressList.get(0).getSubAdminArea().toString();
                addressAdarea=addressList.get(0).getAdminArea().toString();



//                Toast.makeText(this, addressList.get(0).toString(), Toast.LENGTH_SHORT).show();
//                textView.setText(addressList.get(0).toString());
                if (addressPostal==null)
                {
                    editTextRegion.setText(addressAdarea);
                    editTextCity.setText(addressSubArea);
                    if(addressFetureName.equals(addressList.get(0).getThoroughfare()))
                    {
                        editTextArea.setText(addressFetureName+".");
                        editTextAdress.setText(addressFetureName+", "+addressSubArea+", "+addressAdarea+".");

                    }
                    else {
                        addressHolder=addressFetureName+" "+addressList.get(0).getThoroughfare();
                        editTextArea.setText(addressHolder+".");
                        editTextAdress.setText(addressHolder+", "+addressSubArea+", "+addressAdarea+".");
                    }



                }
                else {
                    editTextRegion.setText(addressAdarea+".");
                    editTextCity.setText(addressSubArea+".");
                    editTextArea.setText(addressFetureName+" "+addressList.get(0).getThoroughfare()+".");
                    editTextAdress.setText(addresslocality+".");



                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, latt + lang, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.buttonSave)
        {
            String name=editTextName.getText().toString();
            String phone=editTextPhone.getText().toString();
            String adress=editTextAdress.getText().toString();
            String email=editTextEmail.getText().toString();
            String region=editTextRegion.getText().toString();
            String city=editTextCity.getText().toString();
            String area=editTextArea.getText().toString();
            if (name.isEmpty() || phone.isEmpty() || adress.isEmpty())
            {
                Toast.makeText(this, "Please Fill The all field", Toast.LENGTH_SHORT).show();

            }
            else {
                int selectedId=radioGroupPlace.getCheckedRadioButtonId();
                if (selectedId==-1)
                {
                    Toast.makeText(this,"Please selected Place",Toast.LENGTH_SHORT).show();

                }
                else {
                    radioDelivaryPlace=findViewById(selectedId);
                    int radioId=radioGroupPlace.indexOfChild(radioDelivaryPlace);
                    //Toast.makeText(this,String.valueOf(radioId),Toast.LENGTH_SHORT).show();
                    String delivaryPlace=radioDelivaryPlace.getText().toString();

                    //Toast.makeText(this,radioId,Toast.LENGTH_SHORT).show();
                    //
                    String switchStatus;
                    if (aSwitchShipping.isChecked()){
                         switchStatus=aSwitchShipping.getTextOn().toString();
                    }
                    else
                    {

                         switchStatus=aSwitchShipping.getTextOff().toString();
                    }
                    //Toast.makeText(this,switchStatus,Toast.LENGTH_SHORT).show();

                    //
                    SharedPreferences sharedPreferences=getSharedPreferences("ShipingAdress", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("namekey",name);
                    editor.putString("phonekey",phone);
                    editor.putString("adresskey",adress);
                    editor.putString("emailkey",email);
                    editor.putString("region",region);
                    editor.putString("city",city);
                    editor.putString("area",area);
                    editor.putString("delivaryplace",delivaryPlace);
                    editor.putString("setdefult",switchStatus);
                    editor.putInt("delivaryplacecode",radioId);
                    editor.commit();
                    Toast.makeText(this,"Data Stored Succesful",Toast.LENGTH_SHORT).show();
                   finish();


                }




            }

        }
        else if (view.getId()==R.id.textDeletShipingAdress)
        {
            SharedPreferences sharedPreferences=getSharedPreferences("ShipingAdress", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            if(sharedPreferences.contains("namekey")&&sharedPreferences.contains("phonekey")&&sharedPreferences.contains("adresskey")&&sharedPreferences.contains("delivaryplacecode"))
            {
                sharedPreferences.edit().clear().commit();

                finish();
                Toast.makeText(this,"Data Delete Succesful",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"No Item save",Toast.LENGTH_SHORT).show();

            }




        }

    }
}