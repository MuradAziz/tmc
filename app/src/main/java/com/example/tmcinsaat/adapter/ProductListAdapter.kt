
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tmcinsaat.R
import com.example.tmcinsaat.model.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item.view.*

class ProductListAdapter( private val products: ArrayList<Products>):
    RecyclerView.Adapter<ProductListAdapter.PostHolder>() {

     class PostHolder(val view:View): RecyclerView.ViewHolder(view) {
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflate=LayoutInflater.from(parent.context)
        val view=inflate.inflate(R.layout.row_item, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        var navController: NavController?

        holder.itemView.productname.text=products[position].productname
        holder.itemView.productcost.text=products[position].productprice
        holder.itemView.productdescription.text=products[position].productDetail
        Picasso.get().load(products[position].downloadUrl).into(holder.itemView.productimage)

        val productName=products[position].productname
        val productPrice=products[position].productprice
        val productDetail=products[position].productDetail
        val productUrl=products[position].downloadUrl
        val bundle=Bundle()
        holder.itemView.setOnClickListener{
            bundle.putString("Pname", productName)
            bundle.putString("Pprice", productPrice)
            bundle.putString("Pdetail", productDetail)
            bundle.putString("Purl", productUrl)

            navController=Navigation.findNavController(it)
            navController!!.navigate(R.id.productDetailsFragment, bundle)

        }
        holder.itemView.mMenus.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}
