package com.alexisdev.main.mapper

interface Mapper<in T> {
    fun map(source: T)
}