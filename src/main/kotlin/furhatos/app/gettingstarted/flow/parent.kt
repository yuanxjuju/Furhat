package furhatos.app.gettingstarted.flow

import furhatos.app.gettingstarted.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.app.gettingstarted.nlu.*

var noResponseCount = 0
var responseFailedCount = 0

val Parent: State = state {

    onUserEnter(instant = true) {
        when { // "it" is the user that entered
            furhat.isAttendingUser -> furhat.glance(it) // Glance at new users entering
            !furhat.isAttendingUser -> furhat.attend(it) // Attend user if not attending anyone
        }
    }

    onUserLeave(instant = true) {
        when {
            !users.hasAny() -> { // last user left
                furhat.attendNobody()
                goto(Idle)
            }
            furhat.isAttending(it) -> furhat.attend(users.other) // current user left
            !furhat.isAttending(it) -> furhat.glance(it.head.location) // other user left, just glance
        }
    }

    // 5.1
    onResponse<AskMore> {
        furhat.say("Sure! Happy you like it. But please kindly leave some for others to enjoy the delicious mochi.")
        furhat.gesture(Gestures.BigSmile)
        reentry()
    }

    // 5.2
    onResponse<AskPrice> {
        furhat.say("For a box that is €3.20, but Matcha flavor is on sale! You can get three boxes for just €6.40.")
        furhat.gesture(Gestures.Wink)
        reentry()
    }

    // 5.3
    onResponse<AskRecommendation> {
        furhat.say("Matcha is very popular among young people these days. Red bean is the all-time classic. But I would say chocolate lover can’t miss the rich and smooth chocolate.")
        furhat.gesture(Gestures.Wink)
        reentry()
    }

    // 5.4
    onResponse<AskOriginProcess> {
        furhat.say("Our mochi only uses high-quality ingredients from Japan and a precise process to ensure a soft, chewy texture and delicious flavor in every bite.")
        furhat.gesture(Gestures.Smile)
        reentry()
    }

    // 5.5
    onResponse<AskIdentity> {
        furhat.say("Yes, I'm Furhat – a social robot here to help you explore and enjoy our mochi experience. Nice to meet you!")
        furhat.gesture(Gestures.Wink)
        reentry()
    }

    // 5.6
    onResponse<AskHealth> {
        furhat.say("We use clean ingredients and keep the sweetness low — perfect for a guilt-free treat now and then!")
        reentry()
    }

    // 5.7
    onResponse<AskHuman> {
        furhat.say("Let me get someone to help you right away. Please wait a moment.")
        reentry()
    }

    onNoResponse {
        if (noResponseCount == 0) {
            noResponseCount++
            furhat.ask("Are you still there?")
            reentry()
        } else {
            furhat.say("I'm always here for more questions and mochi. Enjoy your day!")
            noResponseCount = 0
            goto(Idle)
        }
    }

    onResponseFailed {
        furhat.ask("Sorry, I didn’t catch that. Could you say it again?")
        reentry()
    }

    onResponse<Confused> {
        furhat.say("Let me explain that again.")
        reentry()
    }

    // reset the count on successful response
    onResponse {
        noResponseCount = 0
        responseFailedCount = 0
    }

}
