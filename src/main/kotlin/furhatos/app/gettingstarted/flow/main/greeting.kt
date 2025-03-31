package furhatos.app.gettingstarted.flow.main

import furhatos.app.gettingstarted.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.gestures.Gestures

// 2
val Greeting: State = state(Parent) {
    onEntry {
        furhat.gesture(Gestures.BigSmile)
        furhat.attend(users.current)
        furhat.say {
            random {
                +"Welcome shoppers! This is your lucky day! We’re selling delicious mochi!"
                +"Don’t be shy, come closer! come try our Mochi!"
            }
        }
        furhat.gesture(Gestures.Nod)

        goto(PresentProduct)

    }

}

