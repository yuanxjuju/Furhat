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
            "Am I allowed to take more?",
            "Can I have another mochi?",
            "Can I try one more?",
            "I want to try",
            "Can I try"
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
            "What is the cost?",
            "How much is that?",
            "How much"
        )
    }
}

class AskRecommendation : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "What is your favorite mochi?",
            "Which one do you like?",
            "Do you like mochi?",
            "Which one do you recommend?",
            "Can you give me recommendations?"
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
            "Where are the ingredients from?",
            "What are the ingredients?",
            "What is the mochi made of?"
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
            "Is this fattening?",
            "Is healthy?",
            "Will it make me fat?"
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
            "We're out of stock",
            "Can I have a human assistant?",
            "Bring me a human"
        )
    }
}

class StillThinking : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Umm",
            "I don't know",
            "I'm not sure",
            "I'm still thinking",
            "No idea",
            "I can't decide",
            "I don't remember",
            "Hard to say",
            "Uh",
            "Um"
        )
    }
}

class ByeGreeting : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Bye",
            "Goodbye",
            "See you",
            "See you later",
            "Talk to you later",
            "Catch you later",
            "Take care",
            "Have a nice day",
            "Have a good day",
            "Byebye",
            "See you around",
            "You too"
        )
    }
}

class AskMochi : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "What is mochi?",
            "I have no idea about mochi",
            "Can you introduce what mochi is?",
            "What's a mochi?",
            "Explain mochi."
        )
    }
}

class AskTryOthers: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "Can I try chocolate?",
            "Can I try red bean?",
            "Can I try these two?",
            "Can I try chocolate and red bean mochi?",
            "I want to try one more.",
            "I want to have another matacha mochi."
        )
    }
}