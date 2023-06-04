package ba.etf.rma23.projekat.data.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R

private const val ITEM_TYPE_REVIEW = 0
private const val ITEM_TYPE_RATING = 1

class DetailsAdapter (
    private var impressionsList: List<UserImpression>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class RatingBarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val user : TextView = itemView.findViewById(R.id.username_textview)
        val ratingB : RatingBar = itemView.findViewById(R.id.rating_bar)
    }

    inner class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val user : TextView = itemView.findViewById(R.id.username_textview)
        val review: TextView = itemView.findViewById(R.id.review_textview)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if(viewType == ITEM_TYPE_REVIEW){
            val view = layoutInflater.inflate(R.layout.item_review, parent, false)
            ReviewViewHolder(view)
        } else{
            val view = layoutInflater.inflate(R.layout.item_rate, parent, false)
            RatingBarViewHolder(view)
        }
    }

    override fun getItemCount(): Int = impressionsList.size

    override fun getItemViewType(position: Int): Int {
        return if(impressionsList[position] is UserRating){
            ITEM_TYPE_RATING
        } else {
            ITEM_TYPE_REVIEW
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val userImpression = impressionsList[position]
        if(getItemViewType(position) == ITEM_TYPE_REVIEW){
            viewHolder as ReviewViewHolder
            userImpression as UserReview
            viewHolder.user.text = userImpression.username
            viewHolder.review.text = userImpression.review
        } else{
            viewHolder as RatingBarViewHolder
            userImpression as UserRating
            viewHolder.user.text = userImpression.username
            viewHolder.ratingB.rating = userImpression.rating.toFloat()
        }
    }

    fun updateDetails(impr: List<UserImpression>) {
        this.impressionsList = impr
        notifyDataSetChanged()
    }
}