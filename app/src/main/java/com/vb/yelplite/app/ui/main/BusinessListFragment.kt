package com.vb.yelplite.app.ui.main

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.chip.Chip
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
                orientation = LinearLayoutManager.HORIZONTAL
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

        viewModel.businesses.observe(viewLifecycleOwner, Observer { businesses ->
            val prevSize = businessList.size
            businessList.addAll(businesses)
            business_list_recycler.adapter?.notifyItemRangeInserted(prevSize, businesses.size)
        })
    }

    class BusinessListAdapter(
        val businesses: List<Business>,
        val layoutInflater: LayoutInflater,
        val requestManager: RequestManager,
        val detailsCallback: (Business) -> Unit
    ) : RecyclerView.Adapter<BusinessListAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply {
                businesses[position].let {
                    requestManager
                        .load(it.image_url)
                        .placeholder(R.drawable.restaurant_placeholder)
                        .into(image)
                    name.text = it.name
                    price.text = it.price ?: "-"
                    address.text = it.location.toString()
                    address.setOnClickListener {view ->
                        val geoIntent = Intent(
                            Intent.ACTION_VIEW, Uri.parse(
                                "geo:0,0?q="
                                        + it.location.toString()
                            )
                        )
                        view.context.startActivity(geoIntent)
                    }
                    address.paintFlags = address.paintFlags or Paint.UNDERLINE_TEXT_FLAG

                    phone.text = it.phone
                    rating.rating = it.rating
                    reviewCount.text = "(${it.review_count})"
                    categories.removeAllViews()
                    it.categories.forEach {category ->
                        val categoryView = layoutInflater.inflate(R.layout.category_chip, categories, false) as Chip
                        categoryView.text = category.title
                        categories.addView(categoryView)
                    }
                    detailsBtn.setOnClickListener {_ -> detailsCallback(it) }
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
            val image = itemView.business_item_image
            val name = itemView.business_item_name_tv
            val categories = itemView.business_item_categories
            val price = itemView.business_item_price
            val address = itemView.business_item_address
            val phone = itemView.business_item_phone
            val rating = itemView.business_item_ratingbar
            val reviewCount = itemView.business_item_review_count
            val detailsBtn = itemView.business_item_details
        }
    }
}
