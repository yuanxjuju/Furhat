package furhatos.app.gettingstarted.flow.main

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures

val TerminateWithPurchase: State = state {
    onEntry {
        furhat.gesture(Gestures.Smile)
        furhat.say("Enjoy your mochi. Have a nice day!")
        furhat.attendNobody()
        goto(Idle)
    }
}

val TerminateWithoutPurchase: State = state {
    onEntry {
        furhat.gesture(Gestures.Smile)
        furhat.say("You are always welcome to take a bite of the delicious mochi. Have a nice day!")
        furhat.attendNobody()
        goto(Idle)
    }
}