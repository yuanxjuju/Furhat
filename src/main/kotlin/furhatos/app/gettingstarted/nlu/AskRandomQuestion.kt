package furhatos.app.gettingstarted.nlu

import furhatos.nlu.Intent
import furhatos.util.Language

class AskMore : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Can I take more?",
            "Can I have more?",
            "Can I get more?",
            "Can I pick more?",
            "Is it okay to take more?",
            "Am I allowed to take more?"
        )
    }
}

class AskPrice : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "How much is it?",
            "What's the price?",
            "How much does it cost?",
            "How much for one?",
            "Price?",
            "What is the cost?"
        )
    }
}

class AskRecommendation : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "What is your favorite mochi?",
            "Which one do you like?",
            "Do you like mochi?"
        )
    }
}

class AskOriginProcess : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Where is this mochi from?",
            "What is the origin of this?",
            "Is this mochi Japanese?",
            "How is this made?",
            "Can you tell me how it's made?",
            "What's the process of making it?",
            "How do you make mochi?",
            "Where does this come from?",
            "Where are the ingredients from?"
        )
    }
}

class AskIdentity : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Who are you?",
            "What are you?",
            "Are you a robot?",
            "Are you human?",
            "What is your name?",
            "Are you real?",
            "Are you a machine?",
            "Are you an AI?",
            "What do you do?",
            "Are you Furhat?"
        )
    }
}

class AskHealth : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Is mochi healthy?",
            "Is this good for you?",
            "Is this fattening?"
        )
    }
}

class AskHuman : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Can I talk to a real person?",
            "Is there a human staff?",
            "I need some help from someone here",
            "Can someone assist me?",
            "Is anyone working here?",
            "There's no mochi left",
            "It's empty here",
            "The shelf is out of mochi",
            "I don't see any mochi",
            "We're out of stock"
        )
    }
}