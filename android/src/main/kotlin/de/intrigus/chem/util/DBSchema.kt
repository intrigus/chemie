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


class DBSchema {

    object ETable {

        val NAME = "elements"

        object Cols {
            val ELECTRO_NEGATIVITY = "Electronegativity"
            val CALCULATED_RADIUS = "Calculated_Radius"
            val FIRST_IONIZATION = "First_Ionization"
            val CORE_CONFIGURATION = "Core_Configuration"
            val HEAT_OF_VAPOR = "Heat_of_Vapor"
            val COVALENT_RADIUS = "Covalent_Radius"
            val HEAT_OF_FUSTION = "Heat_of_Fusion"
            val BULK_MODULUS = "Bulk_Modulus"
            val BOILING_POINT = "Boiling_Point"
            val BRINELL_HARNESS = "Brinell_Hardness"
            val MELTING_POINT = "Melting_Point"
            val SYMBOL = "Symbol"
            val STP_DENSITY = "STP_Density"
            val YOUNG_MODULUS = "Young_Modulus"
            val SHEAR_MODULUS = "Shear_Modulus"
            val VICKERS_HARDNESS = "Vickers_Hardness"
            val NAME = "Name"
            val COMMON_IONS = "Common_Ions"
            val SECOND_IONIZATION = "Second_Ionization"
            val MASS = "Mass"
            val VAN_DE_WA_RADIUS = "Van_der_Waals_Radius"
            val SPECIFIC_HEAT = "Specific_Heat"
            val THERMAL_COND = "Thermal_Cond"
            val THIRD_IONIZATION = "Third_Ionization"
            val SERIES = "Series"
            val ELECTRON_AFFINITY = "Electron_Affinity"
            val ATOMIC_NUMBER = "Atomic_Number"
            val MOHS_HARDNESS = "Mohs_Hardness"
            val EMPIRICAL_RADIUS = "Empirical_Radius"
            val ELEMENT_STATE = "Element_State"
        }
    }
}