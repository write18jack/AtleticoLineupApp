package com.whitebeach.atleticolineupapp.ui.formation

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
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
            val lb = createRefFor(positionList[6])
            val lcb = createRefFor(positionList[7])
            val rcb = createRefFor(positionList[8])
            val rb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            createHorizontalChain(lst, rst, chainStyle = ChainStyle.Packed)
            createHorizontalChain(lm, lcm, rcm, rm, chainStyle = ChainStyle.Spread)
            createHorizontalChain(lb, lcb, rcb, rb, chainStyle = ChainStyle.Spread)

            constrain(lst) {
                top.linkTo(parent.top)
                bottom.linkTo(lcm.top)
            }
            constrain(rst) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(lcm.top, margin = 0.dp)
            }
            constrain(lm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
            }
            constrain(lcm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
            }
            constrain(rcm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
            }
            constrain(rm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
            }
            constrain(lb) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(lcb) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rb) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(gk) {
                top.linkTo(lcb.bottom, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
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
            val rm = createRefFor(positionList[4])
            val cdm = createRefFor(positionList[5])
            val lb = createRefFor(positionList[6])
            val lcb = createRefFor(positionList[7])
            val rcb = createRefFor(positionList[8])
            val rb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            createHorizontalChain(lm, lcm, rcm, rm, chainStyle = ChainStyle.Spread)
            createHorizontalChain(lb, lcb, rcb, rb, chainStyle = ChainStyle.Spread)

            constrain(st) {
                top.linkTo(parent.top)
                bottom.linkTo(lcm.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(lm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(cdm.top, margin = 0.dp)
            }
            constrain(lcm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(cdm.top, margin = 0.dp)
            }
            constrain(rcm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(cdm.top, margin = 0.dp)
            }
            constrain(rm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(cdm.top, margin = 0.dp)
            }
            constrain(cdm) {
                top.linkTo(lcm.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(lb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(lcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(gk) {
                top.linkTo(lcb.bottom, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
        }
    }
}

class F433 : ManageFormation() {
    override fun formationConstraints(): ConstraintSet {
        return ConstraintSet {
            val lw = createRefFor(positionList[0])
            val st = createRefFor(positionList[1])
            val rw = createRefFor(positionList[2])
            val lcm = createRefFor(positionList[3])
            val rcm = createRefFor(positionList[4])
            val cdm = createRefFor(positionList[5])
            val lb = createRefFor(positionList[6])
            val lcb = createRefFor(positionList[7])
            val rcb = createRefFor(positionList[8])
            val rb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            createHorizontalChain(lw, st, rw, chainStyle = ChainStyle.Spread)
            createHorizontalChain(lcm, rcm, cdm, chainStyle = ChainStyle.Spread)
            createHorizontalChain(lb, lcb, rcb, rb, chainStyle = ChainStyle.Spread)

            constrain(lw) {
                top.linkTo(parent.top)
                bottom.linkTo(lcm.top)
            }
            constrain(st) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(lcm.top, margin = 0.dp)
            }
            constrain(rw) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(lcm.top, margin = 0.dp)
            }
            constrain(lcm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
            }
            constrain(rcm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
            }
            constrain(cdm) {
                top.linkTo(st.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 0.dp)
            }
            constrain(lb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(lcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(gk) {
                top.linkTo(lcb.bottom, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
        }
    }
}

class F532 : ManageFormation() {
    override fun formationConstraints(): ConstraintSet {
        return ConstraintSet {
            val lst = createRefFor(positionList[0])
            val rst = createRefFor(positionList[1])
            val lcm = createRefFor(positionList[2])
            val rcm = createRefFor(positionList[3])
            val cdm = createRefFor(positionList[4])
            val lb = createRefFor(positionList[5])
            val lcb = createRefFor(positionList[6])
            val cb = createRefFor(positionList[7])
            val rcb = createRefFor(positionList[8])
            val rb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            createHorizontalChain(lst, rst, chainStyle = ChainStyle.Packed)
            createHorizontalChain(lcm, cdm, rcm, chainStyle = ChainStyle.Packed)
            createHorizontalChain(lb, rb, chainStyle = ChainStyle.SpreadInside)
            createHorizontalChain(lcb, cb, rcb, chainStyle = ChainStyle.Packed)

            constrain(lst) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(lcm.top, margin = 0.dp)
            }
            constrain(rst) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(rcm.top, margin = 0.dp)
            }
            constrain(lcm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(cb.top, margin = 20.dp)
            }
            constrain(rcm) {
                top.linkTo(rst.bottom, margin = 0.dp)
                bottom.linkTo(cb.top, margin = 20.dp)
            }
            constrain(cdm) {
                top.linkTo(lst.bottom, margin = 30.dp)
                bottom.linkTo(cb.top, margin = 0.dp)
            }
            constrain(lb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(cb.top, margin = 10.dp)
            }
            constrain(lcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(cb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(cb.top, margin = 10.dp)
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

class F3142 : ManageFormation() {
    override fun formationConstraints(): ConstraintSet {
        return ConstraintSet {
            val lst = createRefFor(positionList[0])
            val rst = createRefFor(positionList[1])
            val lm = createRefFor(positionList[2])
            val lcm = createRefFor(positionList[3])
            val rcm = createRefFor(positionList[4])
            val rm = createRefFor(positionList[5])
            val cdm = createRefFor(positionList[6])
            val lcb = createRefFor(positionList[7])
            val cb = createRefFor(positionList[8])
            val rcb = createRefFor(positionList[9])
            val gk = createRefFor(positionList[10])

            createHorizontalChain(lst, rst, chainStyle = ChainStyle.Packed)
            createHorizontalChain(lm, rm, chainStyle = ChainStyle.SpreadInside)
            createHorizontalChain(lcm, cdm, rcm, chainStyle = ChainStyle.Packed)
            createHorizontalChain(lcb, cb, rcb, chainStyle = ChainStyle.Spread)

            constrain(lst) {
                top.linkTo(parent.top)
                bottom.linkTo(lm.top)
            }
            constrain(rst) {
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(rm.top, margin = 0.dp)
            }
            constrain(lm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lcm.top, margin = 10.dp)
            }
            constrain(lcm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(lcb.top, margin = 10.dp)
            }
            constrain(rcm) {
                top.linkTo(rst.bottom, margin = 0.dp)
                bottom.linkTo(rcb.top, margin = 10.dp)
            }
            constrain(rm) {
                top.linkTo(rst.bottom, margin = 0.dp)
                bottom.linkTo(rcm.top, margin = 10.dp)
            }
            constrain(cdm) {
                top.linkTo(lst.bottom, margin = 0.dp)
                bottom.linkTo(cb.top, margin = 30.dp)
            }
            constrain(lcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(cb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
            }
            constrain(rcb) {
                top.linkTo(cdm.bottom, margin = 0.dp)
                bottom.linkTo(gk.top, margin = 0.dp)
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