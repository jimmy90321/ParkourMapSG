package jimmyliao.com.parkourmapsg.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jimmyliao.com.parkourmapsg.Module.Spot
import jimmyliao.com.parkourmapsg.R

class SpotAdapter(val context: Context, val spots: List<Spot>) : RecyclerView.Adapter<SpotAdapter.SpotVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spot, parent, false)
        return SpotVH(view)
    }

    override fun getItemCount(): Int = spots.count()

    override fun onBindViewHolder(holder: SpotVH, position: Int) {
        holder.bind(spots[position])
    }

    inner class SpotVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.tv_spot_name)
        private val desc = itemView.findViewById<TextView>(R.id.tv_spot_description)
        private var photo = itemView.findViewById<ImageView>(R.id.iv_spot_photo)

        fun bind(spot: Spot) {
            name.text = spot.name
            desc.text = spot.description
            photo.setImageDrawable(spot.photo)
        }
    }
}