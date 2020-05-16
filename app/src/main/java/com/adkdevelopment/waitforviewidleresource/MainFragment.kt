package com.adkdevelopment.waitforviewidleresource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private val adapter = TestItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val rvView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        rvView?.adapter = adapter

        // simulating a long delay before API returns data
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            delay(35000)

            // this should be replaced with a view matcher in the test that checks
            // if the data is present in the RV instead of making it visible here.
            withContext(Dispatchers.Main) {
                rvView?.visibility = View.VISIBLE
            }

            adapter.submitList(
                listOf(
                    TestItem("Test 1", 1),
                    TestItem("Test 2", 2),
                    TestItem("Test 3", 3),
                    TestItem("Test 4", 4)
                )
            )
        }

    }

    companion object {
        fun newInstance() =
            MainFragment()
    }

}