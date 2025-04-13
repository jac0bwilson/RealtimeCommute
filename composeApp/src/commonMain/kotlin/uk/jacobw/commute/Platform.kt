package uk.jacobw.commute

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform