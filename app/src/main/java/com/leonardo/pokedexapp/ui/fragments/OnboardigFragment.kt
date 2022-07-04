package com.leonardo.pokedexapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.databinding.FragmentOnboardigBinding
import com.leonardo.pokedexapp.ui.Onboarding.OnboardingAdapter
import com.leonardo.pokedexapp.ui.Onboarding.OnboardingPageTransformer


class OnboardigFragment : Fragment() {
    private lateinit var binding: FragmentOnboardigBinding
    private lateinit var viewPager: ViewPager
    private var onboardingAdapter: OnboardingAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardigBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.onboardingViewPager
        onboardingAdapter = OnboardingAdapter(requireContext())
        viewPager.adapter = onboardingAdapter
        viewPager.setPageTransformer(false, OnboardingPageTransformer())


        binding.button2.setOnClickListener {
            nextPage(it)
        }

    }

    private fun nextPage(view: View) {
        if (view.id == R.id.button2) {

            if (viewPager.currentItem < onboardingAdapter!!.count - 1) {
                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            } else if (viewPager.currentItem == 2) {
                onBoardingToHome()
            }

        }
    }


    private fun onBoardingToHome() {
        findNavController().navigate(OnboardigFragmentDirections.actionOnboardigFragmentToHomeFragment())
    }


}