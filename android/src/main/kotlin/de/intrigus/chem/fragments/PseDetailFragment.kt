/*Copyright 2015 Simon Gerst

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package de.intrigus.chem.fragments

import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder
import com.squareup.picasso.Picasso
import de.intrigus.chem.ChemieActivity
import de.intrigus.chem.R
import de.intrigus.chem.util.DBSchema
import de.intrigus.chem.util.DrawableHelper
import de.intrigus.chem.util.PseDatabaseHelper
import java.util.*

class PseDetailFragment(val position: Int) : Fragment() {
    lateinit private var pseDetailList: RecyclerView
    lateinit private var elementImage: ImageView
    lateinit private var toolbar: CollapsingToolbarLayout

    val ATOMIC_NUMBER: String = DBSchema.ETable.Cols.ATOMIC_NUMBER
    val SYMBOL: String = DBSchema.ETable.Cols.SYMBOL
    val NAME: String = DBSchema.ETable.Cols.NAME
    val COLS = DBSchema.ETable.Cols

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.pse_detail, container, false)
        pseDetailList = view.findViewById(R.id.pse_detail_list) as RecyclerView
        pseDetailList.layoutManager = LinearLayoutManager(activity)
        toolbar = view.findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        (activity as ChemieActivity).setupToolbar(view.findViewById(R.id.toolbar) as Toolbar)
        elementImage = view.findViewById(R.id.element_image) as ImageView
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val elementCursor = PseDatabaseHelper.getInstance(context).readableDatabase.rawQuery("SELECT $ATOMIC_NUMBER _id, * FROM elements WHERE $ATOMIC_NUMBER=$position", null)
        elementCursor.moveToFirst()
        toolbar.title = elementCursor.getString(elementCursor.getColumnIndex(NAME))


        val adapter = PseDetailAdapter(getGroupItems())

        pseDetailList.adapter = adapter

        Picasso.with(activity)
                .load(DrawableHelper.getElementDrawable(position, context))
                .error(R.drawable.element_not_found)
                .placeholder(R.drawable.element_not_found)
                .into(elementImage);
    }

    private fun getGroupItems(): ArrayList<GroupItem> {

        val elementCursor = PseDatabaseHelper.getInstance(context).readableDatabase.rawQuery("SELECT $ATOMIC_NUMBER _id, * FROM elements WHERE $ATOMIC_NUMBER=$position", null)
        elementCursor.moveToFirst()

        val groupItems = ArrayList<GroupItem>()

        val gProps = GroupItem("General properties")
        gProps.setChildObjectList(getGeneralProperties(elementCursor))
        groupItems.add(gProps)

        val phyProps = GroupItem("Physical properties")
        phyProps.setChildObjectList(getPhysicalProperties(elementCursor))
        groupItems.add(phyProps)

        val aProps = GroupItem("Atomic properties")
        aProps.setChildObjectList(getAtomicProperties(elementCursor))
        groupItems.add(aProps)

        val oProps = GroupItem("Other properties")
        oProps.setChildObjectList(getOtherProperties(elementCursor))
        groupItems.add(oProps)

        return groupItems
    }

    private fun getGeneralProperties(cursor: Cursor): List<String> {
        val l = ArrayList<String>()
        l.add("Symbol: " + cursor.getString(cursor.getColumnIndex(COLS.SYMBOL)))
        l.add("Name: " + cursor.getString(cursor.getColumnIndex(NAME)))
        l.add("Atomic number: " + cursor.getString(cursor.getColumnIndex(ATOMIC_NUMBER)))
        l.add("Element category: " + cursor.getString(cursor.getColumnIndex(COLS.SERIES)))
        l.add("Standard atomic weight: " + cursor.getString(cursor.getColumnIndex(COLS.MASS)) + " u")
        l.add("Electron configuration: " + cursor.getString(cursor.getColumnIndex(COLS.CORE_CONFIGURATION)))
        return l
    }

    private fun getPhysicalProperties(cursor: Cursor): List<String> {
        val l = ArrayList<String>()
        l.add("Phase: " + cursor.getString(cursor.getColumnIndex(COLS.ELEMENT_STATE)))
        l.add("Melting point: " + cursor.getString(cursor.getColumnIndex(COLS.MELTING_POINT)) + " K")
        l.add("Boiling point: " + cursor.getString(cursor.getColumnIndex(COLS.BOILING_POINT)) + " K")
        l.add("STP Density: " + cursor.getString(cursor.getColumnIndex(COLS.STP_DENSITY)) + " g/cm3")
        l.add("Heat of fusion: " + cursor.getString(cursor.getColumnIndex(COLS.HEAT_OF_FUSTION)) + " kJ/mol")
        l.add("Heat of vaporization: " + cursor.getString(cursor.getColumnIndex(COLS.HEAT_OF_VAPOR)) + " kJ/mol")
        // l.add("Specific heat: " + cursor.getString(cursor.getColumnIndex(COLS.SPECIFIC_HEAT))  + " kJ/mol")
        return l
    }

    private fun getAtomicProperties(cursor: Cursor): List<String> {
        val l = ArrayList<String>()
        l.add("Common ions: " + cursor.getString(cursor.getColumnIndex(COLS.COMMON_IONS)))
        l.add("Electronegativity: " + cursor.getString(cursor.getColumnIndex(COLS.ELECTRO_NEGATIVITY)))
        l.add("1st Ionization energies: " + cursor.getString(cursor.getColumnIndex(COLS.FIRST_IONIZATION)) + " kJ/mol")
        l.add("2nd Ionization energies: " + cursor.getString(cursor.getColumnIndex(COLS.SECOND_IONIZATION)) + " kJ/mol")
        l.add("3rd Ionization energies: " + cursor.getString(cursor.getColumnIndex(COLS.THIRD_IONIZATION)) + " kJ/mol")
        l.add("Atomic radius: " + cursor.getString(cursor.getColumnIndex(COLS.EMPIRICAL_RADIUS)) + " pm")
        l.add("Covalent radius: " + cursor.getString(cursor.getColumnIndex(COLS.COVALENT_RADIUS)) + " pm")
        l.add("Van der Waals radius: " + cursor.getString(cursor.getColumnIndex(COLS.VAN_DE_WA_RADIUS)) + " pm")
        return l
    }

    private fun getOtherProperties(cursor: Cursor): List<String> {
        val l = ArrayList<String>()
        l.add("Young's modulus: " + cursor.getString(cursor.getColumnIndex(COLS.YOUNG_MODULUS)) + " GPa")
        l.add("Shear modulus: " + cursor.getString(cursor.getColumnIndex(COLS.SHEAR_MODULUS)) + " GPa")
        l.add("Bulk modulus: " + cursor.getString(cursor.getColumnIndex(COLS.BULK_MODULUS)) + " GPa")
        l.add("Mohs hardness: " + cursor.getString(cursor.getColumnIndex(COLS.MOHS_HARDNESS)))
        l.add("Vickers hardness: " + cursor.getString(cursor.getColumnIndex(COLS.VICKERS_HARDNESS)) + " MPa")
        l.add("Brinell hardness: " + cursor.getString(cursor.getColumnIndex(COLS.BRINELL_HARNESS)) + " MPa")
        return l
    }


    class GroupItem(val name: String) : ParentListItem {
        lateinit private var childrenList: List<String>
        override fun getChildItemList(): List<String> {
            return childrenList
        }

        override fun isInitiallyExpanded(): Boolean {
            return true
        }

        fun setChildObjectList(list: List<String>) {
            childrenList = list;
        }

    }

    class GroupViewHolder(itemView: View) : ParentViewHolder(itemView) {
        public var textView: TextView
        public var arrowIndicator: ImageButton

        init {
            textView = itemView.findViewById(R.id.group_title) as TextView
            arrowIndicator = itemView.findViewById(R.id.group_expand_arrow) as ImageButton

            val listener: (View) -> Unit = { v ->
                when (isExpanded) {
                    true -> collapseView()
                    false -> expandView()
                }
            }
            itemView.setOnClickListener(listener)
            arrowIndicator.setOnClickListener(listener)
        }

        override fun onExpansionToggled(expanded: Boolean) {
            when (expanded) {
                true -> arrowIndicator.rotation = 0f
                false -> arrowIndicator.rotation = -180f
            }
        }

        override fun shouldItemViewClickToggleExpansion(): Boolean {
            return false
        }

    }

    class RowViewHolder(itemView: View) : ChildViewHolder(itemView) {
        public var textView: TextView

        init {
            textView = itemView.findViewById(R.id.child_item_text) as TextView
        }
    }

    //FIXME Clicking the indicator doesn't work for some nice reason...
    inner class PseDetailAdapter(list: List<ParentListItem>) : ExpandableRecyclerAdapter<GroupViewHolder, RowViewHolder>(list) {
        override fun onCreateChildViewHolder(childViewGroup: ViewGroup?): RowViewHolder? {
            return RowViewHolder(LayoutInflater.from(activity).inflate(R.layout.pse_detail_item, childViewGroup, false))
        }

        override fun onBindParentViewHolder(parentViewHolder: GroupViewHolder, position: Int, parentListItem: ParentListItem?) {
            parentViewHolder.textView.text = ( parentListItem as GroupItem).name
        }

        override fun onBindChildViewHolder(childViewHolder: RowViewHolder, position: Int, childListItem: Any?) {
            childViewHolder.textView.text = (childListItem as String)
        }

        override fun onCreateParentViewHolder(parentViewGroup: ViewGroup?): GroupViewHolder? {
            return GroupViewHolder(LayoutInflater.from(activity).inflate(R.layout.pse_detail_group, parentViewGroup, false))
        }
    }
}