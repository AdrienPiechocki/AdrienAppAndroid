package com.example.myandroidapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.coroutines.coroutineContext

class ListFragment : Fragment() {

    // Equivalent du setContentView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_list,
            container, false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product1 = Product(
            name = "Pain Perdu",
            brand = "LostBread",
            nutriScore = NutriScore.B,
            barcode = "12348765",
            thumbnail = "https://mapatisserie.fr/wp-content/uploads/2021/05/pain-perdu-20210325_203147-01-350x350.jpeg",
            quantity = "300g",
            countries = listOf("France", "Japon", "Belgique"),
            ingredients = listOf("Pain"),
            allergens = emptyList(),
            additives = emptyList(),
        )

        val product2 = Product(
            name = "Petits Pois et Carrotes",
            brand = "Cassegrain",
            nutriScore = NutriScore.A,
            barcode = "3083680085304",
            thumbnail = "https://static.openfoodfacts.org/images/products/308/368/008/5304/front_fr.7.400.jpg",
            quantity = "400g",
            countries = listOf("France", "Japon", "Suisse"),
            ingredients = listOf(
                "Petits pois 66%",
                "Eau",
                "Garniture 2,8% (salade, oignon grelot)",
                "Sucre",
                "Sel",
                "Arôme naturel"
            ),
            allergens = emptyList(),
            additives = emptyList(),
        )

        val list = listOf<Product>(product1, product2)

        view.findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ListAdapter(list, object : OnProductClickListener {
                override fun onProductClicked(prod: Product) {
                    Log.d("TAG", "clicked on $prod")
                    /*
                    findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToDetailsFragment(
                            prod
                        )
                    )
                    */
                }
            })
        }
    }
}

class ListAdapter(private val listOfPositions: List<Product>,
                  val listener: OnProductClickListener,
) : RecyclerView.Adapter<PositionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        return PositionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_item, parent, false),
            listener,
        )
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.updateCell(listOfPositions[position])
    }

    override fun getItemCount(): Int {
        return listOfPositions.size
    }

}

class PositionViewHolder(v: View, val listener: OnProductClickListener) : RecyclerView.ViewHolder(v) {

    private val name : TextView = v.findViewById(R.id.item_name)
    private val img : ImageView = v.findViewById(R.id.item_image)
    private val brand : TextView = v.findViewById(R.id.item_brand)

    fun updateCell(product: Product) {
        itemView.setOnClickListener {
            listener.onProductClicked(product)
        }

        Glide.with(itemView.context).load(product.thumbnail).into(img)
        name.text = product.name
        brand.text = product.brand
    }

}

interface OnProductClickListener {
    fun onProductClicked(position: Product)
}