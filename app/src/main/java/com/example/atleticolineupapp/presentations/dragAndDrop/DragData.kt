package com.example.atleticolineupapp.presentations.dragAndDrop

import com.example.atleticolineupapp.dragAndDrop.MimeType

class DragData (
    val type: MimeType = MimeType.TEXT_PLAIN,
    val data: Any? = null
)