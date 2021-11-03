package com.paysky.momogrow.views.catalog

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.paysky.momogrow.R
import com.paysky.momogrow.databinding.FragmentAddProductBinding


class AddProductFragment : Fragment() {
    private lateinit var mImage1: Uri
    private lateinit var mImage2: Uri
    private lateinit var mImage3: Uri
    private lateinit var mImage4: Uri
    private var _binding: FragmentAddProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val strings = arrayOf("Fruits", "Vegetables", "Bakery", "Dairy & Eggs")
    var quantity = 0
    var handler: Handler = Handler(Looper.getMainLooper())
    var runnable: Runnable? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        val view = binding.root

        initSPinner()
        initQuantityCounter()
        initImages()

        //clickable
        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnNext.setOnClickListener {
            val prodObj = CatalogAdapter.ProductObj()
            prodObj.name = binding.etProductName.text.toString()
            prodObj.status = "In stock"
            prodObj.qStatus = "under review"
            CatalogAdapter.products.add(0, prodObj)
            findNavController().navigate(R.id.action_addProductFragment_to_pendingApprovalProductFragment)
        }

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        return view
    }

    private val startForImage_1Result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    mImage1 = fileUri
                    binding.image1.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        requireActivity(),
                        ImagePicker.getError(data),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private val startForImage_2Result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    mImage2 = fileUri
                    binding.image2.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        requireActivity(),
                        ImagePicker.getError(data),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private val startForImage_3Result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    mImage3 = fileUri
                    binding.image3.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        requireActivity(),
                        ImagePicker.getError(data),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private val startForImage_4Result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    mImage4 = fileUri
                    binding.image4.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        requireActivity(),
                        ImagePicker.getError(data),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun initImages() {

        binding.image1.setOnClickListener {
            ImagePicker.with(this)
                .maxResultSize(
                    200,
                    200
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForImage_1Result.launch(intent)
                }
        }
        binding.image2.setOnClickListener {
            ImagePicker.with(this)
                .maxResultSize(
                    200,
                    200
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForImage_2Result.launch(intent)
                }
        }
        binding.image3.setOnClickListener {
            ImagePicker.with(this)
                .maxResultSize(
                    200,
                    200
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForImage_3Result.launch(intent)
                }
        }
        binding.image4.setOnClickListener {
            ImagePicker.with(this)
                .maxResultSize(
                    200,
                    200
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForImage_4Result.launch(intent)
                }
        }
    }

    private fun initSPinner() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, strings)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        binding.spinner.adapter = adapter

    }

    private fun initQuantityCounter() {
        // slow
        binding.btnIncrease.setOnClickListener {
            if (quantity == 1000) return@setOnClickListener
            quantity++
            binding.tvQuantity.text = quantity.toString()
        }

        binding.btnDecrease.setOnClickListener {
            if (quantity == 0) return@setOnClickListener
            quantity--
            binding.tvQuantity.text = quantity.toString()
        }

        // fast
        binding.btnIncrease.setOnLongClickListener {
            runnable = Runnable {
                if (!binding.btnIncrease.isPressed || quantity == 1000) return@Runnable
                quantity++
                binding.tvQuantity.text = quantity.toString()
                handler.postDelayed(runnable!!, 10)
            }
            handler.postDelayed(runnable!!, 10)
            true
        }

        binding.btnDecrease.setOnLongClickListener {
            runnable = Runnable {
                if (!binding.btnDecrease.isPressed || quantity == 0) return@Runnable
                quantity--
                binding.tvQuantity.text = quantity.toString()
                handler.postDelayed(runnable!!, 10)
            }
            handler.postDelayed(runnable!!, 10)
            true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}