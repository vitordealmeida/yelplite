package com.vb.yelplite.app.ui.main

import android.content.Intent
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
import kotlinx.android.synthetic.main.business_detail.*
import kotlinx.android.synthetic.main.business_photo_item.view.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.business_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel { parametersOf(arguments?.getString("id")) }

        viewModel.businessDetails.observe(viewLifecycleOwner) {
            business_detail_address.text = it.location.toString()
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
            business_detail_review_count.text = it.review_count.toString()
            business_detail_photos.apply {
                adapter = BusinessPhotoAdapter(
                    it.photos,
                    LayoutInflater.from(context),
                    Glide.with(this@BusinessDetailFragment)
                ) { photo ->
                    context.startActivity(Intent().putExtra("photo", photo))
                }

                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            }
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
}