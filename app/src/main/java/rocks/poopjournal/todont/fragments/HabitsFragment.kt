package rocks.poopjournal.todont.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import rocks.poopjournal.todont.adapters.HabitsAdapter
import rocks.poopjournal.todont.Helper
import rocks.poopjournal.todont.MainActivity
import rocks.poopjournal.todont.R
import rocks.poopjournal.todont.databinding.DialogboxFloatingbuttonBinding
import rocks.poopjournal.todont.model.Habit
import rocks.poopjournal.todont.model.Label
import rocks.poopjournal.todont.showcaseview.ShowcaseViewBuilder
import rocks.poopjournal.todont.showcaseview.ShowcaseViewBuilder.Companion.init
import rocks.poopjournal.todont.utils.Constants
import rocks.poopjournal.todont.utils.DatabaseUtils
import rocks.poopjournal.todont.utils.SharedPrefUtils
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HabitsFragment : Fragment() {
    private var rv: RecyclerView? = null
    private var habitsList = ArrayList<Habit>()
    private var floatingActionButton: FloatingActionButton? = null
    private var date: Date = Calendar.getInstance().time
    private var selectedLabel: Label? = null
    private var dbHelper: DatabaseUtils? = null
    private var adapter: HabitsAdapter? = null
    private var tvNoHabits: TextView? = null
    private var showcaseViewBuilder: ShowcaseViewBuilder? = null
    private var prefUtils: SharedPrefUtils? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_habits, container, false)
        Helper.SelectedButtonOfTodayTab = 0
        rv = view.findViewById(R.id.rv)
        dbHelper = DatabaseUtils(requireContext())

        showcaseViewBuilder = init(requireActivity())
        prefUtils = SharedPrefUtils(requireActivity())
        //  tv1 = view.findViewById(R.id.a);
        tvNoHabits = view.findViewById(R.id.b)
        floatingActionButton = view.findViewById(R.id.floatingbtn)
        floatingActionButton?.bringToFront()
        floatingActionButton?.setVisibility(View.VISIBLE)

        floatingActionButton?.setOnClickListener(View.OnClickListener {
            addNewHabit()
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        setDataInList()
    }

    private fun addNewHabit() {
        if (!prefUtils!!.getBool("plus")) {
            showcaseFab()
        } else {
            val labelCount = dbHelper?.getLabelsCount()
            if (labelCount == 0) {
                Toast.makeText(activity, R.string.please_add_a_label_first, Toast.LENGTH_LONG)
                    .show()
            } else {
                val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
                val formattedDate = dateFormat.format(date)

                val dialog = Dialog(requireActivity())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                val dialogView = DialogboxFloatingbuttonBinding.inflate(layoutInflater)
                dialog.setContentView(dialogView.root)

                val lp = dialog.window!!.attributes
                lp.dimAmount = 0.9f
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val window = dialog.window
                window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                dialog.window!!.attributes = lp

                if (labelCount == 0) {
                    dialogView.txt.visibility = View.VISIBLE
                    dialogView.spinner.visibility = View.GONE
                } else {
                    dialogView.txt.visibility = View.GONE
                    dialogView.spinner.visibility = View.VISIBLE
                }
                val labels = dbHelper?.getAllLabels()!!
                labels.add(0,Label(-1,"Select a label"))
                val arrayAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_item,
                    labels
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dialogView.spinner.adapter = arrayAdapter


                dialogView.spinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            adapterView: AdapterView<*>,
                            view: View,
                            i: Int,
                            l: Long
                        ) {
                            if(i!=0){
                                selectedLabel = labels[i]
                                (adapterView.getChildAt(0) as TextView).setTextColor(
                                    resources.getColor(
                                        R.color.textcolor
                                    )
                                )
                            }

                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>?) {
                        }
                    }


                dialogView.saveTaskButton.setOnClickListener {
                    val habit_text = dialogView.habit.text.toString()
                    val detail_text = dialogView.detail.text.toString()

                    dbHelper?.insertHabit(
                        Habit(
                            date = formattedDate,
                            name = habit_text,
                            description = detail_text,
                            countAvoided = 0,
                            countDone = 0,
                            labelId = selectedLabel?.labelId!!
                        )
                    )

                    Helper.SelectedButtonOfTodayTab = 0
//                    val i = Intent(activity, MainActivity::class.java)
//                    startActivity(i)
                    setDataInList()
                    requireActivity().overridePendingTransition(0, 0)
                    dialog.dismiss()
                }
                dialogView.spinner.adapter = arrayAdapter
                dialog.show()
            }
        }
    }

    private fun showcaseFab() {
        val guideView = GuideView.Builder(requireContext())
            .setContentText(getString(R.string.to_start_off_put_down_a_bad_habit))
            .setTargetView(floatingActionButton)
            .setDismissType(DismissType.anywhere)
            .setPointerType(PointerType.arrow)
            .setGravity(Gravity.center)
            .setGuideListener {
                prefUtils!!.setBool(
                    "plus",
                    true
                )
            }
        guideView.build().show()
    }

    private fun setDataInList() {
        dbHelper?.getAllHabits()?.let {
            habitsList = it
            if (habitsList.isNotEmpty()) {
                tvNoHabits?.visibility = View.INVISIBLE

                rv?.layoutManager = LinearLayoutManager(activity)
                ItemTouchHelper(itemtouchhelper).attachToRecyclerView(rv)
                adapter =
                    HabitsAdapter(
                            requireContext(),
                            dbHelper!!, habitsList
                        )

                rv!!.adapter = adapter
                rv!!.layoutManager = LinearLayoutManager(activity)

            } else {
                tvNoHabits?.visibility = View.VISIBLE
            }

        } ?: run { tvNoHabits?.setVisibility(View.VISIBLE) }

    }

    var itemtouchhelper: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == 8) {
                    val builder1 = AlertDialog.Builder(
                        context
                    )
                    builder1.setMessage(R.string.do_you_really_want_to_delete_this)
                    builder1.setCancelable(true)

                    builder1.setPositiveButton(
                        R.string.yes
                    ) { dialog, id ->
                        val i = viewHolder.adapterPosition
                        dbHelper?.deleteHabit(habitsList[i].id)
                        dbHelper?.deleteAllHabitRecords(habitsList[i].id)
                        Helper.SelectedButtonOfTodayTab = 0
                        habitsList.removeAt(i)
                        adapter?.notifyItemRemoved(i)
                        adapter?.notifyItemRangeChanged(0,habitsList.size)
//                        val intent = Intent(activity, MainActivity::class.java)
//                        startActivity(intent)
                        activity!!.overridePendingTransition(0, 0)
                        dialog.cancel()
                    }

                    builder1.setNegativeButton(
                        R.string.no
                    ) { dialog, id ->
                        Helper.SelectedButtonOfTodayTab = 0
//                        val i = Intent(activity, MainActivity::class.java)
//                        startActivity(i)
                        adapter?.notifyDataSetChanged()
                        activity!!.overridePendingTransition(0, 0)
                        dialog.cancel()
                    }

                    val alert11 = builder1.create()
                    alert11.show()
                }
            }
        }
}
