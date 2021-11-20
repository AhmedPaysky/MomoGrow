package com.paysky.momogrow.views.products

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.databinding.FragmentAddProductBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.viewmodels.ViewModelFactoryMomo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.paysky.momogrow.data.models.AddProductRequestModel
import com.paysky.momogrow.data.models.momo.AddProductResponse
import com.paysky.momogrow.data.models.momo.AttributesFamiliesMainModel
import com.paysky.momogrow.data.models.momo.CatgoriesItem
import com.paysky.momogrow.data.models.momo.Data
import com.paysky.momogrow.utilis.MyUtils
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddProductFragment : Fragment() {
    private val viewModel: ProductViewModel by activityViewModels {
        ViewModelFactoryMomo(
            ApiClientMomo.apiClient().create(
                ApiServiceMomo::class.java
            )
        )
    }
    private var attributesid = 0

    private lateinit var dialog: Dialog
    private lateinit var mImage1: Uri
    private lateinit var mImage2: Uri
    private lateinit var mImage3: Uri
    private lateinit var mImage4: Uri
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    val allCategories = ArrayList<CatgoriesItem>()
    var quantity = 1
    var handler: Handler = Handler(Looper.getMainLooper())
    var runnable: Runnable? = null

    fun CheckEmptyFields() : Boolean {
        var isError  = false
        if (binding.etProductName.text.toString().trim().isEmpty()) {
            binding.etProductName.error = getString(R.string.product_name_error)
            isError = true
        }
        if (binding.etLastProductPrice.text.toString().trim().isEmpty()) {
            binding.etLastProductPrice.error = getString(R.string.Product_price_error)
            isError = true
        }
        if (binding.etSKU.text.toString().trim().isEmpty()) {
            binding.etSKU.error = getString(R.string.SKU_error)
            isError = true
        }
        if (binding.etWidth.text.toString().trim().isEmpty()) {
            binding.etWidth.error = getString(R.string.width_error)
            isError = true
        }
        if (binding.etHeight.text.toString().trim().isEmpty()) {
            binding.etHeight.error = getString(R.string.height_error)
            isError = true
        }
        if (binding.etWeight.text.toString().trim().isEmpty()) {
            binding.etWeight.error = getString(R.string.weight_error)
            isError = true
        }
        return isError
    }

    fun GenerateImagesArray() : ArrayList<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>()
        if (this::mImage1.isInitialized) {
            mImage1.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile = RequestBody.create(
                            activity?.getContentResolver()?.getType(mImage1)!!.toMediaTypeOrNull(), file)
                        parts.add(MultipartBody.Part.createFormData("images[0]", substring(lastIndexOf('/') + 1), requestFile))
                    }
                } catch (e: Exception) {

                }
            }
        }
        if (this::mImage2.isInitialized) {
            mImage2.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile = RequestBody.create(MultipartBody.FORM, file)
                        parts.add(MultipartBody.Part.createFormData("images[1]", substring(lastIndexOf('/') + 1), requestFile))
                    }
                } catch (e: Exception) {

                }
            }
        }
        if (this::mImage3.isInitialized) {
            mImage3.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile = RequestBody.create(MultipartBody.FORM, file)
                        parts.add(MultipartBody.Part.createFormData("images[2]", substring(lastIndexOf('/') + 1), requestFile))
                    }
                } catch (e: Exception) {

                }
            }
        }
        if (this::mImage4.isInitialized) {
            mImage4.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile = RequestBody.create(MultipartBody.FORM, file)
                        parts.add(MultipartBody.Part.createFormData("images[3]", substring(lastIndexOf('/') + 1), requestFile))
                    }
                } catch (e: Exception) {

                }
            }
        }
        return parts
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog = MyUtils.getDlgProgress(requireActivity())
        initSPinner()
        initQuantityCounter()
        initImages()
        inialAttributesFamilies()
        //clickable
        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }
        binding.btnNext.setOnClickListener {
            if (!CheckEmptyFields()) {
                val categories = ArrayList<Int>()
                categories.add(allCategories.get(binding.spinner.selectedItemPosition).id!!)
                val productEntity = AddProductRequestModel()
                productEntity.name = binding.etProductName.text.toString()
                productEntity.meta_title = binding.etProductName.text.toString()
                productEntity.url_key = binding.etProductName.text.toString().replace(" ","-")
                productEntity.description = binding.etProductDescription.text.toString()
                productEntity.short_description = binding.etProductDescription.text.toString()
                productEntity.meta_description = binding.etProductDescription.text.toString()
                productEntity.price = binding.etLastProductPrice.text.toString()
                productEntity.sku = binding.etSKU.text.toString()
                productEntity.attribute_family_id = attributesid
                productEntity.weight = binding.etWeight.text.toString()
                productEntity.width = binding.etWidth.text.toString()
                productEntity.height = binding.etHeight.text.toString()
                productEntity.quantity = binding.tvQuantity.text.toString()
                productEntity.featured = if (binding.switchFeature.isChecked) 1 else 0
                productEntity.new = if (binding.switchNew.isChecked) 1 else 0
                productEntity.show_on_marketplace = if (binding.switchPublish.isChecked) 1 else 0
                productEntity.categories = categories
                if (arguments != null && arguments?.containsKey("productdata") == true) {
                    viewModel.updateproduct((arguments?.getSerializable("productdata") as Data).id!! , productEntity).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                dialog.dismiss()
                                val bundle = Bundle()
                                bundle.putSerializable(
                                    "productdata",
                                    ((it.data) as AddProductResponse).data!!
                                )
                                CoroutineScope(Dispatchers.IO).launch {
                                    findNavController().navigate(
                                        R.id.action_addProductFragment_to_pendingApprovalProductFragment,
                                        bundle
                                    )

                                }
                            }
                            Status.ERROR -> {
                                Log.e("productEntity", Gson().toJson(it.message))
                                dialog.dismiss()
                            }
                            Status.LOADING -> {
                                dialog.show()
                            }
                            else ->
                                dialog.dismiss()

                        }
                    })
                }
                else {
                    viewModel.addproduct(productEntity).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                viewModel.addImagesToProduct(
                                    ((it.data) as AddProductResponse).data!!.id!!,
                                    GenerateImagesArray()
                                ).observe(viewLifecycleOwner, {
                                    when (it.status) {
                                        Status.SUCCESS -> {
                                            dialog.dismiss()
                                            val bundle = Bundle()
                                            bundle.putSerializable(
                                                "productdata",
                                                ((it.data) as AddProductResponse).data!!
                                            )
                                            CoroutineScope(Dispatchers.IO).launch {
                                                findNavController().navigate(
                                                    R.id.action_addProductFragment_to_pendingApprovalProductFragment,
                                                    bundle
                                                )

                                            }
                                        }
                                        Status.ERROR -> {
                                            Log.e("productEntity", Gson().toJson(it.message))
                                            dialog.dismiss()
                                        }
                                        Status.LOADING -> {
                                            dialog.show()
                                        }
                                        else ->
                                            dialog.dismiss()

                                    }
                                })
                            }
                            Status.ERROR -> {
                                Log.e("productEntity", Gson().toJson(it.message))
                                dialog.dismiss()
                            }
                            Status.LOADING -> {
                                dialog.show()
                            }
                            else ->
                                dialog.dismiss()

                        }
                    })
                }
            }
        }

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        if (arguments != null && arguments?.containsKey("productdata") == true) {
            SetDataOfProductToFieldsToUpdate(arguments?.getSerializable("productdata") as Data)
        }
        return view
    }

    private val startForImage_1Result =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
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
        viewModel.allCategories().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    val arrOfStrings = ArrayList<String>()
                    ((it.data) as ArrayList<CatgoriesItem>).forEach {
                        arrOfStrings.add(it.name!!)
                    }
                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, arrOfStrings)
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
                    binding.spinner.adapter = adapter
                }
                Status.ERROR -> {
                    Log.e("productEntity",Gson().toJson(it.message))
                    dialog.dismiss()
                }
                Status.LOADING -> {
                    dialog.show()
                }
                else ->
                    dialog.dismiss()

            }
        })
    }

    fun inialAttributesFamilies(){
        viewModel.allAttributesFamilies().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    attributesid = (it.data as AttributesFamiliesMainModel).data?.families?.get(0)?.id!!
                }
            }
        })

    }

    private fun initQuantityCounter() {
        // slow
        binding.btnIncrease.setOnClickListener {
            if (quantity == 99) return@setOnClickListener
            quantity++
            binding.tvQuantity.text = quantity.toString()
        }

        binding.btnDecrease.setOnClickListener {
            if (quantity == 1) return@setOnClickListener
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




    fun SetDataOfProductToFieldsToUpdate(productdata : Data) {
        productdata.categories?.forEach {
            binding.spinner.setSelection(0)
        }
        binding.etProductName.setText(productdata.name)
        binding.etProductDescription.setText(productdata.description)
        binding.etSKU.setText(productdata.sku)
        binding.etLastProductPrice.setText(productdata.price)
        binding.etWidth.setText(productdata.width.toString())
        binding.etHeight.setText(productdata.height.toString())
        binding.etWeight.setText(productdata.weight.toString())
        binding.tvQuantity.setText("1")
        binding.switchFeature.isChecked = productdata.featured == 1
        binding.switchNew.isChecked = productdata.new  == 1
        binding.switchPublish.isChecked = productdata.active == 1
    }
}