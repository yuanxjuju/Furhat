package furhatos.app.gettingstarted.nlu

import furhatos.nlu.Intent
import furhatos.util.Language

class Confused : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "What?", "Huh?", "I don't understand", "Can you say that again?", "Sorry?"
        )
    }
}