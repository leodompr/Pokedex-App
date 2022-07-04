package com.leonardo.pokedexapp.ui.Onboarding

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.leonardo.pokedexapp.R

class OnboardingPageTransformer : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        val pageWidthTimesPosition = pageWidth * position
        val absPosition = Math.abs(position)


        if (position <= -1.0f || position >= 1.0f) {
        } else if (position == 0.0f) {
        } else {
            val title = page.findViewById<View>(R.id.textView5)
            title.alpha = 1.0f - absPosition
            val description = page.findViewById<View>(R.id.textView6)
            description.translationY = -pageWidthTimesPosition / 2f
            description.alpha = 1.0f - absPosition
            val computer = page.findViewById<View>(R.id.textView6)
            if (computer != null) {
                computer.alpha = 1.0f - absPosition
                computer.translationX = -pageWidthTimesPosition * 1.5f
            }

        }
    }
}