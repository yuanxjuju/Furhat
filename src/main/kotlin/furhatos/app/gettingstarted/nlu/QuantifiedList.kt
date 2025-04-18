package furhatos.app.gettingstarted.nlu

import furhatos.nlu.common.Number
import furhatos.nlu.Intent
import furhatos.util.Language
import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.ListEntity

class BuyMatcha : Intent() {
    val count: Number = Number(1)

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@count matcha mochi",
            "@count",
            "I want @count",
            "Give me @count matcha mochi",
            "Can I get @count?"
        )
    }
}


class QuantifiedMochi(
    val count: Number = Number(1),
    val flavor: Flavor? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "@count @flavor mochi",
            "@count boxes of @flavor mochi",
            "@count boxes of @flavor mochi",
            "@count @flavor",
            "@flavor mochi",
            "@flavor",
            "@count"
        )
    }

    override fun toText(): String {
        val parts = mutableListOf<String>()

        if (count.toText() != "0") {
            val unit = if (count.toText() == "1") "box" else "boxes"
            parts.add("${count.toText()} $unit")
        }

        if (flavor != null) {
            parts.add("${flavor.toText()} mochi")
        }

        return parts.joinToString(" ")
    }
}

class MochiList : ListEntity<QuantifiedMochi>()

class BuyMochi(var items: MochiList? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@items",
            "I want @items",
            "I'd like to buy @items",
            "I'd like to have @items",
            "Can I get @items?",
            "I'd like to have @items",
            "I'd like @items more",
            "Give me another @items matcha",
            "Can I get another one?",
            "Add @items more @items",
            "@items please",
            "maybe @items",
            "@items more"
        )
    }
}

class BuyZero: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I don't want to buy",
            "No I don't want mochi",
            "Nope I don't like it",
            "I don't want",
            "I don't want to",
            "No",
            "Zero",
            "I don't"
        )
    }
}