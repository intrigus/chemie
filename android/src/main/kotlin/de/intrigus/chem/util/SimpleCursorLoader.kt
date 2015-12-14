package de.intrigus.chem.util

/*
* Copyright (C) 2010 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.content.Context
import android.database.Cursor
import android.support.v4.content.AsyncTaskLoader

/**
 * Used to write apps that run on platforms prior to Android 3.0. When running
 * on Android 3.0 or above, this implementation is still used; it does not try
 * to switch to the framework's implementation. See the framework SDK
 * documentation for a class overview.
 *
 *
 * This was based on the CursorLoader class
 */
abstract class SimpleCursorLoader(context: Context) : AsyncTaskLoader<Cursor>(context) {
    private var mCursor: Cursor? = null

    /* Runs on a worker thread */
    abstract override fun loadInBackground(): Cursor

    /* Runs on the UI thread */
    override fun deliverResult(cursor: Cursor?) {
        if (isReset) {
            // An async query came in while the loader is stopped
            cursor?.close()
            return
        }
        val oldCursor = mCursor
        mCursor = cursor

        if (isStarted) {
            super.deliverResult(cursor)
        }

        if (oldCursor != null && oldCursor !== cursor && !oldCursor.isClosed) {
            oldCursor.close()
        }
    }

    /**
     * Starts an asynchronous load of the contacts list data. When the result is ready the callbacks
     * will be called on the UI thread. If a previous load has been completed and is still valid
     * the result may be passed to the callbacks immediately.
     *
     *
     * Must be called from the UI thread
     */
    override fun onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor)
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad()
        }
    }

    /**
     * Must be called from the UI thread
     */
    override fun onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad()
    }

    override fun onCanceled(cursor: Cursor?) {
        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }
    }

    override fun onReset() {
        super.onReset()

        // Ensure the loader is stopped
        onStopLoading()

        if (mCursor != null && !mCursor!!.isClosed) {
            mCursor!!.close()
        }
        mCursor = null
    }
}