package rocks.poopjournal.todont.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rocks.poopjournal.todont.Helper
import rocks.poopjournal.todont.R
import rocks.poopjournal.todont.databinding.FragmentLog2Binding
import rocks.poopjournal.todont.utils.DatabaseUtils

class FragmentLog : Fragment() {
    private var db: DatabaseUtils? = null
    private var binding: FragmentLog2Binding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLog2Binding.inflate(inflater, container, false)

        val view = inflater.inflate(R.layout.fragment_log2, container, false)
        db = DatabaseUtils(requireContext())
        Helper.isTodaySelected = false
        Helper.SelectedButtonOfLogTab = 0

        binding?.dhabits?.setOnClickListener { updateTabSelection(0, HabitsLogFragment()) }
        binding?.davoided?.setOnClickListener { updateTabSelection(1, AvoidedOrDoneLogFragment(true)) }
        binding?.ddone?.setOnClickListener { updateTabSelection(2, AvoidedOrDoneLogFragment(false)) }

        binding!!.day.setOnClickListener {
            binding!!.day.setBackgroundResource(R.drawable.continuebutton2)
            binding!!.week.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.month.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.year.setBackgroundResource(R.drawable.continuebuttontrans)
            Helper.SelectedButtonOfLogTab = 0
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerLogFragment, DailyFragment())
            ft.commit()
        }
        binding!!.week.setOnClickListener {
            binding!!.day.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.week.setBackgroundResource(R.drawable.continuebutton2)
            binding!!.month.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.year.setBackgroundResource(R.drawable.continuebuttontrans)
            Helper.SelectedButtonOfLogTab = 1
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerLogFragment, WeeklyFragment())
            ft.commit()
        }
        binding!!.month.setOnClickListener {
            binding!!.day.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.week.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.month.setBackgroundResource(R.drawable.continuebutton2)
            binding!!.year.setBackgroundResource(R.drawable.continuebuttontrans)
            Helper.SelectedButtonOfLogTab = 2
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerLogFragment, MonthlyFragment())
            ft.commit()
        }
        binding!!.year.setOnClickListener {
            binding!!.day.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.week.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.month.setBackgroundResource(R.drawable.continuebuttontrans)
            binding!!.year.setBackgroundResource(R.drawable.continuebutton2)
            Helper.SelectedButtonOfLogTab = 3
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerLogFragment, YearlyFragment())
            ft.commit()
        }
        updateTabSelection(0, HabitsLogFragment())

        when (Helper.SelectedButtonOfLogTab) {
            0 -> {
                binding!!.day.setBackgroundResource(R.drawable.continuebutton2)
                binding!!.week.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.month.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.year.setBackgroundResource(R.drawable.continuebuttontrans)
                val ft = requireActivity().supportFragmentManager.beginTransaction()
                ft.replace(R.id.containerLogFragment, DailyFragment())
                ft.commit()
            }
            1 -> {
                binding!!.day.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.week.setBackgroundResource(R.drawable.continuebutton2)
                binding!!.month.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.year.setBackgroundResource(R.drawable.continuebuttontrans)
                val ft = requireActivity().supportFragmentManager.beginTransaction()
                ft.replace(R.id.containerLogFragment, WeeklyFragment())
                ft.commit()
            }
            2 -> {
                binding!!.day.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.week.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.month.setBackgroundResource(R.drawable.continuebutton2)
                binding!!.year.setBackgroundResource(R.drawable.continuebuttontrans)
                val ft = requireActivity().supportFragmentManager.beginTransaction()
                ft.replace(R.id.containerLogFragment, MonthlyFragment())
                ft.commit()
            }
            3 -> {
                binding!!.day.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.week.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.month.setBackgroundResource(R.drawable.continuebuttontrans)
                binding!!.year.setBackgroundResource(R.drawable.continuebutton2)
                val ft = requireActivity().supportFragmentManager.beginTransaction()
                ft.replace(R.id.containerLogFragment, YearlyFragment())
                ft.commit()
            }
        }
        return binding!!.root
    }

    private fun updateTabSelection(selectedTab: Int, fragment: Fragment) {
        Helper.SelectedButtonOfLogDailyTab = selectedTab
        binding?.apply {
            dhabits.setBackgroundResource(if (selectedTab == 0) R.drawable.continuebutton2 else R.drawable.continuebuttontrans)
            davoided.setBackgroundResource(if (selectedTab == 1) R.drawable.continuebutton2 else R.drawable.continuebuttontrans)
            ddone.setBackgroundResource(if (selectedTab == 2) R.drawable.continuebutton2 else R.drawable.continuebuttontrans)
        }

        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.containerLogDailyFragment, fragment)?.commit()
    }
}