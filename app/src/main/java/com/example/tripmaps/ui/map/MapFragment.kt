package com.example.tripmaps.ui.map

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tripmaps.PlacesArray
import com.example.tripmaps.R
import com.example.tripmaps.databinding.FragmentMapBinding
import com.example.tripmaps.ui.carousel.CarouselFragment
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


class MapFragment : Fragment() {

    private lateinit var binding : FragmentMapBinding

    val MY_USER_AGENT = "MyOwnUserAgent/1.0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        Configuration.getInstance().userAgentValue = MY_USER_AGENT
        binding = FragmentMapBinding.inflate(inflater)
        binding.map.setMultiTouchControls(true)
        var pointsArray = ArrayList<GeoPoint>()
        for (i in 0 until PlacesArray().places.size){
            pointsArray.add(GeoPoint(PlacesArray().places[i].latitude, PlacesArray().places[i].longtitude))
        }
        var roadManager = OSRMRoadManager(requireContext(), MY_USER_AGENT)
        var road = roadManager.getRoad(pointsArray)
        binding.map.controller.setZoom(15.00)
        binding.map.controller.setCenter(pointsArray[0])
        var markers = ArrayList<Marker>()
        for (i in 0 until pointsArray.size){
            var marker = Marker(binding.map)
            marker.position = pointsArray[i]
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.setOnMarkerClickListener(Marker.OnMarkerClickListener
            { _, _ ->
                var dialog = AlertDialog.Builder(requireContext())
                    .setTitle(PlacesArray().places[i].title)
                    .setMessage(PlacesArray().places[i].describe)
                    .setPositiveButton("Просмотр")
                    { p0, p1 ->
                        var prefs = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE)
                        prefs.edit().putInt("ID", PlacesArray().places[i].id).apply()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.container, CarouselFragment())
                            .commit()
                    }
                    .create()
                    .show()
                return@OnMarkerClickListener true
            })
            markers.add(marker)
        }
        for (i in 0 until markers.size){
            markers[i].icon = writeOnBitmap(R.drawable.location, (i+1).toString())
        }
        for (i in markers){
            binding.map.overlays.add(i)
        }
        binding.map.overlays.add(RoadManager.buildRoadOverlay(road))
        return binding.root
    }

    fun writeOnBitmap(drawableId : Int, text : String) : BitmapDrawable{
        var bm = BitmapFactory.decodeResource(resources, drawableId).copy(Bitmap.Config.ARGB_8888, true)

        var paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        paint.textSize = 50f

        var canvas = Canvas(bm)
        canvas.drawText(text, (bm.width/2-5).toFloat(), (bm.height/2-5).toFloat(), paint)

        return BitmapDrawable(resources, bm)
    }
}
