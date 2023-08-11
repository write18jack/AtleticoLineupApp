package com.whitebeach.atleticolineupapp.util.drag

import com.whitebeach.atleticolineupapp.dragAndDrop.MimeType

class DragData (
    val type: MimeType = MimeType.TEXT_PLAIN,
    val data: Any? = null
)