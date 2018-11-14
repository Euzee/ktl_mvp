package rxfeed.euzee.github.com.rxwallheavenfeed

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import rxfeed.euzee.github.com.rxwallheavenfeed.net.NetManager
import rxfeed.euzee.github.com.rxwallheavenfeed.utils.Logg


class ScrollingFeedActivity : AppCompatActivity(), MainPresenter.MainView {

    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_scrolling)
        val receiver: DataReceiver = FeedAdapter()
        setupRecycler(receiver)
        presenter = MainPresenter(this, NetManager(), receiver)
    }


    private fun setupRecycler(receiver: DataReceiver) {
        val feed = findViewById<RecyclerView>(R.id.recycler_feed)
        feed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        feed.adapter = receiver as RecyclerView.Adapter<*>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            feed.setOnScrollChangeListener { _, _, _, _, _ ->
                val offset = feed.computeVerticalScrollOffset()
                val range = feed.computeVerticalScrollRange()
                Logg.e("Scroll", "Range - $range current -$offset")
                if ((range - offset) / range.toFloat() < 0.3f) {
                    presenter?.loadData()
                }
            }
        }
    }
}
