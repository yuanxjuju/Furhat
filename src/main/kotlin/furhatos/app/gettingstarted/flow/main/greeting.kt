package furhatos.app.gettingstarted.flow.main

import furhatos.app.gettingstarted.flow.Parent
import furhatos.app.gettingstarted.nlu.Greet
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.flow.kotlin.voice.AzureVoice

// 2
val Greeting: State = state(parent = Parent) {
    onEntry {
        furhat.gesture(Gestures.BigSmile)
        furhat.attend(users.current)
        furhat.ask({
            +"Hi sweetie!"
            +Gestures.Wink
        }, timeout = 3000)}

        onResponse<Greet> { //2.1
            furhat.say {
                random {
                    +"Nice to see you!"
                    +"Hey hey!"
                }
                +"Welcome the lucky customer of today!"
            }
            goto(PresentProduct)
        }

        onResponse {    //2.2
            furhat.say("Welcome the lucky customer of today!")
            goto(PresentProduct)
        }

        onNoResponse {
            furhat.say("Welcome the lucky customer of today!")
            goto(PresentProduct)
        }

    }



