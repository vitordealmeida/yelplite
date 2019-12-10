package com.vb.yelplite.app.ui.main

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vb.yelplite.app.R
import com.vb.yelplite.app.domain.Business
import kotlinx.android.synthetic.main.business_list.*
import kotlinx.android.synthetic.main.business_list_item.view.*
import kotlinx.android.synthetic.main.business_list_item_card.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BusinessListFragment : Fragment() {

    companion object {
        fun newInstance() = BusinessListFragment()
    }

    val viewModel by sharedViewModel<MainViewModel>()
    val businessList = ArrayList<Business>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.business_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        business_list_recycler.apply {
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = if (context.resources.getBoolean(R.bool.isTablet)) {
                    LinearLayoutManager.VERTICAL
                } else {
                    LinearLayoutManager.HORIZONTAL
                }
            }
            adapter = BusinessListAdapter(
                businessList,
                LayoutInflater.from(context),
                Glide.with(this@BusinessListFragment)
            ) {
                viewModel.seeBusinessDetails(it)
            }
            PagerSnapHelper().attachToRecyclerView(this)
        }

        viewModel.businesses.observe(viewLifecycleOwner) { businesses ->
            val prevSize = businessList.size
            businessList.addAll(businesses)
            business_list_recycler.adapter?.notifyItemRangeInserted(prevSize, businesses.size)
        }

        viewModel.selectedBusinessId.observe(viewLifecycleOwner) { businessId ->
            business_list_recycler.adapter?.notifyItemChanged(businessList.indexOfFirst { it.id == businessId })
        }
    }

    class BusinessListAdapter(
        val businesses: List<Business>,
        val layoutInflater: LayoutInflater,
        val requestManager: RequestManager,
        val detailsCallback: (Business) -> Unit
    ) : RecyclerView.Adapter<BusinessListAdapter.ViewHolder>() {
        var selectedBusinessId: String? = null

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply {
                businesses[position].let {
                    item.isSelected = it.id == selectedBusinessId
                    if (image != null) {
                        requestManager
                            .load(it.image_url)
                            .placeholder(R.drawable.restaurant_placeholder)
                            .into(image)
                    }
                    name.text = it.name
                    price?.text = it.price ?: "-"

                    rating?.rating = it.rating
                    reviewCount?.text = "(${it.review_count})"
                    categories.removeAllViews()
                    it.categories.forEach { category ->
                        val categoryView = layoutInflater.inflate(
                            R.layout.category_chip,
                            categories,
                            false
                        ) as Chip
                        categoryView.text = category.title
                        categories.addView(categoryView)
                    }
                    detailsBtn?.setOnClickListener { _ -> detailsCallback(it) }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.business_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return businesses.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val item = itemView
            val image: ImageView? = itemView.business_item_image
            val name: TextView = itemView.business_item_name_tv
            val categories: ChipGroup = itemView.business_item_categories
            val price: TextView? = itemView.business_item_price
            val rating: RatingBar? = itemView.business_item_ratingbar
            val reviewCount: TextView? = itemView.business_item_review_count
            val detailsBtn: MaterialButton? = itemView.business_item_details
        }
    }
}
