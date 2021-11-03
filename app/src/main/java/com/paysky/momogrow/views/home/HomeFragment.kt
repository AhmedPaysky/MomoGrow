package com.paysky.momogrow.views.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.github.mikephil.charting.charts.BarChart
import com.paysky.momogrow.databinding.FragmentHomeBinding
import com.paysky.momogrow.views.all_transactions.AllTransactionsActivity
import com.paysky.momogrow.views.base.BaseFragment

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.paysky.momogrow.databinding.FragmentIntroScreensBinding
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.paysky.momogrow.R

import com.paysky.momogrow.views.catalog.AddProductActivity


class HomeFragment : BaseFragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val MAX_X_VALUE = 7
    private val MAX_Y_VALUE = 50
    private val MIN_Y_VALUE = 2
    private val SET_LABEL = ""
    private val DAYS = arrayOf("08h", "12h", "14h", "16h", "18h", "20h", "22h")

    companion object {
        public lateinit var lviewPager: ViewPager
        public lateinit var livDashesIntro: ImageView

    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var chart: BarChart

    // When requested, this adapter returns a IntroScreensFragment,
    // representing an object in the collection.
    private lateinit var introPagerAdapter: IntroPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        chart = view.findViewById(com.paysky.momogrow.R.id.chart1)
        lviewPager = view.pager
        livDashesIntro = view.ivDashesIntro
        introPagerAdapter =
            IntroPagerAdapter(childFragmentManager, requireActivity())
        binding.pager.adapter = introPagerAdapter

        introPagerAdapter.setCallback(object : IntroPagerAdapter.Callback {
            override fun onNextClick(nextPage: Int) {
                binding.pager.currentItem = nextPage
            }
        })

        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.ivDashesIntro.setImageDrawable(
                            AppCompatResources.getDrawable(
                                requireActivity(),
                                com.paysky.momogrow.R.drawable.ic_dashes1
                            )
                        )
                    }
                    1 -> {
                        binding.ivDashesIntro.setImageDrawable(
                            AppCompatResources.getDrawable(
                                requireActivity(),
                                com.paysky.momogrow.R.drawable.ic_dashes2
                            )
                        )
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        binding.tvSeeAll.setOnClickListener {
            startActivity(Intent(requireActivity(), AllTransactionsActivity::class.java))
        }

        binding.ivClose.setOnClickListener {
            binding.intro.visibility = View.GONE
        }
        val data = createChartData()
        configureChartAppearance()
        prepareChartData(data!!)

        binding.tvToday.setOnClickListener(this)
        binding.tvThisWeek.setOnClickListener(this)
        binding.tvThisMonth.setOnClickListener(this)
        binding.tvCustom.setOnClickListener(this)

        return view
    }

    private fun createChartData(): BarData? {
        val values = ArrayList<BarEntry>()
        for (i in 0 until MAX_X_VALUE) {
            val x = i.toFloat()
            val y = MIN_Y_VALUE + Random()
                .nextFloat() * (MAX_X_VALUE - MIN_Y_VALUE)
            values.add(BarEntry(x, y))
        }
        val set1 = BarDataSet(values, SET_LABEL)
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)
        return BarData(dataSets)
    }

    private fun configureChartAppearance() {
        chart.getDescription().setEnabled(false)
        chart.setDrawValueAboveBar(false)
        val xAxis: XAxis = chart?.getXAxis()!!
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return DAYS[value.toInt()]
            }
        }
        val axisLeft: YAxis = chart?.getAxisLeft()!!
        axisLeft.granularity = 10f
        axisLeft.axisMinimum = 0f
        val axisRight: YAxis = chart?.getAxisRight()!!
        axisRight.granularity = 10f
        axisRight.axisMinimum = 0f
    }

    private fun prepareChartData(data: BarData) {
        data.setValueTextSize(12f)
        chart.setData(data)
        chart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tvToday -> {
                binding.tvToday.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvThisMonth.setBackgroundColor(Color.TRANSPARENT)
                binding.tvThisWeek.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCustom.setBackgroundColor(Color.TRANSPARENT)
                binding.tvToday.setTextColor(resources.getColor(R.color.white, null))
                binding.tvThisWeek.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvThisMonth.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCustom.setTextColor(resources.getColor(R.color.gray_light, null))
                randomData()
            }
            R.id.tvThisWeek -> {
                binding.tvThisWeek.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvToday.setBackgroundColor(Color.TRANSPARENT)
                binding.tvThisMonth.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCustom.setBackgroundColor(Color.TRANSPARENT)
                binding.tvThisWeek.setTextColor(resources.getColor(R.color.white, null))
                binding.tvToday.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvThisMonth.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCustom.setTextColor(resources.getColor(R.color.gray_light, null))
                randomData()
            }
            R.id.tvThisMonth -> {
                binding.tvThisMonth.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvToday.setBackgroundColor(Color.TRANSPARENT)
                binding.tvThisWeek.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCustom.setBackgroundColor(Color.TRANSPARENT)
                binding.tvThisMonth.setTextColor(resources.getColor(R.color.white, null))
                binding.tvToday.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvThisWeek.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvCustom.setTextColor(resources.getColor(R.color.gray_light, null))
                randomData()
            }
            R.id.tvCustom -> {
                binding.tvCustom.setBackgroundResource(R.drawable.bc_blue_shape)
                binding.tvToday.setBackgroundColor(Color.TRANSPARENT)
                binding.tvThisWeek.setBackgroundColor(Color.TRANSPARENT)
                binding.tvThisMonth.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCustom.setTextColor(resources.getColor(R.color.white, null))
                binding.tvToday.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvThisWeek.setTextColor(resources.getColor(R.color.gray_light, null))
                binding.tvThisMonth.setTextColor(resources.getColor(R.color.gray_light, null))
                randomData()
            }
        }
    }

    fun randomData() {
        val data = createChartData()
        configureChartAppearance()
        prepareChartData(data!!)
        val d1 = (300..1000).random() // generated random from 0 to 10 included
        val d2 = (0..100).random() // generated random from 0 to 10 included
        val d3 = (0..100).random() // generated random from 0 to 10 included


        binding.tvTotalAmount.text = d1.toString()
        binding.tvPaymentTransactions.text = d2.toString()
        binding.tvMarketplaceOrders.text = d3.toString()
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class IntroPagerAdapter(fm: FragmentManager, var mContext: Context) :
    FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 2

    interface Callback {
        fun onNextClick(nextPage: Int)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun getItem(i: Int): Fragment {
        val fragment = IntroScreensFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, i)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}

private const val ARG_OBJECT = "object"

class IntroScreensFragment : Fragment() {
    private var _binding: FragmentIntroScreensBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntroScreensBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.text == getString(com.paysky.momogrow.R.string.add_product)) {
                startActivity(Intent(requireActivity(), AddProductActivity::class.java))
            } else
                HomeFragment.lviewPager.currentItem = 1
        }
        val ARG_OBJECT = arguments?.getInt(ARG_OBJECT)

        binding.ivClose.setOnClickListener {
            HomeFragment.lviewPager.visibility = View.GONE
            HomeFragment.livDashesIntro.visibility = View.GONE
        }
        when (ARG_OBJECT) {
            0 -> {
                binding.tvTitleIntro.text =
                    getString(com.paysky.momogrow.R.string.setupyourstorefront)
                binding.tvDescriptionIntro.text =
                    getString(com.paysky.momogrow.R.string.setupyourstorefront_description)
                binding.btnNext.text = getString(com.paysky.momogrow.R.string.setup_now)
            }
            1 -> {
                binding.tvTitleIntro.text =
                    getString(com.paysky.momogrow.R.string.addyourfirstproduct)
                binding.tvDescriptionIntro.text =
                    getString(com.paysky.momogrow.R.string.addyourfirstproduct_description)
                binding.btnNext.text = getString(com.paysky.momogrow.R.string.add_product)
            }
        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
