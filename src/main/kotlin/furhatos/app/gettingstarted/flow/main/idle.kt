package furhatos.app.gettingstarted.flow.main

import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onUserEnter
import furhatos.gestures.Gestures
import furhatos.records.Location

// 1
val Idle: State = state {
    // 1.1
    onEntry {
        furhat.attendNobody()
        furhat.gesture(Gestures.Smile)

        // 30% say this sentence
        if ((0..100).random() < 30) {
            furhat.say("Looking around... where are all the mochi lovers?")
        }

        // glance
        furhat.attend(Location(0.5, 0.0, 1.0))
        delay((500..1200).random().toLong())
        furhat.attend(Location(-0.5, 0.0, 1.0))
        delay((500..1200).random().toLong())

        reentry()
    }

    // 1.2
    onUserEnter {
        furhat.attend(it) // 自动看向刚靠近的人
        furhat.say("Who is the lucky customer today ready to try our new matcha mochi?")
        furhat.gesture(Gestures.Smile)
        goto(Greeting) // 跳到你设定的交互阶段
    }
}
