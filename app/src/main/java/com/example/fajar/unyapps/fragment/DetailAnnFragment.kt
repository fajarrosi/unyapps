package com.example.fajar.unyapps.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import com.example.fajar.unyapps.R
import kotlinx.android.synthetic.main.fragment_detail_announcement.*
import android.webkit.WebViewClient
import android.webkit.WebView
import android.content.Intent.getIntent
import android.content.Intent.parseUri
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.widget.FrameLayout


/**
 * Created by fajar on 18/01/18.
 */
class DetailAnnFragment : Fragment() {
    val intent : Intent? = null
    companion object {
        val TAG: String = DetailAnnFragment::class.java.simpleName
        fun newInstance(uri : String,context: Context): DetailAnnFragment {
            var fragmentHome = DetailAnnFragment()
            var args = Bundle()
            args.putString("uri",uri)
            fragmentHome.arguments = args
            return fragmentHome
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_detail_announcement, container, false)
        return rootView
    }
    @Throws(UnsupportedOperationException::class)
    fun buildUri(authority: String) : Uri {
        val builder = Uri.Builder()
        builder.scheme("https")
                .authority(authority)
        return builder.build()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageLoadStatus()
        updateProgress()
        wb_detail.settings.javaScriptEnabled = true
        val u : String = arguments!!.getString("uri")
        try {
//            val uri = buildUri(u)
            wb_detail.loadUrl(u)
        } catch(e: UnsupportedOperationException) {
            e.printStackTrace()
        }
    }

    fun updateProgress() {
        wb_detail.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                pageLoadProgressBar.progress = newProgress
            }


        }
    }
    fun pageLoadStatus() {
        wb_detail.webViewClient = object: WebViewClient() {
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