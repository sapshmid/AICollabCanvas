import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aicollabcanvas.Post
import com.example.aicollabcanvas.R

class PostAdapter(private val posts: List<Post>, private val listener: OnPostInteractionListener) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    interface OnPostInteractionListener {
        fun onDeletePost(position: Int)
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.postProfileName.text = post.user.name
        holder.postProfileRole.text = post.user.role
        holder.postProfilePic.setImageURI(post.user.pic)
        holder.postReplayPic.visibility = if (post.user.role == "Contributor" && post.postPic != null) View.VISIBLE else View.GONE
        holder.postReplayPic.setImageURI(post.postPic)
        holder.postTitle.text = post.title
        holder.postSubtitle.text = post.subtitle
        holder.postText.text = post.text

        holder.btnDeletePost.setOnClickListener {
            listener.onDeletePost(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = posts.size
}