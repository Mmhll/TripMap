package com.example.tripmaps.ui.carousel

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tripmaps.PlacesArray
import com.example.tripmaps.R
import com.example.tripmaps.databinding.FragmentCarouselBinding
import com.example.tripmaps.ui.map.MapFragment
import com.google.android.material.tabs.TabLayoutMediator

class CarouselFragment : Fragment() {
    private lateinit var binding : FragmentCarouselBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarouselBinding.inflate(inflater)
        var id = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE).getInt("ID", 0)

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, MapFragment())
                .commit()
        }


        binding.viewPager.adapter = CarouselAdapter(context = requireContext(), data = PlacesArray().places[id].uris)
        TabLayoutMediator(binding.tabs, binding.viewPager){_,_->}.attach()

        return binding.root
    }
}