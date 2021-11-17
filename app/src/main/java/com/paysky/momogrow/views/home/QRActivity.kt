package com.paysky.momogrow.views.home

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.ActivityAuthenticateBinding
import com.paysky.momogrow.databinding.ActivityQractivityBinding
import com.paysky.momogrow.views.bottomsheets.ConfirmationBottomSheet
import com.paysky.momogrow.views.bottomsheets.ShareSMSlBottomSheet

class QRActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQractivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQractivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnDone.setOnClickListener {
            finish()
        }
        generateQr("https://www.google.com/")
    }

    private fun showConfirmationBottomSheet(fragmentView: View) {
        val modalbottomSheetFragment =
            ConfirmationBottomSheet(fragmentView = fragmentView, fromWhere = "qr")
        modalbottomSheetFragment.show(
            supportFragmentManager,
            modalbottomSheetFragment.tag
        )
    }

    private fun generateQr(orderUrl: String) {
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(orderUrl, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            binding.ivQr.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}