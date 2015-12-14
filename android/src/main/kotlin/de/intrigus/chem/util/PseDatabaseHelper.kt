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

package de.intrigus.chem.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PseDatabaseHelper private constructor(val context: Context) : SQLiteOpenHelper(context, PseDatabaseHelper.DATABASE_NAME, null, PseDatabaseHelper.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        run {
            val stream = context.applicationContext.resources.assets.open("elements_table_create.sql")
            stream.buffered().reader().use { reader ->
                db.execSQL(reader.readText())
            }
        }

        run {
            val stream = context.applicationContext.resources.assets.open("elements_table_content.sql")
            stream.buffered().reader().use { reader ->
                db.execSQL(reader.readText())
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        public val ELEMENT_SMYBOL_INDEX = 1
        public val ELEMENT_NAME_INDEX = 2
        private val ELEMENT_NAME = "elementName"
        private val ELEMENT_SYMBOL = "name"
        private val DATABASE_NAME = "chemDatabase"

        private val DATABASE_VERSION = 1
        private val TABLE_ELEMENTS = "elements"

        private var sInstance: PseDatabaseHelper? = null

        @Synchronized fun getInstance(context: Context): PseDatabaseHelper {
            if (sInstance == null) {
                sInstance = PseDatabaseHelper(context.applicationContext)
            }
            return sInstance as PseDatabaseHelper
        }
    }

}