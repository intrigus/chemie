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

package de.intrigus.chem

import java.util.*

class Reaction {

    public var leftSide: MutableList<Term> = ArrayList()
    public val rightSide: MutableList<Term> = ArrayList()

    public fun addTermToRightSide(term: Term) {
        rightSide.add(term)
    }

    public fun addTermToLeftSide(term: Term) {
        leftSide.add(term)
    }

    override fun toString(): String {
        return "Leftside: $leftSide, Rightside: $rightSide"
    }

    /**
     * @return return the standard reaction enthalpy of this reaction, if data is available
     */
    fun getEnthalpy(): Float {
        var productEnthalpy: Float = 0f
        var eductEnthalpy: Float = 0f
        rightSide.forEach { productEnthalpy += it.enthalpy }
        leftSide.forEach { eductEnthalpy += it.enthalpy }
        return productEnthalpy - eductEnthalpy
    }
}