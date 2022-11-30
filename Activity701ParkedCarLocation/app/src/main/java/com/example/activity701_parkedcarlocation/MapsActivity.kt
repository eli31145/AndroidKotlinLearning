package com.example.activity701_parkedcarlocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.activity701_parkedcarlocation.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*

private const val PERMISSION_LOCATION_FINE_CODE = 1

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val fusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private var marker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        restoreLocation()?.let { userLocation ->
            var carMarker = createMarker(userLocation, "Your car", getBitmapDescriptorFromVectorAsset(R.drawable.icon_car))
            var userMarker = createMarker(userLocation, "You")
        }
        mMap = googleMap.apply {
            setOnMapClickListener { latLng ->
                addOrMoveSelectionMarker(latLng)
            }
        }

        if (hasPermission()) {
            getLastLocation()
        } else {
            askPermissionWithDialogIfNecessary()
        }

    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        Log.d("MapActivity", "getLastLocation() called")
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location: Location? -> location?.let {
                val userLocation = LatLng(location.latitude, location.longitude)
            updateMapLocation(userLocation)
            createMarker(userLocation, "You")
            }
        }
    }

    private fun updateMapLocation(location: LatLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }

    private fun createMarker(location: LatLng, title: String, markerIcon:BitmapDescriptor? = null) =
        mMap.addMarker(MarkerOptions().position(location).title(title).apply {
            markerIcon?.let { icon(markerIcon) }
        })


    private fun hasPermission() = ContextCompat.checkSelfPermission(
        this.applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun askPermissionWithDialogIfNecessary() =
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            showPermissionDialog { requestAllPermissions() }
        } else {
            requestAllPermissions()
        }

    private fun showPermissionDialog(positiveAction: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Location Required for Service")
            .setMessage("Unable to locate or save car location if no permission is given.")
            .setPositiveButton("OK") { _, _ -> positiveAction() }
            .create()
            .show()
    }

    private fun requestAllPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_LOCATION_FINE_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_LOCATION_FINE_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                askPermissionWithDialogIfNecessary()
            }
        }
    }

    private fun getBitmapDescriptorFromVectorAsset(@DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor?{
        val bitmap = ContextCompat.getDrawable(this, vectorDrawableResourceId)?.let { vectorDrawable ->
            vectorDrawable.setBounds(0 ,0, vectorDrawable.intrinsicHeight, vectorDrawable.intrinsicWidth)

            val drawableWithTint = DrawableCompat.wrap(vectorDrawable)
            DrawableCompat.setTint(drawableWithTint, Color.RED)

            val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)
            drawableWithTint.draw(canvas)
            bitmap
        }
        return BitmapDescriptorFactory.fromBitmap(bitmap!!).also {
            bitmap.recycle()
        }
    }

    private fun addOrMoveSelectionMarker(latLng: LatLng){
        if (marker == null){
            marker = createMarker(latLng, "I'm parked here", getBitmapDescriptorFromVectorAsset(R.drawable.icon_car))
        } else {
            marker?.apply {
                position = latLng
            }
        }
    }

    //storing car saved location to retrieve on app open after kill
    private fun saveLocation(latLng: LatLng) =
        getPreferences(MODE_PRIVATE)?.edit()?.apply{
            putString("latitude", latLng.latitude.toString())
            putString("longitude", latLng.longitude.toString())
            apply()
        }

    private fun restoreLocation() = getPreferences(MODE_PRIVATE)?.let { sharedPreferences ->
        val latitude = sharedPreferences.getString("latitude", null)?.toDoubleOrNull() ?: return null
        val longitude = sharedPreferences.getString("longitude", null)?.toDoubleOrNull() ?: return null
        LatLng(latitude, longitude)
    }


}