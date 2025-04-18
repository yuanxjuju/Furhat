package furhatos.app.gettingstarted.flow

import furhatos.app.gettingstarted.flow.main.*
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.app.gettingstarted.nlu.*
import furhatos.records.Location

var noResponseCount = 0
var responseFailedCount = 0
var askMoreCount = 0

val Parent: State = state {

    onUserEnter(instant = true) {
        when { // "it" is the user that entered
            furhat.isAttendingUser -> furhat.glance(it) // Glance at new users entering
            !furhat.isAttendingUser -> furhat.attend(it) // Attend user if not attending anyone
        }
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

    // 5.1
    onResponse<AskMore> {
        askMoreCount++

        when (askMoreCount) {
            1 -> {
                furhat.say("Sure! Happy you like it. But please kindly leave some for others to enjoy the delicious mochi.")
                furhat.gesture(Gestures.BigSmile)
            }
            2 -> {
                furhat.gesture(Gestures.Surprise)
                furhat.say("Seems you really like it. Just take it, but let's give others a chance too.")
                furhat.gesture(Gestures.Thoughtful)
            }
            else -> {
                furhat.say("Okay okay... but this really is the last one! ")
                furhat.gesture(Gestures.BrowFrown)
            }
        }

        delay(4000)
        goto(EngageMatcha)
    }

    // 5.2
    onResponse<AskPrice> {
        furhat.say("For a box that is €2.50, but Matcha flavor is on sale! You can get three boxes for just €5.")
        furhat.gesture(Gestures.Wink)
        reentry()
    }

    // 5.3
    onResponse<AskRecommendation> {
        furhat.say("It's hard to only recommend one, but our best seller is chocolate! People love its rich flavour.")
        furhat.gesture(Gestures.Wink)
        goto(EngageOthers)
    }

    // 5.4
    onResponse<AskOriginProcess> {
        furhat.say("Good question! Mochi is a soft, chewy Japanese treat made from glutinous rice that's pounded into a smooth, stretchy dough.Our mochi only uses high-quality ingredients from Japan and a precise process to ensure a soft, chewy texture and delicious flavor in every bite.")
        furhat.gesture(Gestures.Smile)
        reentry()
    }

    // 5.5
    onResponse<AskIdentity> {
        furhat.gesture(Gestures.Smile)
        furhat.say("Yes, I'm Furhat – a social robot here to help you explore and enjoy our mochi experience. Nice to meet you!")
        furhat.gesture(Gestures.Wink)
        reentry()
    }

    // 5.6
    onResponse<AskHealth> {
        furhat.say("We use clean ingredients and keep the sweetness low — perfect for a guilt-free treat now and then! Come on, sweet is happiness!")
        furhat.gesture(Gestures.Wink)
        delay(3000)
        reentry()
    }

    // 5.7
    onResponse<AskHuman> {
        furhat.say("Sorry I couldn't help you more.")
        furhat.gesture(Gestures.ExpressSad)
        furhat.say("Let me get someone to help you right away. Please wait a moment.")
        furhat.gesture(Gestures.Smile)
        delay(10000)
        goto(PassiveWaiting)
    }

    // 6.8
    onResponse<AskMochi> {
        furhat.gesture(Gestures.Smile)
        furhat.say("Good question! Mochi is a soft, chewy Japanese treat made from sticky rice, with various sweet fillings. The sample in front of me is our new flavour — matcha!")
        furhat.attend(Location(0.0, -0.2, 0.5)) // look at matcha mochi
        furhat.gesture(Gestures.BrowRaise)
        delay(2000)
        goto(PresentMatchaAsk)
    }

    //6.9
    onResponse<AskTryOthers> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        furhat.say("Unfortunately, we're out of chocolate samples today, but trust me, it's our best-seller for a reason! ")
        delay(1000)
        goto(EngageOthers)
    }

    onNoResponse {
        noResponseCount++
        when (noResponseCount) {
            1 -> furhat.ask("Are you still there?", timeout = 7000)
            2 -> furhat.ask("Still there? I’m happy to help.", timeout = 7000)
            else -> {
                noResponseCount = 0
                returnToState = currentState
                goto(PassiveWaiting)
            }
        }
    }

    onResponseFailed {
        responseFailedCount++
        furhat.ask("Sorry, I didn’t catch that. Could you say it again?", timeout = 5000)
        reentry()
    }

    onResponse<Confused> {
        furhat.say("Let me explain that again.")
        reentry()
    }

    onResponse<Greet> {
        furhat.say {
            random {
                +"Nice to see you!"
                +"Hey hey!"
            }
        }
        goto(PassiveWaiting)
    }

    onResponse {
        furhat.say("Sorry, I didn't catch that.")
        reentry()
    }

    noResponseCount = 0
    responseFailedCount = 0

}
