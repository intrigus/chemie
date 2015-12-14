/*Copyright 2015 Simon Gerst

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package de.intrigus.chem.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import de.intrigus.chem.ChemieActivity
import de.intrigus.chem.R

class AboutFragment : Fragment() {
    lateinit private var showLicenses: Button
    lateinit private var licenseText: TextView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.about_app, container, false)
        (activity as ChemieActivity).setupToolbar(view!!.findViewById(R.id.toolbar) as Toolbar)
        (activity as ChemieActivity).supportActionBar.title = "About"

        (view.findViewById(R.id.about_app_text)as TextView).movementMethod = LinkMovementMethod.getInstance()
        licenseText = view.findViewById(R.id.licenses) as TextView
        (view.findViewById(R.id.show_licenses)as Button).setOnClickListener { view ->
            if (licenseText.isShown) {
                licenseText.slideUp({ licenseText.visibility = View.GONE })
            } else {
                licenseText.visibility = View.VISIBLE
                licenseText.movementMethod = LinkMovementMethod.getInstance() // Only has an effect on visible views
                licenseText.slideDown()
            }
        }
        return view
    }

    fun TextView.slideDown() {
        val a = AnimationUtils.loadAnimation(activity, R.anim.slide_down)
        a.reset()
        licenseText.clearAnimation()
        licenseText.startAnimation(a)
    }

    fun TextView.slideUp(action: () -> Unit) {
        val a = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
        a.reset()
        a.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                action.invoke()
            }
        })
        licenseText.clearAnimation()
        licenseText.startAnimation(a)
    }


}