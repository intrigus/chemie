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

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ListView
import android.widget.TextView
import de.intrigus.chem.ChemieActivity
import de.intrigus.chem.R
import de.intrigus.chem.util.DBSchema
import de.intrigus.chem.util.PseDatabaseHelper
import de.intrigus.chem.util.SimpleCursorLoader

class PseListFragment : ListFragment(), LoaderManager.LoaderCallbacks<Cursor> {
    //Kotlin doesn't support importing constants as far as I know
    val ATOMIC_NUMBER: String = DBSchema.ETable.Cols.ATOMIC_NUMBER
    val SYMBOL: String = DBSchema.ETable.Cols.SYMBOL
    val NAME: String = DBSchema.ETable.Cols.NAME
    val ELEMENT_STATE: String = DBSchema.ETable.Cols.ELEMENT_STATE
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.pse_list, container, false)
        (activity as ChemieActivity).setupToolbar(view.findViewById(R.id.toolbar) as Toolbar)
        (activity as ChemieActivity).supportActionBar.title = "Periodic Table"
        (view.findViewById(android.R.id.list) as ListView).addHeaderView(LayoutInflater.from(activity).inflate(R.layout.pse_list_header, null))
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = PseListAdapter(context, null)
        loaderManager.initLoader(0, null, this);
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        (listAdapter as CursorAdapter).swapCursor(data);
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        //(listAdapter as CursorAdapter).swapCursor(null);
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
        return object : SimpleCursorLoader(activity) {
            override fun loadInBackground(): Cursor {
                val elementCursor: Cursor = PseDatabaseHelper.getInstance(context).readableDatabase.rawQuery("SELECT $ATOMIC_NUMBER _id, * FROM elements ORDER BY $ATOMIC_NUMBER", null)
                //TODO Optimize, we don't need the whole table
                return elementCursor
            }
        }
    }


    inner class PseListAdapter : CursorAdapter {

        override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
            // Save the element number, because it will be different in the click listener
            val elementNumber = cursor!!.getInt(cursor.getColumnIndexOrThrow(ATOMIC_NUMBER))

            val elementNumberView = view!!.findViewById(R.id.element_number) as TextView
            elementNumberView.text = elementNumber.toString() + " "

            val elementSymbol = view.findViewById(R.id.element_symbol) as TextView

            //I'm unable to directly set the textcolor, so I've used html...
            val colorString: String = when (cursor.getString(cursor.getColumnIndex(ELEMENT_STATE))) {
                "gaseous" -> java.lang.String.format("#%06X", (0xFFFFFF and activity.resources.getColor(R.color.gaseous_color)))
                "liquid" -> java.lang.String.format("#%06X", (0xFFFFFF and activity.resources.getColor(R.color.liquid_color)))
                "solid" -> java.lang.String.format("#%06X", (0xFFFFFF and activity.resources.getColor(R.color.solid_color)))
                else -> java.lang.String.format("#%06X", (0xFFFFFF and activity.resources.getColor(R.color.solid_color)))
            }

            elementSymbol.text = Html.fromHtml("<font color=\"$colorString\">" + cursor.getString(cursor.getColumnIndex(SYMBOL)) + "</font>")

            val elementName = view.findViewById(R.id.element_name) as TextView
            elementName.text = " " + cursor.getString(cursor.getColumnIndex(NAME))

            view.setOnClickListener { v -> (activity as ChemieActivity).switchScreen(PseDetailFragment(elementNumber)) }
        }

        override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View? {
            return LayoutInflater.from(context).inflate(R.layout.pse_list_row, parent, false)
        }

        constructor(context: Context?, c: Cursor?) : super(context, c)

        constructor(context: Context?, c: Cursor?, flags: Int) : super(context, c, flags)

        constructor(context: Context?, c: Cursor?, autoRequery: Boolean) : super(context, c, autoRequery)
    }

}