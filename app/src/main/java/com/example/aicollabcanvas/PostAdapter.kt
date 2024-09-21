package com.example.aicollabcanvas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val posts: List<Post>, private val listener: OnPostInteractionListener?, private val showEditButtons: Boolean) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    interface OnPostInteractionListener {
        fun onDeletePost(position: Int)
        fun onEditPost(position: Int)
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postProfileName: TextView = itemView.findViewById(R.id.tvProfileName)
        val postProfileRole: TextView = itemView.findViewById(R.id.tvProfileRole)
        val postProfilePic: ImageView = itemView.findViewById(R.id.ivProfilePic)
        val postReplayPic: ImageView = itemView.findViewById(R.id.ivPostReplayPic)
        val postTitle: TextView = itemView.findViewById(R.id.tvPostTitle)
        val postSubtitle: TextView = itemView.findViewById(R.id.tvPostSubtitle)
        val postText: TextView = itemView.findViewById(R.id.tvPostText)
        val btnDeletePost: ImageButton = itemView.findViewById(R.id.ibtnDeletePost)
        val btnEditPost: ImageButton = itemView.findViewById(R.id.ibtnEditPostButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.postProfileName.text = post.profile?.name
        holder.postProfileRole.text = post.profile?.role
        Utils.setImageIntoView(holder.postProfilePic, post.profile?.profilePic, R.drawable.empty_profile)
        holder.postReplayPic.visibility = if (post.profile?.role == "Contributor" && post.postPic != null && post.postPic.trim() != "") View.VISIBLE else View.GONE
        Utils.setImageIntoView(holder.postReplayPic, post.postPic, R.drawable.loading_pic)
        holder.postTitle.text = post.title
        holder.postSubtitle.text = post.subtitle
        holder.postText.text = post.text

        holder.btnDeletePost.setOnClickListener {
            listener?.onDeletePost(holder.adapterPosition)
        }

        holder.btnEditPost.setOnClickListener {
            listener?.onEditPost(holder.adapterPosition)
        }

        if (showEditButtons) {
            holder.btnEditPost.visibility = View.VISIBLE
            holder.btnDeletePost.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = posts.size
}