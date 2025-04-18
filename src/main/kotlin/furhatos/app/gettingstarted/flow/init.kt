package furhatos.app.gettingstarted.flow

import furhatos.app.gettingstarted.flow.main.Idle
import furhatos.app.gettingstarted.flow.main.Greeting
import furhatos.app.gettingstarted.setting.DISTANCE_TO_ENGAGE
import furhatos.app.gettingstarted.setting.MAX_NUMBER_OF_USERS
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.AzureVoice

val Init: State = state {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)
    }
    onEntry {
        furhat.voice = AzureVoice(name = "AriaNeural")
        furhat.voice.rate = 0.9
        /** start interaction */
        when {
            // furhat.isVirtual() -> goto(Greeting) // Convenient to bypass the need for user when running Virtual Furhat
            users.hasAny() -> {
                furhat.attend(users.random)
                goto(Greeting)
            }
            else -> goto(Idle)
        }
    }

}
