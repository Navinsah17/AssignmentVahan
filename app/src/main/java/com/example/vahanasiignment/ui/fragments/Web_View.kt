package com.example.vahanasiignment.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.vahanasiignment.R
import com.example.vahanasiignment.databinding.FragmentHomeBinding
import com.example.vahanasiignment.databinding.FragmentWebViewBinding


class Web_View : Fragment() {

    lateinit var bindingWv : FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingWv = FragmentWebViewBinding.inflate(layoutInflater)

        val url = arguments!!.getString("url").toString()

        bindingWv.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                bindingWv.progressBar.visibility = View.VISIBLE
                bindingWv.webView.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                bindingWv.progressBar.visibility = View.GONE
                bindingWv.webView.visibility = View.VISIBLE
            }

        }
        bindingWv.webView.loadUrl(url)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingWv.root


    }


}