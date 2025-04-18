package furhatos.app.gettingstarted.flow.main

import furhatos.app.gettingstarted.flow.Parent
import furhatos.app.gettingstarted.nlu.ByeGreeting
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

// 5.1
val TerminateWithPurchase: State = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        delay(1000)
        furhat.ask("Enjoy your mochi. I'm always here for you if you want more mochis or have any questions. Have a nice day!", timeout = 7000)
    }

    onNoResponse {
        goto(PassiveWaiting)
    }

    onResponse<ByeGreeting> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Bye bye!")
        delay(3000)
        goto(PassiveWaiting)
    }
}

val TerminateWithoutPurchase: State = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Wink)
        delay(1000)
        furhat.ask("You are always welcome to take a bite of the delicious mochi. Have a nice day!", timeout = 7000)
    }
    onNoResponse {
        goto(PassiveWaiting)
    }

    onResponse<ByeGreeting> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Bye bye!")
        furhat.gesture(Gestures.Blink)
        delay(3000)
        goto(PassiveWaiting)
    }
}