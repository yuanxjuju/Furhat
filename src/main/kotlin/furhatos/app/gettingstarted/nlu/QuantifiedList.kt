package furhatos.app.gettingstarted.nlu

import furhatos.nlu.common.Number
import furhatos.nlu.Intent
import furhatos.util.Language
import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.ListEntity

class BuyMatcha(val count: Number = Number(1)) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@count",
            "I want @count matcha mochi",
            "Give me @count matcha",
            "Can I get @count of matcha"
        )
    }
}

class QuantifiedMochi(
    val count: Number = Number(1),
    val flavor: Flavor? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("@count @flavor", "@flavor")
    }

    override fun toText(): String {
        return generate("$count $flavor")
    }
}

class MochiList : ListEntity<QuantifiedMochi>()

class BuyMochi(val items: MochiList? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@items",
            "I want @items",
            "I'd like to buy @items",
            "Can I get @items?"
        )
    }
}
