package rocks.poopjournal.todont.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rocks.poopjournal.todont.adapters.HabitsLogAdapter
import rocks.poopjournal.todont.R
import rocks.poopjournal.todont.model.Habit
import rocks.poopjournal.todont.utils.DatabaseUtils

class HabitsLogFragment : Fragment() {
    var rv: RecyclerView? = null
    var habitsList = ArrayList<Habit>()
    private var dbHelper: DatabaseUtils? = null
    private var adapter: HabitsLogAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_habits, container, false)
        rv = view.findViewById(R.id.rv)
        dbHelper = DatabaseUtils(requireContext())

        setDataInList()
        return view
    }

    private fun setDataInList() {
        dbHelper?.getAllHabits()?.let {
            habitsList = it
            if (habitsList.isNotEmpty()) {

                rv?.layoutManager = LinearLayoutManager(activity)
                adapter =
                    HabitsLogAdapter(
                        requireContext(),
                        dbHelper!!, habitsList
                    )

                rv!!.adapter = adapter
                rv!!.layoutManager = LinearLayoutManager(activity)

            }

        }

    }
}