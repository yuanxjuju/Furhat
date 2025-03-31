package furhatos.app.gettingstarted.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

// flavor
class Flavor : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("matcha", "chocolate", "red bean", "both")
    }
}

// flavor intention
class ChooseFlavor(val flavor: Flavor? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@flavor",
            "I want @flavor",
            "I prefer @flavor",
            "Can I try @flavor",
            "I like @flavor mochi"
        )
    }
}