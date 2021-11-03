package com.paysky.momogrow.views.register

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.paysky.momogrow.databinding.FragmentLocationBinding
import kotlinx.android.synthetic.main.fragment_location.view.*
import java.lang.Exception

import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.paysky.momogrow.BuildConfig.MAPS_API_KEY
import java.util.*
import com.google.android.libraries.places.api.net.PlacesClient
import timber.log.Timber
import android.R
import android.graphics.Color
import android.widget.ImageView


// locationRequest properties init
private const val INTERVAL: Long = 500 * 900000
private const val FASTEST_INTERVAL: Long = 500 * 900000

class LocationFragment : Fragment(), OnMapReadyCallback {

    private var search: Boolean = false
    private lateinit var locationRequest: LocationRequest
    private var requestingLocationUpdates: Boolean = false
    private val REQUEST_CHECK_SETTINGS: Int = 2
    private val REQUEST_CODE_LOCATION_PERMISSION: Int = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationCallback: LocationCallback = object : LocationCallback() {
        @SuppressLint("TimberArgCount")
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            locationResult.lastLocation

            Log.i(
                TAG,
                "Current Place: " + locationResult.lastLocation.latitude.toString() + ", " +
                        locationResult.lastLocation.longitude.toString()
            )

            val selectedLocation =
                LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
            googleMap!!.addMarker(
                MarkerOptions().position(selectedLocation).title("Marker Title")
                    .icon(
                        bitmapDescriptorFromVector(
                            requireActivity(),
                            com.paysky.momogrow.R.drawable.ic_pin
                        )
                    )
                    .snippet("Marker Description")
            )

            // For zooming automatically to the location of the marker
            val cameraPosition =
                CameraPosition.Builder().target(selectedLocation).zoom(12f).build()
            if (!search)
                googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            for (location in locationResult.locations) {
                Timber.i("New Last Location", location.toString())
            }
        }
    }

    private val TAG: String = "LocationFragment"
    private var _binding: FragmentLocationBinding? = null
    private var googleMap: GoogleMap? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//init location result
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER
        locationRequest.interval = INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL
//init location callback

        binding.btnNext.setOnClickListener(View.OnClickListener {
            findNavController().navigate(com.paysky.momogrow.R.id.action_locationFragment_to_passwordFragment)
        })

        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        view.mapView.getMapAsync(this)


        if (!Places.isInitialized()) {
            activity?.applicationContext?.let {
                Places.initialize(
                    it,
                    MAPS_API_KEY,
                    Locale.US
                )
            }
        }

        val placesClient = Places.createClient(requireActivity())

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(com.paysky.momogrow.R.id.autocomplete_fragment) as AutocompleteSupportFragment

        val fView = autocompleteFragment.view
        val etTextInput =
            fView!!.findViewById<EditText>(com.paysky.momogrow.R.id.places_autocomplete_search_input)
//        etTextInput.setTextColor(Color.WHITE)
//        etTextInput.setHintTextColor(Color.WHITE)

        etTextInput.textSize = 14.5f
        val ivClear: ImageView =
            fView.findViewById(com.paysky.momogrow.R.id.places_autocomplete_search_button)

        ivClear.setImageResource(com.paysky.momogrow.R.drawable.ic_search_et)
        autocompleteFragment.setHint(getString(com.paysky.momogrow.R.string.location_hint))

        // Specify the types of place data to return.
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )

        requestPermission()
        // Set up a PlaceSelectionListener to handle the response.

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                search = true
                Log.i(
                    TAG,
                    "MyPlace: " + place.name + ", " + place.id
                )
                // For dropping a marker at a point on the Map
                val selectedLocation = place.latLng
                googleMap!!.addMarker(
                    MarkerOptions().position(selectedLocation!!).title("Marker Title")
                        .icon(
                            bitmapDescriptorFromVector(
                                requireActivity(),
                                com.paysky.momogrow.R.drawable.ic_pin
                            )
                        )
                        .snippet("Marker Description")
                )

                // For zooming automatically to the location of the marker
                val cameraPosition =
                    CameraPosition.Builder().target(selectedLocation).zoom(12f).build()
                googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            }

            override fun onError(status: Status) {
                Log.i(
                    TAG,
                    "An error occurred: $status"
                )
            }
        })

        return view
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

//         For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        googleMap!!.isMyLocationEnabled = true

    }

    @SuppressLint("MissingPermission")
    private fun requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED ||

            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // show Ui dialog,explain why need this permission
                Toast.makeText(requireActivity(), "Show Ui Dialog", Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQUEST_CODE_LOCATION_PERMISSION
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQUEST_CODE_LOCATION_PERMISSION
                )
            }
        } else {
            // Permission has already been granted

            getCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_LOCATION_PERMISSION -> {

                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    if (ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        getCurrentLocation()
                    }
                } else {

                    Toast.makeText(
                        requireActivity(),
                        "Permission is needed to detect you current location automatically",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    @SuppressLint("MissingPermission", "TimberArgCount")
    private fun getCurrentLocation() {

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val task = LocationServices.getSettingsClient(requireActivity())
            .checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            requestingLocationUpdates = true
            startLocationUpdates()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    Toast.makeText(
                        requireActivity(), "Failed to show", Toast.LENGTH_LONG
                    ).show()
                    exception.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        if (requestingLocationUpdates) startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}