package com.bluecaf.cquec.dto

data class QueResponseDTO(
    val code: Int = 200,
    val message: String,
    val data: Any? = null,
)
