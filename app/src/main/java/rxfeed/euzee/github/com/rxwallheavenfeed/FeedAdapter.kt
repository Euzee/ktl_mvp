package rxfeed.euzee.github.com.rxwallheavenfeed

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import rxfeed.euzee.github.com.rxwallheavenfeed.models.PictureLink
import rxfeed.euzee.github.com.rxwallheavenfeed.net.GlideApp
import java.util.*

internal class FeedAdapter : RecyclerView.Adapter<FeedAdapter.FeedHolder>(), DataReceiver {
    override fun onNewData(parsedLinks: ArrayList<PictureLink>) {
        setLinks(parsedLinks)
    }

    private val links = ArrayList<PictureLink>()

    fun setLinks(links: ArrayList<PictureLink>) {
        this.links.addAll(links)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        return FeedHolder(LayoutInflater.from(parent.context).inflate(R.layout.placeholder_item, null))
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        holder.loadPhoto(links[position].link)
    }

    override fun getItemCount(): Int {
        return links.size
    }

    internal inner class FeedHolder(private var root: View) : RecyclerView.ViewHolder(root) {

        fun loadPhoto(link: String?) {
            GlideApp.with(root.context)
                    .load(link)
                    .into(root.findViewById<View>(R.id.image) as ImageView)
        }


    }
}
