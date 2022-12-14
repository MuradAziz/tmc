
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tmcinsaat.R
import com.example.tmcinsaat.downloadUrl
import com.example.tmcinsaat.model.Products
import com.example.tmcinsaat.placeHolderDrawble
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.row_item.view.*
import kotlinx.android.synthetic.main.row_item.view.productcost
import kotlinx.android.synthetic.main.row_item.view.productdescription
import kotlinx.android.synthetic.main.row_item.view.productimage
import kotlinx.android.synthetic.main.row_item.view.productname
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

class ProductListAdapter(private val products: ArrayList<Products>):
    RecyclerView.Adapter<ProductListAdapter.PostHolder>(), Filterable {
    var countryFilterList = ArrayList<Products>()
    lateinit var documentSnapshot: DocumentSnapshot
    private lateinit var auth:FirebaseAuth
    init {
        countryFilterList = products
    }

    private lateinit var db:FirebaseFirestore
     class PostHolder(val view:View): RecyclerView.ViewHolder(view) {
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflate=LayoutInflater.from(parent.context)
        val view=inflate.inflate(R.layout.row_item, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        var navController: NavController?

        holder.itemView.productname.text="Product name: "+countryFilterList[position].productname
        holder.itemView.productcost.text="Product price: "+products[position].productprice
        holder.itemView.productdescription.text="Product description: "+products[position].productDetail
        holder.itemView.productimage.downloadUrl(products[position].downloadUrl, placeHolderDrawble(holder.itemView.context))


        val productName = products[position].productname
        val productPrice = products[position].productprice
        val productDetail = products[position].productDetail
        val productUrl = products[position].downloadUrl
        val bundle = Bundle()
        holder.itemView.setOnClickListener {
            bundle.putString("Pname", productName)
            bundle.putString("Pprice", productPrice)
            bundle.putString("Pdetail", productDetail)
            bundle.putString("Purl", productUrl)

            navController = Navigation.findNavController(it)
            navController!!.navigate(R.id.productDetailsFragment, bundle)

        }
        holder.itemView.mMenus.setOnClickListener {
            db = Firebase.firestore
            val popupMenus = PopupMenu(holder.itemView.context, it)
            auth=FirebaseAuth.getInstance()
            popupMenus.inflate(R.menu.menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit -> {
                        val v = LayoutInflater.from(holder.itemView.context)
                            .inflate(R.layout.fragment_for_adding, null)
                        val name = v.findViewById<EditText>(R.id.productname)
                        AlertDialog.Builder(holder.itemView.context)
                            .setView(v)
                            .setPositiveButton("OK") { dialog, _ ->
                                products[it.itemId].productname = name.text.toString()
                                db.collection("Products").document("Products")
                                    .update(mapOf("productname" to name))
                                notifyDataSetChanged()
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Duzelis edildi",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }.create()
                            .show()
                        true
                    }

                    R.id.delete -> {
                        AlertDialog.Builder(holder.itemView.context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.delete)
                            .setMessage("Are you sure to delete?")
                            .setPositiveButton("Yes") { dialog, _ ->
                               // delete(productName)
                                val doc = db.collection("Products").document().id
//                                val doc=db.collection("Products").document(it.itemId.toString())
//
//                                Toast.makeText(
//                                    holder.itemView.context,
//                                    doc.toString(),
//                                    Toast.LENGTH_LONG
//                                ).show()

                                db.collection("Products").document()
                                   .delete()
                                   .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                                notifyDataSetChanged()
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }

    }

    private fun delete(productName: String) {
        db.collection("Products").whereEqualTo("productname", productName).get().addOnCompleteListener(
            OnCompleteListener {task->
                if(task.isSuccessful && task.result.isEmpty){
                    documentSnapshot= task.result.documents[0]
                    var docId=documentSnapshot.id
                    db.collection("Products").document(docId).delete()
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                }
            }
        )


    }


    override fun getItemCount(): Int {
        return countryFilterList.size
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                countryFilterList = if (charSearch.isEmpty()) {
                    products
                } else {
                    val resultList = ArrayList<Products>()
                    for (row in products) {
                        if (row.productname.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<Products>
                notifyDataSetChanged()
            }

        }
    }

}
