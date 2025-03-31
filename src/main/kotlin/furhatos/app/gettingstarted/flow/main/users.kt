package furhatos.app.gettingstarted.flow.main

import furhatos.app.gettingstarted.nlu.*
import furhatos.records.User

class MochiData (
    var items : MochiList = MochiList()
)

val User.order : MochiData
    get() = data.getOrPut(MochiData::class.qualifiedName, MochiData())