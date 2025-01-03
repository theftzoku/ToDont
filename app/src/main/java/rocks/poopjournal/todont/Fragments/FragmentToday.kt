package rocks.poopjournal.todont.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import rocks.poopjournal.todont.Helper
import rocks.poopjournal.todont.R

class FragmentToday : Fragment() {
    var avoided: Button? = null
    var done: Button? = null
    var habits: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_today2, container, false)
        Helper.isTodaySelected = true
        avoided = view.findViewById(R.id.avoided)
        done = view.findViewById(R.id.done)
        habits = view.findViewById(R.id.habits)
        if (Helper.SelectedButtonOfTodayTab == 0) {
            habits?.setBackgroundResource(R.drawable.continuebutton2)
            avoided?.setBackgroundResource(R.drawable.continuebuttontrans)
            done?.setBackgroundResource(R.drawable.continuebuttontrans)
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerTodayFragment, HabitsFragment())
            ft.commit()
        } else if (Helper.SelectedButtonOfTodayTab == 1) {
            habits?.setBackgroundResource(R.drawable.continuebuttontrans)
            avoided?.setBackgroundResource(R.drawable.continuebutton2)
            done?.setBackgroundResource(R.drawable.continuebuttontrans)
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerTodayFragment, AvoidedOrDoneFragment(true))
            ft.commit()
        } else if (Helper.SelectedButtonOfTodayTab == 2) {
            habits?.setBackgroundResource(R.drawable.continuebuttontrans)
            avoided?.setBackgroundResource(R.drawable.continuebuttontrans)
            done?.setBackgroundResource(R.drawable.continuebutton2)
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerTodayFragment, AvoidedOrDoneFragment(false))
            ft.commit()
        }


        habits?.setOnClickListener(View.OnClickListener {
            habits?.setBackgroundResource(R.drawable.continuebutton2)
            avoided?.setBackgroundResource(R.drawable.continuebuttontrans)
            done?.setBackgroundResource(R.drawable.continuebuttontrans)
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerTodayFragment, HabitsFragment())
            ft.commit()
        })
        avoided?.setOnClickListener(View.OnClickListener {
            habits?.setBackgroundResource(R.drawable.continuebuttontrans)
            avoided?.setBackgroundResource(R.drawable.continuebutton2)
            done?.setBackgroundResource(R.drawable.continuebuttontrans)
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerTodayFragment, AvoidedOrDoneFragment(true))
            ft.commit()
        })
        done?.setOnClickListener(View.OnClickListener {
            habits?.setBackgroundResource(R.drawable.continuebuttontrans)
            avoided?.setBackgroundResource(R.drawable.continuebuttontrans)
            done?.setBackgroundResource(R.drawable.continuebutton2)
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.containerTodayFragment, AvoidedOrDoneFragment(false))
            ft.commit()
        })
        return view
    }
}