package furhatos.app.gettingstarted.flow.main

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.Location
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No
import furhatos.app.gettingstarted.nlu.*
import furhatos.app.gettingstarted.flow.Parent

// 🟢 3 Customer is with Farhat
val PresentProduct : State = state(parent = Parent) {
    onEntry {
        furhat.attend(Location(0.3, 0.0, 1.0)) // look at matcha mochi
        furhat.gesture(Gestures.Smile)
        furhat.ask("We have a new matcha flavor! Would you like to try a free matcha mochi sample?")
        furhat.attend(users.current)
    }

    onResponse<Yes> {
        goto(MatchaInfo)
    }

    onResponse<No> {
        goto(OtherFlavors)
    }

    onResponse {
        furhat.say("Don't be shy! Come give it a try!") // TBD
        reentry()
    }
}

// 🟢 3.1 Eat Matcha
val MatchaInfo : State = state(parent = Parent) {
    onEntry {
        furhat.say("Great! Take one")
        furhat.gesture(Gestures.Smile)
        delay(300)
        furhat.say(
            """
            Matcha: Our mochi is made with premium Japanese matcha for an authentic taste. 
            It is low in fat, and packed with matcha’s health benefits. 
            We also have a buy 2, get 1 free today for matcha flavour today!”
        """.trimIndent()
        )
        furhat.attend(users.current)
        delay(300)
        furhat.ask("Do you like it?")
        furhat.gesture(Gestures.Wink)
    }

    onResponse<Yes> {
        goto(MatchaLiked)
    }

    onResponse<No> {
        goto(MatchaDisliked)
    }

    onResponse {
        furhat.say("Just let me know if you like it or not.") // TBD
        reentry()
    }
}

// 🟢 3.1.1 Like it
val MatchaLiked = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Happy to hear that!")
        goto(EngageMatcha)
    }
}

// 🟢 3.1.2 Disike it
val MatchaDisliked = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Surprise)
        furhat.say("""
            Really? If you don't like it, you can still consider our other flavors.
            We have chocolate and red bean. Both of them are classic!
            You can get them from the shelf over there.
        """.trimIndent())
        delay(500)
        furhat.gesture(Gestures.Thoughtful)
        goto(EngageOthers)
    }
}

// 🟢 3.2 Refuse
val OtherFlavors = state(parent = Parent) {
    onEntry {
        furhat.gesture(Gestures.Thoughtful)
        furhat.say("""
            If you don't like matcha flavor, we also have other flavors!
            Red bean and chocolate mochi are available.
            They’re tasty too! Do you like any of them?
        """.trimIndent())
        furhat.gesture(Gestures.Smile)

        furhat.listen()
    }

    onResponse<ChooseFlavor> {
        when (it.intent.flavor?.value) {
            "chocolate" -> goto(ChocolateResponse)
            "red bean" -> goto(RedBeanResponse)
            "both" -> goto(BothResponse)
            else -> {
                furhat.say("Those are all good choices!")
                goto(EngageOthers)
            }
        }
    }
}

// 🟢 3.2.1 chocolate
val ChocolateResponse = state(parent = Parent) {
    onEntry {
        furhat.say("""
            Great choice! Our chocolate flavor is perfectly balanced—rich and satisfying without being overwhelming. 
            Unfortunately, we're out of chocolate samples today, but trust me, it's our best-seller for a reason! 
            I highly recommend to buy it!
        """.trimIndent())
        goto(EngageOthers)
    }
}

// 🟢 3.2.2 red bean
val RedBeanResponse = state(parent = Parent) {
    onEntry {
        furhat.say("Red bean is our classic flavor—loved for generations and still a favorite today! You can get them from the shelf over there!")
        goto(EngageOthers)
    }
}

// 🟢 3.2.3 both
val BothResponse = state(parent = Parent) {
    onEntry {
        furhat.say("Cool! Those two flavors are classic. You can get them from the shelf over there!")
        goto(EngageOthers)
    }
}