import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aicollabcanvas.Post
import com.example.aicollabcanvas.PostFragment
import com.example.aicollabcanvas.R

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View, val frameLayout: FrameLayout) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        val frameLayout = view.findViewById<FrameLayout>(R.id.frameLayoutContainer)
        frameLayout.id = View.generateViewId()  // Ensure each FrameLayout has a unique ID
        return PostViewHolder(view, frameLayout)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Remove any existing fragments first
        val fragmentManager = (holder.itemView.context as FragmentActivity).supportFragmentManager

        // Create a new instance of the fragment and add it
        val postFragment = PostFragment.newInstance(posts[position])
        fragmentManager.beginTransaction()
            .replace(holder.frameLayout.id, postFragment)
            .commit()

        // After committing the fragment transaction, notify the item changed to re-layout the Recycle
        holder.itemView.requestLayout()  // Request layout to update RecyclerView layout
    }

    override fun getItemCount(): Int = posts.size
}
