package com.example.exercise701_catagentdeployer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.exercise701_catagentdeployer.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*

private const val PERMISSION_CODE_REQUEST_LOCATION = 1

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val fusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    private lateinit var mMap: GoogleMap
    private var marker:Marker? = null
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap.apply {
            setOnMapClickListener { latLng ->
                addOrMoveSelectedPositionMarker(latLng)
            }
        }

        if (hasLocationPermission()){
            getLastLocation()
        } else {
            requestPermissionWithRationaleIfNeeded()
        }

        /*// Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_CODE_REQUEST_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            PERMISSION_CODE_REQUEST_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            } else {
                requestPermissionWithRationaleIfNeeded()
            }
        }
    }

    private fun hasLocationPermission() = (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

    private fun requestPermissionWithRationaleIfNeeded() = if (
        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        showPermissionRationale { requestLocationPermission() }
    } else {
        requestLocationPermission()
    }


    private fun showPermissionRationale(positiveAction:() -> Unit){
        AlertDialog.Builder(this)
            .setTitle("Location Permission")
            .setMessage("This app will not work without knowing your current location")
            .setPositiveButton("OK"){ _ , _ -> positiveAction()}
            .create()
            .show()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        Log.d("MapsActivity", "getLastLocation() called.")
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location: Location? -> location?.let {
                val userLocation = LatLng(location.latitude, location.longitude)
            updateMapLocation(userLocation)
            addMarkerAtLocation(userLocation, "You")
            }
        }
    }

    private fun updateMapLocation(location: LatLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
    }

    private fun addMarkerAtLocation(location:LatLng, title: String, markerIcon: BitmapDescriptor? = null) =
        mMap.addMarker(
            MarkerOptions().title(title).position(location)
                .apply { markerIcon?.let { icon(markerIcon) } })



    private fun getBitmapDescriptorFromVector(@DrawableRes vectorDrawableResourceId:Int): BitmapDescriptor?{
        val bitmap = ContextCompat.getDrawable(this, vectorDrawableResourceId)?.let { vectorDrawable ->
            vectorDrawable.setBounds(0,0,vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
            //after obtaining drawable obj and setting bounds, wraps in DrawableCompat and sets Red tint
            val drawableWithTint = DrawableCompat.wrap(vectorDrawable)
            DrawableCompat.setTint(drawableWithTint, Color.RED)
            //creates bitmap
            val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            //creates canvas for bitmap
            val canvas = Canvas(bitmap)
            drawableWithTint.draw(canvas)
            //bitmap returned to be used by BitmapDescriptorFactory to build BitmapDescriptor
            //afterwards recycled
            bitmap
        }
        return BitmapDescriptorFactory.fromBitmap(bitmap!!).also {
            bitmap?.recycle()
        }
    }

    private fun addOrMoveSelectedPositionMarker(latLng: LatLng){
        if (marker == null){
            marker = addMarkerAtLocation(latLng, "Deploy here", getBitmapDescriptorFromVector(R.drawable.target_icon))
        } else {
            marker?.apply {
                position = latLng
            }
        }
    }

}