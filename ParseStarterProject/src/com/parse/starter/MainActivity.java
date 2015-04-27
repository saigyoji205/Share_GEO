package com.parse.starter;



import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;



public class MainActivity extends FragmentActivity
{
	private GoogleMap map;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		map =((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

		LatLng coordinate =new LatLng(35.706671,139.759914);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate,16));
	}
}
