package com.leonardo.pokedexapp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.leonardo.pokedexapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            this::verifyNewUser,
            4000
        ) //Delay to splash screen
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun verifyNewUser() {
        val preferences = requireActivity().getSharedPreferences("NEWUSER", 0)
        if (preferences.contains("userCheck")) {
            splashToHome()
        } else {
            openOnboarding()
        }
    }

    private fun splashToHome() { //Go to home screen
        view?.post {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        }

    }

    private fun openOnboarding() {
        val preferences = requireActivity().getSharedPreferences("NEWUSER", 0)
        val editor = preferences.edit()
        editor.putString("userCheck", "false")
        editor.commit()
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnboardigFragment())
    }

}