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

class Term {
    public var elements: MutableList<Element> = ArrayList()
    var eletricCharge: Int = 0
    public var enthalpy: Float = 0f

    public fun addElements(element: Array<Element>) {
        elements.addAll(element)
    }

    override fun toString(): String {
        return "Elements: $elements, Electric charge: $eletricCharge"
    }

    public fun numberOfElements(): Int {
        var sumOfElements: Int = 0
        elements.filter { it.name == "H" }.forEach { sumOfElements += it.repetitionCount }
        return sumOfElements
    }

    /**
     * @return returns an array of the elements in this {@code Term}. Duplicate elements are removed.
     */
    public fun getDistinctElements(): ArrayList<Element> {
        return elements.distinct().toCollection(ArrayList<Element>())
    }


}
