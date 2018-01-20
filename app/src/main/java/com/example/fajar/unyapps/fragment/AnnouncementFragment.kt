package com.example.fajar.unyapps.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fajar.unyapps.R
import com.example.fajar.unyapps.models.AnnouncementModel
import kotlinx.android.synthetic.main.fragment_announcement.*
import android.util.Log
import com.example.fajar.unyapps.adapter.AnnouncementAdapter
import java.io.IOException
import java.util.regex.Pattern
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.regex.Matcher
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import android.app.ProgressDialog
import android.content.Intent
import android.support.v4.app.FragmentTransaction


/**
 * Created by fajar on 29/12/17.
 */
class AnnouncementFragment : Fragment() {

    var url = "https://www.uny.ac.id/index-pengumuman/"
    var total: String? = null
    var model : MutableList<AnnouncementModel> = mutableListOf<AnnouncementModel>()
//    var models : ArrayList<AnnouncementModel>? = null
    var adapter2 : AnnouncementAdapter? = null
    lateinit var LayoutManager : LinearLayoutManager
    private var isLoading: Boolean = false
    private var page: Int = 0
    private lateinit var mProgressDialog: ProgressDialog
    companion object {
        val TAG: String = AnnouncementFragment::class.java.simpleName
        fun newInstance(): AnnouncementFragment {
            var fragmentHome = AnnouncementFragment()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity!!.title = getString(R.string.announcement)
        var rootView = inflater.inflate(R.layout.fragment_announcement, container, false)
        LayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        return rootView

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoading = false
        page = 1
        JsoupAnnouncement(page).execute()
        rv_announcement.run {
            setHasFixedSize(false)
            layoutManager =  LayoutManager
            adapter = adapter2
        }

    }


    protected fun updateList(list: MutableList<AnnouncementModel>) {
        if (adapter2 == null) {
            adapter2 = AnnouncementAdapter(list)
            rv_announcement.adapter = adapter2
            rv_announcement.addOnScrollListener(object : RecyclerView.OnScrollListener() {

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
                            val loadpage = JsoupAnnouncement(++page)
                            loadpage.execute()
                        }
                    }
                }
            })
        } else {
            adapter2!!.notifyDataSetChanged()
        }
    }

    inner class JsoupAnnouncement(internal var page: Int) : AsyncTask<Void, Void, Void>() {


        override fun doInBackground(vararg p0: Void?): Void? {
            if (isLoading) {
                return null
            }
            isLoading = true
            try {
                val doc : Document = Jsoup.connect(url).data("page", ((page-1).toString())).get()
                for (table in doc.select("tbody")) {
//                    models = ArrayList<AnnouncementModel>()

                    for ( row in table.select("tr")) {
                        val td : Elements = row.select("td")
                        val date : String= td[2].text().trim()
                        Log.d("match", "doInBackground: " + date)
                        val matcher : Matcher = Pattern.compile("Post date.*?, (\\w+) (\\d+)",
                                Pattern.CASE_INSENSITIVE).matcher(date)
                        Log.d("if", "doInBackground: " + "test")
                        if (matcher.find()) {
                            val arg_month : String = matcher.group(1)
                            Log.d("month", "doInBackground: " + arg_month)
                            var arg_num : String = matcher.group(2)
                            arg_num = String.format("%2s", arg_num).replace(" ", "0")
                            Log.d("num", "doInBackground: " + arg_num)
                            val month : String = arg_month.substring(0, 3)
                            Log.d("month2", "doInBackground: " + month)
                            total = arg_num + " " + month
                        }
                        val title : String = td[1].text().trim()
                        val aHref : Elements? = row.select("strong > a[href]")
                        val aHrefStr : String = aHref!!.attr("abs:href")
                        Log.d("uri", "uri: " + aHrefStr)
                        model.add(AnnouncementModel(title,total,aHrefStr))
//                        models!!.add(AnnouncementModel(title, total))
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
            adapter2!!.setOnClickListener(object : AnnouncementAdapter.OnClickListener {
                override fun onClick(adapterPosition: Int) {
                    Log.d("adapter", "onClick: " + adapterPosition)
//                    Toast.makeText(context,model.get(adapterPosition).uri)
                   val ft = fragmentManager!!.beginTransaction()
                    val frag = DetailAnnFragment.newInstance(model[adapterPosition].uri,context!!)
                    ft.add(R.id.container,frag as Fragment,frag.tag).commit()
//                    val intent = Intent(context, DetailAnnFragment::class.java)
//                    intent.putExtra("uri", model.get(adapterPosition).uri)
//                    startActivity(intent)

                }
            })
            isLoading = false


        }

        override fun onPreExecute() {
            super.onPreExecute()
            mProgressDialog = ProgressDialog(context)
            mProgressDialog.setTitle("Announcement")
            mProgressDialog.setMessage("Loading...")
            mProgressDialog.isIndeterminate = false
            mProgressDialog.show()
        }


    }

}




