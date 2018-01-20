package com.example.fajar.unyapps.fragment

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.fajar.unyapps.R
import kotlinx.android.synthetic.main.fragment_detail_news.*

/**
 * Created by fajar on 19/01/18.
 */
class DetailNewsFragment : Fragment() {
    companion object {
        val TAG: String = DetailAnnFragment::class.java.simpleName
        fun newInstance(uri : String,context: Context): DetailNewsFragment {
            var fragmentHome = DetailNewsFragment()
            var args = Bundle()
            args.putString("uri",uri)
            fragmentHome.arguments = args
            return fragmentHome
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_detail_news, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageLoadStatus()
        updateProgress()
        wb_detail2.settings.javaScriptEnabled = true
        val u : String = arguments!!.getString("uri")
        try {
            wb_detail2.loadUrl(u)
        } catch(e: UnsupportedOperationException) {
            e.printStackTrace()
        }
    }

    fun updateProgress() {
        wb_detail2.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                pageLoadProgressBar.progress = newProgress
            }


        }
    }
    fun pageLoadStatus() {
        wb_detail2.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                pageLoadProgressBar.visibility = View.GONE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                pageLoadProgressBar.visibility = View.VISIBLE
                pageLoadProgressBar.progress = 0
            }
        }
    }

}