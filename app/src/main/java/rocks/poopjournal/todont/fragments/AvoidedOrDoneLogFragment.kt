package rocks.poopjournal.todont.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rocks.poopjournal.todont.R
import rocks.poopjournal.todont.adapters.AvoidedOrDoneLogAdapter
import rocks.poopjournal.todont.model.Habit
import rocks.poopjournal.todont.utils.DatabaseUtils

class AvoidedOrDoneLogFragment(private val isAvoided: Boolean) : Fragment() {
    private var rv: RecyclerView? = null

    var habits = ArrayList<Habit>()
    var dbHelper: DatabaseUtils? = null
    var adapter: AvoidedOrDoneLogAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_avoided, container, false)
        //Helper.SelectedButtonOfTodayTab=true;
        rv = view.findViewById(R.id.rv)
        dbHelper = DatabaseUtils(requireContext())

        setDataInList()
        return view
    }

    fun setDataInList() {
        getData()?.let {
            habits = it
            rv!!.layoutManager = LinearLayoutManager(activity)
            val resIcon = if (isAvoided) R.drawable.ic_avoided else R.drawable.ic_done
            adapter = AvoidedOrDoneLogAdapter(
                    requireContext(), dbHelper!!, habits, isAvoided, resIcon
                )

            rv!!.adapter = adapter
            rv!!.layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun getData(): ArrayList<Habit>? {
        return if (isAvoided) {
            dbHelper?.getAvoidedHabits()
        } else {
            dbHelper?.getDoneHabits()
        }
    }
}



