package com.example.vahanasiignment.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vahanasiignment.ui.MainActivity
import com.example.vahanasiignment.utils.Resource
import com.example.vahanasiignment.ui.UniversityViewModel
import com.example.vahanasiignment.adapter.UniversityAdapter
import com.example.vahanasiignment.databinding.FragmentHomeBinding


class Home_Fragment : Fragment() {

    lateinit var viewModel: UniversityViewModel
    lateinit var adapter: UniversityAdapter
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            setUpRecyclerView()
            viewModel = (activity as MainActivity).viewModel
            viewModel.universityList.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { univeResponse ->
                            adapter.differ.submitList(univeResponse)
                            Log.d(TAG, univeResponse[0].country)

                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            //Log.d(TAG, "An error occured $message")
                            Toast.makeText(activity,"An error occured: $message",Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()

                    }

                }
            })



        return binding.root
    }
    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setCancelable(false)
        builder.setTitle("No Internet Connection")
        builder.setMessage("Please check your internet connection")
        builder.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            // Close the app when the user clicks "OK"
            activity?.finish()
        }
        val dialog = builder.create()
        dialog.show()
    }



    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        binding.rvhome.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        binding.rvhome.visibility = View.INVISIBLE

    }




    private fun setUpRecyclerView() {
    adapter = UniversityAdapter(object : UniversityAdapter.OnItemsClick {
        override fun onClickWebsite(link: String) {

            val builder = CustomTabsIntent.Builder()
            val CustomTabsIntent = builder.build()
            CustomTabsIntent.launchUrl(binding.root.context,Uri.parse(link))
        }
    })
            binding.rvhome.adapter = adapter // Set the adapter for the RecyclerView
            binding.rvhome.layoutManager = LinearLayoutManager(activity)
}


}