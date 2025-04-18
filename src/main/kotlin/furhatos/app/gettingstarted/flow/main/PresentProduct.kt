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
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        furhat.attend(Location(0.0, -0.2, 0.5)) // matcha mochi
        furhat.say("We have a new matcha flavor with a buy 2, get 1 free discount")
        delay(300)
        furhat.attend(users.current)
        furhat.say("Only for today!")
        furhat.gesture(Gestures.Wink)
        furhat.ask("Would you like to try a free sample?", timeout = 5000)
        furhat.gesture(Gestures.BrowRaise)
    }

    onResponse<Yes> {
        goto(PresentMatcha)
    }

    onResponse<No> {
        goto(OtherFlavors)
    }

    onResponseFailed {
        goto(PresentMatcha)
    }

}

val PresentMatchaAsk : State = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        furhat.ask("Would you like to try a free sample?", timeout = 5000)
        furhat.gesture(Gestures.BrowRaise)
    }

    onResponse<Yes> {
        goto(PresentAsk)
    }

    onResponse<No> {
        goto(OtherFlavors)
    }

    onResponseFailed {
        goto(PresentAsk)
    }

}

val PresentAsk : State = state(parent = Parent) {
    onEntry{
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Great! Take it over here.")
        furhat.attend(Location(0.0, -0.2, 0.5)) // look at matcha mochi
        delay(3000)
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        furhat.say("Our matcha mochi is made with ${furhat.voice.emphasis("premium Japanese matcha")},low in fat, and packed with matcha’s health benefits.")
        furhat.gesture(Gestures.Blink)
        delay(2000)
        goto(AskSample)
    }
}


// 🟢 3.1 Eat Matcha
val PresentMatcha : State = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Ready to try one? Take it over here.")
        furhat.attend(Location(0.0, -0.2, 0.5)) // look at matcha mochi
        delay(3000)
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        furhat.ask("Have you tried mochis before?", timeout = 5000)
    }

    onResponse<Yes> {
        furhat.say({
            +Gestures.Surprise
            +Gestures.BigSmile
            +"Oh great buddy! "
            +"Then you must find our mochi the best one you ever tried."
            +Gestures.Smile
            +"It is made with ${furhat.voice.emphasis("premium Japanese matcha")},low in fat, and packed with matcha’s health benefits."
            +Gestures.Blink
        })
        delay(2000)
        goto(AskSample)
    }

    onResponse<No>{
        furhat.say({
            +Gestures.Surprise
            +Gestures.BrowRaise
            +"Oh great buddy! "
            +Gestures.BigSmile
            +"Congratulations you met the best mochi for your first mochi experience!"
            +Gestures.Smile
            +"It is made with ${furhat.voice.emphasis("premium Japanese matcha")},low in fat, and packed with matcha’s health benefits."
            +Gestures.Blink
        })
        delay(2000)
        goto(AskSample)
    }

    onResponse<StillThinking>{
        furhat.say({
            +Gestures.Nod
            +"Come on buddy! "
            +Gestures.BigSmile
            +"Anyway congratulations you met the best mochi."
            +Gestures.Smile
            +"It is made with ${furhat.voice.emphasis("premium Japanese matcha")},${furhat.voice.emphasis("low in fat")}, and packed with matcha’s health benefits."
            +Gestures.Blink
        })
        delay(2000)
        goto(AskSample)
    }

    onResponseFailed {
        furhat.say({
            +Gestures.BigSmile
            +"Anyway congratulations you met the best mochi."
            +Gestures.Smile
            +"It is made with ${furhat.voice.emphasis("premium Japanese matcha")},${furhat.voice.emphasis("low in fat")}, and packed with matcha’s health benefits."
            +Gestures.Blink
        })
        delay(2000)
        goto(AskSample)
    }
}

//3.1.1
val AskSample : State = state(parent = Parent) {
    onEntry{
        furhat.ask("Do you like it?", timeout = 10000)
        furhat.gesture(Gestures.Wink)
    }

    onResponse<Yes> {
        goto(MatchaLiked)
    }

    onResponse<No> {
        goto(MatchaDisliked)
    }

    onResponse<StillThinking> {
        goto(MatchaLiked)
    }

    onResponseFailed {
        goto(MatchaLiked)
    }
}

// 🟢 3.1.1.1 Like it
val MatchaLiked = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Great! Mochi is always a nice snack!")
        goto(EngageMatcha)
    }
}

// 🟢 3.1.1.2 Disike it
val MatchaDisliked = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Surprise)
        furhat.say({
            +"Really?"
            +Gestures.ExpressSad
            +"Sorry it doesn't match your taste. "
            +Gestures.Thoughtful
            +attend(Location(-0.7, -0.2, 0.7)) // look at other mochi
            +"But we still have choices for chocolate and red bean. Both of them are classic!"
        })
        furhat.attend(users.current)
        furhat.gesture(Gestures.Smile)
        delay(1000)
        furhat.ask("Would you like me to introduce them to you?", timeout = 10000)
        furhat.gesture(Gestures.Blink)

        onResponse<ChooseFlavor> {
            when (it.intent.flavor?.value) {
                "chocolate" -> goto(ChocolateResponse)
                "red bean" -> goto(RedBeanResponse)
                "both" -> goto(BothResponse)
                }
            }
        }

        onResponse<Yes> {
            goto(BothResponse)
        }

        onResponse<No> {
            furhat.attend(users.current)
            furhat.gesture(Gestures.Nod)
            furhat.gesture(Gestures.Smile)
            furhat.say("No problem. They are all high quality mochis in ingredients and taste!")
            goto(EngageOthers)
        }

        onResponse<StillThinking> {
            furhat.attend(users.current)
            furhat.gesture(Gestures.Nod)
            furhat.gesture(Gestures.Smile)
            furhat.say("Let me just give you a quick introduction! Red Bean is our ${furhat.voice.emphasis("classic flavor")}, while Chocolate is ${furhat.voice.emphasis("perfectly balanced")} and a favorite among the young.")
            goto(EngageOthers)
        }

        onResponseFailed {
            furhat.attend(users.current)
            furhat.gesture(Gestures.Nod)
            furhat.gesture(Gestures.Smile)
            furhat.say("Let me just give you a quick introduction! Red Bean is our ${furhat.voice.emphasis("classic flavor")}, while Chocolate is ${furhat.voice.emphasis("perfectly balanced")} and a favorite among the young.")
            goto(EngageOthers)
        }
    }


// 🟢 3.2 Refuse
val OtherFlavors = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Thoughtful)
        furhat.say({
            +"If you don't want matcha flavor, we also have red bean and chocolate flavor available now!"
            +Gestures.BrowRaise
            +"They’re tasty too! Do you like any of them?"
            })
        furhat.gesture(Gestures.Wink)

        furhat.listen(timeout = 10000)
    }

    onResponse<ChooseFlavor> {
        when (it.intent.flavor?.value) {
            "chocolate" -> goto(ChocolateResponse)
            "red bean" -> goto(RedBeanResponse)
            "both" -> goto(BothResponse)
            else -> {
                furhat.say("Those are all good choices! Let me just give you a quick introduction! Red Bean is our classic flavor, while Chocolate is perfectly balanced and a favorite among the young.")
                goto(EngageOthers)
            }
        }
    }

    onResponse<StillThinking> {
        furhat.attend(users.current)
        furhat.gesture(Gestures.Nod)
        furhat.gesture(Gestures.Smile)
        furhat.say("Let me just give you a quick introduction! Red Bean is our classic flavor, while Chocolate is perfectly balanced and a favorite among the young.")
        goto(EngageOthers)
    }

    onResponse{
        furhat.say("Let me just give you a quick introduction! Red Bean is our classic flavor, while Chocolate is perfectly balanced and a favorite among the young.")
        goto(EngageOthers)
    }
}

// 🟢 3.2.1 chocolate
val ChocolateResponse = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say({
            +"Great choice! Our chocolate flavor is perfectly balanced—rich and satisfying without being overwhelming."
            +Gestures.BrowFrown
            +"Unfortunately, we're out of chocolate samples today,"
            +Gestures.BrowRaise
            +"But trust me, it's our best-seller for a reason! I highly recommend to buy it!"
        })
        furhat.gesture(Gestures.BigSmile)
        furhat.attend(Location(-0.7, -0.2, 0.7)) // look at other mochi
        furhat.say("You can get them from the shelf over there.")
        delay(1000)
        goto(EngageOthers)
    }
}

// 🟢 3.2.2 red bean
val RedBeanResponse = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say({
            +"Great choice! Red bean is our classic flavor—loved for generations and still a favorite today!"
            +Gestures.BrowFrown
            +"Unfortunately, we're out of samples today,"
            +Gestures.BrowRaise
            +"But trust me, if it's your first time trying mochi, I highly recommend to get the most classic flavor!"
        })
        furhat.attend(Location(-0.7, -0.2, 0.7)) // look at other mochi
        furhat.say("You can get them from the shelf over there.")
        delay(1000)
        goto(EngageOthers)
    }
}

// 🟢 3.2.3 both
val BothResponse = state(parent = Parent) {
    onEntry {
        furhat.attend(users.current)
        furhat.gesture(Gestures.BigSmile)
        furhat.say("Our chocolate flavor is perfectly balanced—rich and satisfying without being overwhelming. Red bean is our classic flavor—loved for generations and still a first choice in Japan today! You can get them from the shelf over there!")
        furhat.attend(Location(-0.7, -0.2, 0.7)) // look at other mochi
        delay(1000)
        goto(EngageOthers)
    }
}