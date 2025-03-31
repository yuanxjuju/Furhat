package furhatos.app.gettingstarted

import furhatos.app.gettingstarted.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class GettingstartedSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
