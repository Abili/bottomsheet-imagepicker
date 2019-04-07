package com.kroegerama.imgpicker.demo

import android.net.Uri
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import com.kroegerama.kaiteki.baseui.BaseActivity
import com.kroegerama.kaiteki.toast
import kotlinx.android.synthetic.main.ac_main.*

class AcMain : BaseActivity(), BottomSheetImagePicker.OnImagesSelectedListener {

    override val layoutResource = R.layout.ac_main

    override fun setupGUI() {
        btnPickSingle.setOnClickListener { pickSingle() }
        btnPickMulti.setOnClickListener { pickMulti() }
        btnClear.setOnClickListener { imageContainer.removeAllViews() }
    }

    private fun pickSingle() {
        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .cameraButton(ButtonType.Tile)
            .galleryButton(ButtonType.Tile)
            .singleSelectTitle(R.string.pick_single)
            .peekHeight(R.dimen.peekHeight)
            .requestTag("single")
            .show(supportFragmentManager)
    }

    private fun pickMulti() {
        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .columnSize(R.dimen.columnSize)
            .multiSelect(3, 6)
            .multiSelectTitles(
                R.plurals.pick_multi,
                R.plurals.pick_multi_more,
                R.string.pick_multi_limit
            )
            .requestTag("multi")
            .show(supportFragmentManager)
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        toast("Result from tag: $tag")

        imageContainer.removeAllViews()
        uris.forEach { uri ->
            val iv = LayoutInflater.from(this).inflate(R.layout.scrollitem_image, imageContainer, false) as ImageView
            imageContainer.addView(iv)
            Glide.with(this).load(uri).into(iv)
        }
    }
}