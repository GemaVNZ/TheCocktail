package com.example.thecocktail.data

import com.google.gson.annotations.SerializedName
import android.os.Parcel
import android.os.Parcelable

data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Cocktail>
)
data class Cocktail (
    @SerializedName("idDrink") val id:Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strAlcoholic") val type : String,
    @SerializedName("strGlass") val glass : String,
    @SerializedName("strDrinkThumb") val imageURL : String,
    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strIngredient4") val ingredient4: String?,
    @SerializedName("strIngredient5") val ingredient5: String?,
    @SerializedName("strIngredient6") val ingredient6: String?,
    @SerializedName("strIngredient7") val ingredient7: String?,
    @SerializedName("strIngredient8") val ingredient8: String?,
    @SerializedName("strIngredient9") val ingredient9: String?,
    @SerializedName("strIngredient10") val ingredient10: String?,
    @SerializedName("strIngredient11") val ingredient11: String?,
    @SerializedName("strIngredient12") val ingredient12: String?,
    @SerializedName("strIngredient13") val ingredient13: String?,
    @SerializedName("strIngredient14") val ingredient14: String?,
    @SerializedName("strIngredient15") val ingredient15: String?,
    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?,
    @SerializedName("strMeasure4") val measure4: String?,
    @SerializedName("strMeasure5") val measure5: String?,
    @SerializedName("strMeasure6") val measure6: String?,
    @SerializedName("strMeasure7") val measure7: String?,
    @SerializedName("strMeasure8") val measure8: String?,
    @SerializedName("strMeasure9") val measure9: String?,
    @SerializedName("strMeasure10") val measure10: String?,
    @SerializedName("strMeasure11") val measure11: String?,
    @SerializedName("strMeasure12") val measure12: String?,
    @SerializedName("strMeasure13") val measure13: String?,
    @SerializedName("strMeasure14") val measure14: String?,
    @SerializedName("strMeasure15") val measure15: String?,
    @SerializedName("strInstructions") val instruction: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(category)
        dest.writeString(type)
        dest.writeString(glass)
        dest.writeString(imageURL)
        dest.writeString(instruction)
        dest.writeString(ingredient1)
        dest.writeString(ingredient2)
        dest.writeString(ingredient3)
        dest.writeString(ingredient4)
        dest.writeString(ingredient5)
        dest.writeString(ingredient6)
        dest.writeString(ingredient7)
        dest.writeString(ingredient8)
        dest.writeString(ingredient9)
        dest.writeString(ingredient10)
        dest.writeString(ingredient11)
        dest.writeString(ingredient12)
        dest.writeString(ingredient13)
        dest.writeString(ingredient14)
        dest.writeString(ingredient15)
        dest.writeString(measure1)
        dest.writeString(measure2)
        dest.writeString(measure3)
        dest.writeString(measure4)
        dest.writeString(measure5)
        dest.writeString(measure6)
        dest.writeString(measure7)
        dest.writeString(measure8)
        dest.writeString(measure9)
        dest.writeString(measure10)
        dest.writeString(measure11)
        dest.writeString(measure12)
        dest.writeString(measure13)
        dest.writeString(measure14)
        dest.writeString(measure15)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cocktail> {
        override fun createFromParcel(parcel: Parcel): Cocktail {
            return Cocktail(parcel)
        }

        override fun newArray(size: Int): Array<Cocktail?> {
            return arrayOfNulls(size)
        }
    }

    // Función para obtener un ingrediente basado en el índice (1 a 15)
    fun getIngredient(index: Int): String? {
        return when (index) {
            1 -> ingredient1
            2 -> ingredient2
            3 -> ingredient3
            4 -> ingredient4
            5 -> ingredient5
            6 -> ingredient6
            7 -> ingredient7
            8 -> ingredient8
            9 -> ingredient9
            10 -> ingredient10
            11 -> ingredient11
            12 -> ingredient12
            13 -> ingredient13
            14 -> ingredient14
            15 -> ingredient15
            else -> null
        }
    }

    // Función para obtener una medida basada en el índice (1 a 15)
    fun getMeasure(index: Int): String? {
        return when (index) {
            1 -> measure1
            2 -> measure2
            3 -> measure3
            4 -> measure4
            5 -> measure5
            6 -> measure6
            7 -> measure7
            8 -> measure8
            9 -> measure9
            10 -> measure10
            11 -> measure11
            12 -> measure12
            13 -> measure13
            14 -> measure14
            15 -> measure15
            else -> null
        }
    }

}