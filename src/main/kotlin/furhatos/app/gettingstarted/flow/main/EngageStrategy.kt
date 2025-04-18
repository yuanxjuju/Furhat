package furhatos.app.gettingstarted.flow.main

import furhatos.app.gettingstarted.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.app.gettingstarted.nlu.*
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No
import furhatos.gestures.Gestures
import furhatos.records.Location

// 4.1
val EngageMatcha: State = state(parent = Parent) {

    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        furhat.attend(Location(-0.7, -0.2, 0.7)) // look at other mochi
        furhat.say("You can take them from the shelf over there.")
        furhat.attend(users.current)
        furhat.ask("How many would you like to have?", timeout = 10000)
    }

    onResponse<BuyMochi> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("${it.intent.items}, good choice!")
        goto(MatchaConfirm)
    }

    onResponse<BuyZero> {
        goto(OtherFlavors)
    }

    onResponse<StillThinking> {
        goto(NotSureAmount)
    }
}

// 4.1.2
val NotSureAmount: State = state(parent = Parent) {
    onEntry{
        furhat.attend(users.current)
        furhat.gesture(Gestures.Thoughtful)
        furhat.ask("If you are not sure, I suggest you buy 3 boxes of matcha mochi for the discount. Or if you think that's too much, you can buy one for a try.", timeout = 10000)
        furhat.gesture(Gestures.Smile)
    }

    onResponse<BuyMochi> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("${it.intent.items}, good choice!")
        goto(MatchaConfirm)
    }

    onResponse<BuyZero> {
        goto(OtherFlavors)
    }

    onResponse<StillThinking> {
        goto(BuyZero)
    }

}

// 4.1.2.1
val BuyZero: State = state(parent = Parent){

    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BrowRaise)
        furhat.ask("We also have chocolate and red bean flavor mochi. Would you like to take some with you?", timeout = 10000)
        furhat.gesture(Gestures.Smile)
    }

    onResponse<BuyMochi> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("${it.intent.items}, what a lovely choice!")
        goto(TerminateWithPurchase)
    }

    onResponse<BuyZero> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Nod)
        furhat.say("Alright! You can always come and take more if you feel like later.")
        furhat.gesture(Gestures.Smile)
        goto(TerminateWithoutPurchase)
    }

    onResponse<StillThinking> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Nod)
        furhat.say("Alright! You can always come and take more if you feel like later.")
        furhat.gesture(Gestures.Smile)
        goto(TerminateWithoutPurchase)
    }
}

// 4.1.1
val MatchaConfirm: State = state(parent = Parent) {

    onEntry{
        furhat.attend(users.current)
        furhat.gesture(Gestures.BrowRaise)
        furhat.ask("Are you sure not to take more with you? The discount will end tomorrow.", timeout = 10000)
        furhat.gesture(Gestures.Blink)
    }

    onResponse<Yes> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Good choice! You can pick from the shelf over there.")
        goto(TerminateWithPurchase)
    }

    onResponse<No> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Nod)
        furhat.say("Alright! You can come and take more if you feel like later.")
        goto(TerminateWithPurchase)
    }

    onResponse<BuyMochi> {
        val items = it.intent.items
        if (items != null) {
            furhat.attend(users.current)
            furhat.gesture(Gestures.BigSmile)
            furhat.say("Good choice! You can pick from the shelf over there.")
            furhat.gesture(Gestures.Blink)
            furhat.attend(Location(-0.7, -0.2, 0.7)) // look at other mochi
        } else {
            furhat.attend(users.current)
            furhat.gesture(Gestures.Nod)
            furhat.say("Alright! You can come and take more if you feel like later.")
            furhat.gesture(Gestures.Blink)
        }
        goto(TerminateWithPurchase)
    }

    onNoResponse {
        furhat.attend(users.current)
        furhat.say("Just take your time.")
        furhat.gesture(Gestures.Blink)
        reentry()
    }

    onResponse<StillThinking> {
        furhat.attend(users.current)
        furhat.say("Take your time. You can come and take more if you feel like later.")
        furhat.gesture(Gestures.Blink)
        goto(TerminateWithPurchase)
    }
}

// 4.2
val EngageOthers: State = state(parent = Parent) {

    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        furhat.ask("How many would you like to have?", timeout = 10000)
        furhat.gesture(Gestures.Blink)
    }

    onResponse<BuyMochi> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("${it.intent.items}, what a lovely choice!")
        goto(TerminateWithPurchase)
    }

    onResponse<BuyZero> {
        goto(MatchaConfirm)
    }

    onResponse<StillThinking> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Nod)
        furhat.say("Take your time! You can always come and take more if you feel like later.")
        furhat.gesture(Gestures.Smile)
        goto(TerminateWithoutPurchase)
    }

}
