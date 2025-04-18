package furhatos.app.gettingstarted.flow.main

import furhatos.app.gettingstarted.flow.Parent
import furhatos.app.gettingstarted.flow.noResponseCount
import furhatos.app.gettingstarted.nlu.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onUserEnter
import furhatos.flow.kotlin.voice.AzureVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.records.Location

var returnToState: State? = null
val glancePositions = listOf(
    Location(0.5, 0.0, 1.0),
    Location(-0.5, 0.0, 1.0),
    Location(0.0, 0.2, 1.0),
    Location(0.3, -0.1, 1.0)
)
val pos = glancePositions.random()

// 1
val Idle: State = state {
    // 1.1
    onEntry {
        furhat.voice = AzureVoice(name = "AriaNeural")
        furhat.gesture(Gestures.Smile)
        furhat.gesture(Gestures.Wink)

        val idlePhrases = listOf(
            "Mochi mochi! Come try some!",             // English
            "Sweet, soft, and chewy mochi!",           // English
            "Come closer, I’ve got something sweet!"
        )

        if ((0..100).random() < 70) {
            furhat.say(idlePhrases.random())
        }

        // 👀 glance
        furhat.attend(pos)
        delay((800..1600).random().toLong())
        furhat.attendNobody()

        val gestures = listOf(Gestures.Nod, Gestures.Smile, Gestures.Wink)
        furhat.gesture(gestures.random())

        reentry()
    }

    // 1.2
    onUserEnter {
        furhat.attend(it)
        furhat.say("Welcome! Tasty matcha mochi is waiting for you.")
        furhat.gesture(Gestures.Smile)
        goto(Greeting)
    }
}

// 1.3 Passive Waiting
val PassiveWaiting : State = state{

    onEntry {
        furhat.attend(users.current)
        furhat.ask(random(
            "I'm always here for more questions and mochi.",
            "Let me know if you’d like to try something.",
            "Any more questions?"
        ), timeout = 7000)
    }

    val idlePhrases = listOf(
        "Mochi mochi! Come try some!",             // English
        "Sweet, soft, and chewy mochi~",           // English
        "Come closer, I’ve got something sweet!"
    )

    onNoResponse {
        furhat.attend(pos)
        furhat.say(idlePhrases.random())
        delay(2000)
        reentry()
    }

    onResponse<AskMore> {
        furhat.say("Of course! It is yummy, isn't it? Consider take some home?")
        noResponseCount = 0
        goto(EngageMatcha)
    }

    onResponse<BuyMochi> {
        furhat.say("${it.intent.items}, what a lovely choice!")
        noResponseCount = 0
        goto(MatchaConfirm)
    }

    onResponse<AskPrice> {
        furhat.say("For a box that is €3.20, but Matcha flavor is on sale! You can get three boxes for just €6.40.")
        furhat.gesture(Gestures.Wink)
        goto(EngageOthers)
    }

    onResponse<AskRecommendation> {
        furhat.say("Matcha is very popular among young people these days. Red bean is the all-time classic. But I would say chocolate lover can’t miss the rich and smooth chocolate.")
        furhat.gesture(Gestures.Wink)
        goto(EngageOthers)
    }

    onResponse<AskOriginProcess> {
        furhat.say("Our mochi only uses high-quality ingredients from Japan and a precise process to ensure a soft, chewy texture and delicious flavor in every bite.")
        furhat.gesture(Gestures.Smile)
        goto(EngageOthers)
    }

    onResponse<AskIdentity> {
        furhat.say("Yes, I'm Furhat – a social robot here to help you explore and enjoy our mochi experience. Nice to meet you!")
        furhat.gesture(Gestures.Wink)
        goto(returnToState ?: PresentProduct)
    }

    onResponse<AskHealth> {
        furhat.say("We use clean ingredients and keep the sweetness low — perfect for a guilt-free treat now and then!")
        furhat.gesture(Gestures.Wink)
        goto(returnToState ?: PresentProduct)
    }

    onResponse<AskHuman> {
        furhat.say("Let me get someone to help you right away. Please wait a moment.")
        delay(10000)
        goto(TerminateWithoutPurchase)
    }

    onResponse {
        furhat.say("Welcome back!")
        goto(returnToState ?: PresentProduct)
    }

    onUserLeave {
        when {
            !users.hasAny() -> { // If no users are present
                // Wait for the user to leave the visual field and then go to idle state
                furhat.glance(it.head.location) // Make Furhat glance at the user's location
                furhat.listen()
                delay(3000)
                furhat.attendNobody()
                goto(Idle)
            }
            furhat.isAttending(it) -> furhat.attend(users.other) // If the current user left, attend to the other user
            !furhat.isAttending(it) -> furhat.glance(it.head.location) // If another user left, glance at their location
        }
    }

}
