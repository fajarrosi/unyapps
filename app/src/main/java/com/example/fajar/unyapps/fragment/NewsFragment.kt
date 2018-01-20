package com.example.fajar.unyapps.fragment

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.fajar.unyapps.R
import com.example.fajar.unyapps.adapter.NewsAdapter
import com.example.fajar.unyapps.models.NewsModel
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.item_news.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by fajar on 28/12/17.
 */
class NewsFragment : Fragment() {

    var url = "https://www.uny.ac.id/index-berita/"
    var total: String? = null
    var model : MutableList<NewsModel> = mutableListOf<NewsModel>()
    var adapter2 : NewsAdapter? = null
    lateinit var LayoutManager : LinearLayoutManager
    private var isLoading: Boolean = false
    private var page: Int = 0
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var mProgressBar : ProgressBar
    companion object {
        val TAG: String = NewsFragment::class.java.simpleName
        fun newInstance(): NewsFragment {
            var fragmentHome = NewsFragment()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity!!.title = getString(R.string.news)
        var rootView = inflater.inflate(R.layout.fragment_news, container, false)
        LayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        return rootView

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoading = false
        page = 1
        JsoupNews(page).execute()
        rv_news.run {
            setHasFixedSize(false)
            layoutManager =  LayoutManager
            adapter = adapter2
        }

    }


    protected fun updateList(list: MutableList<NewsModel>) {
        if (adapter2 == null) {
            adapter2 = NewsAdapter(list, context!!)
            rv_news.adapter = adapter2
            rv_news.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = LayoutManager.childCount
                    val totalItemCount = LayoutManager.itemCount
                    val pastVisibleitems = LayoutManager.findFirstVisibleItemPosition()
                    val `var` = "pv $pastVisibleitems vi $visibleItemCount ti $totalItemCount"
                    //                    Toast.makeText(IndexPengumuman.this, var, Toast.LENGTH_SHORT).show();
                    Log.d("vsr", "onScrolled: " + `var`)
                    if (pastVisibleitems + visibleItemCount >= totalItemCount) {

                        if (!isLoading) {
                            val loadpage = JsoupNews(++page)
                            loadpage.execute()
                        }
                    }
                }
            })
        } else {
            adapter2!!.notifyDataSetChanged()
        }
    }

    inner class JsoupNews(internal var page: Int) : AsyncTask<Void, Void, Void>() {


        override fun doInBackground(vararg p0: Void?): Void? {
            if (isLoading) {
                return null
            }
            isLoading = true
            try {
                val doc : Document = Jsoup.connect(url).data("page", ((page-1).toString())).get()
                for (table in doc.select("tbody")) {
                    for ( row in table.select("tr")) {
                       for (data in row.select("td")){
                            val div : Elements = data.select("div")
                           val title : String = div[2].text().trim()
                           val aHref : Elements? = row.select("div > strong > a[href]")
                           val aHrefStr : String = aHref!!.attr("abs:src")
                           model.add(NewsModel(title,aHrefStr))
                       }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                isLoading = false

            }
            return null
        }
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            updateList(model)
            mProgressDialog.dismiss()
//            adapter = AnnouncementAdapter(models!!)
//            rv_announcement.adapter = adapter
            adapter2!!.setOnClickListener(object : NewsAdapter.OnClickListener {
                override fun onClick(adapterPosition: Int) {
//                    Log.d("adapter", "onClick: " + adapterPosition)
//                    val ft = fragmentManager!!.beginTransaction()
//                    val frag = DetailNewsFragment.newInstance(model[adapterPosition].uri,context!!)
//                    ft.add(R.id.container,frag as Fragment,frag.tag).commit()
//                    Toast.makeText(context, adapterPosition, Toast.LENGTH_SHORT).show()


                }
            })
            isLoading = false


        }

        override fun onPreExecute() {
            super.onPreExecute()
            mProgressDialog = ProgressDialog(context)
            mProgressDialog.setTitle("News")
            mProgressDialog.setMessage("Loading...")
            mProgressDialog.isIndeterminate = false
            mProgressDialog.show()
        }


    }

}