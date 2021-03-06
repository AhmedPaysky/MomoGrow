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
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.paysky.momogrow.R
import com.paysky.momogrow.data.api.ApiClientMomo
import com.paysky.momogrow.data.api.ApiServiceMomo
import com.paysky.momogrow.databinding.FragmentAddProductBinding
import com.paysky.momogrow.helper.Status
import com.paysky.momogrow.viewmodels.ViewModelFactoryMomo
import com.paysky.momogrow.MyApplication
import com.paysky.momogrow.data.models.momo.requests.AddProductRequestModel
import com.paysky.momogrow.data.models.momo.*
import com.paysky.momogrow.utilis.MyUtils
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
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
    var allCategories: ArrayList<CatgoriesItem?>? = null
    var quantity = 1
    var id1 = ""
    var id2 = ""
    var id3 = ""
    var id4 = ""
    var handler: Handler = Handler(Looper.getMainLooper())
    var runnable: Runnable? = null
    private val images = HashMap<String, String>()

    fun CheckEmptyFields(): Boolean {
        var isError = false
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

    fun GenerateImagesArray(): ArrayList<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>()
        val tempParts = ArrayList<MultipartBody.Part>()
        MultipartBody.Builder().setType(MultipartBody.FORM)
        if (MyApplication.productObj != null) {
            if (MyApplication.productObj.images != null) {
                for (image in MyApplication.productObj.images!!) {
                    val requestFile =
                        RequestBody.create("text/plain".toMediaTypeOrNull(), "")
                    val part = MultipartBody.Part.createFormData(
                        "images[${image?.id}]",
                        "",
                        requestFile
                    )
                    parts.add(part)
                    tempParts.add(part)
                }
            }
        }
        if (this::mImage1.isInitialized) {


            if (id1.isNotEmpty()) {
                parts.remove(tempParts[0])
            }

            mImage1.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile =
                            RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
                        parts.add(
                            MultipartBody.Part.createFormData(
                                "images[]",
                                substring(lastIndexOf('/') + 1),
                                requestFile
                            )
                        )
                    }
                } catch (e: Exception) {
                    Log.e("addImagesToProduct", "exception:" + e.toString())
                }
            }
        }
        if (this::mImage2.isInitialized) {
            if (images.contains(id2)) {
                images.remove(id2)
            }


            mImage2.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
                        parts.add(
                            MultipartBody.Part.createFormData(
                                "images[]",
                                substring(lastIndexOf('/') + 1),
                                requestFile
                            )
                        )
                    }
                } catch (e: Exception) {

                }
            }
        }
        if (this::mImage3.isInitialized) {
            if (images.contains(id3)) {
                images.remove(id3)
            }

            mImage3.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
                        parts.add(
                            MultipartBody.Part.createFormData(
                                "images[]",
                                substring(lastIndexOf('/') + 1),
                                requestFile
                            )
                        )
                    }
                } catch (e: Exception) {

                }
            }
        }
        if (this::mImage4.isInitialized) {

            if (id4.isNotEmpty()) {
                parts.remove(tempParts[3])
            }
            mImage4.path?.apply {
                try {
                    val file = File(this)
                    if (file.exists()) {
                        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
                        parts.add(
                            MultipartBody.Part.createFormData(
                                "images[]",
                                substring(lastIndexOf('/') + 1),
                                requestFile
                            )
                        )
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
                categories.add(allCategories?.get(binding.spinner.selectedItemPosition)?.id!!)
                val productEntity = AddProductRequestModel()
                productEntity.name = binding.etProductName.text.toString()
                productEntity.meta_title = binding.etProductName.text.toString()
                productEntity.url_key = binding.etProductName.text.toString().replace(" ", "-")
                productEntity.description = binding.etProductDescription.text.toString()
                productEntity.short_description = binding.etProductDescription.text.toString()
                productEntity.meta_description = binding.etProductDescription.text.toString()
                productEntity.price = binding.etLastProductPrice.text.toString()
                productEntity.sku = binding.etSKU.text.toString()
                productEntity.attribute_family_id = attributesid
                productEntity.weight = binding.etWeight.text.toString()
                productEntity.width = binding.etWidth.text.toString()
                productEntity.height = binding.etHeight.text.toString()
                productEntity.quantity = binding.tvQuantity.text.toString().toInt()
                productEntity.featured = if (binding.switchFeature.isChecked) 1 else 0
                productEntity.new = if (binding.switchNew.isChecked) 1 else 0
                productEntity.show_on_marketplace = if (binding.switchPublish.isChecked) 1 else 0
                productEntity.categories = categories
                productEntity.images = images
                if (MyApplication.productObj != null) {
                    viewModel.updateproduct(MyApplication.productObj.id!!, productEntity)
                        .observe(viewLifecycleOwner, {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    val images = GenerateImagesArray()
                                    Log.v("ImagesContent", images.toString())
                                    if (images.size > 0) {
                                        GoImagesApi(
                                            (it.data as AddProductResponse).data?.product?.id!!,
                                            images
                                        )
                                    } else {
                                        dialog.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Product Edited successfully",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        requireActivity().finish()
                                    }
                                }
                                Status.ERROR -> {
                                    Toast.makeText(
                                        context,
                                        it.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    dialog.dismiss()
                                }
                                Status.LOADING -> {
                                    dialog.show()
                                }
                                else ->
                                    dialog.dismiss()

                            }
                        })
                } else {
                    viewModel.addproduct(productEntity).observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                val images = GenerateImagesArray()
                                if (images.size > 0) {
                                    GoImagesApi(
                                        (it.data as AddProductResponse).data?.product?.id!!, images
                                    )
                                } else {
                                    dialog.dismiss()
                                    if (productEntity.show_on_marketplace != 1) {
                                        Toast.makeText(
                                            context,
                                            "Product addedd successfully",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        requireActivity().finish()
                                    } else {
                                        requireActivity().finish()
                                        findNavController().navigate(
                                            R.id.action_addProductFragment_to_pendingApprovalProductFragment
                                        )
                                    }
                                }
                            }
                            Status.ERROR -> {
                                dialog.dismiss()
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            }
                            Status.LOADING -> {
                                dialog.show()
                            }

                        }
                    })
                }
            }
        }

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        if (MyApplication.productObj != null) {
            SetDataOfProductToFieldsToUpdate(MyApplication.productObj)
        }
        return view
    }

    fun GoImagesApi(id: Int, arr: ArrayList<MultipartBody.Part>) {
        viewModel.addImagesToProduct(id, arr).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    if (MyApplication.productObj != null) {
                        Toast.makeText(
                            context, "Product Edited successfully", Toast.LENGTH_LONG
                        ).show()
                        requireActivity().finish()
                    } else if (!binding.switchNew.isChecked) {
                        Toast.makeText(context, "Product addedd successfully", Toast.LENGTH_LONG)
                            .show()
                        requireActivity().finish()
                    } else {
                        requireActivity().finish()
                        findNavController().navigate(R.id.action_addProductFragment_to_pendingApprovalProductFragment)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    dialog.dismiss()
                }
                else -> dialog.dismiss()
            }
        })

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
                    if (images.contains(id1)) {
                        images.remove(id1)
                    }
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
                    if (images.contains(id2)) {
                        images.remove(id2)
                    }
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
                    if (images.contains(id3)) {
                        images.remove(id3)
                    }
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
                    if (images.contains(id4)) {
                        images.remove(id4)
                    }
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
                    allCategories = ((it.data) as MainOfMainCategories).data?.catgories
                    val arrOfStrings = ArrayList<String>()
                    allCategories?.forEach {
                        arrOfStrings.add(it?.name!!)
                    }
                    val adapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(
                            requireActivity(),
                            android.R.layout.simple_spinner_item,
                            arrOfStrings
                        )
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
                    binding.spinner.adapter = adapter
                }
                Status.ERROR -> {
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

    fun inialAttributesFamilies() {
        viewModel.allAttributesFamilies().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    attributesid =
                        (it.data as AttributesFamiliesMainModel).data?.families?.get(0)?.id!!
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


    fun SetDataOfProductToFieldsToUpdate(productdata: DataItem) {
        productdata.categories?.forEach {
            binding.spinner.setSelection(0)
        }

        //add all images id and delete id if image edited
        if (MyApplication.productObj.images != null) {
            for (image in MyApplication.productObj.images!!) {
                images[image?.id.toString()] = ""
            }
        }

        binding.etProductName.setText(productdata.name)
        binding.etProductDescription.setText(productdata.description)
        binding.etSKU.setText(productdata.sku)
        binding.etLastProductPrice.setText(productdata.price.toString())
        binding.etWidth.setText(productdata.width.toString())
        binding.etHeight.setText(productdata.height.toString())
        binding.etWeight.setText(productdata.weight.toString())
        binding.tvQuantity.setText(productdata.quantity.toString())
        binding.switchFeature.isChecked = productdata.featured == 1
        binding.switchNew.isChecked = productdata.jsonMemberNew == 1
        binding.switchPublish.isChecked = productdata.active == 1


        if (productdata.images?.size!! > 0) {
            for (i in 0..productdata.images.size) {
                if (i >= productdata.images.size) {
                    break
                } else if (i == 0) {
                    Glide.with(requireContext()).load(productdata.images[i]?.mediumImageUrl)
                        .into(binding.image1)
                    id1 = productdata.images[i]?.id.toString()
                } else if (i == 1) {
                    Glide.with(requireContext()).load(productdata.images[i]?.mediumImageUrl)
                        .into(binding.image2)
                    id2 = productdata.images[i]?.id.toString()
                } else if (i == 2) {
                    Glide.with(requireContext()).load(productdata.images[i]?.mediumImageUrl)
                        .into(binding.image3)
                    id3 = productdata.images[i]?.id.toString()
                } else {
                    Glide.with(requireContext()).load(productdata.images[i]?.mediumImageUrl)
                        .into(binding.image4)
                    id4 = productdata.images[i]?.id.toString()
                }
            }
        }
    }
}