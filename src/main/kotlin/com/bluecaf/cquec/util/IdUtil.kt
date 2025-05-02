package com.bluecaf.cquec.util

import java.time.Instant
import java.util.UUID

object IdUtil {
    fun generateUUIDv7(): String {
        // 현재 시간을 밀리초 단위로 가져옵니다.
        val timestamp = Instant.now().toEpochMilli()
        // 랜덤 값을 생성합니다.
        val randomPart = UUID.randomUUID().toString().replace("-", "").take(8) // 랜덤 8자리
        // UUIDv7 형식: [타임스탬프(밀리초) + 랜덤 부분]
        return "$timestamp-$randomPart"
    }
}