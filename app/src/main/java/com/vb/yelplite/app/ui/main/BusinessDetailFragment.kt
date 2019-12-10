package com.vb.yelplite.app.ui.main

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.chip.Chip
import com.vb.yelplite.app.R
import com.vb.yelplite.app.domain.BusinessReview
import kotlinx.android.synthetic.main.business_detail.*
import kotlinx.android.synthetic.main.business_photo_item.view.*
import kotlinx.android.synthetic.main.business_review_item.view.*
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.core.parameter.parametersOf


class BusinessDetailFragment : Fragment() {
    companion object {
        fun newInstance(id: String?): BusinessDetailFragment {
            val fragment = BusinessDetailFragment()
            val bundle = Bundle().apply { putString("id", id) }
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var viewModel: BusinessDetailsViewModel
    val photos = ArrayList<String>()
    val reviews = ArrayList<BusinessReview>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.business_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val businessId = arguments?.getString("id")
        if (businessId != null) bindToViewModel(businessId)

        business_detail_photos.apply {
            adapter = BusinessPhotoAdapter(
                photos,
                LayoutInflater.from(context),
                Glide.with(this@BusinessDetailFragment)
            ) { photo ->
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(photo)))
            }

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        business_detail_reviews.apply {
            adapter = BusinessReviewsAdapter(
                reviews, LayoutInflater.from(context),
                Glide.with(this@BusinessDetailFragment)
            ) { profileUrl ->
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl)))
            }

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun bindToViewModel(businessId: String) {
        viewModel = getSharedViewModel { parametersOf(businessId) }

        viewModel.businessDetails.observe(viewLifecycleOwner) {
            business_detail_address.text = it.location.toString()
            business_detail_address.setOnClickListener { view ->
                val geoIntent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "geo:0,0?q="
                                + it.location.toString()
                    )
                )
                view.context.startActivity(geoIntent)
            }
            business_detail_address.paintFlags =
                business_detail_address.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            business_detail_name.text = it.name
            business_detail_categories.removeAllViews()
            it.categories.forEach { category ->
                val categoryView = layoutInflater.inflate(
                    R.layout.category_chip,
                    business_detail_categories,
                    false
                ) as Chip
                categoryView.text = category.title
                business_detail_categories.addView(categoryView)
            }
            business_detail_phone.text = it.display_phone
            business_detail_price.text = it.price ?: "No info"
            business_detail_ratingbar.rating = it.rating
            val existingPhotos = photos.size
            photos.addAll(it.photos)
            business_detail_photos.adapter?.notifyItemRangeInserted(existingPhotos, it.photos.size)
        }

        viewModel.businessReviews.observe(viewLifecycleOwner) {
            val existingReviews = reviews.size
            reviews.addAll(it)
            business_detail_photos.adapter?.notifyItemRangeInserted(existingReviews, it.size)
        }
    }

    class BusinessPhotoAdapter(
        val businessPhotos: List<String>,
        val layoutInflater: LayoutInflater,
        val requestManager: RequestManager,
        val photoCallback: (String) -> Unit
    ) : RecyclerView.Adapter<BusinessPhotoAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply {
                businessPhotos[position].let {
                    requestManager
                        .load(it)
                        .into(image)
                    image.setOnClickListener { _ -> photoCallback(it) }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.business_photo_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return businessPhotos.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val image = itemView.business_detail_photo
        }
    }

    class BusinessReviewsAdapter(
        val businessReviews: List<BusinessReview>,
        val layoutInflater: LayoutInflater,
        val requestManager: RequestManager,
        val reviewCallback: (String) -> Unit
    ) : RecyclerView.Adapter<BusinessReviewsAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply {
                businessReviews[position].let {
                    requestManager
                        .load(it.user.image_url)
                        .into(image)
                    card.setOnClickListener { _ -> reviewCallback(it.user.profile_url) }
                    name.text = it.user.name
                    rating.rating = it.rating.toFloat()
                    text.text = it.text
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.business_review_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return businessReviews.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val card = itemView
            val image = itemView.business_review_user_image
            val name = itemView.business_review_user_name
            val rating = itemView.business_review_rating
            val text = itemView.business_review_text
        }
    }
}