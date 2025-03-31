package furhatos.app.gettingstarted.flow.main

import furhatos.flow.kotlin.*
import furhatos.app.gettingstarted.nlu.*
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No
import furhatos.gestures.Gestures

val EngageMatcha: State = state {

    onEntry {
        furhat.attend(users.random)
        furhat.ask("We are having a buy 2 get 1 free discount for matcha mochi today! You can take them from the shelf over there. How many would you like to have?")
    }

    onResponse<BuyMatcha> {
        val count = it.intent.count.value ?: 1

        when {
            count >= 3 -> {
                furhat.say("Good choice!")
                furhat.gesture(Gestures.Smile)
                goto(TerminateWithPurchase)
            }

            count in 1..2 -> {
                furhat.say("Are you sure not joining the discount? Don’t miss the chance. The discount will finish tomorrow.")
                goto(ConfirmMoreMatcha) // 🔁 进入后续确认的 state
            }

            else -> {
                furhat.say("Are you sure not taking any with you? It tastes yummy right? No regrets!")
                furhat.gesture(Gestures.Smile)
                goto(TerminateWithoutPurchase)
            }
        }
    }
}

val ConfirmMoreMatcha: State = state {

    onEntry{
        furhat.ask("Would you like to take more?")
    }

    onResponse<Yes> {
        furhat.say("Good choice!")
        goto(TerminateWithPurchase)
    }

    onResponse<No> {
        furhat.say("Alright! You can come and take more if you feel like later.")
        goto(TerminateWithoutPurchase)
    }

    onNoResponse {
        furhat.say("Just take your time.")
        reentry()
    }
}


val EngageOthers: State = state {

    onEntry {
        furhat.attend(users.random)
        furhat.ask("How many would you like to have?")
    }

    onResponse<BuyMochi> {
        furhat.gesture(Gestures.Smile)
        furhat.say("${it.intent.items}, what a lovely choice!")
        goto(TerminateWithPurchase)
    }
}
