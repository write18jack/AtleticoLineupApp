package com.example.atleticolineupapp.ui.formation

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet

val positionList = mutableListOf("P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9", "P10", "P11")

open class ManageFormation {
    open fun formationConstraints(): ConstraintSet {
        return ConstraintSet {

        }
    }
}
class F442 : ManageFormation() {
    override fun formationConstraints(): ConstraintSet {
        return ConstraintSet {
            val lst = createRefFor(positionList[0])
            val rst = createRefFor(positionList[1])
            val lm = createRefFor(positionList[2])
            val lcm = createRefFor(positionList[3])
            val rcm = createRefFor(positionList[4])
            val rm = createRefFor(positionList[5])
            val lsb = createRefFor(positionList[6])
            val lcb = createRefFor(positionList[7])
            val rcb = createRefFor(positionList[8])
            val rsb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            constrain(lst) {
                top.linkTo(parent.top)
                bottom.linkTo(lcm.top)
                start.linkTo(parent.start)
                end.linkTo(rst.start)
            }
            constrain(rst) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(rcm.top, margin = 0.dp)
                start.linkTo(lst.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(lm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lsb.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(lcm.start, margin = 0.dp)
            }
            constrain(lcm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
                start.linkTo(lm.end, margin = 0.dp)
                end.linkTo(rcm.start, margin = 0.dp)
            }
            constrain(rcm) {
                top.linkTo(rst.bottom, margin = 0.dp)
                bottom.linkTo(rcb.top, margin = 0.dp)
                start.linkTo(lcm.end, margin = 0.dp)
                end.linkTo(rm.start, margin = 0.dp)
            }
            constrain(rm) {
                top.linkTo(rst.bottom, margin = 0.dp)
                bottom.linkTo(rsb.top, margin = 0.dp)
                start.linkTo(rcm.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(lsb) {
                top.linkTo(lm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(lcb.start, margin = 0.dp)
            }
            constrain(lcb) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(lsb.end, margin = 0.dp)
                end.linkTo(rcb.start, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(rcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(lcb.end, margin = 0.dp)
                end.linkTo(rsb.start, margin = 0.dp)
            }
            constrain(rsb) {
                top.linkTo(rm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(rcb.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(gk) {
                top.linkTo(lcb.bottom, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(lsb.end, margin = 0.dp)
                end.linkTo(rsb.start, margin = 0.dp)
            }
        }
    }
}
class F4141 : ManageFormation() {
    override fun formationConstraints(): ConstraintSet {
        return ConstraintSet {
            val st = createRefFor(positionList[0])
            val lm = createRefFor(positionList[1])
            val lcm = createRefFor(positionList[2])
            val rcm = createRefFor(positionList[3])
            val cdm = createRefFor(positionList[4])
            val rm = createRefFor(positionList[5])
            val lsb = createRefFor(positionList[6])
            val lcb = createRefFor(positionList[7])
            val rcb = createRefFor(positionList[8])
            val rsb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            constrain(st) {
                top.linkTo(parent.top)
                bottom.linkTo(lcm.top)
                start.linkTo(parent.start)
                end.linkTo(st.start)
            }
            constrain(lm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(lsb.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(lcm.start, margin = 0.dp)
            }
            constrain(lcm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
                start.linkTo(lm.end, margin = 0.dp)
                end.linkTo(rcm.start, margin = 0.dp)
            }
            constrain(rcm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(rcb.top, margin = 0.dp)
                start.linkTo(lcm.end, margin = 0.dp)
                end.linkTo(rm.start, margin = 0.dp)
            }
            constrain(cdm) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(rcm.top, margin = 0.dp)
                start.linkTo(st.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(rm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(rsb.top, margin = 0.dp)
                start.linkTo(rcm.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(lsb) {
                top.linkTo(lm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(lcb.start, margin = 0.dp)
            }
            constrain(lcb) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(lsb.end, margin = 0.dp)
                end.linkTo(rcb.start, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(rcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(lcb.end, margin = 0.dp)
                end.linkTo(rsb.start, margin = 0.dp)
            }
            constrain(rsb) {
                top.linkTo(rm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(rcb.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(gk) {
                top.linkTo(lcb.bottom, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(lsb.end, margin = 0.dp)
                end.linkTo(rsb.start, margin = 0.dp)
            }
        }
    }

}
class F532 : ManageFormation(){
    override fun formationConstraints(): ConstraintSet {
        return ConstraintSet {
            val lst = createRefFor(positionList[0])
            val rst = createRefFor(positionList[1])
            val lcm = createRefFor(positionList[2])
            val rcm = createRefFor(positionList[3])
            val cdm = createRefFor(positionList[4])
            val lsb = createRefFor(positionList[5])
            val lcb = createRefFor(positionList[6])
            val cb = createRefFor(positionList[7])
            val rcb = createRefFor(positionList[8])
            val rsb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            constrain(lst) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(lcm.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(rst.start, margin = 0.dp)
            }
            constrain(rst) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(rcm.top, margin = 0.dp)
                start.linkTo(lst.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(lcm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(cdm.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(rcm.start, margin = 30.dp)
            }
            constrain(rcm) {
                top.linkTo(rst.bottom, margin = 0.dp)
                bottom.linkTo(cdm.top, margin = 0.dp)
                start.linkTo(lcm.end, margin = 30.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(cdm) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(cb.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(lsb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(rsb.start, margin = 0.dp)
            }
            constrain(lcb) {
                top.linkTo(lsb.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(cb.start, margin = 0.dp)
            }
            constrain(cb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(lcb.end, margin = 0.dp)
                end.linkTo(rcb.start, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(rsb.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
                start.linkTo(cb.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(rsb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(rcb.top, margin = 0.dp)
                start.linkTo(lsb.end, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
            constrain(gk) {
                top.linkTo(cb.bottom, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
        }
    }
}