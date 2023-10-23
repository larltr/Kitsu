package com.angelika.kitsu.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ProgressTarget(
    private val progressBar: ProgressBar,
    private val imageView: ImageView
) : CustomTarget<Drawable>() {

    override fun onLoadStarted(placeholder: Drawable?) {
        imageView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        imageView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        imageView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        imageView.setImageDrawable(resource)
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        imageView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
}