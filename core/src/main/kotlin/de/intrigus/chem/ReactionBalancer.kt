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


//TODO Implement this part of the app
class ReactionBalancer {
    //var reaction = Reaction()

    fun balance(reaction: Reaction): Reaction {
        return reaction
    }

    fun test() {
        var reaction: Reaction = Reaction()
        var h2o: Term = Term()
        var h2: Term = Term()
        var o2: Term = Term()
        o2.addElements(arrayOf(Element(2, "O")))
        h2.addElements(arrayOf(Element(2, "H")))
        h2o.addElements(arrayOf(Element(2, "H"), Element(1, "O")))
        reaction.addTermToRightSide(h2o)
        reaction.addTermToLeftSide(h2)
        reaction.addTermToLeftSide(o2)

        reaction.rightSide.forEach { it.getDistinctElements().forEach {} }
    }
}